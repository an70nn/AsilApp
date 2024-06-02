package com.example.asilapp.Database.Listeners;

import com.example.asilapp.Models.Measurement;

import java.util.List;

public interface OnMeasurementReadListener {
    void onMeasurementsLoaded(List<Measurement> measurements);
}
