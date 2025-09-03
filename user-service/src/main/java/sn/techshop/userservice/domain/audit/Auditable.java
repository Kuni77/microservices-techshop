package sn.techshop.userservice.domain.audit;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.r2dbc.mapping.event.ReactiveAuditingEntityCallback;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(ReactiveAuditingEntityCallback.class)
public abstract class Auditable<T> {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected  T createdBy;

    @org.springframework.data.annotation.CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    protected Timestamp createdDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    protected T lastModifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date")
    protected Timestamp lastModifiedDate;
}
