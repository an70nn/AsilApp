package com.example.asilapp.Views.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;
import com.example.asilapp.Models.Patient;
import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnPazienteDataReadListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ProfileFragment extends Fragment{
    private ShapeableImageView profileUserPic;
    private MaterialCardView profileEdit, profileLogout, profileDelete;
    private TextView profileName, profileSurname, profileGender, profileBirthplace, profileBirthdate, profileCountry, profilePhone, profileEmail, profilePassword;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        profileName = view.findViewById(R.id.TextView_Profile_Name);
        profileSurname = view.findViewById(R.id.TextView_Profile_Surname);
        profileGender = view.findViewById(R.id.TextView_Profile_Gender);
        profileBirthdate = view.findViewById(R.id.TextView_Profile_Birthdate);
        profileBirthplace = view.findViewById(R.id.TextView_Profile_Birthplace);
        profileCountry = view.findViewById(R.id.TextView_Profile_Country);
        profilePhone = view.findViewById(R.id.TextView_Profile_Phone);
        profileEmail = view.findViewById(R.id.TextView_Profile_Email);
        profilePassword = view.findViewById(R.id.TextView_Profile_Password);

        profileEdit = view.findViewById(R.id.MaterialCardView_Profile_Editprofile);
        profileLogout = view.findViewById(R.id.MaterialCardView_Profile_Logout);
        profileDelete = view.findViewById(R.id.MaterialCardView_Profile_Deleteprofile);

        profileUserPic = view.findViewById(R.id.ShapeableImageView_Profile_pic);
        profileUserPic.setImageResource(R.drawable.ic_account_circle_black_24dp);

        DatabasePazienti databasePazienti = new DatabasePazienti(getContext());
        databasePazienti.read(new OnPazienteDataReadListener() {
            @Override
            public void onPazienteDataRead(Patient patient) {
                if (patient != null) {
                    profileName.setText(patient.getName());
                    profileSurname.setText(patient.getSurname());
                    profileGender.setText(patient.getGender());
                    profileBirthdate.setText(patient.getBirthDate());
                    profileBirthplace.setText(patient.getBirthPlace());
                    profileCountry.setText(patient.getCountry() );
                    profilePhone.setText(patient.getPhone());
                    profileEmail.setText(patient.getEmail());
                    profilePassword.setText(patient.getPassword());
                } else {
                    Toast.makeText(getContext(), "Paziente non trovato", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPazienteDataRead(List<Patient> patients) {
                //Niente
            }
        });

        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasePazienti.logout();
            }
        });
    }
}
