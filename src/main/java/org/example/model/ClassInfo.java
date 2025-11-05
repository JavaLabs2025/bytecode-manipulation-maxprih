package org.example.model;

import java.util.HashSet;
import java.util.Set;

public class ClassInfo {
    private final String name;
    private final String superName;
    private int fieldCount = 0;
    private int overriddenMethodsCount = 0;
    private int inheritanceDepth = 0;
    private final AbcMetric abcMetric = new AbcMetric();
    private final Set<String> methodSignatures = new HashSet<>();

    public ClassInfo(String name, String superName) {
        this.name = name;
        this.superName = superName;
    }

    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getOverriddenMethodsCount() {
        return overriddenMethodsCount;
    }

    public void setOverriddenMethodsCount(int overriddenMethodsCount) {
        this.overriddenMethodsCount = overriddenMethodsCount;
    }

    public int getInheritanceDepth() {
        return inheritanceDepth;
    }

    public void setInheritanceDepth(int inheritanceDepth) {
        this.inheritanceDepth = inheritanceDepth;
    }

    public AbcMetric getAbcMetric() {
        return abcMetric;
    }

    public Set<String> getMethodSignatures() {
        return methodSignatures;
    }
}
