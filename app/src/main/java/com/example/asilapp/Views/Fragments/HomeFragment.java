package com.example.asilapp.Views.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.widget.Toast;

import com.example.asilapp.R;

public class HomeFragment extends Fragment {

    private Button cityInfoBtn, residenceInfoBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cityInfoBtn = view.findViewById(R.id.city_info_btn);
        cityInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        residenceInfoBtn = view.findViewById(R.id.residence_info_btn);
        residenceInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ResidenceFragment fragment = new ResidenceFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        return view;
    }

    private void openMap() {
        // Controllo se l'app ha i permessi di localizzazione
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Se non ha i permessi, li richiedo
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Se ha i permessi, ottengo la posizione e apro la mappa
            Uri gmmIntentUri = Uri.parse("geo:0,0");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Mostra un messaggio all'utente che non c'è un'app per gestire l'Intent
                Toast.makeText(getActivity(), "Non c'è un'app disponibile per aprire la mappa", Toast.LENGTH_LONG).show();
            }
        }
    }
}
