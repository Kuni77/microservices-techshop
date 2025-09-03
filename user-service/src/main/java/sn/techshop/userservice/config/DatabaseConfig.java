package sn.techshop.userservice.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import sn.techshop.userservice.domain.audit.AuditableImpl;

@Configuration
@EnableR2dbcRepositories(basePackages = "sn.techshop.userservice.repository")
@EnableR2dbcAuditing(auditorAwareRef = "auditableimpl")
public class DatabaseConfig extends AbstractR2dbcConfiguration {
    @Override
    @NonNull
    public ConnectionFactory connectionFactory() {
        return super.connectionFactory();
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public AuditorAware<Integer> auditableImpl() {
        return new AuditableImpl();
    }
}
