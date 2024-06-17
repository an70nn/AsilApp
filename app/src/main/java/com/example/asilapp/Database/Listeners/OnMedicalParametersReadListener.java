package com.example.asilapp.Database.Listeners;

import com.example.asilapp.Models.MedicalParameter;

import java.util.List;

public interface OnMedicalParametersReadListener {
    void onMedicalParametersLoaded(List<MedicalParameter> medicalParameters);
}
