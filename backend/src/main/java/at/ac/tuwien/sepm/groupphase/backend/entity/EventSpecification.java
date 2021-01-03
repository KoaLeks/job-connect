package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventSpecification implements Specification<Event> {

    private SearchCriteria criteria;

    public EventSpecification(final SearchCriteria criteria){
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return "EventSpecification{" +
            "criteria=" + criteria +
            '}';
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                    root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }


    public static final class EventSpecificationsBuilder {
        private final List<SearchCriteria> params;

        public EventSpecificationsBuilder() {
            params = new ArrayList<>();
        }

        public EventSpecificationsBuilder with(String key, String operation, Object value) {
            params.add(new SearchCriteria(key, operation, value));
            return this;
        }

        public Specification<Event> build() {
            if (params.size() == 0) {
                return null;
            }

            List<Specification> specs = params.stream()
                .map(EventSpecification::new)
                .collect(Collectors.toList());

            Specification result = specs.get(0);

            for (int i = 1; i < params.size(); i++) {
                result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
            }
            return result;
        }
    }
}

