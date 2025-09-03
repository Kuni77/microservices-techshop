package sn.techshop.userservice.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.techshop.userservice.domain.User;
import sn.techshop.userservice.repository.UserRepositoryCustom;
import sn.techshop.userservice.service.criteria.UserCriteria;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final R2dbcEntityTemplate template;

    @Override
    public Flux<User> findWithCriteria(UserCriteria criteria) {

        Criteria c = buildCriteria(criteria);

        return template.select(User.class)
                .matching(Query.query(c))
                .all();
    }

    @Override
    public Flux<User> findWithCriteria(UserCriteria criteria, Pageable pageable) {
        Criteria c = buildCriteria(criteria);

        return template.select(User.class)
                .matching(Query.query(c).with(pageable))
                .all();
    }

    @Override
    public Mono<Long> countWithCriteria(UserCriteria criteria) {
        Criteria c = buildCriteria(criteria);

        return template.count(Query.query(c), User.class);
    }

    private Criteria buildCriteria(UserCriteria criteria) {
        Criteria c = Criteria.empty();

        if (criteria.getEmail() != null) {
            c = c.and("email").like("%" + criteria.getEmail().toLowerCase() + "%");
        }
        if (criteria.getFirstName() != null) {
            c = c.and("first_name").like("%" + criteria.getFirstName().toLowerCase() + "%");
        }
        if (criteria.getLastName() != null) {
            c = c.and("last_name").like("%" + criteria.getLastName().toLowerCase() + "%");
        }
        if (criteria.getRole() != null) {
            c = c.and("role").is(criteria.getRole());
        }
        if (criteria.getEnabled() != null) {
            c = c.and("enabled").is(criteria.getEnabled());
        }

        return c;
    }
}
