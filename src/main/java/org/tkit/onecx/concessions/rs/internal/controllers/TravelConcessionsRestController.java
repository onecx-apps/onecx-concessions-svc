package org.tkit.onecx.concessions.rs.internal.controllers;

import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.tkit.onecx.concessions.domain.daos.TravelConcessionDAO;
import org.tkit.onecx.concessions.domain.daos.TravelOfferingDAO;
import org.tkit.onecx.concessions.rs.internal.mappers.ExceptionMapper;
import org.tkit.onecx.concessions.rs.internal.mappers.TravelConcessionMapper;
import org.tkit.quarkus.jpa.exceptions.ConstraintException;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.db.concessions.rs.internal.TravelConcessionsInternalApi;
import gen.org.tkit.db.concessions.rs.internal.model.CreateTravelConcessionDTO;
import gen.org.tkit.db.concessions.rs.internal.model.ProblemDetailResponseDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelConcessionSearchCriteriaDTO;
import gen.org.tkit.db.concessions.rs.internal.model.UpdateTravelConcessionDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LogService
@ApplicationScoped
@Transactional(value = NOT_SUPPORTED)
public class TravelConcessionsRestController implements TravelConcessionsInternalApi {

    @Inject
    TravelConcessionDAO dao;

    @Inject
    TravelOfferingDAO offeringDAO;

    @Inject
    TravelConcessionMapper mapper;

    @Inject
    ExceptionMapper exceptionMapper;

    @Context
    UriInfo uriInfo;

    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> exception(ConstraintException ex) {
        return exceptionMapper.exception(ex);
    }

    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> constraint(ConstraintViolationException ex) {
        return exceptionMapper.constraint(ex);
    }

    @ServerExceptionMapper
    public RestResponse<ProblemDetailResponseDTO> optimisticLockException(OptimisticLockException ex) {
        return exceptionMapper.optimisticLock(ex);
    }

    @Override
    public Response createNewTravelConcession(@Valid @NotNull CreateTravelConcessionDTO createTravelConcessionDTO) {
        var travelConcession = mapper.create(createTravelConcessionDTO);

        // Search for provided offering
        var offering = offeringDAO.findById(createTravelConcessionDTO.getOfferingId());
        if (offering == null) {
            return Response.serverError()
                    .entity("Invalid Offering Id (" + createTravelConcessionDTO.getOfferingId() + ")").build();
        }
        travelConcession.setOffering(offering);
        travelConcession = dao.create(travelConcession);
        return Response
                .created(uriInfo.getAbsolutePathBuilder().path(travelConcession.getId()).build())
                .entity(mapper.map(travelConcession))
                .build();
    }

    @Override
    public Response deleteTravelConcession(String id) {
        dao.deleteQueryById(id);
        return Response.noContent().build();
    }

    @Override
    public Response getTravelConcessionById(String id) {
        var travelConcession = dao.findById(id);
        if (travelConcession == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.map(travelConcession)).build();
    }

    @Override
    public Response updateTravelConcession(String id,
            @Valid @NotNull UpdateTravelConcessionDTO updateTravelConcessionDTO) {
        var travelConcession = dao.findById(id);
        if (travelConcession == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Search for provided offering
        var offering = offeringDAO.findById(updateTravelConcessionDTO.getOfferingId());
        if (offering == null) {
            return Response.serverError()
                    .entity("Invalid Offering Id (" + updateTravelConcessionDTO.getOfferingId() + ")").build();
        }
        travelConcession.setOffering(offering);
        mapper.update(updateTravelConcessionDTO, travelConcession);
        dao.update(travelConcession);
        return Response.noContent().build();
    }

    @Override
    public Response searchTravelConcession(
            @Valid @NotNull TravelConcessionSearchCriteriaDTO travelConcessionSearchCriteriaDTO) {
        var criteria = mapper.map(travelConcessionSearchCriteriaDTO);
        var result = dao.findTravelConcessionsByCriteria(criteria);
        return Response.ok(mapper.mapPage(result)).build();
    }

}
