package com.example.asilapp.Controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.asilapp.R;

import java.util.Locale;

public class LanguageManager extends Dialog {

    private Context context;
    private LinearLayout languageSelectItalian, languageSelectEnglish, languageSelectFrance, languageSelectGermany, languageSelectSpain;

    public LanguageManager(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_home_change_language);

        // Inizializzazione dei LinearLayout per le lingue
        languageSelectItalian = findViewById(R.id.LinearLayout_Home_Change_Language_Ita);
        languageSelectEnglish = findViewById(R.id.LinearLayout_Home_Change_Language_Eng);
        languageSelectFrance = findViewById(R.id.LinearLayout_Home_Change_Language_Fra);
        languageSelectGermany = findViewById(R.id.LinearLayout_Home_Change_Language_Ger);
        languageSelectSpain = findViewById(R.id.LinearLayout_Home_Change_Language_Spa);

        languageSelectItalian.setOnClickListener(v -> {
            changeLanguage("it");
        });

        languageSelectEnglish.setOnClickListener(v -> {
            changeLanguage("en");
        });

        languageSelectFrance.setOnClickListener(v -> {
            changeLanguage("fr");
        });

        languageSelectGermany.setOnClickListener(v -> {
            changeLanguage("de");
        });

        languageSelectSpain.setOnClickListener(v -> {
            changeLanguage("es");
        });
    }

    private void changeLanguage(String languageCode) {
        Log.d("LanguageManager", "Changing language to: " + languageCode);
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        Resources resources = context.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        Log.d("LanguageManager", "Language changed, restarting activity.");
        Intent intent = new Intent(context, context.getClass());
        context.startActivity(intent);
        ((Activity) context).finish();
        dismiss(); // Chiudi il dialogo
    }

}
