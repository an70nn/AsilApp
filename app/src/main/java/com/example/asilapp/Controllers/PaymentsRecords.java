package com.example.asilapp.Controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Database.DatabasePazienti;
import com.example.asilapp.Database.Listeners.OnTransactionReadListener;
import com.example.asilapp.Models.Transaction;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.PaymentsRecordsAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Fragment per visualizzare e gestire i record delle transazioni.
 */
public class PaymentsRecords extends Fragment {
    private static final String TAG = "PaymentsRecords";
    private ImageButton recordAddExpense;
    private RecyclerView recyclerView;
    private Spinner recordSpinnerCategory;
    private List<Transaction> transactionList;
    private PaymentsRecordsAdapter expenseAdapter;
    private DatabasePazienti databasePazienti;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments_records, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recordSpinnerCategory = view.findViewById(R.id.Spinner_Payments_Records_Category);
        recordAddExpense      = view.findViewById(R.id.ImageButton_Payments_Records_AddExpense);
        recyclerView          = view.findViewById(R.id.RecyclerView_Payments_Records);

        setupCategorySpinner(recordSpinnerCategory);

        //Configurazione dello Spinner per filtrare le transazioni per categoria
        recordSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterTransactions(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Implementazione non necessaria in questo caso
            }
        });

        //Configurazione del RecyclerView per visualizzare le transazioni
        transactionList = new ArrayList<>();
        expenseAdapter = new PaymentsRecordsAdapter(transactionList);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inizializzazione del database
        databasePazienti = new DatabasePazienti(getContext());
    }

    /**
     * Filtra le transazioni in base alla categoria selezionata.
     * @param selectedCategory La categoria selezionata per il filtro.
     */
    private void filterTransactions(String selectedCategory) {
        List<Transaction> filteredTransactions = new ArrayList<>();

        if (selectedCategory.equals("ALL_CATEGORIES")) {
            //  viene selezionata l'opzione "Tutte le categorie", mostra tutte le transazioni
            filteredTransactions.addAll(transactionList);
        } else {
            //Altrimenti, filtra le transazioni in base alla categoria selezionata
            for (Transaction transaction : transactionList) {
                if (transaction.getCategory().equals(selectedCategory)) {
                    filteredTransactions.add(transaction);
                }
            }
        }

        //Aggiorna l'adapter del RecyclerView con le transazioni filtrate
        expenseAdapter.setTransactions(filteredTransactions);
        expenseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Carica tutte le transazioni dal database quando il fragment diventa visibile
        String userID = databasePazienti.getCurrentUserId();
        databasePazienti.loadTransactions(userID, new OnTransactionReadListener() {
            @Override
            public void onDataLoaded(List<Transaction> transactions) {
                transactionList.addAll(transactions);
                expenseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Errore durante il caricamento delle transazioni: "+errorMessage);
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        //Configura l'azione del pulsante "+" per aggiungere una nuova spesa
        recordAddExpense.setOnClickListener(v -> {
            final Dialog newExpenseDialog = new Dialog(getContext());
            newExpenseDialog.setContentView(R.layout.dialog_payments_records_new_expense);
            newExpenseDialog.setTitle("Aggiungere nuova spesa...");

            EditText expenseTitle      = newExpenseDialog.findViewById(R.id.EditText_Payments_Records_Dialog_AddExpense_Title);
            EditText expensePrice      = newExpenseDialog.findViewById(R.id.EditText_Payments_Records_Dialog_AddExpense_Price);
            EditText expenseData       = newExpenseDialog.findViewById(R.id.EditText_Payments_Records_Dialog_AddExpense_Date);
            Spinner  expenseCategory   = newExpenseDialog.findViewById(R.id.Spinner_Payments_Records_Dialog_AddExpense_Category);
            Button   expenseSaveRecord = newExpenseDialog.findViewById(R.id.Button_Payments_Records_Dialog_AddExpense_SaveRecord);

            setupCategorySpinner(expenseCategory);

            // Configura EditText per la data di acquisto del prodotto
            expenseData.setOnClickListener(v1 -> showDatePickerDialog(expenseData));

            expenseSaveRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title     = expenseTitle.getText().toString().trim();
                    String category  = expenseCategory.getSelectedItem().toString();
                    String price     = expensePrice.getText().toString().trim();
                    String date      = expenseData.getText().toString().trim();

                    if(!title.isEmpty() && !price.isEmpty() && !date.isEmpty()){
                        try {
                            String userID = databasePazienti.getCurrentUserId();
                            Double.parseDouble(price);
                            Transaction newRecord = new Transaction(userID, title, category, price, date);
                            Log.e(TAG, "Record: "+newRecord);

                            databasePazienti.addTransaction(newRecord, userID);
                            Toast.makeText(getContext(), "Spesa aggiunta", Toast.LENGTH_SHORT).show();

                            transactionList.add(newRecord);
                            expenseAdapter.notifyDataSetChanged();
                            newExpenseDialog.dismiss();
                        }catch (NumberFormatException e){
                            Toast.makeText(getContext(), "Perfavore, inserisci un prezzo valido", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getContext(), "Perfavore, inserisci tutti i campi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            newExpenseDialog.show();
        });
    }

    /**
     * Mostra un DatePickerDialog per selezionare la data della spesa.
     */
    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void setupCategorySpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}