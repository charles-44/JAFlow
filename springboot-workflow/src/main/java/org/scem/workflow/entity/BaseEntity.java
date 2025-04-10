package org.scem.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdDate;

    @Column(updatable = false)
    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime updatedDate;

    @LastModifiedBy
    protected String updatedBy;
}
