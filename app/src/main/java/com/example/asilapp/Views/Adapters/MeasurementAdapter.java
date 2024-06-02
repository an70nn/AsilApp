package com.example.asilapp.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Measurement;
import com.example.asilapp.R;

import java.util.List;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<Measurement> measurementList;

    public MeasurementAdapter(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }

    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_measurement, parent, false);
        return new MeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        Measurement measurement = measurementList.get(position);
        holder.itemDate.setText(measurement.getDate());
        holder.itemTime.setText(measurement.getTime());
        holder.itemValue.setText(measurement.getValue());
    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    static class MeasurementViewHolder extends RecyclerView.ViewHolder {
        TextView itemDate, itemTime, itemValue;

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDate = itemView.findViewById(R.id.TextView_Health_Nested_TextView_Item_MeasurementDate);
            itemTime = itemView.findViewById(R.id.TextView_Health_Nested_TextView_Item_MeasurementTime);
            itemValue = itemView.findViewById(R.id.TextView_Health_Nested_TextView_Item_MeasurementLastValue);
        }
    }
}