package com.example.asilapp.Views.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.asilapp.Database.DatabaseCentroAccoglienza;
import com.example.asilapp.Models.CentroAccoglienza;
import com.example.asilapp.R;
import com.example.asilapp.Views.Fragments.LoginChoseUserFragments;

public class IntroActivity extends AppCompatActivity {
    //private Button bttnToLoginFragment, bttnToSignupFragment;
    private DatabaseCentroAccoglienza databaseCentroAccoglienza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        databaseCentroAccoglienza = new DatabaseCentroAccoglienza(getApplicationContext());
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

        loadFragment(new LoginChoseUserFragments());

        //Fase di test
        databaseCentroAccoglienza.isCollectionEmpty(databaseCentroAccoglienza.getCollectionReference(), isEmpty -> {
            if(isEmpty){
                //Quando avvio l'applicazione creo manualmente 10 records di Centri d'accoglienza nel database.
                //Se la collezione è vuota
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("01","Casa del Sole", "0", "image_casa_del_sole.jpg", "Bari", "Via Solare 12", "+39 080 1234567", "08:00 - 20:00", "Norma ABC", "Regola XYZ", "La Casa del Sole offre un ambiente accogliente e confortevole per coloro che necessitano di assistenza."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("02", "Centro Accoglienza Santa Maria", "5", "santa_maria.jpg", "Bari", "Via Roma 1", "0801234567", "08:00 - 20:00", "Norma di accoglienza X", "Regola Y", "Un centro accogliente nel cuore di Bari, con personale cordiale e servizi eccellenti."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("03", "Casa Famiglia Aurora", "4", "aurora.jpg", "Mola di Bari", "Via Garibaldi 10", "0802345678", "09:00 - 18:00", "Norma di accoglienza Z", "Regola W", "Un ambiente caldo e accogliente per famiglie bisognose, con assistenza 24 ore su 24."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("04", "Centro Aiuto Fraternità", "3", "aiuto_fraternita.jpg", "Bitonto", "Via Nazionale 5", "0803456789", "07:00 - 22:00", "Norma di accoglienza P", "Regola Q", "Un centro che offre aiuto e sostegno agli indigenti, con servizi di prima necessità e attenzione individuale."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("05", "Casa della Speranza", "5", "speranza.jpg", "Putignano", "Via dei Fiori 7", "0805678901", "08:30 - 19:30", "Norma di accoglienza T", "Regola U", "Un luogo dove la speranza prende vita, offrendo supporto emotivo e risorse pratiche per chi è in difficoltà."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("06", "Centro Solidarietà Gioia", "3", "solidarieta_gioia.jpg", "Gioia del Colle", "Via Matteotti 15", "0806789012", "09:00 - 17:00", "Norma di accoglienza V", "Regola X", "Un centro che promuove la solidarietà e l'inclusione sociale, con attività culturali e supporto comunitario."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("07", "Casa Accoglienza Fratelli", "4", "accoglienza_fratelli.jpg", "Conversano", "Via Verdi 20", "0807890123", "08:00 - 21:00", "Norma di accoglienza Y", "Regola Z", "Una casa che accoglie tutti come fratelli, fornendo cibo, alloggio e assistenza a chi ne ha bisogno."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("08", "Centro Assistenza Maria Ausiliatrice", "5", "maria_ausiliatrice.jpg", "Altamura", "Via Don Bosco 25", "0808901234", "07:30 - 22:30", "Norma di accoglienza A", "Regola B", "Un centro gestito dalle suore salesiane per assistere donne e bambini in difficoltà, con amore e compassione."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("09", "Casa Rifugio Fiori", "4", "rifugio_fiori.jpg", "Rutigliano", "Via della Libertà 30", "0809012345", "09:30 - 18:30", "Norma di accoglienza C", "Regola D", "Un rifugio accogliente circondato da fiori e verde, dove trovare conforto e sostegno in momenti difficili."));
                databaseCentroAccoglienza.addCentroAccoglienza(new CentroAccoglienza("10", "Centro Solidale Arcobaleno", "3", "solidale_arcobaleno.jpg", "Triggiano", "Via Kennedy 35", "0800123456", "10:00 - 20:00", "Norma di accoglienza E", "Regola F", "Un centro che celebra la diversità e promuove l'inclusione, offrendo servizi e attività per tutti."));
            }
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
