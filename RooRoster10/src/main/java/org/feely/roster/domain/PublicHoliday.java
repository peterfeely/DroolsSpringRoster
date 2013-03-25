package org.feely.roster.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class PublicHoliday extends AbstractHoliday {

    @Size(min = 3, max = 30)
    private String HolidayPeriod;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicHoliday")
    private Set<WorkShift> workShift = new HashSet<WorkShift>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<Employee>();

	public String getHolidayPeriod() {
        return this.HolidayPeriod;
    }

	public void setHolidayPeriod(String HolidayPeriod) {
        this.HolidayPeriod = HolidayPeriod;
    }

	public Set<WorkShift> getWorkShift() {
        return this.workShift;
    }

	public void setWorkShift(Set<WorkShift> workShift) {
        this.workShift = workShift;
    }

	public Set<Employee> getEmployees() {
        return this.employees;
    }

	public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
