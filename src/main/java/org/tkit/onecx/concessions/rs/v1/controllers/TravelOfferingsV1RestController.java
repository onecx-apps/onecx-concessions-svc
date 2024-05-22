package org.tkit.onecx.concessions.rs.v1.controllers;

import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.concessions.domain.daos.TravelOfferingDAO;
import org.tkit.onecx.concessions.rs.v1.mappers.ExternalV1Mapper;
import org.tkit.quarkus.log.cdi.LogService;

import gen.org.tkit.onecx.concessions.rs.v1.TravelOfferingsV1Api;

@LogService
@ApplicationScoped
@Transactional(value = NOT_SUPPORTED)
public class TravelOfferingsV1RestController implements TravelOfferingsV1Api {
    @Inject
    TravelOfferingDAO dao;

    @Inject
    ExternalV1Mapper mapper;

    @Override
    public Response searchTravelOfferingsItem(String offeringId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchTravelOfferingsItem'");
    }

}
