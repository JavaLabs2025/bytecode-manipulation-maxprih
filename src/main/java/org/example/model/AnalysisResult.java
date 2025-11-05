package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisResult {
    @JsonProperty("max_inheritance_depth")
    private int maxInheritanceDepth;

    @JsonProperty("average_inheritance_depth")
    private double averageInheritanceDepth;

    @JsonProperty("abc_metric")
    private AbcMetric abcMetric;

    @JsonProperty("average_overridden_methods")
    private double averageOverriddenMethods;

    @JsonProperty("average_fields_per_class")
    private double averageFieldsPerClass;

    public int getMaxInheritanceDepth() {
        return maxInheritanceDepth;
    }

    public void setMaxInheritanceDepth(int maxInheritanceDepth) {
        this.maxInheritanceDepth = maxInheritanceDepth;
    }

    public double getAverageInheritanceDepth() {
        return averageInheritanceDepth;
    }

    public void setAverageInheritanceDepth(double averageInheritanceDepth) {
        this.averageInheritanceDepth = averageInheritanceDepth;
    }

    public AbcMetric getAbcMetric() {
        return abcMetric;
    }

    public void setAbcMetric(AbcMetric abcMetric) {
        this.abcMetric = abcMetric;
    }

    public double getAverageOverriddenMethods() {
        return averageOverriddenMethods;
    }

    public void setAverageOverriddenMethods(double averageOverriddenMethods) {
        this.averageOverriddenMethods = averageOverriddenMethods;
    }

    public double getAverageFieldsPerClass() {
        return averageFieldsPerClass;
    }

    public void setAverageFieldsPerClass(double averageFieldsPerClass) {
        this.averageFieldsPerClass = averageFieldsPerClass;
    }
}
