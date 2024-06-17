package com.example.asilapp.Views.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.asilapp.Database.DatabaseCentroAccoglienza;
import com.example.asilapp.Models.CentroAccoglienza;
import com.example.asilapp.R;
import com.example.asilapp.Views.Fragments.*;
import com.google.firebase.firestore.CollectionReference;

public class IntroActivity extends AppCompatActivity {
    //private Button bttnToLoginFragment, bttnToSignupFragment;
    private final String TAG = "IntroActivity";
    private DatabaseCentroAccoglienza databaseCentroAccoglienza;
    private CollectionReference centroAccoglienzaCollection;
    private int currentFragmentIndex = 0;
    private Fragment[] fragments = new Fragment[]{
            new WelcomeFragmentOne(), // Il tuo primo fragment
            new WelcomeFragmentTwo(), // Il tuo secondo fragment
            new WelcomeFragmentThree(), // Il tuo terzo fragment
            new WelcomeFragmentFour(),
            new LoginChoseUserFragments()  // Ultimo fragment
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        databaseCentroAccoglienza = new DatabaseCentroAccoglienza(getApplicationContext());
        loadFragment(fragments[currentFragmentIndex]); // Carica il primo fragment

        Button buttonAvanti = findViewById(R.id.button_avanti);
        buttonAvanti.setOnClickListener(v -> goToNextFragment());
    }

    private void goToNextFragment() {
        if (currentFragmentIndex < fragments.length - 1) {
            currentFragmentIndex++;
            replaceFragment(fragments[currentFragmentIndex]);
        }

        if (currentFragmentIndex == fragments.length - 1) {
            Button buttonAvanti = findViewById(R.id.button_avanti);
            buttonAvanti.setVisibility(View.GONE); // Nasconde il bottone
        }
    }

    public void replaceFragment(Fragment fragment) {
        if(!isFinishing() && !isDestroyed()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.frameLayout_activity_intro, fragment);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //loadFragment(new LoginChoseUserFragments());

        // Recupera il riferimento alla collezione di centri di accoglienza
        centroAccoglienzaCollection = databaseCentroAccoglienza.getCollectionReference();

        // Controlla se la collezione è vuota
        databaseCentroAccoglienza.isCollectionEmpty(centroAccoglienzaCollection)
                .addOnSuccessListener(isEmpty -> {
                    if (isEmpty) {
                        // Aggiungi dei centri d'accoglienza d'esempio
                        Log.i(TAG, "Generazione automatica di Centri d'accoglienza...");
                        databaseCentroAccoglienza.addCenter(new CentroAccoglienza(null, "Centro Accoglienza Bari Sud", "4.5", "img/bari_sud.jpg", "Bari", "Bari", "Puglia", "Via delle Palme, 123", "+39 080 1234567", "08:00 - 20:00", "Normativa XYZ", "Regola ABC", "Centro accoglienza situato nel sud di Bari, offre supporto ai rifugiati e richiedenti asilo.", "info@barisudaccoglienza.it"));
                        databaseCentroAccoglienza.addCenter(new CentroAccoglienza(null, "Centro Accoglienza Bari Centro", "4.2", "img/bari_centro.jpg", "Bari", "Bari", "Puglia", "Via Roma, 456", "+39 080 7654321", "09:00 - 18:00", "Normativa ABC", "Regola XYZ", "Centro situato nel centro storico di Bari, fornisce assistenza ai nuovi arrivati.", "info@baricentroaccoglienza.it"));
                        databaseCentroAccoglienza.addCenter(new CentroAccoglienza(null, "Centro Accoglienza Altamura", "4.7", "img/altamura.jpg", "Altamura", "Bari", "Puglia", "Via dei Lecci, 789", "+39 080 246810", "07:30 - 22:00", "Normativa DEF", "Regola GHI", "Situato ad Altamura, il centro accoglie rifugiati con un'attenzione particolare all'inclusione sociale.", "info@altamuraaccoglienza.it"));
                        databaseCentroAccoglienza.addCenter(new CentroAccoglienza(null, "Centro Accoglienza Molfetta", "4.4", "img/molfetta.jpg", "Molfetta", "Bari", "Puglia", "Via Garibaldi, 1011", "+39 080 112233", "08:30 - 19:00", "Normativa GHI", "Regola DEF", "Centro accoglienza situato a Molfetta, supporta i richiedenti asilo nella fase di integrazione.", "info@molfettaaccoglienza.it"));
                        databaseCentroAccoglienza.addCenter(new CentroAccoglienza(null, "Centro Accoglienza Monopoli", "4.6", "img/monopoli.jpg", "Monopoli", "Bari", "Puglia", "Lungomare, 12", "+39 080 334455", "09:00 - 17:00", "Normativa LMN", "Regola OPQ", "Situato a Monopoli, offre supporto integrato per rifugiati e richiedenti asilo.", "info@monopoliaccoglienza.it"));
                    } else {
                        // Legge i documenti e prosegue
                        Log.i(TAG, "Lettura dei documenti...");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Errore nel controllo della collezione di "+centroAccoglienzaCollection.getPath()+".", e);
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
