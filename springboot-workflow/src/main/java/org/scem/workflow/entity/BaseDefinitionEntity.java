package org.scem.workflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class BaseDefinitionEntity extends BaseEntity{

    @Column(length=128, nullable=false, unique = true)
    protected String name;

    @Column(length=1024, nullable=true, unique = false)
    protected String description;
}
