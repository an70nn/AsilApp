package com.example.asilapp.Views.Activity;

import com.example.asilapp.R;
import com.example.asilapp.Views.Fragments.HealthFragment;
import com.example.asilapp.Views.Fragments.HomeFragment;
import com.example.asilapp.Views.Fragments.PaymentsFragment;
import com.example.asilapp.Views.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomePageActivity extends AppCompatActivity {

    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.navigation_health) {
                selectedFragment = new HealthFragment();
            } else if (item.getItemId() == R.id.navigation_account) {
                selectedFragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.navigation_payments) {
                selectedFragment = new PaymentsFragment();
            }


            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            return true;
        });

        // Carica il fragment Home come default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }
}
