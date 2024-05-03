package com.example.asilapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.R;
import com.example.asilapp.ui.Adapters.DataModel;
import com.example.asilapp.ui.Adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class HealthFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<DataModel> medicalParametersList;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_medical_parameters, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.RecyclerView_Health_Root_MedicalParameters);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Valori di test
        medicalParametersList = new ArrayList<>();
        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("Jams and Honey");
        nestedList1.add("Pickles and Chutneys");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Book");
        nestedList2.add("Office Chair");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Decorates");
        nestedList3.add("Tea Table");

        medicalParametersList.add(new DataModel(nestedList1,"Pressione"));
        medicalParametersList.add(new DataModel(nestedList2,"Temperatura corporea"));
        medicalParametersList.add(new DataModel(nestedList3,"Frequenza cardiaca"));

        adapter = new ItemAdapter(medicalParametersList);
        recyclerView.setAdapter(adapter);
    }
}
