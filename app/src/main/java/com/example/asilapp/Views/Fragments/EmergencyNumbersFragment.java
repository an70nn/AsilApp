package com.example.asilapp.Views.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;

public class EmergencyNumbersFragment extends Fragment {

    private static final int REQUEST_CALL_PHONE = 1;

    public EmergencyNumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency_numbers, container, false);

        ImageButton callCenterButton = view.findViewById(R.id.ImageButton_Patient_MyCenter_UsefullNumbers_CallCenter);
        ImageButton callAmbulanceButton = view.findViewById(R.id.ImageButton_Patient_MyCenter_UsefullNumbers_CallAmbulance);
        ImageButton callPoliceButton = view.findViewById(R.id.ImageButton_Patient_MyCenter_UsefullNumbers_CallPolice);
        ImageButton callFirefighterButton = view.findViewById(R.id.ImageButton_Patient_MyCenter_UsefullNumbers_CallFirefighter);

        callCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("123456789");
            }
        });

        callAmbulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("118");
            }
        });

        callPoliceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("112");
            }
        });

        callFirefighterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("115");
            }
        });

        return view;
    }

    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        } else {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make phone call
                // You can retry the phone call here
            }
        }
    }
}
