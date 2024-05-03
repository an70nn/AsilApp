package com.example.asilapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.asilapp.R;

public class IntroActivity extends AppCompatActivity {
    private Button bttnToLoginFragment, bttnToSignupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        bttnToLoginFragment = findViewById(R.id.buttonToLogin);
        bttnToSignupFragment = findViewById(R.id.buttonToSignup);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bttnToLoginFragment.setOnClickListener(v -> {
            //Se premo il bottone "Accedi" mi visualizza il Fragment Login (fragment_login.xml)
            loadFragment(new LoginFragment());

            //Nasconde il bottone ridondante "Accedi" dall'activity principale
            bttnToLoginFragment.setVisibility(View.GONE);

            //Visualizza il bottone per passare alternativamente all'activity di registrazione
            bttnToSignupFragment.setText(R.string.signup_prompt);

            //Per una questione grafica, modifico l'attributo "layout_width" con una nuova larghezza
            //La nuova larghezza sarà impostata su MATCH_PARENT
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bttnToSignupFragment.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            bttnToSignupFragment.setLayoutParams(params);
        });

        bttnToSignupFragment.setOnClickListener(v -> {
            //Se premo il bottone "Registrati" mi visualizza il Fragment Signup (fragment_signup.xml)
            loadFragment(new SignupFragment());

            //Nasconde il bottone ridondante "Registrati" dall'activity principale
            bttnToSignupFragment.setVisibility(View.GONE);

            //Visualizza il bottone per passare alternativamente all'activity di registrazione
            bttnToLoginFragment.setText(R.string.login_prompt);

            //Per una questione grafica, modifico l'attributo "layout_width" con una nuova larghezza
            //La nuova larghezza sarà impostata su MATCH_PARENT
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bttnToLoginFragment.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            bttnToLoginFragment.setLayoutParams(params);
        });
    }

    /**
     * Sostituisce un fragment all'interno di un'attività Android con un nuovo fragment specificato, <BR>
     * utilizzando il FragmentManager e le transazioni di fragment.
     * @param nameFragment - Fragment che si desidera sostituire all'interno del FrameLayout
     */
    private void loadFragment(Fragment nameFragment){
        // FragmentManager permette di visualizzare un Fragment all'interno di un contenitore di Layout.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // L'istanza del FragmentManager gestirà le transazioni (operazioni come add() o replace()  sui fragment)
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_activity_intro, nameFragment)
                .commitNow();
    }
}
