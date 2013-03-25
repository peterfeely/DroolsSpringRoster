package org.feely.roster.repository;

import org.feely.roster.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaSpecificationExecutor<Shift>, JpaRepository<Shift, Long> {
}
