package org.scem.workflow.repository;

import org.scem.workflow.entity.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition, Long> {
}
