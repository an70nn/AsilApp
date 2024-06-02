package com.example.asilapp.Controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnMeasurementReadListener;
import com.example.asilapp.Models.Measurement;
import com.example.asilapp.Models.MedicalParameter;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.MedicalParameterAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ProfileMeasurement extends Fragment {
    private static final String TAG = "ProfileMeasurement";
    private RecyclerView recyclerView;
    private Spinner measurementSpinnerCategory;
    private ImageButton measurementAddMeasurement;
    private List<MedicalParameter> medicalParameterList;
    private MedicalParameterAdapter medicalParameterAdapter;
    private DatabasePazienti databasePazienti;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_measurement, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        measurementSpinnerCategory = view.findViewById(R.id.Spinner_Doctor_ListPatient_Profile_Measurement_Category);
        measurementAddMeasurement  = view.findViewById(R.id.ImageButton_Doctor_ListPatient_Profile_Measurement_AddMeasurement);
        recyclerView               = view.findViewById(R.id.RecyclerView_Doctor_ListPatients_Profile_Measurement);

        setupCategorySpinner(measurementSpinnerCategory);

        medicalParameterList = new ArrayList<>();
        medicalParameterAdapter = new MedicalParameterAdapter(medicalParameterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(medicalParameterAdapter);

        databasePazienti = new DatabasePazienti(getContext());

        //Test: simulazione del caricamento dei dati
        //loadMockData();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Aggiornare la lista dei parametri medici con le nuove misurazioni
        databasePazienti.loadMeasurements(databasePazienti.getCurrentUserId(), new OnMeasurementReadListener() {
            @Override
            public void onMeasurementsLoaded(List<Measurement> measurements) {
                for (Measurement measurement : measurements) {
                    // Trova il parametro medico corrispondente o crea un nuovo parametro se non esiste
                    addMeasurementToParameter(measurement.getCategory(), measurement);
                }

                // Aggiorna l'adapter
                medicalParameterAdapter.notifyDataSetChanged();
            }
        });


        measurementAddMeasurement.setOnClickListener(v -> {
            final Dialog newMeasurementDialog = new Dialog(getContext());
            newMeasurementDialog.setContentView(R.layout.dialog_measurements_new_measurement);
            newMeasurementDialog.setTitle("Aggiungere nuova misurazione...");

            final Spinner measurementCategory    = newMeasurementDialog.findViewById(R.id.Spinner_Doctor_Dialog_AddMeasurement_Category);
            final MaterialButton measurementDate = newMeasurementDialog.findViewById(R.id.MaterialButton_Doctor_Dialog_AddMeasurement_Date);
            final EditText measurementTime       = newMeasurementDialog.findViewById(R.id.EditText_Doctor_Dialog_AddMeasurement_Time);
            final EditText measurementValue      = newMeasurementDialog.findViewById(R.id.EditText_Doctor_Dialog_AddMeasurement_Value);
            final Button   measurementSaveRecord = newMeasurementDialog.findViewById(R.id.Button_Doctor_Dialog_AddMeasurement_SaveRecord);

            setupCategorySpinner(measurementCategory);

            // Configura EditText per la data di misurazione
            measurementDate.setOnClickListener(v1 -> showDatePickerDialog(measurementDate));

            measurementSaveRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category = measurementCategory.getSelectedItem().toString().trim();
                    String date     = measurementDate.getText().toString();
                    String time     = measurementTime.getText().toString().trim();
                    String value    = measurementValue.getText().toString().trim();

                    if(!date.isEmpty() && !time.isEmpty() && !value.isEmpty()){
                        String userID = databasePazienti.getCurrentUserId();

                        Measurement newMeasurement = new Measurement(userID, date, time, value, category);
                        Log.i(TAG, "Record: "+newMeasurement);

                        // Aggiungi la nuova misurazione alla lista delle misurazioni per il parametro medico corrispondente
                        addMeasurementToParameter(category, newMeasurement);

                        databasePazienti.addMeasurement(newMeasurement, userID, category);

                        medicalParameterAdapter.notifyDataSetChanged();
                        newMeasurementDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Perfavore, inserisci tutti i campi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            newMeasurementDialog.show();
        });
    }

    /**
     * Configura lo spinner per la selezione della categoria di misurazione.
     *
     * @param spinner lo spinner da configurare
     */
    private void setupCategorySpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Aggiunge una nuova misurazione al parametro medico corrispondente o crea un nuovo parametro medico se necessario.
     *
     * @param category    la categoria della misurazione
     * @param measurement la nuova misurazione da aggiungere
     */
    private void addMeasurementToParameter(String category, Measurement measurement) {
        boolean parameterExists = false;
        for (MedicalParameter medicalParameter : medicalParameterList) {
            if (medicalParameter.getParameterName().equals(category)) {
                medicalParameter.getMeasurements().add(measurement);
                parameterExists = true;
                break;
            }
        }
        if (!parameterExists) {
            // Se il parametro medico non esiste, crea un nuovo parametro medico con la nuova misurazione
            List<Measurement> measurements = new ArrayList<>();
            measurements.add(measurement);
            medicalParameterList.add(new MedicalParameter(category, measurements));
        }
        medicalParameterAdapter.notifyDataSetChanged();
    }

    /**
     * Mostra un DatePickerDialog per selezionare la data della spesa.
     */
    private void showDatePickerDialog(final MaterialButton materialButton) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        materialButton.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}
