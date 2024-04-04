package com.example.asilapp.persistence;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asilapp.HomePageActivity;
import com.example.asilapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static final String TAG = "FirebaseManager";

    //Contesto della schermata in utilizzo
    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore dataFirebase;

    public DatabaseManager(Context context){
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();      // Ottiene un'istanza di FirebaseAuth
        dataFirebase = FirebaseFirestore.getInstance(); // Ottiene un'istanza di FirebaseFirestore
    }

    /**
     * Effettua l'accesso dell'utente
     * @param EMAIL Indirizzo email dell'utente
     * @param PASSWORD Password dell'utente
     */
    public void login(final String EMAIL, final String PASSWORD){
            firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(task -> {
                    //Verifica se l'operazione di accesso sia avvenuta
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Autenticazione riuscita.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Accesso compiuto");

                        //Se l'autenticazione è riuscita avvia la schermata homepage
                        context.startActivity(new Intent(context, HomePageActivity.class));
                    } else {
                        //Se l'operazione fallisce mostra un messaggio di autentificazione fallita
                        String errorMessage = "Autentificazione fallita: ";
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
     * Registra un nuovo utente
     * @param user Oggetto User contenente tutte le informazioni dell'utente da registrare
     */
    public void signup(final User user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            //Verifica se l'operazione di registazione sia avvenuta
            if (task.isSuccessful()) {

                //Se la registrazione ha successo crea un documento utente nel database Firestore
                Map<String, Object> userData = createUserData(user);

                //Aggiunge i dati dell'utente al nodo "utenti" utilizzando l'ID dell'utente come chiave
                dataFirebase.collection("utenti").document(firebaseAuth.getCurrentUser().getUid()).set(userData)
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
     * Crea un oggetto conntenente i dati dell'utente per essere memorizzato nel database Firestore
     * @param user Oggetto contenente le informazioni dell'utente
     * @return Mappa di dati contenente le informazioni dell'Utente
     */
    @NonNull
    private Map<String, Object> createUserData(User user) {
        Map<String, Object> userData = new HashMap<>();

        userData.put("id", firebaseAuth.getCurrentUser().getUid());
        userData.put("name", user.getName());
        userData.put("surname", user.getSurname());
        userData.put("gender", user.getGender());
        userData.put("birthplace", user.getBirthPlace());
        userData.put("birthday", user.getBirthDate());
        userData.put("country", user.getCountry());
        userData.put("phone", user.getPhone());
        userData.put("centreID", user.getCenter_id());
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        return userData;
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

    /*
    Esegue il logout dell'utente

    public void logout(){
        firebaseAuth.signOut();
    }
    */
}
