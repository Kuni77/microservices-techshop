package sn.techshop.userservice.security.interfaces;

import reactor.core.publisher.Mono;
import sn.techshop.userservice.domain.User;

public interface JwtService {
    String generateToken(User user);
    String generateRefreshToken(User user);
    Mono<Boolean> validateToken(String token);
    Mono<String> getUserEmailFromToken(String token);
    Mono<Long> getUserIdFromToken(String token);
    boolean isTokenExpired(String token);
    long getExpirationTime();
}
