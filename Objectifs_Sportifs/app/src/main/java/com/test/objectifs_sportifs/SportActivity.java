package com.test.objectifs_sportifs;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        List<String> listeLibelleSports = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listeLibelleSports);
        afficheSport.setAdapter(arrayAdapter);

        final EditText saisieLibelleSport = findViewById(R.id.saisieSport);
        final CheckBox saisieDureeSport = findViewById(R.id.chkDureeSport);
        final CheckBox saisieDistanceSport = findViewById(R.id.chkDistanceSport);

        // partie pour l'ecouteur du bouton ENREGISTRER
        findViewById(R.id.enregistrer).setOnClickListener(v -> {
            // on récupère le libellé
            String monLibelle = saisieLibelleSport.getText().toString();
            if (monLibelle.equals("") || monLibelle == null) {
                appelAlerteDialog(SportActivity.this, "Veuillez renseigner le nom du sport");
                return;
            }

            // on regarde quels objectifs peuvent etre créés
            if (!saisieDureeSport.isChecked() && !saisieDistanceSport.isChecked()) {
                appelAlerteDialog(SportActivity.this, "Veuillez cocher au moins un objectif");
                return;
            }
            boolean objectifDuree = saisieDureeSport.isChecked();
            boolean objectifDistance = saisieDistanceSport.isChecked();

            // on enregistre le nouveau sport en BDD
            long id = enregistrerSport(monLibelle, objectifDuree, objectifDistance);
            if (id == 0) {
                Toast.makeText(SportActivity.this, "Erreur lors de l'enregistrement du sport : " + monLibelle, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SportActivity.this, "Sport " + monLibelle + " enregistré avec l'id : " + id, Toast.LENGTH_LONG).show();
            }
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
        });
    }

    // fonction qui enregistre un sport
    private long enregistrerSport(String libelle, boolean duree, boolean distance) {
        long res;
        try {
            Sport s = new Sport();
            s.libelle = libelle;
            s.duree = duree;
            s.distance = distance;
            res = appDb().sportDao().insert(s);
        } catch (Exception e) {
            res = 0;
        }

        return res;
    }

    // fonction qui récupère tous les sports
    public List<Sport> tousLesSports() {
        return appDb().sportDao().trouverTousLesSports();
    }

}
