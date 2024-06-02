package com.example.asilapp.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Transaction;
import com.example.asilapp.R;

import java.util.List;

public class PaymentsRecordsAdapter extends RecyclerView.Adapter<PaymentsRecordsAdapter.ViewHolder> {
    private List<Transaction> transactionsList;

    public PaymentsRecordsAdapter(List<Transaction> transactionsList){
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactionsList.get(position);
        holder.itemTitle.setText(transaction.getTitle());
        holder.itemCategory.setText(transaction.getCategory());
        holder.itemPrice.setText(transaction.getPrice());
        holder.itemDate.setText(transaction.getDate());
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Metodo per impostare una nuova lista di transazioni
    public void setTransactions(List<Transaction> transactions) {
        this.transactionsList = transactions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final TextView itemCategory;
        private final TextView itemPrice;
        private final TextView itemDate;

        public ViewHolder(View view) {
            super(view);
            itemTitle       = view.findViewById(R.id.TextView_Payments_Records_Title_Item_Dialog_AddExpense);
            itemCategory    = view.findViewById(R.id.TextView_Payments_Records_Category_Item_Dialog_AddExpense);
            itemPrice       = view.findViewById(R.id.TextView_Payments_Records_Price_Item_Dialog_AddExpense);
            itemDate        = view.findViewById(R.id.TextView_Payments_Records_Date_Item_Dialog_AddExpense);
        }
    }
}
