package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.R;

public class LoginDefaultUserFragment extends Fragment {

    private RadioButton buttonChoseDefaultPatient, buttonChoseDefaultDoctor;
    private DatabasePazienti databasePazienti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_user_default, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        buttonChoseDefaultPatient = view.findViewById(R.id.RadioButton_Login_Button_Patient_Default);
        buttonChoseDefaultDoctor  = view.findViewById(R.id.RadioButton_Login_Button_Doctor_Default);
        databasePazienti          = new DatabasePazienti(requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonChoseDefaultPatient.setOnClickListener(v ->
                databasePazienti.logInAs("Patient", "default@patient.com", "defa1023"));
        buttonChoseDefaultDoctor.setOnClickListener(v ->
                databasePazienti.logInAs("Doctor", "lucamedici@doctor.com", "casadelsole123"));
    }
}
