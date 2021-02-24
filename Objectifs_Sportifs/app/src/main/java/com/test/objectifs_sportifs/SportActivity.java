package com.test.objectifs_sportifs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.test.objectifs_sportifs.db.AppDatabase;
import com.test.objectifs_sportifs.db.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        // partie pour l'ecouteur du bouton ENREGISTRER
        findViewById(R.id.enregistrer).setOnClickListener(v -> {
            EditText name = findViewById(R.id.saisieSport);
            long id = enregistrerSport(name.getText().toString());
            Toast.makeText(SportActivity.this, "Numéro enregistré avec l'id : " + id, Toast.LENGTH_LONG).show();
        });

        // partie pour l'ecouteur du bouton RECHERCHER
        findViewById(R.id.rechercher).setOnClickListener(v -> {
            TextView affiche = findViewById(R.id.afficheSport);
            String sport = getSport(1);
            Toast.makeText(SportActivity.this, "Numéros : " + sport, Toast.LENGTH_LONG).show();
            affiche.setText(sport);
        });
    }

    // fonction qui enregistre un nom / prenom
    private long enregistrerSport(String libelle) {
        Sport s = new Sport();
        s.libelle = libelle;
        s.distance = false;

        return db.sportDao().insert(s);
    }

    // fonction qui récupère les numéros
    private String getSport(int id) {
        Sport sport = db.sportDao().trouverSportParId(id);

        return sport.libelle;
    }

}
