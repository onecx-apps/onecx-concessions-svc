package org.tkit.onecx.concessions.domain.criteria;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class TravelConcessionSearchCriteria {

    private String principalRole;

    private String customerRelationToPrincipal;

    private Integer pageNumber;

    private Integer pageSize;

    private String offeringName;

}
