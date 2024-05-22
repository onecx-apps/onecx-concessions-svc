package org.tkit.onecx.concessions.rs.v1.mappers;

import org.mapstruct.Mapper;
import org.tkit.onecx.concessions.domain.models.TravelOffering;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import gen.org.tkit.onecx.concessions.rs.v1.model.TravelOfferingDTOV1;

@Mapper(uses = { OffsetDateTimeMapper.class })
public interface ExternalV1Mapper {

    TravelOfferingDTOV1 map(TravelOffering data);
}
