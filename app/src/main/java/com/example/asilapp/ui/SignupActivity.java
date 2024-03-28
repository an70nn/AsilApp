package com.example.asilapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asilapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    // Dichiarazione dei campi di input e dei pulsanti
    private TextInputEditText signupName, signupSurname, signupBirthplace, signupBirthday, signupPhone, signupCenterID, signupEmail, signupPassword;
    private Spinner signupCountry;
    private RadioButton signupButtonMale, signupButtonFemale;
    private String gender;
    private Button bttnSignup;
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Abilita il layout edge-to-edge
        EdgeToEdge.enable(this);

        // Imposta il layout dell'attività
        setContentView(R.layout.activity_signup);

        // Inizializzazione dei campi di input e dei pulsanti
        signupName          = findViewById(R.id.TextInputEditText_Signup_Name);
        signupSurname       = findViewById(R.id.TextInputEditText_Signup_Surname);
        signupButtonMale    = findViewById(R.id.radiobutton_signup_button_male);
        signupButtonFemale  = findViewById(R.id.radiobutton_signup_button_female);
        signupBirthplace    = findViewById(R.id.TextInputEditText_Signup_Birthplace);
        signupBirthday      = findViewById(R.id.TextInputEditText_Signup_Birthday);
        signupCountry       = findViewById(R.id.Spinner_Signup_Country);
        signupPhone         = findViewById(R.id.TextInputEditText_Signup_Phone);
        signupCenterID      = findViewById(R.id.TextInputEditText_Signup_CenterID);
        signupEmail         = findViewById(R.id.TextInputEditText_Signup_Email);
        signupPassword      = findViewById(R.id.TextInputEditText_Signup_Password);
        bttnSignup          = findViewById(R.id.button_signup);

        // Ottieni un'istanza di FirebaseAuth
        mAuth               = FirebaseAuth.getInstance();
        // Ottiene un'istanza al database di Firestore
        database            = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Gestione del click sul pulsante di registrazione
        bttnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //______________PROMEMORIA
                //Aggiornare con la condizione ternaria per rimuovere il warning (NullPointerException)
                //" Method invocation 'toString' may produce 'NullPointerException' "
                //Un primo esempio potrebbe essere...
                //final String NAME_RECORD = signupName.getText() != null ? signupName.getText().toString().trim() : "";
                //Se l'oggetto è effettivamente nullo, assegniamo una stringa vuota

                // Recupera il genere selezionato
                final String GENDER_RECORD = (signupButtonMale.isChecked()) ? "Male" : "Female";

                // Recupera i valori inseriti nei campi di input
                final String NAME_RECORD          = signupName.getText().toString().trim();
                final String SURNAME_RECORD       = signupSurname.getText().toString().trim();
                final String BIRTHPLACE_RECORD    = signupBirthplace.getText().toString().trim();
                final String BIRTHDAY_RECORD      = signupBirthday.getText().toString().trim();
                final String COUNTRY_RECORD       = signupCountry.getSelectedItem().toString().trim();
                final String PHONE_RECORD         = signupPhone.getText().toString().trim();
                final String CENTREID_RECORD      = signupCenterID.getText().toString().trim();
                final String EMAIL_RECORD         = signupEmail.getText().toString().trim();
                final String PASSWORD_RECORD      = signupPassword.getText().toString().trim();

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

                //Creazione di un nuovo utente con tali campi di input
                mAuth.createUserWithEmailAndPassword(EMAIL_RECORD, PASSWORD_RECORD).addOnCompleteListener(task -> {
                    //Verifica se la creazione dell'utente è stata completata con successo
                    if(task.isSuccessful()) {

                        //Prepara i dati del nuovo utente per essere aggiunti al database Firestore
                        Map<String, Object> userData = new HashMap<>();

                        userData.put("id", mAuth.getCurrentUser().getUid());
                        userData.put("name", NAME_RECORD);
                        userData.put("surname", SURNAME_RECORD);
                        userData.put("gender", GENDER_RECORD);
                        userData.put("birthplace", BIRTHPLACE_RECORD);
                        userData.put("birthday", BIRTHDAY_RECORD);
                        userData.put("country", COUNTRY_RECORD);
                        userData.put("phone", PHONE_RECORD);
                        userData.put("centreid", CENTREID_RECORD);
                        userData.put("email", EMAIL_RECORD);
                        userData.put("password", PASSWORD_RECORD);

                        database.collection("user").document(mAuth.getCurrentUser().getUid()).set(userData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignupActivity.this, "Registrazione avvenuta", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignupActivity.this, "Registrazione non avvenuta", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
            }
        });
    }

    /**
     * Verifica se il campo (field) sia vuoto.<BR>
     * In caso affermativo, imposta il messaggio di errore; <BR>
     * In caso negativo,    imposta una stringa vuota o un valore NULL come valore predefinito. <BR>
     * Infine richiede il focus sul campo.
     * @param valueRecord
     * @param field
     * @param errorMessage
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
