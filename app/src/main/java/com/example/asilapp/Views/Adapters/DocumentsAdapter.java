package com.example.asilapp.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Document;
import com.example.asilapp.Models.Measurement;
import com.example.asilapp.R;

import java.util.List;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder>{
    private List<Document> documentList;

    public DocumentsAdapter(List<Document> documentList) {
        this.documentList = documentList;
    }

    @NonNull
    @Override
    public DocumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_document, parent, false);
        return new DocumentsAdapter.DocumentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsViewHolder holder, int position) {
        Document document = documentList.get(position);
        holder.itemName.setText(document.getName());
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class DocumentsViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        public DocumentsViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.TextView_Patient_Mymedia_Document_Item_NameDocument);
        }
    }
}
