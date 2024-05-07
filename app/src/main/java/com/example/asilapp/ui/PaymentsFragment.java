package com.example.asilapp.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;

import java.util.Calendar;

public class PaymentsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payments, container, false);

        EditText etDaData = view.findViewById(R.id.et_da_data);
        etDaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        EditText etAData = view.findViewById(R.id.et_a_data);
        etAData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Button btnAggiungi = view.findViewById(R.id.btn_aggiungi);
        btnAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupDialog();
            }
        });
        return view;
    }

    public void showDatePickerDialog(View view) {
        EditText editText = (EditText) view;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void showPopupDialog() {
        // Crea il Dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_layout);

        // Collega gli elementi del layout del popup
        EditText etTitolo = dialog.findViewById(R.id.et_titolo);
        EditText etPrezzo = dialog.findViewById(R.id.et_prezzo);
        EditText etData = dialog.findViewById(R.id.et_data);
        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        Spinner spinnerCategoria = dialog.findViewById(R.id.spinner_categoria_popup);
        Button btnSalva = dialog.findViewById(R.id.btn_salva);

        // Imposta il listener per il pulsante Salva
        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Salva i dati inseriti dall'utente
                // ...
                dialog.dismiss(); // Chiude il popup
            }
        });

        dialog.show();
    }
}
