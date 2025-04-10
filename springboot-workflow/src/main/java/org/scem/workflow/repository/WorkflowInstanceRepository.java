package org.scem.workflow.repository;

import org.scem.workflow.entity.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {
}
