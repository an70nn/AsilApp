package com.example.asilapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.asilapp.R;
import com.example.asilapp.persistence.DatabaseManager;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {
    private TextInputEditText loginEmail, loginPassword;
    private Button bttnLogin;
    private DatabaseManager databaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);

        //Inizializza i componeneti UI
        loginEmail      = view.findViewById(R.id.TextInputEditText_Login_Email);
        loginPassword   = view.findViewById(R.id.TextInputEditText_Login_Password);
        bttnLogin       = view.findViewById(R.id.button_login);

        //Inizializza l'istanza per la gestione del database
        databaseManager = new DatabaseManager(requireContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Quando il bottone "Login" viene premuto...
        bttnLogin.setOnClickListener(v -> {
            //Ottiene il testo dai campi di testo
            //Se contiene qualcosa (diverso da null) restituisce il contenuto nella costante
            //Altrimenti restituisce una stringa vuota
            final String EMAIL_RECORD    = loginEmail.getText()    != null ? loginEmail.getText().toString().trim() : "";
            final String PASSWORD_RECORD = loginPassword.getText() != null ? loginPassword.getText().toString().trim() : "";

            //Controlla se i campi email o password sono vuoti
            if(EMAIL_RECORD.isEmpty() || PASSWORD_RECORD.isEmpty()){
                //Mostra un messaggio Toast (a tutto schermo) se i campi email o password sono vuoti
                Toast.makeText(getActivity(), "ACCESSO FALLITO: Campi vuoti", Toast.LENGTH_SHORT).show();
            }else{
                //Chiama il metodo login in DatabaseManager se sia email che password sono forniti.
                databaseManager.login(EMAIL_RECORD, PASSWORD_RECORD);
            }
        });
    }
}

