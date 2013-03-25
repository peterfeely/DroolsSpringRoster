package org.feely.roster.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Shift {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date shiftDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private WorkShift workShift;

    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Employee> employees = new HashSet<Employee>();

	public Date getShiftDate() {
        return this.shiftDate;
    }

	public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }

	public WorkShift getWorkShift() {
        return this.workShift;
    }

	public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
    }

	public Set<Employee> getEmployees() {
        return this.employees;
    }

	public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }


	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
