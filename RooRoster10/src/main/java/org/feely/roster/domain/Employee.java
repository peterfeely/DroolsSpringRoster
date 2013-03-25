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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.api.domain.entity.PlanningEntity;
import org.drools.planner.api.domain.variable.PlanningVariable;
import org.drools.planner.api.domain.variable.ValueRange;
import org.drools.planner.api.domain.variable.ValueRangeType;
import org.feely.roster.drools.domain.solver.EmployeeDifficultyComparator;
import org.feely.roster.drools.domain.solver.RosterStrengthComparator;
import org.feely.roster.reference.Grade;
import org.feely.roster.reference.Preference;
import org.feely.roster.reference.ShiftPattern;
import org.springframework.stereotype.Component;

@PlanningEntity(difficultyComparatorClass = EmployeeDifficultyComparator.class)
@Entity
@Component
public class Employee {
//	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	@Version
    @Column(name = "version")
    private Integer version;	

    @Size(min = 3, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    private String lastName;

    @Size(min = 6, max = 30)
    private String email;

    @NotNull
    @Enumerated
    private Grade grade;
    @NotNull
    @Enumerated
    private Preference preference;

    @Enumerated
    private ShiftPattern shiftPattern;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;
    
    @Transient
   	private WorkShift workshift;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<PersonalHoliday> personalHoliday = new HashSet<PersonalHoliday>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Set<PublicHoliday> publicHolidays = new HashSet<PublicHoliday>();

    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER, mappedBy = "employees")
    private Set<Shift> shift = new HashSet<Shift>();
    
    public Employee(){
    	
    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public Grade getGrade() {
        return this.grade;
    }

	public void setGrade(Grade grade) {
        this.grade = grade;
    }

	public Preference getPreference() {
        return this.preference;
    }

	public void setPreference(Preference preference) {
        this.preference = preference;
    }

	public ShiftPattern getShiftPattern() {
        return this.shiftPattern;
    }

	public void setShiftPattern(ShiftPattern shiftPattern) {
        this.shiftPattern = shiftPattern;
    }

	public String getUsername() {
        return this.username;
    }

	public void setUsername(String username) {
        this.username = username;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }
	
	@PlanningVariable(strengthComparatorClass = RosterStrengthComparator.class)
    @ValueRange(type = ValueRangeType.FROM_SOLUTION_PROPERTY, solutionProperty = "workShiftList")
	public WorkShift getWorkshift() {
		return workshift;
	}

	public void setWorkshift(WorkShift workshift) {
		this.workshift = workshift;
	}

	public Set<PersonalHoliday> getPersonalHoliday() {
        return this.personalHoliday;
    }

	public void setPersonalHoliday(Set<PersonalHoliday> personalHoliday) {
        this.personalHoliday = personalHoliday;
    }

	public Set<PublicHoliday> getPublicHolidays() {
        return this.publicHolidays;
    }

	public void setPublicHolidays(Set<PublicHoliday> publicHolidays) {
        this.publicHolidays = publicHolidays;
    }

	public Set<Shift> getShift() {
        return this.shift;
    }

	public void setShift(Set<Shift> shift) {
        this.shift = shift;
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
//methods
	public Employee clone() {
		Employee clone = new Employee();
		clone.id = id;
		clone.grade = grade;
		clone.password = password;
		clone.personalHoliday = personalHoliday;
		clone.preference = preference;
		clone.shift = shift;
		clone.shiftPattern = shiftPattern;
		clone.workshift = workshift;
		clone.email = email;
		clone.firstName = firstName;
		clone.lastName = lastName;
		clone.username = username;
		return clone;
	}

	public boolean solutionEquals(Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof Employee) {
			Employee other = (Employee) o;
			return new EqualsBuilder().append(id, other.id)
					.append(workshift, other.workshift).isEquals();
		} else {
			return false;
		}
	}

	public int solutionHashCode() {
		return new HashCodeBuilder().append(id).append(workshift).toHashCode();
	}
	
	public String getLabel() {
		return " Employee " + id + getFirstName()+" "+getLastName();
	}
	
	public int getPreferenceProblemWeight(){
		return 100;
	}

	@Override
	public String toString() {
		return getLabel() + "->" + workshift;
//		
	}

}




