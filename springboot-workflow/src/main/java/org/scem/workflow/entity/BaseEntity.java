package org.scem.workflow.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;


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
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime updatedDate;

    protected String updatedBy;
}
