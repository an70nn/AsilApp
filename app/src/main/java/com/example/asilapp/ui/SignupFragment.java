package com.example.asilapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.asilapp.R;
import com.example.asilapp.model.User;
import com.example.asilapp.persistence.DatabaseManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;


public class SignupFragment extends Fragment {
    private TextInputEditText signupName, signupSurname, signupBirthplace, signupBirthday, signupPhone, signupCenterID, signupEmail, signupPassword;
    private Spinner signupCountry;
    private RadioButton signupButtonMale, signupButtonFemale;
    private Button bttnSignup;
    private DatabaseManager databaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, null);

        //Inizializza i componenti UI
        signupName = view.findViewById(R.id.TextInputEditText_Signup_Name);
        signupSurname = view.findViewById(R.id.TextInputEditText_Signup_Surname);
        signupButtonMale = view.findViewById(R.id.radiobutton_signup_button_male);
        signupButtonFemale = view.findViewById(R.id.radiobutton_signup_button_female);
        signupBirthplace = view.findViewById(R.id.TextInputEditText_Signup_Birthplace);
        signupBirthday = view.findViewById(R.id.TextInputEditText_Signup_Birthday);
        signupCountry = view.findViewById(R.id.Spinner_Signup_Country);
        signupPhone = view.findViewById(R.id.TextInputEditText_Signup_Phone);
        signupCenterID = view.findViewById(R.id.TextInputEditText_Signup_CenterID);
        signupEmail = view.findViewById(R.id.TextInputEditText_Signup_Email);
        signupPassword = view.findViewById(R.id.TextInputEditText_Signup_Password);
        bttnSignup = view.findViewById(R.id.button_signup);

        //Inizializza l'istanza per la gestione del database
        databaseManager = new DatabaseManager(requireContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Quando il bottone "Registrazione" viene premuto...
        bttnSignup.setOnClickListener(v -> {
            // Recupera il genere selezionato
            final String GENDER_RECORD = (signupButtonMale.isChecked()) ? "Male" : "Female";

            //Ottiene il testo dai campi di testo
            //Se contiene qualcosa (diverso da null) restituisce il contenuto nella costante
            //Altrimenti restituisce una stringa vuota
            final String NAME_RECORD       = signupName.getText() != null ? signupName.getText().toString().trim() : "";
            final String SURNAME_RECORD    = signupName.getText() != null ? signupSurname.getText().toString().trim() : "";
            final String BIRTHPLACE_RECORD = signupName.getText() != null ? signupBirthplace.getText().toString().trim() : "";
            final String BIRTHDAY_RECORD   = signupName.getText() != null ? signupBirthday.getText().toString().trim() : "";
            final String COUNTRY_RECORD    = signupName.getText() != null ? signupCountry.getSelectedItem().toString().trim() : "";
            final String PHONE_RECORD      = signupName.getText() != null ? signupPhone.getText().toString().trim() : "";
            final String CENTREID_RECORD   = signupName.getText() != null ? signupCenterID.getText().toString().trim() : "";
            final String EMAIL_RECORD      = signupName.getText() != null ? signupEmail.getText().toString().trim() : "";
            final String PASSWORD_RECORD   = signupName.getText() != null ? signupPassword.getText().toString().trim() : "";

            //Verifica che i campi di input siano validi
            validateField(NAME_RECORD,          signupName,         "Inserire un nome valido");
            validateField(SURNAME_RECORD,       signupSurname,      "Inserire un cognome valido");
            validateField(BIRTHPLACE_RECORD,    signupBirthplace,   "Inserire un luogo di nascita valido");
            validateField(BIRTHDAY_RECORD,      signupBirthday,     "Inserire una data di nascita valida");
            validateField(COUNTRY_RECORD,       signupName,         "Inserire una nazionalità valida");
            validateField(PHONE_RECORD,         signupName,         "Inserire un numero di cellulare valido");
            validateField(CENTREID_RECORD,      signupName,         "Inserire un centro ID valido");
            validateField(EMAIL_RECORD,         signupName,         "Inserire una email valida");
            validateField(PASSWORD_RECORD,      signupName,         "Inserire una password valida");

            //Crea un nuovo oggetto User con i dati forniti dai campi di input
            User utente = new User(NAME_RECORD, SURNAME_RECORD, BIRTHPLACE_RECORD, BIRTHDAY_RECORD, COUNTRY_RECORD,
                                 EMAIL_RECORD, PASSWORD_RECORD, CENTREID_RECORD, PHONE_RECORD, GENDER_RECORD);

            //Esegue la registrazione dell'utente utilzzando tali dati
            databaseManager.signup(utente);

        });
    }

    /**
     * Verifica se il campo (field) sia vuoto.<BR>
     * In caso affermativo, imposta il messaggio di errore specificato; <BR>
     * In caso negativo,    imposta una stringa vuota o un valore NULL come valore predefinito. <BR>
     * Infine richiede il focus sul campo.
     * @param valueRecord Il valore del campo da verificare
     * @param field Il campo (View) da validare
     * @param errorMessage Il messaggio di errore da impostare se il campo è vuoto
     */
    //Funzione di utilità creata per evitare la duplicazione del codice
    private void validateField(String valueRecord, View field, String errorMessage){
        // Verifica se il campo è un TextInputEditText
        if(field instanceof TextInputEditText){
            //Nel caso lo fosse, applico un cast esplicito in modo da poter accedere ai metodi e alle proprietà specifiche dello TextInputEditText.
            TextInputEditText inputEditText = (TextInputEditText) field;

            // Se il valore è vuoto, imposta il messaggio di errore, altrimenti imposta una stringa vuota
            inputEditText.setError(TextUtils.isEmpty(valueRecord) ? errorMessage : "");

            // Se il valore è vuoto, richiede il focus sul campo
            if(TextUtils.isEmpty(valueRecord)) inputEditText.requestFocus();
        }
        // Verifica se il campo è un RadioButton
        else if(field instanceof RadioButton){
            //Nel caso lo fosse, applico un cast esplicito in modo da poter accedere ai metodi e alle proprietà specifiche dello RadioButton.
            RadioButton inputRadioButton = (RadioButton) field;

            // Se il valore è vuoto, imposta il messaggio di errore, altrimenti imposta un valore nullo
            if (TextUtils.isEmpty(valueRecord)) {
                inputRadioButton.setError(errorMessage);
            } else {
                inputRadioButton.setError(null);
            }
        }
        // Verifica se il campo è uno Spinner
        else if(field instanceof Spinner){
            //Nel caso lo fosse, applico un cast esplicito in modo da poter accedere ai metodi e alle proprietà specifiche dello Spinner.
            Spinner inputSpinner = (Spinner) field;

            /* Ottiene il TextInputLayout genitore dello Spinner
             * Nel layout "activity_signup.xml", lo Spinner è incorporato all'interno di un TextInputLayout, il che significa che la gerarchia del layout è la seguente:
             * -TextInputLayout (genitore)
             *   -Spinner (figlio)
             * Quindi, chiamare il metodo getParent() due volte, significa che: il primo getParent() restituisce il layout del Spinner stesso,
             * e il secondo getParent() restituisce il TextInputLayout che contiene lo Spinner.
             * */
            TextInputLayout textInputLayout = (TextInputLayout) inputSpinner.getParent().getParent();
            if (textInputLayout != null) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(errorMessage);
            }
        }
    }
}
