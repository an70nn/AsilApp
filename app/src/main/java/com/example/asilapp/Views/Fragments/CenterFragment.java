package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asilapp.Controllers.CenterContacts;
import com.example.asilapp.Controllers.CenterRule;
import com.example.asilapp.Controllers.PaymentsRecords;
import com.example.asilapp.Controllers.PaymentsReports;
import com.example.asilapp.Database.DatabaseCentroAccoglienza;
import com.example.asilapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CenterFragment extends Fragment {
    private static final String TAG = "CenterFragment";
    private TabLayout centerTabLayout;
    private TabItem centerContacts, centerRule;
    private ViewPager2 centerViewPager;
    private FloatingActionButton centerRating, centerCall;
    private TextView centerName, centerDescription;
    private ImageView centerImage;
    private DatabaseCentroAccoglienza databaseCenter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycenter, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        centerTabLayout   = view.findViewById(R.id.TabLayout_Patient_Mycenter);
        centerContacts    = view.findViewById(R.id.TabItem_Patient_Mycenter_Contacts);
        centerRule        = view.findViewById(R.id.TabItem_Patient_Mycenter_Rule);
        centerViewPager   = view.findViewById(R.id.ViewPager_Patient_Mycenter);
        centerRating      = view.findViewById(R.id.FloatingActionButton_Patient_Mycenter_Ratingcenter);
        centerCall        = view.findViewById(R.id.FloatingActionButton_Patient_Mycenter_Callcenter);
        centerName        = view.findViewById(R.id.TextView_Patient_Mycenter_NameCenter);
        centerImage       = view.findViewById(R.id.ImageView_Patient_Mycenter_ImageCenter);
        centerDescription = view.findViewById(R.id.TextView_Patient_Mycenter_Description);

        databaseCenter    = new DatabaseCentroAccoglienza(getContext());
/*

PRIMA DI PROCEDERE QUA DEVO SISTEMARE IL SIGNUP-FRAGMENT:
Quando il paziente si registra per la prima volta, seleziona il nome del Centro d'accoglienza e
con esso anche l'ID, che vedrò di far passare tramite Bundle dal SignupFragment al CenterFragment.
Comunque, promemoria:
Per vedere meglio come funziona, ricordati che:

INIZIA   con ListPatientsFramgent
CONTINUA con ProfileSpecificPatientFragment
FINISCE  con ProfileAnagrafic


        // Leggi i dati del centro accoglienza dal database
        if (getArguments() != null) {
            String centerId = getArguments().getString("centerId");
            if (centerId != null) {
                databaseCenter.readCenterById(centerId).addOnSuccessListener(centroAccoglienza -> {
                    // Aggiorna le viste con i dati del centro accoglienza
                    if (centroAccoglienza != null) {
                        centerName.setText(centroAccoglienza.getName());
                        centerDescription.setText(centroAccoglienza.getDescription());
                        // Qui puoi usare un metodo per ottenere l'immagine dallo storage
                        // Ad esempio: centerImage.setImageBitmap(getImageFromStorage(centroAccoglienza.getImage()));
                    } else {
                        Toast.makeText(getContext(), "Centro d'accoglienza non trovato", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Errore durante la lettura del centro d'accoglienza", e);
                    Toast.makeText(getContext(), "Errore durante la lettura del centro d'accoglienza", Toast.LENGTH_SHORT).show();
                });
            }
        }else{
            Log.i(TAG, "Passaggio dei paramentri non avvenuta");
        }
*/

        // Configura l'adattatore per il ViewPager2
        centerViewPager.setAdapter(new CenterPageAdapter(this));
        // Imposta i titoli delle schede per i fragment
        new TabLayoutMediator(centerTabLayout, centerViewPager, (tab, position) -> {
            if(position == 0){
                tab.setText(getString(R.string.contatti));
            }else{
                tab.setText(getString(R.string.regolamento));
            }
        }
        ).attach();
    }

    private static class CenterPageAdapter extends FragmentStateAdapter {
        public CenterPageAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new CenterContacts();
            } else {
                return new CenterRule();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
