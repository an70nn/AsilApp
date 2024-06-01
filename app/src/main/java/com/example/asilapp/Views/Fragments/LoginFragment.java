package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.asilapp.Database.DatabaseDottori;
import com.example.asilapp.R;
import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Views.Activity.IntroActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private TextInputEditText loginEmail, loginPassword;
    private Button loginButton;
    private final String typeUser;
    private ImageButton loginGoBack;
    private TextView loginSwitchToSignup;
    private DatabasePazienti databasePazienti;
    private DatabaseDottori databaseDottori;

    public LoginFragment(String typeUser){
        this.typeUser = typeUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loginGoBack             = view.findViewById(R.id.ImageButton_Login_BackArrow);
        loginEmail              = view.findViewById(R.id.TextInputEditText_Login_Email);
        loginPassword           = view.findViewById(R.id.TextInputEditText_Login_Password);
        loginButton             = view.findViewById(R.id.Button_Login);
        loginSwitchToSignup     = view.findViewById(R.id.TextView_Login_SwitchToSignuFragment);
        //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        databaseDottori         = new DatabaseDottori(requireContext());
        databasePazienti        = new DatabasePazienti(requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();

        loginSwitchToSignup.setOnClickListener(v -> {
            Log.i(TAG, "Passaggio alla schermata di registrazione");
            ((IntroActivity) requireActivity()).replaceFragment(new SignupFragment());
        });

        loginGoBack.setOnClickListener(V -> {
            Log.i(TAG, "Passaggio alla schermata precedente");
            ((IntroActivity) requireActivity()).replaceFragment(new LoginChoseUserFragments());
        });

        loginButton.setOnClickListener(v -> {

            final String EMAIL_RECORD    = loginEmail.getText().toString().trim();
            final String PASSWORD_RECORD = loginPassword.getText().toString().trim();

            //Controlla se i campi email o password sono vuoti
            if(EMAIL_RECORD.isEmpty() || PASSWORD_RECORD.isEmpty()){
                //Mostra un messaggio Toast (a tutto schermo) se i campi email o password sono vuoti
                Toast.makeText(getActivity(), "ACCESSO FALLITO: Campi vuoti", Toast.LENGTH_SHORT).show();
            }else{
                if(typeUser.equals("Patient")){

                    //PROMEMORIA---------------------------------------------------------------------------------------------------------
                    //Ricreare la classe DatabasePazienti e DatabaseDottori
                    /*Il metodo log-In-As per come è strutturato permette l'accesso a due tipi di utenti.
                    * Conviene separare il database in due classi (paziente e dottore), eventualmente
                    * aggiungendo dati di testi direttaemnente nelle classi Database, oppure, creare un unico
                    * Database chimaato DatabaseUsers? */

                    databasePazienti.logInAs("Patient", EMAIL_RECORD, PASSWORD_RECORD);
                    Log.i(TAG, "Passaggio alla schermata homepage del paziente");
                } else if (typeUser.equals("Doctor")){
                    databaseDottori.login(EMAIL_RECORD, PASSWORD_RECORD);
                    Log.i(TAG, "Passaggio alla schermata homepage del dottore");
                } else {
                    // Nessun RadioButton selezionato
                    Toast.makeText(getActivity(), "Seleziona un tipo di account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

