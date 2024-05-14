package com.example.asilapp.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.R;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private List<String> mList;

    public NestedAdapter(List<String> mList){
        this.mList = mList;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_medical_parameters, parent , false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.measurementDate.setText(mList.get(position));
        holder.measurementTime.setText(mList.get(position));
        holder.measurementLastValue.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private TextView measurementDate, measurementTime, measurementLastValue;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            measurementDate = itemView.findViewById(R.id.TextView_Health_Nested_TextView_Item_MeasurementDate);
            measurementTime = itemView.findViewById(R.id.TextView_Health_Nested_TextView_Item_MeasurementTime);
            measurementLastValue = itemView.findViewById(R.id.TextView_Health_Nested_TextView_Item_MeasurementLastValue);
        }
    }
}
