package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnMeasurementReadListener;
import com.example.asilapp.Models.Measurement;
import com.example.asilapp.Views.Adapters.MeasurementAdapter;
import com.example.asilapp.R;

import java.util.ArrayList;
import java.util.List;

public class HealthFragment extends Fragment {
    private static final String TAG = "HealthFragment";

    private RecyclerView recyclerView;
    private MeasurementAdapter measurementAdapter;
    private List<Measurement> measurementList;
    private DatabasePazienti databasePazienti;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RecyclerView_Patient_Health);

        // Inizializzazione del RecyclerView e del suo adapter
        measurementList = new ArrayList<>();
        measurementAdapter = new MeasurementAdapter(measurementList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(measurementAdapter);

        // Inizializzazione del databasePazienti
        databasePazienti = new DatabasePazienti(getContext());

        // Carica le misurazioni per l'utente corrente
        String userID = databasePazienti.getCurrentUserId();
        loadMeasurements(userID);
    }

    /**
     * Carica le misurazioni dell'utente corrente.
     *
     * @param userID ID dell'utente di cui caricare le misurazioni
     */
    private void loadMeasurements(String userID) {
        databasePazienti.loadMeasurements(userID, new OnMeasurementReadListener() {
            @Override
            public void onMeasurementsLoaded(List<Measurement> measurements) {
                if (measurements != null) {
                    measurementList.clear();
                    measurementList.addAll(measurements);
                    measurementAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Nessuna misurazione trovata");
                }
            }
        });
    }
}
