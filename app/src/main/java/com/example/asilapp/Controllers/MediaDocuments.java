package com.example.asilapp.Controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Document;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.DocumentsAdapter;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MediaDocuments extends Fragment {
    private RecyclerView recyclerView;
    private DocumentsAdapter adapter;
    private List<Document> documentList;
    private StorageReference storageReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mymedia_document, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.RecyclerView_Patient_Mymedia_Document);

        documentList = new ArrayList<>();
        adapter      = new DocumentsAdapter(documentList, this::onDocumentClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Inizializza il riferimento allo Storage di Firebase
        storageReference = FirebaseStorage.getInstance().getReference();

        // Carica i documenti da Firebase Storage
        loadDocumentsFromFirebaseStorage();
    }

    private void onDocumentClick(Document document) {
        // Create an intent to view the PDF
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(document.getLink()), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        // Verify that there is an app to handle the intent
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDocumentsFromFirebaseStorage() {
        // Ottieni un riferimento alla cartella contenente i documenti su Firebase Storage
        StorageReference documentsRef = storageReference.child("Documenti");

        // Ottieni la lista dei documenti nella cartella
        documentsRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        // Per ogni documento nella cartella, ottieni il nome e l'URL
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            String documentName = item.getName();
                            String documentUrl = uri.toString();

                            // Crea un oggetto Document e aggiungilo alla lista
                            Document document = new Document(documentName, documentUrl);
                            documentList.add(document);

                            // Notifica all'adapter che i dati sono stati aggiornati
                            adapter.notifyDataSetChanged();
                        }).addOnFailureListener(exception -> {
                            // Gestisci eventuali errori nel recupero dell'URL
                            Toast.makeText(getContext(), "Errore nel caricamento del documento: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                })
                .addOnFailureListener(exception -> {
                    // Gestisci eventuali errori nel recupero della lista dei documenti
                    Toast.makeText(getContext(), "Errore nel caricamento dei documenti: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
