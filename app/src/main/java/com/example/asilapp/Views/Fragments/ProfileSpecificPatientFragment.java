package com.example.asilapp.Views.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asilapp.Controllers.ProfileAnagrafic;
import com.example.asilapp.Controllers.ProfileMeasurement;
import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnMeasurementReadListener;
import com.example.asilapp.Models.Measurement;
import com.example.asilapp.Models.MedicalParameter;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.MedicalParameterAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfileSpecificPatientFragment extends Fragment {
    private final String TAG = "ProfileSpecificPatientFragment";
    private TextView specificPatientFullName, specificPatientAge, specificPatientBirthplace, specificPatientBirthdate, specificPatientCountry, specificPatientPhone, specificPatientCenterName, specificPatientGender;
    private Spinner specificPatientCategoryMeasurement;
    private ImageButton specificPatientAddMeasurement;
    private RecyclerView recyclerView;
    private List<MedicalParameter> medicalParameterList;
    private MedicalParameterAdapter medicalParameterAdapter;
    private DatabasePazienti databasePazienti;
    private String patientId;
    //private TabLayout specificPatientTabLayout;
    //private TabItem specificPatientAnagrafic, specificPatientMeasurement;
    //private ViewPager2 specificPatientViewPager;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listpatients_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        specificPatientFullName = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Fullname);
        specificPatientAge = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Age);
        specificPatientBirthplace = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Birthplace);
        specificPatientBirthdate = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Birthdate);
        specificPatientCountry = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Country);
        specificPatientPhone = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Phone);
        specificPatientCenterName = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_CenterID);
        specificPatientGender = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Gender);

        specificPatientCategoryMeasurement = view.findViewById(R.id.Spinner_Doctor_ListPatient_Profile_Measurement_Category);
        specificPatientAddMeasurement = view.findViewById(R.id.ImageButton_Doctor_ListPatient_Profile_Measurement_AddMeasurement);
        recyclerView = view.findViewById(R.id.RecyclerView_Doctor_ListPatients_Profile_Measurement);

        //Passaggio dei valori dell'Item del paziente (dal ListPatientsFragment) al Profilo specifico del paziente
        Bundle args = getArguments();
        if (args != null) {
            //Settaggio di tali valori ai campi di testo
            specificPatientFullName.setText(args.getString("fullName"));
            specificPatientAge.setText(args.getString("age"));
            specificPatientBirthplace.setText(args.getString("birthPlace"));
            specificPatientBirthdate.setText(args.getString("birthDate"));
            specificPatientCountry.setText(args.getString("country"));
            specificPatientPhone.setText(args.getString("phone"));
            specificPatientCenterName.setText(args.getString("centerId"));
            specificPatientGender.setText(args.getString("gender"));
            patientId = args.getString("patientId");
            Log.i("ProfileSpecificPatientFragment", "Il paziente ha ID: " + patientId);
        }

        setupCategorySpinner(specificPatientCategoryMeasurement);

        medicalParameterList = new ArrayList<>();
        medicalParameterAdapter = new MedicalParameterAdapter(medicalParameterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(medicalParameterAdapter);

        databasePazienti = new DatabasePazienti(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "Il paziente ha ID: "+ patientId);

        //Aggiornare la lista dei parametri medici con le nuove misurazioni
        databasePazienti.loadMeasurements(patientId, new OnMeasurementReadListener() {
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


        specificPatientAddMeasurement.setOnClickListener(v -> {
            final Dialog newMeasurementDialog = new Dialog(getContext());
            newMeasurementDialog.setContentView(R.layout.dialog_measurements_new_measurement);
            newMeasurementDialog.setTitle("Aggiungere nuova misurazione...");

            final Spinner measurementCategory    = newMeasurementDialog.findViewById(R.id.Spinner_Doctor_Dialog_AddMeasurement_Category);
            final MaterialButton measurementDate = newMeasurementDialog.findViewById(R.id.MaterialButton_Doctor_Dialog_AddMeasurement_Date);
            final EditText measurementTime       = newMeasurementDialog.findViewById(R.id.EditText_Doctor_Dialog_AddMeasurement_Time);
            final EditText measurementValue      = newMeasurementDialog.findViewById(R.id.EditText_Doctor_Dialog_AddMeasurement_Value);
            final Button measurementSaveRecord = newMeasurementDialog.findViewById(R.id.Button_Doctor_Dialog_AddMeasurement_SaveRecord);

            setupCategorySpinner(measurementCategory);
            // Configura il filtro per lo Spinner
            setupCategoryFilterSpinner(specificPatientCategoryMeasurement, medicalParameterList, medicalParameterAdapter);

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
                        String userID = patientId;


                        Measurement newMeasurement = new Measurement(userID, date, time, value, category);
                        Log.i(TAG, "Record: "+newMeasurement);

                        // Aggiunge la nuova misurazione alla lista dei parametri medici corrispondente
                        addMeasurementToParameter(category, newMeasurement);

                        // Aggiungi la misurazione anche al database
                        databasePazienti.addMeasurement(newMeasurement, userID, category);

                        // Pulisce l'adapter e aggiunge il messaggio
                        medicalParameterList.clear();
                        for (MedicalParameter parameter : medicalParameterList) {
                            medicalParameterList.add(parameter);
                        }
                        medicalParameterAdapter.notifyDataSetChanged();

                        // Chiude il dialog per aggiungere una nuova misurazione
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterMeasurements(selectedCategory, medicalParameterList, medicalParameterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Implementazione opzionale se necessario
            }
        });
    }

    /**
     * Configura lo spinner per filtrare le transazioni per categoria.
     *
     * @param spinner lo spinner da configurare
     * @param medicalParameterList la lista delle transazioni
     * @param adapter l'adapter del RecyclerView
     */
    private void setupCategoryFilterSpinner(Spinner spinner, List<MedicalParameter> medicalParameterList, MedicalParameterAdapter adapter) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterMeasurements(selectedCategory, medicalParameterList, adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Filtra le misurazioni in base alla categoria selezionata.
     *
     * @param category la categoria selezionata
     * @param medicalParameterList la lista dei parametri medici
     * @param adapter l'adapter del RecyclerView
     */
    private void filterMeasurements(String category, List<MedicalParameter> medicalParameterList, MedicalParameterAdapter adapter) {
        List<MedicalParameter> filteredList = new ArrayList<>();

        if (category.equals("Tutte le categorie")) {
            filteredList.addAll(medicalParameterList);
        } else {
            for (MedicalParameter medicalParameter : medicalParameterList) {
                if (medicalParameter.getParameterName().equals(category)) {
                    filteredList.add(medicalParameter);
                }
            }
        }

        adapter.updateList(filteredList);
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


            // Passa i dati al fragment ProfileAnagraficFragment
            //profileAnagraficArgs = new Bundle();
            //Anziché fare String name = args.getString("phone");
            // E poi profileAnagraficArgs.putString("name", name);
            /*
            profileAnagraficArgs.putString("patientId",  args.getString("patientId"));
            profileAnagraficArgs.putString("name",       args.getString("name"));
            profileAnagraficArgs.putString("surname",    args.getString("surname"));
            profileAnagraficArgs.putString("gender",     args.getString("gender"));
            profileAnagraficArgs.putString("birthPlace", args.getString("birthPlace"));
            profileAnagraficArgs.putString("birthDate",  args.getString("birthDate"));
            profileAnagraficArgs.putString("country",    args.getString("country"));
            profileAnagraficArgs.putString("phone",      args.getString("phone"));
            profileAnagraficArgs.putString("centerId",   args.getString("centerId"));
            profileAnagraficArgs.putString("email",      args.getString("email"));
            profileAnagraficArgs.putString("password",   args.getString("password"));*/




        //specificPatientTabLayout   = view.findViewById(R.id.TabLayout_Doctor_ProfileSpecificPatient);
        //specificPatientAnagrafic   = view.findViewById(R.id.TabItem_Doctor_ProfileSpecificPatient_Anagrafic);
        //specificPatientMeasurement = view.findViewById(R.id.TabItem_Doctor_ProfileSpecificPatient_Measurement);
        //specificPatientViewPager   = view.findViewById(R.id.ViewPager_Doctor_ListPatients_Profile);

        // Configura l'adattatore per il ViewPager2
        //specificPatientViewPager.setAdapter(new ProfileSpecificPatientFragment.ProfilePagerAdapter(this, profileAnagraficArgs));
        // Imposta i titoli delle schede per i fragment
        /*new TabLayoutMediator(specificPatientTabLayout, specificPatientViewPager,
                (tab, position) -> tab.setText(position == 0 ? "Anagrafica" : "Misurazioni")
        ).attach();*/

/*
    private static class ProfilePagerAdapter extends FragmentStateAdapter {
        private final Bundle profileArgs;
        public ProfilePagerAdapter(Fragment fragment, Bundle profileArgs) {
            super(fragment);
            this.profileArgs = profileArgs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                ProfileAnagrafic profileAnagraficFragment = new ProfileAnagrafic();
                profileAnagraficFragment.setArguments(profileArgs);
                return profileAnagraficFragment;
            } else {
                return new ProfileMeasurement();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

 */
}
