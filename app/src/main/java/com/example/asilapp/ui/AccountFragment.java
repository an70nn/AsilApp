package com.example.asilapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;
import com.example.asilapp.model.User;
import com.google.firebase.auth.*;

public class AccountFragment extends Fragment {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ImageView userImageView; // Dichiarare userImageView come variabile di istanza

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final TextView nomeTextView = view.findViewById(R.id.nomeTextView);
        final TextView colonna2_1 = view.findViewById(R.id.nomeUtente);
        final TextView cognomeTextView = view.findViewById(R.id.cognomeTextView);
        final TextView colonna2_2 = view.findViewById(R.id.cognomeUtente);
        final TextView dataDiNascitaTextView = view.findViewById(R.id.dataDiNascitaTextView);
        final TextView colonna2_3 = view.findViewById(R.id.dataDiNascitaUtente);
        final TextView paeseDiNascitaTextView = view.findViewById(R.id.luogoDiNascitaTextView);
        final TextView colonna2_4 = view.findViewById(R.id.luogoDiNascitaUtente);
        final TextView luogoDiNascitaTextView = view.findViewById(R.id.paeseDiNascitaTextView);
        final TextView colonna2_5 = view.findViewById(R.id.paeseDiNascitaUtente);
        final TextView centroDiAccoglienzaTextView = view.findViewById(R.id.centroDiAccoglienzaTextView);
        final TextView colonna2_6 = view.findViewById(R.id.centroDiAccoglienzaUtente);
        final TextView recapitoTelefonicoTextView = view.findViewById(R.id.recapitoTelefonicoTextView);
        final TextView colonna2_7 = view.findViewById(R.id.recapitoTelefonicoUtente);
        // Imposta l'immagine dell'utente nell'ImageView
        userImageView = view.findViewById(R.id.userImageView);
        userImageView.setImageResource(R.drawable.ic_account_circle_black_24dp);

        // Imposta i dati nei TextView
        colonna2_1.setText("Testo1 ");
        colonna2_2.setText("Testo2 ");
        colonna2_3.setText("Testo3 Dinamico");
        colonna2_4.setText("Testo4 Dinamico");
        colonna2_5.setText("Testo5 Dinamico");
        colonna2_6.setText("Testo6 Dinamico");
        colonna2_7.setText("Testo7 Dinamico");

    }
}
