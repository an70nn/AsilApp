package com.example.asilapp.Views.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.MedicalParameter;
import com.example.asilapp.R;

import java.util.List;

public class MedicalParameterAdapter extends RecyclerView.Adapter<MedicalParameterAdapter.MedicalParameterViewHolder> {
    private static final String TAG = "MedicalParameterAdapter";
    private List<MedicalParameter> medicalParameterList;

    public MedicalParameterAdapter(List<MedicalParameter> medicalParameterList) {
        this.medicalParameterList = medicalParameterList;
    }

    @NonNull
    @Override
    public MedicalParameterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_parameter, parent, false);
        return new MedicalParameterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalParameterViewHolder holder, int position) {
        MedicalParameter medicalParameter = medicalParameterList.get(position);
        holder.itemName.setText(medicalParameter.getParameterName());

        boolean isExpandable = medicalParameter.isExpandable();
        holder.itemArrow.setImageResource(isExpandable ? R.drawable.baseline_arrow_drop_up_24 : R.drawable.baseline_arrow_drop_down_24);
        holder.itemExpandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        Log.e(TAG, "E' visibile? "+holder.itemExpandableLayout);

        holder.itemLayout.setOnClickListener(v -> {
            medicalParameter.setExpandable(!isExpandable);
            notifyItemChanged(position);
        });

        // Imposta il RecyclerView interno solo se l'elemento è espandibile
        if (isExpandable) {
            MeasurementAdapter measurementAdapter = new MeasurementAdapter(medicalParameter.getMeasurements());
            holder.itemRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.itemRecyclerView.setAdapter(measurementAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return medicalParameterList.size();
    }

    static class MedicalParameterViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName;
        private final ImageView itemArrow;
        private final LinearLayout itemLayout;
        private final RelativeLayout itemExpandableLayout;
        private final RecyclerView itemRecyclerView;

        public MedicalParameterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName             = itemView.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Measurement_Name_MedicalParameter);
            itemArrow            = itemView.findViewById(R.id.ImageView_Doctor_ListPatient_Profile_Measurement_Arrow);
            itemLayout           = itemView.findViewById(R.id.LinearLayout_Doctor_ListPatient_Profile_Measurement);
            itemExpandableLayout = itemView.findViewById(R.id.RelativeLayout_Doctor_ListPatient_Profile_Measurement_ExpandableLayout_Item);
            itemRecyclerView     = itemView.findViewById(R.id.RecyclerView_Doctor_ListPatient_Profile_Measurement_Root);
        }
    }
}