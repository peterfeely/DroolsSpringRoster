package org.feely.roster.repository;

import org.feely.roster.domain.PersonalHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalHolidayRepository extends JpaSpecificationExecutor<PersonalHoliday>, JpaRepository<PersonalHoliday, Long> {
}
