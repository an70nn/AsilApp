package com.example.asilapp.ui.Adapters;

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

import java.util.ArrayList;
import java.util.List;
import com.example.asilapp.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<DataModel> mList;
    private List<String> list = new ArrayList<>();

    public ItemAdapter(List<DataModel> mList){
        this.mList  = mList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_parameters , parent , false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        DataModel model = mList.get(position);
        holder.textView.setText(model.getItemText());

        boolean isExpandable = model.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.arrowImage.setImageResource(R.drawable.baseline_arrow_drop_up_24);
        }else{
            holder.arrowImage.setImageResource(R.drawable.baseline_arrow_drop_down_24);
        }

        NestedAdapter adapter = new NestedAdapter(list);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);
        holder.linearLayout.setOnClickListener(v -> {
            model.setExpandable(!model.isExpandable());
            list = model.getNestedList();
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final TextView textView;
        private final ImageView arrowImage;
        private final RelativeLayout expandableLayout;
        private final RecyclerView nestedRecyclerView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.LinearLayout_Health_LinearLayout_Item);
            textView = itemView.findViewById(R.id.TextView_Health_TextView_Item);
            arrowImage = itemView.findViewById(R.id.ImageView_Health_ImageView_Item);
            expandableLayout = itemView.findViewById(R.id.RelativeLayout_Health_ExpandableLayout_Item);
            nestedRecyclerView = itemView.findViewById(R.id.RecyclerView_Health_Nested_MedicalParameters);
        }
    }
}
