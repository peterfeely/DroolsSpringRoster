package org.feely.roster.service;

import java.util.List;
import org.feely.roster.domain.PersonalHoliday;
import org.feely.roster.repository.PersonalHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PersonalHolidayServiceImpl implements PersonalHolidayService {

	@Autowired
    PersonalHolidayRepository personalHolidayRepository;

	public long countAllPersonalHolidays() {
        return personalHolidayRepository.count();
    }

	public void deletePersonalHoliday(PersonalHoliday personalHoliday) {
        personalHolidayRepository.delete(personalHoliday);
    }

	public PersonalHoliday findPersonalHoliday(Long id) {
        return personalHolidayRepository.findOne(id);
    }

	public List<PersonalHoliday> findAllPersonalHolidays() {
        return personalHolidayRepository.findAll();
    }

	public List<PersonalHoliday> findPersonalHolidayEntries(int firstResult, int maxResults) {
        return personalHolidayRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void savePersonalHoliday(PersonalHoliday personalHoliday) {
        personalHolidayRepository.save(personalHoliday);
    }

	public PersonalHoliday updatePersonalHoliday(PersonalHoliday personalHoliday) {
        return personalHolidayRepository.save(personalHoliday);
    }
}
