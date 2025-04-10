package org.scem.workflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wf_inc")
public class WorkflowInstance extends  BaseEntity {


    @ManyToOne
    @JoinColumn(name = "workflow_definition_id")
    private WorkflowDefinition workflowDefinition;

}
