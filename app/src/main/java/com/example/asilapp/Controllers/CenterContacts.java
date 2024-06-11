package com.example.asilapp.Controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;

public class CenterContacts extends Fragment {
    private TextView contactEmail, contactPhone, contactAddress, contactCity;
    private TextView contactProvince, contactRegion, contactTimeOpening;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycenter_contacts, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        contactEmail       = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_Email);
        contactPhone       = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_Phone);
        contactAddress     = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_Address);
        contactCity        = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_City);
        contactProvince     = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_Province);
        contactRegion      = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_Region);
        contactTimeOpening = view.findViewById(R.id.TextView_Patient_Mycenter_Contacts_Item_TimeOpening);
    }
}
