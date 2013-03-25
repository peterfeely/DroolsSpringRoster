package org.feely.roster.drools.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;

public class AbstractPersistable implements Serializable, Comparable<AbstractPersistable> {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int compareTo(AbstractPersistable other) {
        return new CompareToBuilder()
                .append(getClass().getName(), other.getClass().getName())
                .append(id, other.id)
                .toComparison();
    }

    public String toString() {
        return "[" + getClass().getName().replaceAll(".*\\.", "") + "-" + id + "]";
    }

}
