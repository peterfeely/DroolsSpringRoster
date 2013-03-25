package org.feely.roster.service;

import java.util.List;

import org.feely.roster.domain.AbstractHoliday;

public interface AbstractHolidayService {

	public abstract long countAllAbstractHolidays();


	public abstract void deleteAbstractHoliday(AbstractHoliday abstractHoliday);


	public abstract AbstractHoliday findAbstractHoliday(Long id);


	public abstract List<AbstractHoliday> findAllAbstractHolidays();


	public abstract List<AbstractHoliday> findAbstractHolidayEntries(int firstResult, int maxResults);


	public abstract void saveAbstractHoliday(AbstractHoliday abstractHoliday);


	public abstract AbstractHoliday updateAbstractHoliday(AbstractHoliday abstractHoliday);

}
