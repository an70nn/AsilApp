package com.example.asilapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asilapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText loginEmail, loginPassword;
    private Button bttnLogin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        loginEmail    = findViewById(R.id.TextInputEditText_Login_Email);
        loginPassword = findViewById(R.id.TextInputEditText_Login_Password);
        bttnLogin     = findViewById(R.id.button_login);
        mAuth         = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Controlla se l'utente ha effettuato l'accesso e aggiorna l'interfaccia utente di conseguenza.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Lo faccio dopo
        }

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String    EMAIL_RECORD = loginEmail.getText().toString().trim();
                final String PASSWORD_RECORD = loginPassword.getText().toString().trim();

                if(EMAIL_RECORD.isEmpty() || PASSWORD_RECORD.isEmpty()){
                    Toast.makeText(LoginActivity.this, "ACCESSO FALLITO: Campi vuoti", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(EMAIL_RECORD, PASSWORD_RECORD)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this, "Autenticazione riuscita.", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        /*
                                        Lo verifico dopo
                                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("user");
                                        */

                                        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Autenticazione fallita.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}