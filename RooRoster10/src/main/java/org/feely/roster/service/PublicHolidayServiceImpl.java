package org.feely.roster.service;

import java.util.List;
import org.feely.roster.domain.PublicHoliday;
import org.feely.roster.repository.PublicHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {

	@Autowired
    PublicHolidayRepository publicHolidayRepository;

	public long countAllPublicHolidays() {
        return publicHolidayRepository.count();
    }

	public void deletePublicHoliday(PublicHoliday publicHoliday) {
        publicHolidayRepository.delete(publicHoliday);
    }

	public PublicHoliday findPublicHoliday(Long id) {
        return publicHolidayRepository.findOne(id);
    }

	public List<PublicHoliday> findAllPublicHolidays() {
        return publicHolidayRepository.findAll();
    }

	public List<PublicHoliday> findPublicHolidayEntries(int firstResult, int maxResults) {
        return publicHolidayRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void savePublicHoliday(PublicHoliday publicHoliday) {
        publicHolidayRepository.save(publicHoliday);
    }

	public PublicHoliday updatePublicHoliday(PublicHoliday publicHoliday) {
        return publicHolidayRepository.save(publicHoliday);
    }
}
