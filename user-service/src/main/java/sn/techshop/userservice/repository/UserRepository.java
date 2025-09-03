package sn.techshop.userservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import sn.techshop.userservice.domain.User;

public interface UserRepository extends R2dbcRepository<User, String>, UserRepositoryCustom {
    Mono<User> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);
}
