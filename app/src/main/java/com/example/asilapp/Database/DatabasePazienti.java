package com.example.asilapp.Database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asilapp.Database.Listeners.OnMeasurementReadListener;
import com.example.asilapp.Database.Listeners.OnPazienteDataReadListener;
import com.example.asilapp.Database.Listeners.OnTransactionReadListener;
import com.example.asilapp.Models.Measurement;
import com.example.asilapp.Models.Transaction;
import com.example.asilapp.Views.Activity.HomepageDoctorActivity;
import com.example.asilapp.Views.Activity.HomepagePatientActivity;
import com.example.asilapp.Models.Patient;
import com.example.asilapp.Views.Activity.IntroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DatabasePazienti {
    private static final String TAG = "DatabasePazienti";
    private final String TYPE_DOCTOR = "Doctor";
    private final String TYPE_PATIENT = "Patient";
    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestoreDatabase;
    private final CollectionReference patientsCollection, transactionCollection, measurementCollection;

    public DatabasePazienti(Context context){
        this.context          = context;                          //Ottiene il contesto della schermata in utilizzo
        firebaseAuth          = FirebaseAuth.getInstance();       //Ottiene un'istanza di FirebaseAuth
        firestoreDatabase     = FirebaseFirestore.getInstance();  //Ottiene un'istanza di FirebaseFirestore
        patientsCollection    = firestoreDatabase.collection("Pazienti");
        transactionCollection = firestoreDatabase.collection("Transazioni");
        measurementCollection = firestoreDatabase.collection("Misurazioni");
    }

    /**
     * Effettua l'accesso dell'utente come Dottore o Paziente.abdul@test.com
     * @param typeUser Tipologia di utente
     * @param EMAIL Indirizzo email dell'utente
     * @param PASSWORD Password dell'utente
     */
    public void logInAs(String typeUser, final String EMAIL, final String PASSWORD){
            firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Autenticazione riuscita.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Accesso compiuto");

                        //Verifico il tipo di utente e avvio la schermata hompeage corrispondente
                        if(TYPE_DOCTOR.equals(typeUser)){
                            //Aggiungere HomePageActivity del Dottore
                            context.startActivity(new Intent(context, HomepageDoctorActivity.class));
                        }
                        else if(TYPE_PATIENT.equals(typeUser)){
                            context.startActivity(new Intent(context, HomepagePatientActivity.class));
                        }
                        else{
                            Log.e(TAG, "Tipo di utente non valido: " + typeUser);
                            Toast.makeText(context, "Tipo di utente non valido.", Toast.LENGTH_SHORT).show();
                        }
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
     * @param patient Oggetto User contenente tutte le informazioni dell'utente da registrare
     */
    public void signup(final Patient patient) {
        firebaseAuth.createUserWithEmailAndPassword(patient.getEmail(), patient.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Crea un oggetto contenente le informazioni dell'utente da memorizzare poi nel database Firestore
                Map<String, Object> userData = new HashMap<>();
                userData.put("id",         firebaseAuth.getCurrentUser().getUid());
                userData.put("name",       patient.getName());
                userData.put("surname",    patient.getSurname());
                userData.put("gender",     patient.getGender());
                userData.put("birthPlace", patient.getBirthPlace());
                userData.put("birthDate",  patient.getBirthDate());
                userData.put("country",    patient.getCountry());
                userData.put("phone",      patient.getPhone());
                userData.put("centerID",   patient.getCenterID());
                userData.put("email",      patient.getEmail());
                userData.put("password",   patient.getPassword());

                patientsCollection.document(firebaseAuth.getCurrentUser().getUid()).set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Registrazione avvenuta", Toast.LENGTH_SHORT).show();
                                //Se l'autenticazione è riuscita avvia la schermata homepage
                                context.startActivity(new Intent(context, HomepagePatientActivity.class));
                            }
                        })
                        .addOnFailureListener(e -> {
                            //Se l'operazione fallisce mostra un messaggio di registrazione fallita
                            String errorMessage = "Registrazione non avvenuta: ";
                            if(e.getMessage() != null){
                                //E mostra anche un feedback specifico sull'errore
                                errorMessage += e.getMessage();
                                Log.e(TAG, "Messaggio d'Errore: "+errorMessage);
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        });
            }else{
                //Se l'operazione di registrazione non avviene
                String errorMessage = "Autentificazione non avvenuta: ";
                if(task.getException() != null){
                    //E mostra anche un feedback specifico sull'errore
                    errorMessage += task.getException().getMessage();
                    Log.e(TAG, "Messaggio d'Errore: "+errorMessage);
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
        //Ottiene il riferimento al singolo documento (cioè al singolo utente) della collezione "Pazienti"
        //Che viene identificato tramite una stringa non nulla dell'ID dell'utente
        patientsCollection.document(getCurrentUserId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Patient patient = documentSnapshot.toObject(Patient.class);
                        listener.onPazienteDataRead(patient);
                    } else {
                        listener.onPazienteDataRead((Patient) null);
                        Log.d(TAG, "Documento non esistente");
                    }
                } else {
                    Toast.makeText(context, "Lettura non avvenuta", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "get failed with ", task.getException());
                    listener.onPazienteDataRead((Patient) null);
                }
            }
        });
    }

    /**
     * Recupera tutti i pazienti dal database Firestore e notifica il risultato tramite un listener.
     * @param listener Il listener per gestire l'evento di lettura dei dati dei pazienti.
     */
    public void readAll(final OnPazienteDataReadListener listener){
        patientsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Patient> patientsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Patient patient = document.toObject(Patient.class);
                    patientsList.add(patient);
                }
                listener.onPazienteDataRead(patientsList);
            } else {
                Log.d(TAG, "Errore nell'ottenimento dei documenti: ", task.getException());
                listener.onPazienteDataRead((List<Patient>) null);
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
            Log.e(TAG, "L'utente corrente è "+currentUser);
            Log.e(TAG, "L'ID dell'utente corrente è "+currentUser.getUid());
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

    /**
     * Aggiunge una nuova transazione al database.
     *
     * @param transaction l'oggetto Transaction da aggiungere
     * @param userID l'ID dell'utente associato alla transazione
     */
    public void addTransaction(Transaction transaction, String userID) {
        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("userID", userID);
        transactionData.put("title", transaction.getTitle());
        transactionData.put("date", transaction.getDate());
        transactionData.put("category", transaction.getCategory());
        transactionData.put("price", transaction.getPrice());

        transactionCollection.add(transactionData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Transazione aggiunta con ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Errore durante l'aggiunta della transazione", e);
                    }
                });
    }

    //Da controllare
    public void loadTransactions(String userID, final OnTransactionReadListener listener){
        transactionCollection.whereEqualTo("userID", userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Transaction> transactions = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Transaction transaction = document.toObject(Transaction.class);
                                transactions.add(transaction);
                            }
                            // Chiamare il metodo di callback con le transazioni ottenute
                            listener.onDataLoaded(transactions);
                        } else {
                            // Se si verifica un errore, notificare l'ascoltatore
                            listener.onError("Errore nel caricamento delle transazioni");
                            Log.e(TAG, "Details: "+task.getException());
                        }
                    }
                });
    }

    /**
     * Aggiunge una nuova misurazione al database.
     * @param measurement l'oggetto Measurement da aggiungere
     * @param userID l'ID dell'utente associato alla misurazione
     * @param category la categoria della misurazione (es. Pressione, Temperatura, ecc.)
     */
    public void addMeasurement(Measurement measurement, String userID, String category) {
        Map<String, Object> measurementData = new HashMap<>();
        measurementData.put("userID", userID);
        measurementData.put("date",     measurement.getDate());
        measurementData.put("time",     measurement.getTime());
        measurementData.put("value",    measurement.getValue());
        measurementData.put("category", category); // Aggiunge la categoria della misurazione

        measurementCollection.add(measurementData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Misurazione aggiunta con ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Errore durante l'aggiunta della misurazione", e);
                    }
                });
    }

    //Da controllare
    public void loadMeasurements(String userID, final OnMeasurementReadListener listener){
        Log.d(TAG, "loadMeasurements: Loading measurements for user ID: " + userID);
        measurementCollection.whereEqualTo("userID", userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Measurement> measurements = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Measurement measurement = document.toObject(Measurement.class);
                                measurements.add(measurement);
                            }
                            Log.d(TAG, "loadMeasurements: Measurements loaded successfully");
                            // Chiamare il metodo di callback con le misurazioni ottenute
                            listener.onMeasurementsLoaded(measurements);
                        } else {
                            // Se si verifica un errore, notificare l'ascoltatore
                            listener.onMeasurementsLoaded(null);
                            Log.e(TAG, "loadMeasurements: Error loading measurements", task.getException());
                        }
                    }
                });
    }




}
