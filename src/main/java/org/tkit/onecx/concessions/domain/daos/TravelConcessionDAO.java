package org.tkit.onecx.concessions.domain.daos;

import static org.tkit.quarkus.jpa.utils.QueryCriteriaUtil.addSearchStringPredicate;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

import org.tkit.onecx.concessions.domain.criteria.TravelConcessionSearchCriteria;
import org.tkit.onecx.concessions.domain.models.TravelConcession;
import org.tkit.onecx.concessions.domain.models.TravelConcession_;
import org.tkit.onecx.concessions.domain.models.TravelOffering;
import org.tkit.quarkus.jpa.daos.AbstractDAO;
import org.tkit.quarkus.jpa.daos.Page;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.jpa.exceptions.DAOException;
import org.tkit.quarkus.jpa.models.TraceableEntity_;

@ApplicationScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class TravelConcessionDAO extends AbstractDAO<TravelConcession> {

    @Inject
    TravelOfferingDAO travelOfferingDAO;

    // https://hibernate.atlassian.net/browse/HHH-16830#icft=HHH-16830
    @Override
    public TravelConcession findById(Object id) throws DAOException {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(TravelConcession.class);
            var root = cq.from(TravelConcession.class);
            cq.where(cb.equal(root.get(TraceableEntity_.ID), id));
            return this.getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new DAOException(ErrorKeys.FIND_ENTITY_BY_ID_FAILED, e, entityName, id);
        }
    }

    public PageResult<TravelConcession> findTravelConcessionsByCriteria(TravelConcessionSearchCriteria criteria) {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(TravelConcession.class);
            var root = cq.from(TravelConcession.class);

            List<Predicate> predicates = new ArrayList<>();
            addSearchStringPredicate(predicates, cb, root.get(TravelConcession_.principalRole),
                    criteria.getPrincipalRole());
            addSearchStringPredicate(predicates, cb, root.get(TravelConcession_.customerRelationToPrincipal),
                    criteria.getCustomerRelationToPrincipal());

            // Allow search by offering name
            if (criteria.getOfferingName() != null) {
                List<TravelOffering> offerings = travelOfferingDAO
                        .getAllTravelOfferingsByName(criteria.getOfferingName());
                In<TravelOffering> in = cb.in(root.get(TravelConcession_.offering));
                offerings.forEach(o -> in.value(o));
                predicates.add(in);
            }

            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(new Predicate[] {}));
            }
            return createPageQuery(cq, Page.of(criteria.getPageNumber(), criteria.getPageSize())).getPageResult();
        } catch (Exception ex) {
            throw new DAOException(ErrorKeys.ERROR_GET_BY_APP_ID_AND_ITEM_ID, ex);
        }
    }

    public enum ErrorKeys {
        FIND_ENTITY_BY_ID_FAILED,
        ERROR_GET_BY_APP_ID_AND_ITEM_ID,
        ERROR_FIND_APPLICATIONS_WITH_HELP_ITEMS
    }
}
