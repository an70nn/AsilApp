package com.example.asilapp.Models;

import java.util.List;

public class MedicalParameter {
    private String parameterName;
    private List<Measurement> measurements;
    private boolean expandable;

    public MedicalParameter(){}

    public MedicalParameter(String parameterName, List<Measurement> measurements) {
        this.parameterName = parameterName;
        this.measurements = measurements;
        expandable = true;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    @Override
    public String toString() {
        return "VitalParameter{" +
                "parameterName='" + parameterName + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}
