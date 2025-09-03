package sn.techshop.userservice.domain.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditableImpl implements AuditorAware<Integer> {
    @Override
    @NonNull
    public Optional<Integer> getCurrentAuditor() {
        return Optional.empty();
    }
}
