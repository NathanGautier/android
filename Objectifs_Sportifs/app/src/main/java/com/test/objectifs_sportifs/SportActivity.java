package com.test.objectifs_sportifs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.test.objectifs_sportifs.db.AppDatabase;
import com.test.objectifs_sportifs.db.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportActivity extends ClasseMere {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        // variables
        ListView afficheSport = findViewById(R.id.afficheSport);
        List<String> listeLibelleSports = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listeLibelleSports);
        afficheSport.setAdapter(arrayAdapter);

        // partie pour l'ecouteur du bouton ENREGISTRER
        findViewById(R.id.enregistrer).setOnClickListener(v -> {
            EditText name = findViewById(R.id.saisieSport);
            long id = enregistrerSport(name.getText().toString());
            Toast.makeText(SportActivity.this, "Sport enregistré avec l'id : " + id, Toast.LENGTH_LONG).show();
        });

        // partie pour l'ecouteur du bouton RECHERCHER
        findViewById(R.id.rechercher).setOnClickListener(v -> {
            arrayAdapter.clear();
            List<Sport> listeDesSports = tousLesSports();

            if(listeDesSports.size() <= 0) {
                listeLibelleSports.add("Ajoute un sport espèce de feignasse !!");
            } else {
                for(Sport unSport : listeDesSports) {
                    listeLibelleSports.add(unSport.libelle);
                }
            }
            arrayAdapter.notifyDataSetChanged();
            //Toast.makeText(SportActivity.this, "libellé pour l'id : " + sport, Toast.LENGTH_LONG).show();

        });
    }

    // fonction qui enregistre un sport
    private long enregistrerSport(String libelle) {
        Sport s = new Sport();
        s.libelle = libelle;
        s.distance = false;

        return appDb().sportDao().insert(s);
    }

    // fonction qui récupère tous les sports
    public List<Sport> tousLesSports() {
        List<Sport> sports = appDb().sportDao().trouverTousLesSports();

        return sports;
    }

}
