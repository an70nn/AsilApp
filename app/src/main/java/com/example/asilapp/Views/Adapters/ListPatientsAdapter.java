package com.example.asilapp.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.Patient;
import com.example.asilapp.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;

public class ListPatientsAdapter extends RecyclerView.Adapter<ListPatientsAdapter.ListPatientViewHolder> {
    private List<Patient> listPatients;
    private List<Patient> filteredList;
    private OnItemClickListener itemClickListener;

    public ListPatientsAdapter(List<Patient> listPatients){
        this.listPatients = listPatients;
        this.filteredList = new ArrayList<>(listPatients);
    }

    // Interfaccia per il listener di clic
    public interface OnItemClickListener {
        void onItemClick(Patient patient);
    }
    // Aggiunta del setter per il listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    // Implementa il metodo filterList per aggiornare l'elenco filtrato
    public void filterList(List<Patient> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged(); // Notifica il cambio nella visualizzazione
    }

    @NonNull
    @Override
    public ListPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_record, parent, false);
        return new ListPatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPatientsAdapter.ListPatientViewHolder holder, int position) {
        Patient patient = listPatients.get(position);
        holder.itemPic.setImageResource(R.drawable.ic_account);
        holder.itemName.setText(patient.getName());
        holder.itemSurname.setText(patient.getSurname());
        holder.itemGender.setText(patient.getGender());
        holder.itemAge.setText(String.valueOf(calculateAge(patient.getBirthDate())).concat(" Anni"));
        holder.itemCountry.setText(patient.getCountry());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(patient);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPatients.size();
    }

    /**
     * Calcola l'età attuale basata sulla data di nascita fornita.
     * Questo metodo prende una stringa che rappresenta la data di nascita in formato "dd-MM-yyyy"
     * e calcola l'età dell'utente in base alla data attuale.
     * Se la data di nascita non è valida, il metodo restituirà 0.
     *
     * @param birthDateString la data di nascita in formato "dd-MM-yyyy"
     * @return l'età calcolata in anni, o 0 se la data di nascita non è valida
     */
    public static int calculateAge(String birthDateString) {

        /* CONSIDERAZIONI DI PROGETTAZIONE
         * A causa delle limitazioni delle versioni precedenti di Android (minSdkVersion < 26),
         * non è stato possibile utilizzare direttamente le API java.time introdotte in Java 8.
         * Per risolvere questo problema, è stata importata la libreria ThreeTenABP,
         * che fornisce una backport delle API java.time per versioni di Android inferiori.
         */

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
            LocalDate currentDate = LocalDate.now();
            if ((birthDate != null) && (currentDate != null)) {
                return Period.between(birthDate, currentDate).getYears();
            } else {
                return 0;
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static class ListPatientViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton itemPic;
        private final TextView itemName;
        private final TextView itemSurname;
        private final TextView itemGender;
        private final TextView itemAge;
        private final TextView itemCountry;

        public ListPatientViewHolder(View itemView) {
            super(itemView);
            itemPic        = itemView.findViewById(R.id.ImageButton_Doctor_ListPatient_Item_UserPic);
            itemName       = itemView.findViewById(R.id.TextView_Doctor_ListPatient_Item_Name);
            itemSurname    = itemView.findViewById(R.id.TextView_Doctor_ListPatient_Item_Surname);
            itemGender     = itemView.findViewById(R.id.TextView_Doctor_ListPatient_Item_Gender);
            itemAge        = itemView.findViewById(R.id.TextView_Doctor_ListPatient_Item_Age);
            itemCountry    = itemView.findViewById(R.id.TextView_Doctor_ListPatient_Item_Country);
        }
    }
}
