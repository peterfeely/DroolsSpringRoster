package org.feely.roster.service;

import java.util.List;

import org.feely.roster.domain.PublicHoliday;

public interface PublicHolidayService {

	public abstract long countAllPublicHolidays();


	public abstract void deletePublicHoliday(PublicHoliday publicHoliday);


	public abstract PublicHoliday findPublicHoliday(Long id);


	public abstract List<PublicHoliday> findAllPublicHolidays();


	public abstract List<PublicHoliday> findPublicHolidayEntries(int firstResult, int maxResults);


	public abstract void savePublicHoliday(PublicHoliday publicHoliday);


	public abstract PublicHoliday updatePublicHoliday(PublicHoliday publicHoliday);

}
