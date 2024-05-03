package com.example.asilapp.persistence;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asilapp.HomePageActivity;
import com.example.asilapp.model.Paziente;
import com.example.asilapp.ui.IntroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabasePazienti {
    private static final String TAG = "DatabaseManager";
    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestoreDatabase;

    public DatabasePazienti(Context context){
        this.context      = context;                          //Ottiene il contesto della schermata in utilizzo
        firebaseAuth      = FirebaseAuth.getInstance();       //Ottiene un'istanza di FirebaseAuth
        firestoreDatabase = FirebaseFirestore.getInstance();  //Ottiene un'istanza di FirebaseFirestore
    }

    /**
     * Effettua l'accesso dell'utente
     * @param EMAIL Indirizzo email dell'utente
     * @param PASSWORD Password dell'utente
     */
    public void login(final String EMAIL, final String PASSWORD){
            firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Autenticazione riuscita.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Accesso compiuto");

                        //Se l'autenticazione è riuscita avvia la schermata homepage
                        context.startActivity(new Intent(context, HomePageActivity.class));
                    } else {
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
     * @param paziente Oggetto User contenente tutte le informazioni dell'utente da registrare
     */
    public void signup(final Paziente paziente) {
        firebaseAuth.createUserWithEmailAndPassword(paziente.getEmail(), paziente.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Crea un oggetto contenente le informazioni dell'utente da memorizzare poi nel database Firestore
                Map<String, Object> userData = new HashMap<>();
                userData.put("id", getCurrentUserId());
                userData.put("name",       paziente.getName());
                userData.put("surname",    paziente.getSurname());
                userData.put("gender",     paziente.getGender());
                userData.put("birthPlace", paziente.getBirthPlace());
                userData.put("birthDate",  paziente.getBirthDate());
                userData.put("country",    paziente.getCountry());
                userData.put("phone",      paziente.getPhone());
                userData.put("centerID",   paziente.getCenterID());
                userData.put("email",      paziente.getEmail());
                userData.put("password",   paziente.getPassword());

                firestoreDatabase.collection("utenti").document(Objects.requireNonNull(getCurrentUserId())).set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Registrazione avvenuta", Toast.LENGTH_SHORT).show();
                                //Se l'autenticazione è riuscita avvia la schermata homepage
                                context.startActivity(new Intent(context, HomePageActivity.class));
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
     * Legge i dati del paziente dal database Firestore e notifica il risultato tramite un listener.
     * @param listener Il listener per gestire l'evento di lettura dei dati del paziente.
     */
    public void read(final OnPazienteDataReadListener listener){
        //Ottiene il riferimento al singolo documento (cioè al singolo utente) della collezione "utenti"
        //Che viene identificato tramite una stringa non nulla dell'ID dell'utente
        DocumentReference documentReference = firestoreDatabase.collection("utenti").document(getCurrentUserId());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Paziente paziente = documentSnapshot.toObject(Paziente.class);
                        listener.onPazienteDataRead(paziente);
                    } else {
                        listener.onPazienteDataRead(null);
                        Log.d(TAG, "Documento non esistente");
                    }
                } else {
                    Toast.makeText(context, "Lettura non avvenuta", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "get failed with ", task.getException());
                    listener.onPazienteDataRead(null);
                }
            }
        });
    }

    /**
     *  Restituisce l'ID dell'utente corrente autenticato,
     */
    @Nullable
    private String getCurrentUserId(){
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


    /**
     * Esegue il logout dell'utente
     */
     public void logout(){
        firebaseAuth.signOut();
        context.startActivity(new Intent(context, IntroActivity.class));
    }
}
