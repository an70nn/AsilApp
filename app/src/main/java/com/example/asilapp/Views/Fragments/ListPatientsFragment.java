package com.example.asilapp.Views.Fragments;

import static com.example.asilapp.Views.Adapters.ListPatientsAdapter.calculateAge;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnPazienteDataReadListener;
import com.example.asilapp.Models.Patient;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.ListPatientsAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ListPatientsFragment extends Fragment{

    private RecyclerView recyclerView;
    private List<Patient> patientsList;
    private ListPatientsAdapter patientsAdapter;
    private DatabasePazienti databasePazienti;
    private ImageButton patientsAddMeasurement;
    private TextInputEditText patientsSearchText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listpatients, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView           = view.findViewById(R.id.RecyclerView_Doctor_ListPatients);
        patientsAddMeasurement = view.findViewById(R.id.ImageButton_Doctor_ListPatient_AddMeasurament);
        patientsSearchText     = view.findViewById(R.id.TextInputEditText_Doctor_ListPatients_SearchPatients);

        databasePazienti       = new DatabasePazienti(getContext());
        patientsList           = new ArrayList<>();
        patientsAdapter        = new ListPatientsAdapter(patientsList);
        recyclerView.setAdapter(patientsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Aggiungi il listener di clic agli elementi dell'adapter
        patientsAdapter.setOnItemClickListener(new ListPatientsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Patient patient) {
                // Creare e visualizzare il nuovo fragment per il profilo specifico del paziente
                ProfileSpecificPatientFragment profileFragment = new ProfileSpecificPatientFragment();

                // Trasmetti i dati del paziente al nuovo fragment
                Bundle args = new Bundle();
                args.putString("patientId", patient.getId());
                Log.i("ListPatientsFragment", patient.getId());
                args.putString("fullName", patient.getName() + " " + patient.getSurname());
                args.putString("age", String.valueOf(calculateAge(patient.getBirthDate())) + " anni");
                args.putString("birthPlace", patient.getBirthPlace());
                args.putString("birthDate", patient.getBirthDate());
                args.putString("country", patient.getCountry());
                args.putString("phone", patient.getPhone());
                args.putString("centerId", patient.getCenterName());
                args.putString("gender", patient.getGender());
                profileFragment.setArguments(args);

                // Sostituisci il fragment attuale con il nuovo fragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.Fragment_Doctor_Container, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //Visualizza il nome filtrato del paziente durante la digitatura del nome nel EditText
        patientsSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Prima che il testo viene modificato
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Mentre il testo viene modificato
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Dopo che il testo viene modificato
                filterPatients(s.toString());
            }
        });

        //Visualizza tutti i pazienti nel RecyclerView
        databasePazienti.readAll(new OnPazienteDataReadListener() {
            @Override
            public void onPazienteDataRead(Patient patient) {}

            @Override
            public void onPazienteDataRead(List<Patient> patients) {
                if (patients != null) {
                    patientsList.clear();
                    patientsList.addAll(patients);
                    patientsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Pazienti non trovati", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Filtra l'elenco dei pazienti in base al testo di ricerca fornito.
     * @param searchText Il testo da utilizzare per filtrare l'elenco dei pazienti.
     */
    private void filterPatients(String searchText) {
        // Crea una nuova lista per memorizzare i pazienti filtrati
        List<Patient> filteredList = new ArrayList<>();

        for (Patient patient : patientsList) {
            // Controlla se il nome del paziente contiene il testo di ricerca, ignorando maiuscole e minuscole
            if (patient.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(patient);
            }
        }
        // Aggiorna l'elenco visualizzato nel RecyclerView
        patientsAdapter.filterList(filteredList);
    }
}
