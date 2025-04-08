package org.scem.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wf_def")
public class WorkflowDefinition extends  BasePermissionDefinition{

    @OneToMany(mappedBy = "workflowDefinition", cascade = CascadeType.ALL)
    private List<StepDefinition> steps = new ArrayList<>();

    @OneToMany(mappedBy = "workflowDefinition", cascade = CascadeType.ALL)
    private List<WorkflowInstance> instances = new ArrayList<>();
}
