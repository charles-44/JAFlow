package org.scem.workflow.repository;

import org.scem.workflow.entity.StepInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepInstanceRepository extends JpaRepository<StepInstance, Long> {
}
