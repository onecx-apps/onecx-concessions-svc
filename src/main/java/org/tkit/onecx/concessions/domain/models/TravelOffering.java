package org.tkit.onecx.concessions.domain.models;

import jakarta.persistence.*;

import org.hibernate.annotations.TenantId;
import org.tkit.quarkus.jpa.models.TraceableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TRAVEL_OFFERING")
public class TravelOffering extends TraceableEntity {

    @TenantId
    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATE")
    private String state;

    @Column(name = "REMOTE_ID")
    private String remoteId;

    @Column(name = "ALLOWED_WAGON_CLASS")
    private String allowedWagonClass;

    @Column(name = "REQUIRED_PAYMENT")
    private Float requiredPayment;

}
