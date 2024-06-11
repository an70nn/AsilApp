package com.example.asilapp.Controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Document;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.DocumentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MediaDocuments extends Fragment {
    private RecyclerView recyclerView;
    private DocumentsAdapter adapter;
    private List<Document> documentList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mymedia_document, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.RecyclerView_Patient_Mymedia_Document);

        documentList = new ArrayList<>();
        adapter      = new DocumentsAdapter(documentList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
