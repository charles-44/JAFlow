package org.scem.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.scem.workflow.enumeration.StepType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_step_def")
public class StepDefinition extends BasePermissionDefinition{

    @ManyToOne(optional = false)
    @JoinColumn(name = "workflow_definition_id")
    private WorkflowDefinition workflowDefinition;

    @Enumerated(EnumType.STRING)
    private StepType type;

    @Column(name = "start")
    private boolean start = false;

    @Column(name = "duration_days")
    private int durationDays;

    // Action → Etape suivante
    @OneToMany
    @JoinTable(
            name = "step_transitions",
            joinColumns = @JoinColumn(name = "source_step_id"),
            inverseJoinColumns = @JoinColumn(name = "target_step_id")
    )
    @MapKeyColumn(name = "action")
    private Map<String, StepDefinition> transitions = new HashMap<>();

}
