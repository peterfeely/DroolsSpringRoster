package org.feely.roster.repository;

import org.feely.roster.domain.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long>, JpaSpecificationExecutor<PublicHoliday> {
}
