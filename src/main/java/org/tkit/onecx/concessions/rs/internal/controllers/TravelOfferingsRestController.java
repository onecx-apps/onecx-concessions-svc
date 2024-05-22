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
import org.tkit.onecx.concessions.domain.daos.TravelOfferingDAO;
import org.tkit.onecx.concessions.rs.internal.mappers.ExceptionMapper;
import org.tkit.onecx.concessions.rs.internal.mappers.TravelOfferingMapper;
import org.tkit.quarkus.jpa.exceptions.ConstraintException;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.db.concessions.rs.internal.TravelOfferingsInternalApi;
import gen.org.tkit.db.concessions.rs.internal.model.CreateTravelOfferingDTO;
import gen.org.tkit.db.concessions.rs.internal.model.ProblemDetailResponseDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelOfferingSearchCriteriaDTO;
import gen.org.tkit.db.concessions.rs.internal.model.UpdateTravelOfferingDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LogService
@ApplicationScoped
@Transactional(value = NOT_SUPPORTED)
public class TravelOfferingsRestController implements TravelOfferingsInternalApi {

    @Inject
    TravelOfferingDAO dao;

    @Inject
    TravelOfferingMapper mapper;

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
    public Response createNewTravelOffering(@Valid @NotNull CreateTravelOfferingDTO createTravelOfferingDTO) {
        var travelOffering = mapper.create(createTravelOfferingDTO);
        travelOffering = dao.create(travelOffering);
        return Response
                .created(uriInfo.getAbsolutePathBuilder().path(travelOffering.getId()).build())
                .entity(mapper.map(travelOffering))
                .build();
    }

    @Override
    public Response deleteTravelOffering(String id) {
        dao.deleteQueryById(id);
        return Response.noContent().build();
    }

    @Override
    public Response getTravelOfferingById(String id) {
        var travelOffering = dao.findById(id);
        if (travelOffering == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.map(travelOffering)).build();
    }

    @Override
    public Response updateTravelOffering(String id, @Valid @NotNull UpdateTravelOfferingDTO updateTravelOfferingDTO) {
        var travelOffering = dao.findById(id);
        if (travelOffering == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        mapper.update(updateTravelOfferingDTO, travelOffering);
        dao.update(travelOffering);
        return Response.noContent().build();
    }

    @Override
    public Response searchTravelOfferings(
            @Valid @NotNull TravelOfferingSearchCriteriaDTO travelOfferingSearchCriteriaDTO) {
        var criteria = mapper.map(travelOfferingSearchCriteriaDTO);
        var result = dao.findTravelOfferingsByCriteria(criteria);
        return Response.ok(mapper.mapPage(result)).build();
    }

    @Override
    public Response getAllTravelOfferings() {
        var travelOffering = dao.findAll();
        if (travelOffering == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.map(travelOffering)).build();
    }

}
