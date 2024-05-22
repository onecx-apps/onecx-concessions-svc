package org.tkit.onecx.concessions.rs.internal.mappers;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.tkit.onecx.concessions.domain.criteria.TravelOfferingSearchCriteria;
import org.tkit.onecx.concessions.domain.models.TravelOffering;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import gen.org.tkit.db.concessions.rs.internal.model.CreateTravelOfferingDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelOfferingDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelOfferingPageResultDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelOfferingSearchCriteriaDTO;
import gen.org.tkit.db.concessions.rs.internal.model.UpdateTravelOfferingDTO;

@Mapper(uses = { OffsetDateTimeMapper.class })
public interface TravelOfferingMapper {

    TravelOfferingSearchCriteria map(TravelOfferingSearchCriteriaDTO dto);

    @Mapping(target = "removeStreamItem", ignore = true)
    TravelOfferingPageResultDTO mapPage(PageResult<TravelOffering> page);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    TravelOffering create(CreateTravelOfferingDTO object);

    List<TravelOfferingDTO> map(Stream<TravelOffering> entity);

    TravelOfferingDTO map(TravelOffering travelOffering);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    void update(UpdateTravelOfferingDTO travelOfferingDTO, @MappingTarget TravelOffering entity);

}
