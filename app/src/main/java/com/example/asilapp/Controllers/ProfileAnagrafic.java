package com.example.asilapp.Controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnPazienteDataReadListener;
import com.example.asilapp.Models.Patient;
import com.example.asilapp.R;

import java.util.List;

public class ProfileAnagrafic extends Fragment {
    private TextView anagraficName;
    private TextView anagraficSurname;
    private TextView anagraficGender;
    private TextView anagraficBirthplace;
    private TextView anagraficBirthdate;
    private TextView anagraficCountry;
    private TextView anagraficPhone;
    private TextView anagraficCenterId;
    private TextView anagraficEmail;
    private TextView anagraficPassword;
    private DatabasePazienti databasePazienti;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_anagrafic, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        anagraficName       = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Name);
        anagraficSurname    = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Surname);
        anagraficGender     = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Gender);
        anagraficBirthplace = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Birthplace);
        anagraficBirthdate  = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Birthdate);
        anagraficCountry    = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Country);
        anagraficPhone      = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Phone);
        anagraficCenterId   = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_CenterID);
        anagraficEmail      = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Email);
        anagraficPassword   = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Anagrafic_Password);

        databasePazienti    = new DatabasePazienti(getContext());


        Bundle args = getArguments();
        if (args != null) {
            // Visualizza i dati del paziente nei campi di testo
            anagraficName.setText(args.getString("name"));
            anagraficSurname.setText(args.getString("surname"));
            anagraficGender.setText(args.getString("gender"));
            anagraficBirthplace.setText(args.getString("birthPlace"));
            anagraficBirthdate.setText(args.getString("birthDate"));
            anagraficCountry.setText(args.getString("country"));
            anagraficPhone.setText(args.getString("phone"));
            anagraficCenterId.setText(args.getString("centerId"));
            anagraficEmail.setText(args.getString("email"));
            anagraficPassword.setText(args.getString("password"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        databasePazienti.read(new OnPazienteDataReadListener() {

            @Override
            public void onPazienteDataRead(Patient patient) {
                if (patient != null) {
                    anagraficName.setText(patient.getName());
                    anagraficSurname.setText(patient.getSurname());
                    anagraficGender.setText(patient.getGender());
                    anagraficBirthdate.setText(patient.getBirthDate());
                    anagraficBirthplace.setText(patient.getBirthPlace());
                    anagraficCountry.setText(patient.getCountry() );
                    anagraficPhone.setText(patient.getPhone());
                    anagraficCenterId.setText(patient.getCenterID());
                    anagraficEmail.setText(patient.getEmail());
                    anagraficPassword.setText(patient.getPassword());
                } else {
                    Toast.makeText(getContext(), "Paziente non trovato", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPazienteDataRead(List<Patient> patients) {}
        });


    }
}
