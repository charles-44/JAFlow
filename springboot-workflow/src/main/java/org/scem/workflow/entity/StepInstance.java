package org.scem.workflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "step_inc")
public class StepInstance extends  BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "step_def_id")
    private StepDefinition stepDefinition;

}
