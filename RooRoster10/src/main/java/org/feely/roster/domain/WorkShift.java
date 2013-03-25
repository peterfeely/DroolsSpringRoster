package org.feely.roster.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.feely.roster.reference.ShiftType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Component
public class WorkShift {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;
	
	private String name;

	@NotNull
	@Enumerated
	private ShiftType shiftType;

	@ManyToOne(cascade = CascadeType.ALL)
	private Department department;

	@ManyToOne
	private PersonalHoliday personalHoliday;

	@ManyToOne
	private PublicHoliday publicHoliday;

	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER, mappedBy = "workShift")
	private Set<Shift> shift = new HashSet<Shift>();
	
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
	
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ShiftType getShiftType() {
		return this.shiftType;
	}

	public void setShiftType(ShiftType shiftType) {
		this.shiftType = shiftType;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public PersonalHoliday getPersonalHoliday() {
		return this.personalHoliday;
	}

	public void setPersonalHoliday(PersonalHoliday personalHoliday) {
		this.personalHoliday = personalHoliday;
	}

	public PublicHoliday getPublicHoliday() {
		return this.publicHoliday;
	}

	public void setPublicHoliday(PublicHoliday publicHoliday) {
		this.publicHoliday = publicHoliday;
	}

	public Set<Shift> getShift() {
		return this.shift;
	}

	public void setShift(Set<Shift> shift) {
		this.shift = shift;
	}

	
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}