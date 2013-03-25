package org.feely.roster.service;

import java.util.List;

import org.feely.roster.domain.PersonalHoliday;

public interface PersonalHolidayService {

	public abstract long countAllPersonalHolidays();


	public abstract void deletePersonalHoliday(PersonalHoliday personalHoliday);


	public abstract PersonalHoliday findPersonalHoliday(Long id);


	public abstract List<PersonalHoliday> findAllPersonalHolidays();


	public abstract List<PersonalHoliday> findPersonalHolidayEntries(int firstResult, int maxResults);


	public abstract void savePersonalHoliday(PersonalHoliday personalHoliday);


	public abstract PersonalHoliday updatePersonalHoliday(PersonalHoliday personalHoliday);

}
