package com.example.asilapp.Views.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.asilapp.Views.Fragments.ListPatientsFragment;
import com.jakewharton.threetenabp.AndroidThreeTen;

import com.example.asilapp.R;
import com.example.asilapp.Views.Fragments.HomeDoctorFragment;
import com.example.asilapp.Views.Fragments.HomePatientFragment;
import com.example.asilapp.Views.Fragments.PaymentsFragment;
import com.example.asilapp.Views.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * HomepageDoctorActivity funge da container per diversi fragment visualizzati in base all'elemento selezionato nella BottomNavigationView.
 */
public class HomepageDoctorActivity extends AppCompatActivity {
    private Fragment selectedFragment = null;
    private ImageButton homepageGoHomePage;
    private TextView homepageTitlePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_container);
        AndroidThreeTen.init(this);

        Toolbar toolbar = findViewById(R.id.Toolbar_Home_Doctor);
        setSupportActionBar(toolbar);

        homepageGoHomePage = findViewById(R.id.ImageButton_Doctor_Toolbar_GoHomePage);
        homepageTitlePage  = findViewById(R.id.TextView_Doctor_Toolbar_NamePage);

        BottomNavigationView navigation = findViewById(R.id.BottomNavigationView_Doctor);
        navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomeDoctorFragment();
                setToolbarTitle("Pagina iniziale");
            }
            else if (item.getItemId() == R.id.navigation_account) {
                selectedFragment = new ProfileFragment();
                setToolbarTitle("Sezione profilo");
            }
            else if (item.getItemId() == R.id.navigation_patients) {
                selectedFragment = new ListPatientsFragment();
                setToolbarTitle("Sezione pazienti");
            }
            else{
                selectedFragment = new HomeDoctorFragment();
                setToolbarTitle("Pagina iniziale");
            }

            //Sostituisce il fragment corrente con quello selezionato
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Doctor_Container, selectedFragment).commit();
            }
            return true;
        });

        //Configura il bottone presente nel Toolbar come pulsante per tornare alla HomePage
        homepageGoHomePage.setOnClickListener(v -> {
            selectedFragment = new HomePatientFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Doctor_Container, selectedFragment).commit();
            setToolbarTitle("Home Page");
            navigation.setSelectedItemId(R.id.navigation_home); // Aggiorna lo stato del BottomNavigationView
        });

        //Carica il fragment HomePatientFragment come default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Doctor_Container, new HomeDoctorFragment()).commit();
        }
    }

    /**
     * Imposta il titolo della pagina nel Toolbar.
     * @param title Il nuovo titolo della Toolbar.
     */
    private void setToolbarTitle(String title) {
        homepageTitlePage.setText(title);
    }
}
