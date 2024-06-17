package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.asilapp.Controllers.RatingCenter;
import com.example.asilapp.Database.DatabaseCentroAccoglienza;
import com.example.asilapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CenterFragment extends Fragment {
    private static final String TAG = "CenterFragment";
    private FloatingActionButton centerRating, centerCall;
    private TextView centerName, centerDescription, centerEmail, centerPhone, centerAddress, centerCity, centerProvince, centerRegion, centerOpeningTime;
    private ImageView centerImage;
    private DatabaseCentroAccoglienza databaseCenter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycenter, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        centerRating = view.findViewById(R.id.FloatingActionButton_Patient_Mycenter_Ratingcenter);
        centerCall = view.findViewById(R.id.FloatingActionButton_Patient_Mycenter_Callcenter);
        centerName = view.findViewById(R.id.TextView_Patient_Mycenter_NameCenter);
        centerDescription = view.findViewById(R.id.TextView_Patient_Mycenter_Description);
        centerEmail = view.findViewById(R.id.TextView_Patient_Mycenter_Email);
        centerPhone = view.findViewById(R.id.TextView_Patient_Mycenter_Phone);
        centerAddress = view.findViewById(R.id.TextView_Patient_Mycenter_Address);
        centerCity = view.findViewById(R.id.TextView_Patient_Mycenter_City);
        centerProvince = view.findViewById(R.id.TextView_Patient_Mycenter_Province);
        centerRegion = view.findViewById(R.id.TextView_Patient_Mycenter_Region);
        centerOpeningTime = view.findViewById(R.id.TextView_Patient_Mycenter_TimeOpening);
        centerImage = view.findViewById(R.id.ImageView_Patient_Mycenter_ImageCenter);

        databaseCenter = new DatabaseCentroAccoglienza(getContext());

        // Leggi i dati del centro accoglienza dal database
        if (getArguments() != null) {
            String centerId = getArguments().getString("centerId");
            if (centerId != null) {
                databaseCenter.readCenterById(centerId).addOnSuccessListener(centroAccoglienza -> {
                    // Aggiorna le viste con i dati del centro accoglienza
                    if (centroAccoglienza != null) {
                        centerName.setText(centroAccoglienza.getName());
                        centerDescription.setText(centroAccoglienza.getDescription());
                        centerEmail.setText(centroAccoglienza.getEmail());
                        centerPhone.setText(centroAccoglienza.getPhone());
                        centerAddress.setText(centroAccoglienza.getAddress());
                        centerCity.setText(centroAccoglienza.getCity());
                        centerProvince.setText(centroAccoglienza.getProvince());
                        centerRegion.setText(centroAccoglienza.getDescription());
                        centerOpeningTime.setText(centroAccoglienza.getOpeningTime());
                        // Qui usare un metodo per ottenere l'immagine dallo storage
                        // Ad esempio: centerImage.setImageBitmap(getImageFromStorage(centroAccoglienza.getImage()));
                    } else {
                        Toast.makeText(getContext(), "Centro d'accoglienza non trovato", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Errore durante la lettura del centro d'accoglienza", e);
                    Toast.makeText(getContext(), "Errore durante la lettura del centro d'accoglienza", Toast.LENGTH_SHORT).show();
                });
            }
        } else {
            Log.i(TAG, "Passaggio dei paramentri non avvenuta");
        }

        //Se premuto visualizza il Dialog per l'inserimento della valutazione del centro
        centerRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRatingCenter();
            }
        });
    }

    private void openRatingCenter() {
        // Replace current fragment with RatingCenter fragment
        RatingCenter ratingCenterFragment = new RatingCenter();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Fragment_Patient_Container, ratingCenterFragment);
        transaction.addToBackStack(null); // Optional: Permette di tornare indietro premendo il pulsante back
        transaction.commit();
    }
}
