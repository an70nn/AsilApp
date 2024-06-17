package com.example.asilapp.Controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RatingCenter extends Fragment {

    private RatingBar ratingBar;
    private EditText commentEditText;
    private FirebaseFirestore firestoreDatabase;
    private CollectionReference collectionReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_raking_center, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ratingBar = view.findViewById(R.id.RatingBar_Patient_MyCenter_RatingBar);
        commentEditText = view.findViewById(R.id.EditText_Patient_MyCenter_RatingComment);

        firestoreDatabase = FirebaseFirestore.getInstance();
        collectionReference = firestoreDatabase.collection("Valutazioni");

        view.findViewById(R.id.AppCompatButton_Patient_MyCenter_RatingSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating();
            }
        });
    }

    private void saveRating() {
        float rating = ratingBar.getRating();
        String comment = commentEditText.getText().toString();

        if (rating == 0 || comment.isEmpty()) {
            Toast.makeText(getContext(), R.string.rate_our_center, Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> ratingObj = new HashMap<>();
        ratingObj.put("rating", rating);
        ratingObj.put("comment", comment);

        collectionReference.add(ratingObj)
                .addOnSuccessListener(documentReference -> Toast.makeText(getContext(), "Valutazione salvata", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Fallimento nell'aggiunta della valutazione", Toast.LENGTH_SHORT).show());
    }
}
