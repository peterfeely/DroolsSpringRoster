package org.feely.roster.repository;

import org.feely.roster.domain.AbstractHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractHolidayRepository extends JpaSpecificationExecutor<AbstractHoliday>, JpaRepository<AbstractHoliday, Long> {
}
