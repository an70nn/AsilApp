package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asilapp.Database.DatabaseCentroAccoglienza;
import com.example.asilapp.Database.Listeners.OnReadCentroAccoglienzaListener;
import com.example.asilapp.Models.CentroAccoglienza;
import com.example.asilapp.Models.Patient;
import com.example.asilapp.R;
import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Views.Activity.IntroActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;


public class SignupFragment extends Fragment{
    private static final String TAG = "SignupFragment";
    private static final String TYPE_PATIENT = "Patient";
    //Gestione parametri anagrafici del paziente
    private TextInputEditText signupName, signupSurname, signupBirthplace, signupPhone, signupEmail, signupPassword, signupPasswordConfirm;
    private RadioButton signupButtonMale;
    private MaterialButton signupBirthday;
    private String stringValueBirthday;
    private AutoCompleteTextView signupCountry;
    private Spinner signupCenterID;
    //Gestione dell'interfaccia utente
    private Button signupButton;
    private TextView signupSwitchToLoginFragment;
    private ImageButton signupGoBack;
    //Gestione dell'accesso al database
    private DatabasePazienti databasePazienti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        signupName                  = view.findViewById(R.id.TextInputEditText_Signup_Name);
        signupSurname               = view.findViewById(R.id.TextInputEditText_Signup_Surname);
        signupButtonMale            = view.findViewById(R.id.RadioButton_Signup_Button_Male);
        signupBirthplace            = view.findViewById(R.id.TextInputEditText_Signup_Birthplace);
        signupBirthday              = view.findViewById(R.id.MaterialButton_Signup_Birthdate);
        signupCountry               = view.findViewById(R.id.AutoCompleteTextView_Signup_Country);
        signupPhone                 = view.findViewById(R.id.TextInputEditText_Signup_Phone);
        signupCenterID              = view.findViewById(R.id.Spinner_Signup_CenterID);
        signupEmail                 = view.findViewById(R.id.TextInputEditText_Signup_Email);
        signupPassword              = view.findViewById(R.id.TextInputEditText_Signup_Password);
        signupPasswordConfirm       = view.findViewById(R.id.TextInputEditText_Signup_Password_Confirm);

        signupButton                = view.findViewById(R.id.Button_Signup);
        signupSwitchToLoginFragment = view.findViewById(R.id.TextView_Signup_SwitchToLoginFragment);
        signupGoBack                = view.findViewById(R.id.ImageButton_Login_BackArrow);

        databasePazienti = new DatabasePazienti(requireContext());

        DatabaseCentroAccoglienza databaseCentroAccoglienza = new DatabaseCentroAccoglienza(requireContext());

        //Recupero il valore della Data di nascita del paziente
        signupBirthday.setOnClickListener(v -> {

            //Permette di selezionare una data tra 01/01/1960 e 01/01/2010
            Calendar calendarStart = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendarStart.set(1960, 1, 1);
            Calendar calendarEnd = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendarEnd.set(2010, 1, 1);
            long minDate = calendarStart.getTimeInMillis();
            long maxDate = calendarEnd.getTimeInMillis();

            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Seleziona una data");
            builder.setCalendarConstraints(new CalendarConstraints.Builder().setStart(minDate).setEnd(maxDate).build());

            final MaterialDatePicker<Long> materialDatePicker = builder.build();
            materialDatePicker.show(getChildFragmentManager(), "DATE_PICKER");

            //Gestisce l'evento di Click su "OK" all'interno del selettore della data, che viene formattata nel formato giorno/mese/anno
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                Date selectedDate = new Date(selection);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date = format.format(selectedDate);
                signupBirthday.setText(date);
                stringValueBirthday = date;
            });
        });

        //Recupera i valori dei Paesi di provenienza per essere visualizzati nel corrispettivo campo
        //String[] contries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<>(requireActivity(), R.layout.dropdown_item_country);
        signupCountry.setAdapter(adapterCountry);

        //Recupera i valori di Nomi dei centri d'accoglienza per essere visualizzati nello Spinner
        ArrayList<String> listNameCenters = new ArrayList<>();
        ArrayAdapter<String> adapterCenter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listNameCenters);
        adapterCenter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupCenterID.setAdapter(adapterCenter);

        //Carica i dati del centri dal Databae
        databaseCentroAccoglienza.getCentroAccoglienzaAll(centroAccoglienza -> {
            if (centroAccoglienza != null) {
                // Aggiunge il nome del centro accoglienza alla lista delle opzioni
                listNameCenters.add(centroAccoglienza.getName());
                // Notifica all'ArrayAdapter che i dati sono cambiati
                adapterCenter.notifyDataSetChanged();
            } else {
                // Gestisci il caso in cui non è stato possibile leggere i dati dal database
                Toast.makeText(getContext(), "Errore nel caricamento dei centri di accoglienza", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //Se premo "Registrati"...
        signupButton.setOnClickListener(v -> {

            //Recupera i dati dai campi
            final String RECORD_NAME             = signupName.getText().toString().trim();
            final String RECORD_SURNAME          = signupSurname.getText().toString().trim();
            final String RECORD_GENDER           = (signupButtonMale.isChecked()) ? "Maschio" : "Femmina";
            final String RECORD_BIRTHPLACE       = signupBirthplace.getText().toString().trim();
            final String RECORD_BIRTHDAY         = stringValueBirthday;
            final String RECORD_COUNTRY          = signupCountry.getText().toString().trim();
            final String RECORD_PHONE            = signupPhone.getText().toString().trim();
            final String RECORD_CENTER           = signupCenterID.getSelectedItem().toString().trim();
            final String RECORD_EMAIL            = signupEmail.getText().toString().trim();
            final String RECORD_PASSWORD         = signupPassword.getText().toString().trim();
            final String RECORD_PASSWORD_CONFIRM = signupPasswordConfirm.getText().toString().trim();

            if (TextUtils.isEmpty(RECORD_NAME)         || TextUtils.isEmpty(RECORD_SURNAME)  || TextUtils.isEmpty(RECORD_GENDER) || TextUtils.isEmpty(RECORD_BIRTHPLACE) ||
                    TextUtils.isEmpty(RECORD_BIRTHDAY) || TextUtils.isEmpty(RECORD_COUNTRY)  || TextUtils.isEmpty(RECORD_PHONE)  || TextUtils.isEmpty(RECORD_CENTER)     ||
                    TextUtils.isEmpty(RECORD_EMAIL)    || TextUtils.isEmpty(RECORD_PASSWORD) || TextUtils.isEmpty(RECORD_PASSWORD_CONFIRM)) {
                Toast.makeText(getContext(), "Completa tutti i campi", Toast.LENGTH_SHORT).show();
            } else {
                //Verifico se tutti i campi sono stati compilati correttamente, e nel caso procedo con la registrazione

                Patient patient = new Patient(null, RECORD_NAME, RECORD_SURNAME, RECORD_GENDER, RECORD_BIRTHPLACE, RECORD_BIRTHDAY, RECORD_COUNTRY, RECORD_PHONE, RECORD_CENTER, RECORD_EMAIL, RECORD_PASSWORD);

                databasePazienti.signup(patient);


            }
        });

        //Se premo "<-" per tornare indietro o "Accedi" per passare alla schermata d'accesso
        //Entrambe le opzioni riportano alla schermata precedente.
        View.OnClickListener buttonClickedListener = v ->
                ((IntroActivity) requireActivity()).replaceFragment(new LoginFragment(TYPE_PATIENT));

        //Entrambi i bottoni hanno lo stesso gestore di eventi, se uno dei due viene premuto, verrà riportato alla schermata d'accesso
        signupGoBack.setOnClickListener(buttonClickedListener);
        signupSwitchToLoginFragment.setOnClickListener(buttonClickedListener);
    }
}