package com.example.asilapp.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Document;
import com.example.asilapp.Models.Measurement;
import com.example.asilapp.R;

import java.util.List;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder>{
    private List<Document> documentList;
    private OnItemClickListener listener;
    private Context context;
    private String patientID;

    public DocumentsAdapter(List<Document> documentList, OnItemClickListener listener) {
        this.documentList = documentList;
        this.listener = listener;
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
        holder.itemNamePdf.setText(document.getName());
        holder.itemButtonShare.setImageResource(R.drawable.baseline_share_32);


        //Se preme la scritta apre il documento PDF
        holder.itemNamePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDownloadClick(document);
                }
            }
        });

        //Se preme l'ImageView a sinistra permette la condivisione del documento
        holder.itemButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Modificare il contenuto
                //Da qua
                if (listener != null) {
                    listener.onDownloadClick(document);
                }
                //A qua, il contenuto dev'essere rimosso e sostituito con un'altra logica
            }
        });


    }

    public interface OnItemClickListener{
        void onDownloadClick(Document document);
    }


    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class DocumentsViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNamePdf;
        private ImageView itemButtonShare;
        public DocumentsViewHolder(View itemView) {
            super(itemView);
            itemNamePdf     = itemView.findViewById(R.id.TextView_Patient_Mymedia_Document_Item_NameDocument);
            itemButtonShare = itemView.findViewById(R.id.ImageView_Patient_Mymedia_Document_Item_SharePdf);
        }
    }
}
