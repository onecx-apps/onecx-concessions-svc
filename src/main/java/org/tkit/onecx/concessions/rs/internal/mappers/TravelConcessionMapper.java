package org.tkit.onecx.concessions.rs.internal.mappers;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.tkit.onecx.concessions.domain.criteria.TravelConcessionSearchCriteria;
import org.tkit.onecx.concessions.domain.models.TravelConcession;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import gen.org.tkit.db.concessions.rs.internal.model.CreateTravelConcessionDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelConcessionDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelConcessionPageResultDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelConcessionSearchCriteriaDTO;
import gen.org.tkit.db.concessions.rs.internal.model.UpdateTravelConcessionDTO;

@Mapper(uses = { OffsetDateTimeMapper.class })
public interface TravelConcessionMapper {

    TravelConcessionSearchCriteria map(TravelConcessionSearchCriteriaDTO dto);

    @Mapping(target = "removeStreamItem", ignore = true)
    TravelConcessionPageResultDTO mapPage(PageResult<TravelConcession> page);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "offering", ignore = true)
    TravelConcession create(CreateTravelConcessionDTO object);

    List<TravelConcessionDTO> map(Stream<TravelConcession> entity);

    TravelConcessionDTO map(TravelConcession travelConcession);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "offering", ignore = true)
    void update(UpdateTravelConcessionDTO travelConcessionDTO, @MappingTarget TravelConcession entity);

}
