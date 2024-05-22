package org.tkit.onecx.concessions.rs.internal.log;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.quarkus.log.cdi.LogParam;

import gen.org.tkit.db.concessions.rs.internal.model.CreateTravelOfferingDTO;
import gen.org.tkit.db.concessions.rs.internal.model.TravelOfferingSearchCriteriaDTO;
import gen.org.tkit.db.concessions.rs.internal.model.UpdateTravelOfferingDTO;

@ApplicationScoped
public class InternalLogParam implements LogParam {

    @Override
    public List<Item> getClasses() {
        return List.of(
                item(10, CreateTravelOfferingDTO.class, x -> {
                    CreateTravelOfferingDTO d = (CreateTravelOfferingDTO) x;
                    return CreateTravelOfferingDTO.class.getSimpleName() + "[" + d.getName() + "," + d.getRemoteId()
                            + "]";
                }),
                item(10, UpdateTravelOfferingDTO.class, x -> {
                    UpdateTravelOfferingDTO d = (UpdateTravelOfferingDTO) x;
                    return UpdateTravelOfferingDTO.class.getSimpleName() + "[" + d.getName() + "," + d.getRemoteId()
                            + "]";
                }),
                item(10, TravelOfferingSearchCriteriaDTO.class, x -> {
                    TravelOfferingSearchCriteriaDTO d = (TravelOfferingSearchCriteriaDTO) x;
                    return TravelOfferingSearchCriteriaDTO.class.getSimpleName() + "[" + d.getPageNumber() + ","
                            + d.getPageSize()
                            + "]";
                }));
    }
}
