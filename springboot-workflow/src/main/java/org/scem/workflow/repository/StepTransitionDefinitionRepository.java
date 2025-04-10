package org.scem.workflow.repository;


import org.scem.workflow.entity.StepTransitionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepTransitionDefinitionRepository extends JpaRepository<StepTransitionDefinition, Long> {
}
