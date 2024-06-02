package com.example.asilapp.Database.Listeners;

import com.example.asilapp.Models.Patient;

import java.util.List;

public interface OnPazienteDataReadListener {
    void onPazienteDataRead(Patient patient);
    void onPazienteDataRead(List<Patient> patients);
}
