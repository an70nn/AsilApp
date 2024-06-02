package com.example.asilapp.Database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.asilapp.Models.Doctor;
import com.example.asilapp.Views.Activity.HomepageDoctorActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabaseDottori {
    private static final String TAG = "DatabaseDottori";
    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestoreDatabase;
    private final CollectionReference doctorsCollection;

    public DatabaseDottori(Context context){
        this.context          = context;                          //Ottiene il contesto della schermata in utilizzo
        firebaseAuth          = FirebaseAuth.getInstance();       //Ottiene un'istanza di FirebaseAuth
        firestoreDatabase     = FirebaseFirestore.getInstance();  //Ottiene un'istanza di FirebaseFirestore
        doctorsCollection     = firestoreDatabase.collection("Dottori");
    }

    /**
     * Effettua l'accesso dell'utente come Dottore o Paziente.abdul@test.com
     * @param EMAIL Indirizzo email dell'utente
     * @param PASSWORD Password dell'utente
     */
    public void login(final String EMAIL, final String PASSWORD){
        firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Autenticazione riuscita.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Accesso compiuto");
                            //Aggiungere HomePageActivity del Dottore
                            context.startActivity(new Intent(context, HomepageDoctorActivity.class));
                    }
                    else {
                        String errorMessage = "Autentificazione fallita: ";
                        if(task.getException() != null){
                            //Mostra un feedback specifico sull'errore
                            errorMessage += task.getException().getMessage();
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, errorMessage);
                        }
                    }
                });
    }

    /**
     * Registra un nuovo utente
     * @param doctor Oggetto User contenente tutte le informazioni dell'utente da registrare
     */
    public void signup(final Doctor doctor) {
        firebaseAuth.createUserWithEmailAndPassword(doctor.getEmail(), doctor.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Crea un oggetto contenente le informazioni dell'utente da memorizzare poi nel database Firestore
                Map<String, Object> userData = new HashMap<>();
                userData.put("id",         getCurrentUserId());
                userData.put("name",       doctor.getName());
                userData.put("surname",    doctor.getSurname());
                userData.put("gender",     doctor.getGender());
                userData.put("birthDate",  doctor.getBirthDate());
                userData.put("phone",      doctor.getPhone());
                userData.put("centerID",   doctor.getCenterId());
                userData.put("email",      doctor.getEmail());
                userData.put("password",   doctor.getPassword());

                doctorsCollection.document(Objects.requireNonNull(getCurrentUserId())).set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Registrazione avvenuta", Toast.LENGTH_SHORT).show();
                                //Se l'autenticazione è riuscita avvia la schermata homepage
                                context.startActivity(new Intent(context, HomepageDoctorActivity.class));
                            }
                        })
                        .addOnFailureListener(e -> {
                            //Se l'operazione fallisce mostra un messaggio di registrazione fallita
                            String errorMessage = "Registrazione non avvenuta: ";
                            if(e.getMessage() != null){
                                //E mostra anche un feedback specifico sull'errore
                                errorMessage += e.getMessage();
                                Log.e(TAG, "Messaggio d'Errore: "+e.getMessage());
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        });
            }else{
                //Se l'operazione di registrazione non avviene
                String errorMessage = "Autentificazione non avvenuta: ";
                if(task.getException() != null){
                    //E mostra anche un feedback specifico sull'errore
                    errorMessage += task.getException().getMessage();
                    Log.e(TAG, "Messaggio d'Errore: "+task.getException().getMessage());
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *  Restituisce l'ID dell'utente corrente autenticato,
     */
    @Nullable
    public String getCurrentUserId(){
        //Ottiene l'utente corrente
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //Se esiste lo restituisce
        if(currentUser != null){
            return currentUser.getUid();
        }else{
            //Se "non esiste" (quindi null) viene registrato un messaggio di errore
            Log.e(TAG, "L'utente corrente è null ");
            return null;
        }
    }

}
