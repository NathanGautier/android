package com.test.objectifs_sportifs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.test.objectifs_sportifs.db.Activite;
import com.test.objectifs_sportifs.db.Sport;

import java.util.ArrayList;
import java.util.List;

public class ActiviteActivity extends ClasseMere {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite);

        ////////////////////////////// Partie Visualisation //////////////////////////////


        // variables
        ListView afficheActivite = findViewById(R.id.historiqueActivite);
        List<String> listeLibelleActivites = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapterListeActivite = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listeLibelleActivites);
        afficheActivite.setAdapter(arrayAdapterListeActivite);

        // affichage de l'historique des activites
        arrayAdapterListeActivite.clear();
        List<Activite> listeDesActivites = toutesLesActivites();

        if(listeDesActivites.size() <= 0) {
            listeLibelleActivites.add("Ajoute vite une activité !!");
        } else {
            for(Activite uneActivite : listeDesActivites) {
                listeLibelleActivites.add(uneActivite.libelle);
            }
        }
        arrayAdapterListeActivite.notifyDataSetChanged();

        // ecouteur du bouton demarrerActivite
        findViewById(R.id.demarrerActivite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on masque l'écran de visualisation pour rendre visible l'ajout / ou l'inverse
                //partie visualisation
                View displayVisualisation = findViewById(R.id.layoutVisualisationActivite);
                displayVisualisation.setVisibility(displayVisualisation.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);

                // partie ajout
                View displayAjout = findViewById(R.id.layoutAjoutActivite);
                displayAjout.setVisibility(displayAjout.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
            }
        });


        ////////////////////////////// Partie Ajout //////////////////////////////


        // variables
        final TimePicker pickerDureeAjoutActivite = findViewById(R.id.choixDureeAjoutActivite);
        final Spinner afficheSportAjoutActivite = findViewById(R.id.listeSportsAjoutActivite);
        final EditText dateAjoutActivite = findViewById(R.id.saisieDateAjoutActivite);
        final EditText distanceAjoutActivite = findViewById(R.id.saisieDistanceAjoutActivite);

        // remplissage de la liste des sports
        List<String> listeSportsAjoutActivite = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapterListeSport = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listeSportsAjoutActivite);
        afficheSportAjoutActivite.setAdapter(arrayAdapterListeSport);

        arrayAdapterListeSport.clear();
        List<Sport> listeDesSports = tousLesSports();
        if(listeDesSports.size() <= 0) {
            listeSportsAjoutActivite.add("Aucun sport créé");
        } else {
            // on ajoute un texte pour la première valeur
            listeSportsAjoutActivite.add("-- Sélectionner un sport --");
            for(Sport unSport : listeDesSports) {
                listeSportsAjoutActivite.add(unSport.libelle);
            }
        }
        arrayAdapterListeSport.notifyDataSetChanged();

        // on met le timePicker pour le choix de la durée en format 24h avec une initialisation a 0 heure et 0 minutes
        pickerDureeAjoutActivite.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= 23 ) {
            pickerDureeAjoutActivite.setHour(00);
            pickerDureeAjoutActivite.setMinute(00);
        }

        // ecouteur pour le timePicker pour récupérer l'heure et les minutes
        findViewById(R.id.validerAjoutActivite).setOnClickListener(new View.OnClickListener() {
            String monLibelle;
            float maDuree, maDistance;
            int maDate, monSport_id;
            final boolean estTerminer = false;

            @Override
            public void onClick(View v) {
                // on récupère le libelle
                monLibelle = "test";
                // TODO => voir si une activite a besoin d'un libelle, si oui le rajouter un EditText dans le xml

                // on récupère la date
                String tempDate = dateAjoutActivite.getText().toString();
                maDate=0;
                if (!"".equals(tempDate))
                    maDate=Integer.parseInt(tempDate);

                // mise en forme de la durée
                maDuree = miseEnFormeDuree();
                if (maDuree == 0) {
                    appelAlerteDialog(ActiviteActivity.this, "Veuillez renseigner une durée supérieure a 0");
                    return;
                }
                // TODO => quand on rentre 0.39, ca devient 0.38999998569488525 en base

                // on récupère la distance
                maDistance = Float.parseFloat(distanceAjoutActivite.getText().toString());
                if (maDistance == 0) {
                    appelAlerteDialog(ActiviteActivity.this, "Veuillez renseigner une distance supérieure a 0");
                    return;
                }

                // on récupère le sport
                monSport_id = (int) afficheSportAjoutActivite.getSelectedItemId();
                if (monSport_id <= 0) {
                    appelAlerteDialog(ActiviteActivity.this, "Veuillez sélectionner un sport");
                    return;
                }

                // TODO => a supprimer quand ce sera ok
                System.out.println("libelle : " + monLibelle);
                System.out.println("date : " + maDate);
                System.out.println("duree : " + maDuree);
                System.out.println("distance : " + maDistance);
                System.out.println("terminer : " + estTerminer);
                System.out.println("sport_id : " + monSport_id);

                // on enregistre la nouvelle activité en BDD
                long id = enregistrerNouvelleActivite(monLibelle, maDate, maDuree, maDistance, estTerminer, monSport_id);
                if (id == 0) {
                    Toast.makeText(ActiviteActivity.this, "Erreur lors de l'enregistrement de l'activité : " + monLibelle, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ActiviteActivity.this, "Activité " + monLibelle + " enregistrée avec l'id : " + id, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    // fonction qui met en forme la durée
    private Float miseEnFormeDuree() {
        TimePicker view = findViewById(R.id.choixDureeAjoutActivite);
        int heure, minute;
        if (Build.VERSION.SDK_INT >= 23 ){
            heure = view.getHour();
            minute = view.getMinute();
        }
        else {
            heure = view.getCurrentHour();
            minute = view.getCurrentMinute();
        }
        // l'heure se trouve avant la virgule et les minutes se trouvent apres la virgule
        return heure + minute * 0.01f;
    }

    private void appelAlerteDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle("Attention")
                .setMessage(message)
                .setPositiveButton("ok", null)
                .show();
    }

    // fonction qui récupère tous les sports
    private List<Activite> toutesLesActivites() {
        List<Activite> activites = appDb().activiteDao().trouverToutesLesActivites();
        return activites;
    }

    // fonction qui récupère tous les sports
    public List<Sport> tousLesSports() {
        List<Sport> sports = appDb().sportDao().trouverTousLesSports();
        return sports;
    }

    // fonction qui enregistre un sport
    private long enregistrerNouvelleActivite(String libelle, int date, Float duree, Float distance, boolean terminer, int idSport) {
        long res;
        try {
            Activite a = new Activite();
            a.libelle = libelle;
            a.date = date;
            a.duree = duree;
            a.distance = distance;
            a.terminer = terminer;
            a.idSport = idSport;
            res = appDb().activiteDao().insert(a);
        } catch (Exception e) {
            res = 0;
        }
        return res;
    }
}
