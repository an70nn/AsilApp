package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.Database.DatabaseCentroAccoglienza;
import com.example.asilapp.Database.Listeners.OnReadCentroAccoglienzaListener;
import com.example.asilapp.Models.CentroAccoglienza;
import com.example.asilapp.R;

public class ResidenceFragment extends Fragment {
    private static final String TAG = "ResidenceFragment";
    private static final String ARG_CENTER_ID = "centerId";
    private String centerId;
    private TextView residenceName;
    private RatingBar residenceRanking;
    private ImageView residenceImage;
    private TextView residenceCity, residenceAddress, residencePhone, residenceOpeningTime;
    private DatabaseCentroAccoglienza databaseCentroAccoglienza;

    /*
    public static ResidenceFragment newInstance(String centerId) {
        ResidenceFragment fragment = new ResidenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CENTER_ID, centerId);
        fragment.setArguments(args);
        return fragment;
    }

     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_residence, container, false);

        /*
        // Ricevi gli argomenti
        Bundle args = getArguments();
        String centerId = args.getString(ARG_CENTER_ID);
        // Ora puoi utilizzare centerId come necessario

         */
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        residenceName = view.findViewById(R.id.TextView_Residence_Name);
        residenceRanking = view.findViewById(R.id.RatingBar_Residence_Ranking);
        residenceImage = view.findViewById(R.id.ImageView_Residence_ImageCenter);
        residenceImage.setImageResource(R.drawable.center_example);
        residenceCity = view.findViewById(R.id.TextView_Residence_City);
        residenceAddress = view.findViewById(R.id.TextView_Residence_Address);
        residencePhone = view.findViewById(R.id.TextView_Residence_Phone);
        residenceOpeningTime = view.findViewById(R.id.TextView_Residence_OpeningTime);

        databaseCentroAccoglienza = new DatabaseCentroAccoglienza(getContext());

        if (getArguments() != null) {
            centerId = getArguments().getString(ARG_CENTER_ID);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            String centerId = getArguments().getString("centerId");
            Log.i(TAG, "Passaggio dei paramentri avvenuta");
            if (centerId != null) {
                //Lettura dal database e settaggio dei dati sui TextView
                databaseCentroAccoglienza.getCentroAccoglienzaById(centerId, new OnReadCentroAccoglienzaListener() {
                    @Override
                    public void onCentroAccoglienzaDataRead(CentroAccoglienza centroAccoglienza) {
                        if (centroAccoglienza != null) {
                            residenceName.setText(centroAccoglienza.getName());
                            residenceCity.setText(centroAccoglienza.getCity());
                            residenceAddress.setText(centroAccoglienza.getAddress());
                            residencePhone.setText(centroAccoglienza.getPhone());
                            residenceOpeningTime.setText(centroAccoglienza.getOpeningTime());
                        } else {
                            Toast.makeText(getContext(), "Centro d'accoglienza non trovato", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        else{
            Log.i(TAG, "Passaggio dei paramentri non avvenuta");
        }
    }
}
