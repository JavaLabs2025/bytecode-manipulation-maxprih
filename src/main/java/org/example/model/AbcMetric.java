package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.lang.Math.sqrt;

public class AbcMetric {
    @JsonProperty("assignments")
    private long assignments = 0;

    @JsonProperty("branches")
    private long branches = 0;

    @JsonProperty("conditions")
    private long conditions = 0;

    public void add(AbcMetric other) {
        this.assignments += other.assignments;
        this.branches += other.branches;
        this.conditions += other.conditions;
    }

    public long getAssignments() {
        return assignments;
    }

    public void setAssignments(long assignments) {
        this.assignments = assignments;
    }

    public long getBranches() {
        return branches;
    }

    public void setBranches(long branches) {
        this.branches = branches;
    }

    public long getConditions() {
        return conditions;
    }

    public void setConditions(long conditions) {
        this.conditions = conditions;
    }

    public double getMetric() {
        return sqrt(assignments * assignments + branches * branches + conditions * conditions);
    }
}
