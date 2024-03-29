package com.example.asilapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asilapp.HomePageActivity;
import com.example.asilapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText loginEmail, loginPassword;
    private Button bttnLogin;
    private TextView loginSwapToSignupActivity;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        loginEmail    = findViewById(R.id.TextInputEditText_Login_Email);
        loginPassword = findViewById(R.id.TextInputEditText_Login_Password);
        bttnLogin     = findViewById(R.id.button_login);
        loginSwapToSignupActivity = findViewById(R.id.textview_swap_to_activity_register);
        mAuth         = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Quando il bottone "Login" viene premuto...
        bttnLogin.setOnClickListener(v -> {
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
                                    Toast.makeText(LoginActivity.this, "Autenticazione riuscita.", Toast.LENGTH_SHORT).show();

                                    Intent homepage = new Intent(getApplicationContext(), HomePageActivity.class);
                                    startActivity(homepage);

                                } else {
                                    Toast.makeText(LoginActivity.this, "Autenticazione fallita.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //Quando la scritta "Registrati" (TextView) della schermata Login viene premuta...
        loginSwapToSignupActivity.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            Toast.makeText(LoginActivity.this, "Apertura schermata di registrazione in corso...", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        });
    }
}