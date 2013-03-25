package org.feely.roster.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class PersonalHoliday extends AbstractHoliday {

    @ManyToOne
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalHoliday")
    private Set<WorkShift> workShift = new HashSet<WorkShift>();

	public Employee getEmployee() {
        return this.employee;
    }

	public void setEmployee(Employee employee) {
        this.employee = employee;
    }

	public Set<WorkShift> getWorkShift() {
        return this.workShift;
    }

	public void setWorkShift(Set<WorkShift> workShift) {
        this.workShift = workShift;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
