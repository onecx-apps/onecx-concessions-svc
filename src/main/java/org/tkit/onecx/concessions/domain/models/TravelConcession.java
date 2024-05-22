package org.tkit.onecx.concessions.domain.models;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TRAVEL_CONCESSION")
public class TravelConcession extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PRINCIPAL_ROLE")
    private String principalRole;

    @Column(name = "CUSTOMER_RELATION_TO_PRINCIPAL")
    private String customerRelationToPrincipal;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "traveloffering_guid")
    private TravelOffering offering;

}
