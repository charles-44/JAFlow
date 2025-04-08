package org.scem.workflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class BaseDefinitionEntity extends BaseEntity{

    @Column(length=128, nullable=false, unique = true)
    protected String name;

    @Column(length=1024, nullable=true, unique = false)
    protected String description;
}
