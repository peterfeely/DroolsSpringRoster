package org.feely.roster.drools.domain.solver;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.feely.roster.domain.Employee;

public class EmployeeDifficultyComparator implements Comparator<Employee>, Serializable {

    public int compare(Employee a, Employee b) {
        return new CompareToBuilder()
                .append(a.getPreferenceProblemWeight(), b.getPreferenceProblemWeight())
                .append(a.getLabel(), b.getLabel())
                .toComparison();
    }

}