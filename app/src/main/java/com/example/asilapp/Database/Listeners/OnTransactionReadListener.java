package com.example.asilapp.Database.Listeners;

import com.example.asilapp.Models.Transaction;

import java.util.List;

public interface OnTransactionReadListener {
    void onDataLoaded(List<Transaction> transactions);
    void onError(String errorMessage);
}
