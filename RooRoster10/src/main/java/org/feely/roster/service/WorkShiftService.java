package org.feely.roster.service;

import java.util.List;

import org.feely.roster.domain.WorkShift;

public interface WorkShiftService {

	public abstract long countAllWorkShifts();


	public abstract void deleteWorkShift(WorkShift workShift);


	public abstract WorkShift findWorkShift(Long id);


	public abstract List<WorkShift> findAllWorkShifts();


	public abstract List<WorkShift> findWorkShiftEntries(int firstResult, int maxResults);


	public abstract void saveWorkShift(WorkShift workShift);


	public abstract WorkShift updateWorkShift(WorkShift workShift);

}
