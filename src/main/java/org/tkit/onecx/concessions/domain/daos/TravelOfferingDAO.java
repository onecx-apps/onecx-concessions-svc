package org.tkit.onecx.concessions.domain.daos;

import static org.tkit.quarkus.jpa.utils.QueryCriteriaUtil.addSearchStringPredicate;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

import org.tkit.onecx.concessions.domain.criteria.TravelOfferingSearchCriteria;
import org.tkit.onecx.concessions.domain.models.TravelOffering;
import org.tkit.onecx.concessions.domain.models.TravelOffering_;
import org.tkit.quarkus.jpa.daos.AbstractDAO;
import org.tkit.quarkus.jpa.daos.Page;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.jpa.exceptions.DAOException;
import org.tkit.quarkus.jpa.models.TraceableEntity_;

@ApplicationScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class TravelOfferingDAO extends AbstractDAO<TravelOffering> {

    // https://hibernate.atlassian.net/browse/HHH-16830#icft=HHH-16830
    @Override
    public TravelOffering findById(Object id) throws DAOException {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(TravelOffering.class);
            var root = cq.from(TravelOffering.class);
            cq.where(cb.equal(root.get(TraceableEntity_.ID), id));
            return this.getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new DAOException(ErrorKeys.FIND_ENTITY_BY_ID_FAILED, e, entityName, id);
        }
    }

    public PageResult<TravelOffering> findTravelOfferingsByCriteria(TravelOfferingSearchCriteria criteria) {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(TravelOffering.class);
            var root = cq.from(TravelOffering.class);

            List<Predicate> predicates = new ArrayList<>();
            addSearchStringPredicate(predicates, cb, root.get(TravelOffering_.remoteId), criteria.getRemoteId());
            addSearchStringPredicate(predicates, cb, root.get(TravelOffering_.name), criteria.getName());
            addSearchStringPredicate(predicates, cb, root.get(TravelOffering_.allowedWagonClass),
                    criteria.getAllowedWagonClass());
            addSearchStringPredicate(predicates, cb, root.get(TravelOffering_.state),
                    criteria.getState());

            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(new Predicate[] {}));
            }
            return createPageQuery(cq, Page.of(criteria.getPageNumber(), criteria.getPageSize())).getPageResult();
        } catch (Exception ex) {
            throw new DAOException(ErrorKeys.ERROR_GET_BY_APP_ID_AND_ITEM_ID, ex);
        }
    }

    public List<TravelOffering> getAllTravelOfferingsByName(String name) {
        try {
            var cb = this.getEntityManager().getCriteriaBuilder();
            var cq = cb.createQuery(TravelOffering.class);
            var root = cq.from(TravelOffering.class);

            List<Predicate> predicates = new ArrayList<>();
            addSearchStringPredicate(predicates, cb, root.get(TravelOffering_.name), name);

            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(new Predicate[] {}));
            }

            return this.getEntityManager().createQuery(cq).getResultList();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new DAOException(ErrorKeys.FIND_ENTITY_BY_ID_FAILED, e, entityName);
        }
    }

    public enum ErrorKeys {

        FIND_ENTITY_BY_ID_FAILED,
        ERROR_GET_BY_APP_ID_AND_ITEM_ID,
        ERROR_FIND_APPLICATIONS_WITH_HELP_ITEMS
    }
}
