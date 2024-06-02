package com.example.asilapp.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.asilapp.Database.Listeners.OnCheckCollectionEmptyCallback;
import com.example.asilapp.Database.Listeners.OnReadCentroAccoglienzaListener;
import com.example.asilapp.Models.CentroAccoglienza;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DatabaseCentroAccoglienza {
    private static final String TAG = "DatabaseCentroAccoglienza";
    private final Context context;
    private final FirebaseFirestore firestoreDatabase;
    private final CollectionReference collectionReference;

    public DatabaseCentroAccoglienza(Context context){
        this.context = context;
        firestoreDatabase = FirebaseFirestore.getInstance();
        collectionReference = firestoreDatabase.collection("CentroAccoglienza");
    }

    public CollectionReference getCollectionReference(){
        return collectionReference;
    }

    /**
     * Aggiunge un nuovo centro d'accoglienza al database
     * @param centroAccoglienza Il centro di accoglienza da aggiungere al database
     */
    public void addCentroAccoglienza(final CentroAccoglienza centroAccoglienza){
        String documentID = getCurrentCentroAccoglienzaID();

        //Crea un oggetto contenente le informazioni del centro d'accoglienza da memorize poi nel database Firestore
        Map<String, Object> centerData = new HashMap<>();
        centerData.put("id",            documentID);
        centerData.put("name",          centroAccoglienza.getName());
        centerData.put("valueRanking",  centroAccoglienza.getValueRanking());
        centerData.put("image",         centroAccoglienza.getImage());
        centerData.put("city",          centroAccoglienza.getCity());
        centerData.put("address",       centroAccoglienza.getAddress());
        centerData.put("phone",         centroAccoglienza.getPhone());
        centerData.put("openingTime",   centroAccoglienza.getOpeningTime());
        centerData.put("norma",         centroAccoglienza.getNorma());
        centerData.put("rule",          centroAccoglienza.getRule());
        centerData.put("description",   centroAccoglienza.getDescription());

        collectionReference.document(documentID).set(centerData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Registrazione del centro d'accogliennza: "+centerData);
                    }
                })
                .addOnFailureListener(e -> {
                    String errorMessage = "Registrazione del centro d'accoglienza fallita: ";
                    if(e.getMessage() != null){
                        //Mostra un feedback specifico sull'errore
                        errorMessage += e.getMessage();
                        Log.e(TAG, "Messaggio d'Errore: " + errorMessage);
                    }
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Recupera tutti i documenti presenti nella raccolta "CentroAccoglienza"
     * @param listener Il listener per gestire il risultato della lettura dei documenti.
     */
    public void getCentroAccoglienzaAll(final OnReadCentroAccoglienzaListener listener){
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();

                    for (DocumentSnapshot document : myListOfDocuments) {
                        //Converte il documento in un oggetto CentroAccoglienz
                        CentroAccoglienza centroAccoglienza = document.toObject(CentroAccoglienza.class);
                        //Trasferisce i dati dei Centri d'accoglienza nel listener
                        listener.onCentroAccoglienzaDataRead(centroAccoglienza);
                    }
                    Log.d(TAG, "Lista dei Centri d'accoglienza: " + myListOfDocuments);
                }
                else {
                    Log.e(TAG, "Errore nel recupero dei documenti: " + task.getException());
                    listener.onCentroAccoglienzaDataRead(null);
                }
            }
        });
    }
    /**
     * Ottiene i dettagli di un Centro d'accoglienza tramite il suo ID.
     * @param id L'ID del Centro d'accoglienza da recuperare.
     * @param listener Il listener per gestire il risultato della lettura del documento.
     */
    public void getCentroAccoglienzaById(final String id, final OnReadCentroAccoglienzaListener listener){
        collectionReference.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        //Converte il documento in un oggetto CentroAccoglienz
                        CentroAccoglienza centroAccoglienza = documentSnapshot.toObject(CentroAccoglienza.class);
                        //Trasferisce i dati del Centro d'accoglienza nel listener
                        listener.onCentroAccoglienzaDataRead(centroAccoglienza);
                        Log.i(TAG, "Lettura del Centro d'accoglienza con ID:"+id+"="+centroAccoglienza);
                    } else {
                        listener.onCentroAccoglienzaDataRead(null);
                        Log.d(TAG, "Centro d'accoglienza con ID:"+id+" non esistente.");
                    }
                } else {
                    Toast.makeText(context, "Lettura del centro d'accoglienza tramite ID non avvenuta", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Errore in lettura: ", task.getException());
                    listener.onCentroAccoglienzaDataRead(null);
                }
            }
        });
    }

    /**
     * Metodo per controllare se una collezione di Firestore è vuota.
     *
     * @param collectionReference Il riferimento alla collezione di Firestore da controllare.
     * @param callback Callback che gestirà il risultato del controllo.
     */
    public void isCollectionEmpty(final CollectionReference collectionReference, final OnCheckCollectionEmptyCallback callback) {
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && querySnapshot.isEmpty()) {
                        // La collezione è vuota
                        callback.onResult(true);
                        Log.i(TAG, "La collezione "+collectionReference.getPath()+" è vuota.");
                    } else {
                        // La collezione non è vuota
                        callback.onResult(false);
                        Log.i(TAG, "La collezione "+collectionReference.getPath()+" non è vuota.");
                        Log.i(TAG, "Lettura dei documenti...");
                    }
                } else {
                    // Errore nel recupero dei documenti della collezione
                    callback.onResult(false);
                    Log.e(TAG, "Errore nel controllo della collezione: ", task.getException());
                }
            }
        });
    }

    /**
     *  Restituisce l'ID del Centro d'Accoglienza corrente.
     */
    public static String getCurrentCentroAccoglienzaID(){
        UUID uuid = UUID.randomUUID();

        // Converte l'UUID in una stringa e rimuove i trattini
        return uuid.toString().replace("-", "");
    }
}
