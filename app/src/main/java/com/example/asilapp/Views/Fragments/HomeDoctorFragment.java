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

public class HomeDoctorFragment extends Fragment {
    private ImageButton doctorChangeLanguage;
    private MaterialCardView doctorAddMeasurement, doctorListPatients;
    private FragmentTransaction transaction;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_doctor, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        doctorChangeLanguage = view.findViewById(R.id.ImageButton_Home_Doctor_Language);
        doctorAddMeasurement = view.findViewById(R.id.MaterialCardView_Home_Doctor_AddMeasurement);
        doctorListPatients = view.findViewById(R.id.MaterialCardView_Home_Doctor_ListPatients);

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Override
    public void onStart() {
        super.onStart();

        doctorAddMeasurement.setOnClickListener(v -> {
            //Aggiungere schermata apposita
            ListPatientsFragment fragment = new ListPatientsFragment();
            transaction.replace(R.id.Fragment_Doctor_Container, fragment);
            transaction.commit();
        });

        doctorListPatients.setOnClickListener(v -> {
            ListPatientsFragment fragment = new ListPatientsFragment();
            transaction.replace(R.id.Fragment_Doctor_Container, fragment);
            transaction.commit();
        });
    }
}
