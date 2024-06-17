package com.example.asilapp.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.asilapp.Database.Listeners.OnCheckCollectionEmptyCallback;
import com.example.asilapp.Database.Listeners.OnReadCentroAccoglienzaListener;
import com.example.asilapp.Models.CentroAccoglienza;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class DatabaseCentroAccoglienza {
    private static final String TAG = "DatabaseCentroAccoglienza";
    private static final String NAME_COLLECTION = "CentroAccoglienza";
    private final Context context;
    private final FirebaseFirestore firestoreDatabase;
    private final CollectionReference collectionReference;
    private final StorageReference storageReference;
    private List<CentroAccoglienza> centerList;

    public DatabaseCentroAccoglienza(Context context){
        this.context = context;
        firestoreDatabase = FirebaseFirestore.getInstance();
        collectionReference = firestoreDatabase.collection(NAME_COLLECTION);
        this.storageReference = getStorageReference(NAME_COLLECTION);
    }

    public CollectionReference getCollectionReference(){
        return collectionReference;
    }

    public StorageReference getStorageReference(String nameCollection){
        return storageReference;
    }

    /**
     * Aggiunge un nuovo Centro d'accoglienza alla collezione Firestore.
     *
     * @param centroAccoglienza L'oggetto CentroAccoglienza da aggiungere.
     * @return Un Task contenente il CentroAccoglienza aggiornato con il suo ID.
     */
    public Task<CentroAccoglienza> addCenter(CentroAccoglienza centroAccoglienza) {

        /* NUOVO METODO
        * Differenza con quello precedente:
        * Firestore gestisce automaticamente gli ID dei documenti.
        * Un approccio meno soggetto ad errori.
        * Il metodo precedente aveva un problema di affidabilità: collisione fra vari ID.
        */

        //Permette di creare e controllare manualemente il completamento di un Task,
        //utile per le operazioni asincrone effettuate sul database firebase
        TaskCompletionSource<CentroAccoglienza> taskCompletionSource = new TaskCompletionSource<>();

        Map<String, Object> newCenterData = new HashMap<>();
        newCenterData.put("name",           centroAccoglienza.getName());
        newCenterData.put("valueRanking",   centroAccoglienza.getValueRanking());
        newCenterData.put("image",          centroAccoglienza.getImage());
        newCenterData.put("city",           centroAccoglienza.getCity());
        newCenterData.put("province",       centroAccoglienza.getProvince());
        newCenterData.put("region",         centroAccoglienza.getRegion());
        newCenterData.put("address",        centroAccoglienza.getAddress());
        newCenterData.put("phone",          centroAccoglienza.getPhone());
        newCenterData.put("openingTime",    centroAccoglienza.getOpeningTime());
        newCenterData.put("norma",          centroAccoglienza.getNorma());
        newCenterData.put("rule",           centroAccoglienza.getRule());
        newCenterData.put("description",    centroAccoglienza.getDescription());
        newCenterData.put("email",          centroAccoglienza.getEmail());

        //Aggiunge un nuovo documento senza specificare il suo ID
        collectionReference.add(newCenterData)
                .addOnSuccessListener(documentReference ->  {
                   String documentID = documentReference.getId();

                    // Ora il documento ha il suo ID generato settato automaticamente
                    Log.i(TAG, "Registrazione del centro d'accoglienza con ID: " + documentID);

                    // Aggiorna l'oggetto CentroAccoglienza con l'ID generato
                    centroAccoglienza.setId(documentID);

                    // Aggiorna il documento su Firebase con il campo ID
                    documentReference.update("id", documentID)
                            .addOnSuccessListener(aVoid -> taskCompletionSource.setResult(centroAccoglienza))
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Aggiornamento dell'ID del centro d'accoglienza fallito: " + e.getMessage());
                                taskCompletionSource.setException(e);
                            });
                })
                .addOnFailureListener(e ->  {
                    Log.e(TAG, "Registrazione del centro d'accoglienza fallita: " + e.getMessage());
                    Toast.makeText(context, "Registrazione del centro d'accoglienza fallita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    taskCompletionSource.setException(e);
                    });

            // Restituisce il Task
            return taskCompletionSource.getTask();
        }

    /**
     * Recupera un oggetto CentroAccoglienza dal database Firestore dato il suo ID.
     *
     * @param centerId L'ID del centro d'accoglienza da recuperare.
     * @return Un oggetto Task che rappresenta l'operazione di lettura del centro d'accoglienza.
     *         Il Task restituirà il CentroAccoglienza se il documento esiste nel database,
     *         altrimenti restituirà null.
     * @throws FirebaseFirestoreException Se si verifica un errore durante l'operazione di lettura dal database.
     */
    public Task<CentroAccoglienza> readCenterById(String centerId){
        // Riferimento al documento del centro d'accoglienza specificato
        DocumentReference centroRef = collectionReference.document(centerId);

        // Esegue l'operazione di lettura del documento
        return centroRef.get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Il documento esiste, quindi restituisci un oggetto CentroAccoglienza
                    CentroAccoglienza centro = document.toObject(CentroAccoglienza.class);
                    Log.i(TAG, "Lettura del Centro d'accoglienza con ID: "+centerId);
                    return centro;
                } else {
                    Log.d(TAG, "Centro d'accoglienza con ID:"+centerId+" non esistente.");
                    return null;
                }
            } else {
                Toast.makeText(context, "Lettura del centro d'accoglienza tramite ID non avvenuta", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Errore in lettura: ", task.getException());
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    /**
     * Recupera tutti i centri d'accoglienza presenti nel database Firestore.
     *
     * @return Un oggetto Task che rappresenta l'operazione di lettura di tutti i centri d'accoglienza.
     *         Il Task restituirà una lista di oggetti CentroAccoglienza se l'operazione ha successo,
     *         altrimenti restituirà un'eccezione.
     * @throws FirebaseFirestoreException Se si verifica un errore durante l'operazione di lettura dal database.
     */
    public Task<List<CentroAccoglienza>> getAllCenter() {
        // Esegue la query per recuperare tutti i documenti dalla collezione Centri d'accoglienza
        return collectionReference.get().continueWith(task -> {
            if (task.isSuccessful()) {
                // Recupera il risultato della query
                QuerySnapshot querySnapshot = task.getResult();

                // Inizializza una lista per contenere tutti i centri d'accoglienza
                centerList = new ArrayList<>();

                // Itera su tutti i documenti nella collezione
                for (QueryDocumentSnapshot document : querySnapshot) {
                    // Converte il documento in un oggetto CentroAccoglienza e lo aggiunge alla lista
                    CentroAccoglienza centro = document.toObject(CentroAccoglienza.class);
                    centerList.add(centro);
                }

                // Restituisce la lista di centri d'accoglienza
                Log.d(TAG, "Lista dei Centri d'accoglienza: "+centerList);
                return centerList;
            } else {
                // Gestisce eventuali errori durante l'operazione di lettura
                Log.e(TAG, "Errore nel recupero dei documenti: " + task.getException());
                throw task.getException();
            }
        });
    }

    /**
     * Metodo per controllare se una collezione di Firestore è vuota.
     *
     * @param collectionReference Il riferimento alla collezione di Firestore da controllare.
     * @return Un oggetto Task<Boolean> che rappresenta l'operazione di controllo sulla collezione.
     */
    public Task<Boolean> isCollectionEmpty(final CollectionReference collectionReference) {
        // Crea un oggetto TaskCompletionSource per completare il task in base al risultato del controllo
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        // Esegue l'operazione di lettura della collezione
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && querySnapshot.isEmpty()) {
                        // La collezione è vuota, completa il task con il valore true
                        taskCompletionSource.setResult(true);
                        Log.i(TAG, "La collezione " + collectionReference.getPath() + " è vuota.");
                    } else {
                        // La collezione non è vuota, completa il task con il valore false
                        taskCompletionSource.setResult(false);
                        Log.i(TAG, "La collezione " + collectionReference.getPath() + " non è vuota.");
                    }
                } else {
                    // Errore nel recupero dei documenti della collezione, completa il task con un'eccezione
                    taskCompletionSource.setException(Objects.requireNonNull(task.getException()));
                    Log.e(TAG, "Errore nel recupero dei documenti della collezione: ", task.getException());
                }
            }
        });

        // Restituisce il Task<Boolean> risultante dall'operazione di controllo sulla collezione
        return taskCompletionSource.getTask();
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
     *
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

    public void getCenterIdFromName(String centerName, OnGetCenterIdListener listener) {
        collectionReference.whereEqualTo("name", centerName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Se viene trovato almeno un centro con il nome specificato,
                            // prendi l'ID del primo centro (assumendo che non ci siano nomi duplicati)
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            String centerId = document.getId();
                            listener.onCenterIdReceived(centerId);
                        } else {
                            // Se non viene trovato alcun centro con il nome specificato
                            listener.onCenterIdReceived(null);
                        }
                    } else {
                        // Gestisci eventuali errori durante il recupero dei dati
                        Log.e(TAG, "Errore durante il recupero dell'ID del centro: ", task.getException());
                        listener.onCenterIdReceived(null);
                    }
                });
    }

    // Interfaccia per il listener del risultato dell'ID del centro
    public interface OnGetCenterIdListener {
        void onCenterIdReceived(String centerId);
    }

}