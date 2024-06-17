package com.example.asilapp.Views.Activity;

import com.example.asilapp.R;
import com.example.asilapp.Views.Fragments.HealthFragment;
import com.example.asilapp.Views.Fragments.HomePatientFragment;
import com.example.asilapp.Views.Fragments.PaymentsFragment;
import com.example.asilapp.Views.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * HomepagePatientActivity funge da container per diversi fragment visualizzati in base all'elemento selezionato nella BottomNavigationView.
 */
public class HomepagePatientActivity extends AppCompatActivity {
    private Fragment selectedFragment = null;
    private ImageButton homepageGoHomePage;
    private TextView homepageTitlePage;
    private String centerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_container);

        Toolbar toolbar = findViewById(R.id.Toolbar_Home_Patient);
        setSupportActionBar(toolbar);

        homepageGoHomePage = findViewById(R.id.ImageButton_Patient_Toolbar_GoHomePage);
        homepageTitlePage  = findViewById(R.id.TextView_Patient_Toolbar_NamePage);

        // Ottieni centerId dall'intent
        centerId = getIntent().getStringExtra("centerId");


        BottomNavigationView navigation = findViewById(R.id.BottomNavigationView_Patient);
        navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomePatientFragment();
                setToolbarTitle("Pagina iniziale");
            }
            else if (item.getItemId() == R.id.navigation_health) {
                selectedFragment = new HealthFragment();
                setToolbarTitle("Sezione salute");
            }
            else if (item.getItemId() == R.id.navigation_account) {
                selectedFragment = new ProfileFragment();
                setToolbarTitle("Sezione profilo");
            }
            else if (item.getItemId() == R.id.navigation_payments) {
                selectedFragment = new PaymentsFragment();
                setToolbarTitle("Sezione pagamenti");
            }
            else{
                selectedFragment = new HomePatientFragment();
                setToolbarTitle("Pagina iniziale");
            }

            //Sostituisce il fragment corrente con quello selezionato
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Patient_Container, selectedFragment).commit();
            }
            return true;
        });

        //Configura il bottone presente nel Toolbar come pulsante per tornare alla HomePage
        homepageGoHomePage.setOnClickListener(v -> {
            selectedFragment = new HomePatientFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Patient_Container, selectedFragment).commit();
            setToolbarTitle("Home Page");
            navigation.setSelectedItemId(R.id.navigation_home); // Aggiorna lo stato del BottomNavigationView
        });

        //Carica il fragment HomePatientFragment come default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Patient_Container, new HomePatientFragment()).commit();
        }
    }

    /**
     * Imposta il titolo della pagina nel Toolbar.
     * @param title Il nuovo titolo della Toolbar.
     */
    private void setToolbarTitle(String title) {
        homepageTitlePage.setText(title);
    }

    public String getCenterId() {
        return centerId;
    }

}
