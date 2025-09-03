package sn.techshop.userservice.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.techshop.userservice.domain.User;
import sn.techshop.userservice.service.criteria.UserCriteria;


public interface UserRepositoryCustom {
    Flux<User> findWithCriteria(UserCriteria criteria);
    Flux<User> findWithCriteria(UserCriteria criteria, Pageable pageable);
    Mono<Long> countWithCriteria(UserCriteria criteria);
}
