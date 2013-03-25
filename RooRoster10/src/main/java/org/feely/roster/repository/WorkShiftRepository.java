package org.feely.roster.repository;

import org.feely.roster.domain.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkShiftRepository extends JpaSpecificationExecutor<WorkShift>, JpaRepository<WorkShift, Long> {
}
