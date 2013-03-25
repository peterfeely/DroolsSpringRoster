package org.feely.roster.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.feely.roster.reference.Dept;
import org.feely.roster.reference.Grade;

@Entity
public class Department {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

    @Enumerated
    private Dept dept;

    @NotNull
    @Enumerated
    private Grade requiredGrade;

    @NotNull
    private boolean maxAssignmentAbsolute;

    @NotNull
    @Min(0L)
    private Integer maxEarlyEmps;

    @NotNull
    @Min(0L)
    private Integer maxStandardEmps;

    @NotNull
    @Min(0L)
    private Integer maxLateEmps;

    @NotNull
    @Min(0L)
    private Integer minEarlyEmps;

    @NotNull
    @Min(0L)
    private Integer minStandardEmps;

    @NotNull
    @Min(0L)
    private Integer minLateEmps;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private Set<WorkShift> workShift = new HashSet<WorkShift>();

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

	public Dept getDept() {
        return this.dept;
    }

	public void setDept(Dept dept) {
        this.dept = dept;
    }

	public Grade getRequiredGrade() {
        return this.requiredGrade;
    }

	public void setRequiredGrade(Grade requiredGrade) {
        this.requiredGrade = requiredGrade;
    }

	public boolean isMaxAssignmentAbsolute() {
        return this.maxAssignmentAbsolute;
    }

	public void setMaxAssignmentAbsolute(boolean maxAssignmentAbsolute) {
        this.maxAssignmentAbsolute = maxAssignmentAbsolute;
    }

	public Integer getMaxEarlyEmps() {
        return this.maxEarlyEmps;
    }

	public void setMaxEarlyEmps(Integer maxEarlyEmps) {
        this.maxEarlyEmps = maxEarlyEmps;
    }

	public Integer getMaxStandardEmps() {
        return this.maxStandardEmps;
    }

	public void setMaxStandardEmps(Integer maxStandardEmps) {
        this.maxStandardEmps = maxStandardEmps;
    }

	public Integer getMaxLateEmps() {
        return this.maxLateEmps;
    }

	public void setMaxLateEmps(Integer maxLateEmps) {
        this.maxLateEmps = maxLateEmps;
    }

	public Integer getMinEarlyEmps() {
        return this.minEarlyEmps;
    }

	public void setMinEarlyEmps(Integer minEarlyEmps) {
        this.minEarlyEmps = minEarlyEmps;
    }

	public Integer getMinStandardEmps() {
        return this.minStandardEmps;
    }

	public void setMinStandardEmps(Integer minStandardEmps) {
        this.minStandardEmps = minStandardEmps;
    }

	public Integer getMinLateEmps() {
        return this.minLateEmps;
    }

	public void setMinLateEmps(Integer minLateEmps) {
        this.minLateEmps = minLateEmps;
    }

	public Set<WorkShift> getWorkShift() {
        return this.workShift;
    }

	public void setWorkShift(Set<WorkShift> workShift) {
        this.workShift = workShift;
    }
}
