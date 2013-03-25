package org.feely.roster.service;

import java.util.List;
import org.feely.roster.domain.AbstractHoliday;
import org.feely.roster.repository.AbstractHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AbstractHolidayServiceImpl implements AbstractHolidayService {

	@Autowired
    AbstractHolidayRepository abstractHolidayRepository;

	public long countAllAbstractHolidays() {
        return abstractHolidayRepository.count();
    }

	public void deleteAbstractHoliday(AbstractHoliday abstractHoliday) {
        abstractHolidayRepository.delete(abstractHoliday);
    }

	public AbstractHoliday findAbstractHoliday(Long id) {
        return abstractHolidayRepository.findOne(id);
    }

	public List<AbstractHoliday> findAllAbstractHolidays() {
        return abstractHolidayRepository.findAll();
    }

	public List<AbstractHoliday> findAbstractHolidayEntries(int firstResult, int maxResults) {
        return abstractHolidayRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveAbstractHoliday(AbstractHoliday abstractHoliday) {
        abstractHolidayRepository.save(abstractHoliday);
    }

	public AbstractHoliday updateAbstractHoliday(AbstractHoliday abstractHoliday) {
        return abstractHolidayRepository.save(abstractHoliday);
    }
}
