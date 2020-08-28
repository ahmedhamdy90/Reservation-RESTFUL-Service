package cs544.project.repository.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * <h1>Maharishi University of Management<br/>
 * Computer Science Department</h1>
 * 
 * <p>
 * Reacts to a {@link SearchCriteria} and adds it to the
 * {@link CriteriaBuilder}.
 * </p>
 *
 * @author Payman Salek
 * 
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class EntitySpecification<T> implements Specification<T> {

    private static final long serialVersionUID = 8457994581703917514L;

    private SearchCriteria criteria;

    public EntitySpecification(SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder
                    .greaterThanOrEqualTo(root.<String>get(criteria.getKey()),
                                          criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder
                    .lessThanOrEqualTo(root.<String>get(criteria.getKey()),
                                       criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder
                        .like(root.<String>get(criteria.getKey()),
                              "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()),
                                             criteria.getValue());
            }
        }
        return null;
    }

}
