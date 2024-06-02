package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.asilapp.R;
import com.google.android.material.card.MaterialCardView;

public class HomePatientFragment extends Fragment {

    private ImageButton patientChangeLanguage;
    private MaterialCardView patientServiceCenter, patientServiceInfo, patientServiceInterestingSities, patientServiceVideos, patientServiceNumbers, patientServiceDocuments;
    private FragmentTransaction transaction;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_patient, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        patientServiceCenter            = view.findViewById(R.id.MaterialCardView_Home_Patient_CenterInfo);
        patientServiceInfo              = view.findViewById(R.id.MaterialCardView_Home_Patient_GeneralInfo);
        patientServiceInterestingSities = view.findViewById(R.id.MaterialCardView_Home_Patient_InterestingSites);
        patientServiceVideos            = view.findViewById(R.id.MaterialCardView_Home_Patient_Video);
        patientServiceNumbers           = view.findViewById(R.id.MaterialCardView_Home_Patient_SOSNumbers);
        patientServiceDocuments         = view.findViewById(R.id.MaterialCardView_Home_Patient_Documents);

        patientChangeLanguage           = view.findViewById(R.id.ImageButton_Home_Patient_Language);

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Override
    public void onStart() {
        super.onStart();


        patientServiceCenter.setOnClickListener(v -> {
            ResidenceFragment fragment = new ResidenceFragment();
            transaction.replace(R.id.Fragment_Patient_Container, fragment);
            transaction.commit();
        });

        patientServiceInfo.setOnClickListener(v -> {

        });

        patientServiceInterestingSities.setOnClickListener(v -> {

        });

        patientServiceVideos.setOnClickListener(v -> {

        });

        patientServiceNumbers.setOnClickListener(v -> {

        });

        patientServiceDocuments.setOnClickListener(v -> {

        });

    }
}