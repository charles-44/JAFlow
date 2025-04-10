package org.scem.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BasePermissionDefinition extends  BaseDefinitionEntity{
    @ElementCollection
    @CollectionTable(name = "user_step_map_logins", joinColumns = @JoinColumn(name = "user_step_id"))
    @Column(name = "login")
    protected List<String> logins = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_step_map_groups", joinColumns = @JoinColumn(name = "user_step_id"))
    @Column(name = "group")
    protected List<String> groups = new ArrayList<>();
}
