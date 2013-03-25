package org.feely.roster.service;

import java.util.List;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.repository.WorkShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class WorkShiftServiceImpl implements WorkShiftService {

	@Autowired
    WorkShiftRepository workShiftRepository;

	public long countAllWorkShifts() {
        return workShiftRepository.count();
    }

	public void deleteWorkShift(WorkShift workShift) {
        workShiftRepository.delete(workShift);
    }

	public WorkShift findWorkShift(Long id) {
        return workShiftRepository.findOne(id);
    }

	public List<WorkShift> findAllWorkShifts() {
        return workShiftRepository.findAll();
    }

	public List<WorkShift> findWorkShiftEntries(int firstResult, int maxResults) {
        return workShiftRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveWorkShift(WorkShift workShift) {
        workShiftRepository.save(workShift);
    }

	public WorkShift updateWorkShift(WorkShift workShift) {
        return workShiftRepository.save(workShift);
    }
}
