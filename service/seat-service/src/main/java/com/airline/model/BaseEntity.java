package com.airline.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * NEW: Common base class so every entity gets the same id, audit columns and
 * optimistic-lock version. Removes copy-paste and keeps behaviour consistent.
 *
 * Requires: @EnableJpaAuditing on a config class, and an AuditorAware<String>
 * bean if you want createdBy/updatedBy filled (else they stay null).
 */

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Version
    private Long version;

    @Override
    public final boolean equals(Object e){
        if (this == e){
            return true;
        }
        if (e == null || Hibernate.getClass(this) != Hibernate.getClass(e)){
             return false;
        }

        BaseEntity that = (BaseEntity) e;
        return id != null && id.equals(that.id);
    }

    @Override
    public final int hashCode(){
        return Hibernate.getClass(this).hashCode();
    }
}
