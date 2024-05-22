package org.tkit.onecx.concessions.domain.criteria;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class TravelOfferingSearchCriteria {

    private String name;

    private String remoteId;

    private String allowedWagonClass;

    private String state;

    private Integer pageNumber;

    private Integer pageSize;

}
