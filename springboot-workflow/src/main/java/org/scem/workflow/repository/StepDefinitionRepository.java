package org.scem.workflow.repository;

import org.scem.workflow.entity.StepDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepDefinitionRepository extends JpaRepository<StepDefinition, Long> {
}
