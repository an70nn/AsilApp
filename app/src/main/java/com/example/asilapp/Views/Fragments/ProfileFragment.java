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
import com.example.asilapp.Models.Paziente;
import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnPazienteDataReadListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment{

    private ShapeableImageView profileUserPic;
    private MaterialCardView profileEdit, profileLogout, profileDelete;
    private TextView profileFullname, profileName, profileSurname, profileGender, profileBirthplace, profileBirthdate, profileCountry, profilePhone, profileCentreID, profileEmail, profilePassword;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        profileFullname = view.findViewById(R.id.TextView_Profile_Fullname);
        profileName = view.findViewById(R.id.TextView_Profile_Name);
        profileSurname = view.findViewById(R.id.TextView_Profile_Surname);
        profileGender = view.findViewById(R.id.TextView_Profile_Gender);
        profileBirthdate = view.findViewById(R.id.TextView_Profile_Birthdate);
        profileBirthplace = view.findViewById(R.id.TextView_Profile_Birthplace);
        profileCountry = view.findViewById(R.id.TextView_Profile_Country);
        profilePhone = view.findViewById(R.id.TextView_Profile_Phone);
        profileCentreID = view.findViewById(R.id.TextView_Profile_CentreID);
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
            public void onPazienteDataRead(Paziente paziente) {
                if (paziente != null) {
                    profileName.setText(paziente.getName());
                    profileSurname.setText(paziente.getSurname());
                    profileGender.setText(paziente.getGender());
                    profileBirthdate.setText(paziente.getBirthDate());
                    profileBirthplace.setText(paziente.getBirthPlace());
                    profileCountry.setText(paziente.getCountry() );
                    profilePhone.setText(paziente.getPhone());
                    profileCentreID.setText(paziente.getCenterID());
                    profileEmail.setText(paziente.getEmail());
                    profilePassword.setText(paziente.getPassword());
                } else {
                    Toast.makeText(getContext(), "Paziente non trovato", Toast.LENGTH_SHORT).show();
                }
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
