package org.scem.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "step_transitions")
@Data
public class StepTransitionDefinition extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_step_id")
    private StepDefinition fromStep;

    @ManyToOne(optional = false)
    @JoinColumn(name = "target_step_id")
    private StepDefinition toStep;

    private String action;
}
