package com.test.objectifs_sportifs;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.objectifs_sportifs.db.Activite;
import com.test.objectifs_sportifs.db.Objectif;
import com.test.objectifs_sportifs.db.Sport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ClasseMere {

    // variables
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.nouvelleActivite).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActiviteActivity.class);
            startActivity(intent);
        });

        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);

        // variables dernieres Activités
        ListView derniereActivitesMain = findViewById(R.id.derniereActiviteInfos);
        List<String> listeLibelleDerniereActivitesMain = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapterListeDerniereActiviteMain = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listeLibelleDerniereActivitesMain);
        derniereActivitesMain.setAdapter(arrayAdapterListeDerniereActiviteMain);

        // variables historique Objectifs terminés
        ListView listeObjectifsMain = findViewById(R.id.listeObjectifs);
        List<String> listeLibelleObjectifsTerminerMain = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapterListeObjectifsTerminerMain = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listeLibelleObjectifsTerminerMain);
        listeObjectifsMain.setAdapter(arrayAdapterListeObjectifsTerminerMain);

        // affichage de l'historique des activites
        arrayAdapterListeDerniereActiviteMain.clear();
        List<Activite> listeDesDerniereActivitesMain = toutesLesActivites();

        if(listeDesDerniereActivitesMain.size() <= 0) {
            listeLibelleDerniereActivitesMain.add("Ajoute vite une activité !!");
        } else {
            for(Activite uneActivite : listeDesDerniereActivitesMain) {
                String leLibelle = miseEnFormeDeLaDate(uneActivite.date);
                Sport leSport = unSportAvecId(uneActivite.idSport);
                leLibelle += " " + leSport.libelle;
                listeLibelleDerniereActivitesMain.add(leLibelle);
            }
        }
        arrayAdapterListeDerniereActiviteMain.notifyDataSetChanged();

        // affichage de l'historique des objectifs
        arrayAdapterListeObjectifsTerminerMain.clear();
        List<Objectif> listeDesObjectifsTerminerMain = tousLesObjectifsTerminer();

        if(listeDesObjectifsTerminerMain.size() <= 0) {
            listeLibelleObjectifsTerminerMain.add("Tu n'as toujours pas terminé d'objectifs !!");
        } else {
            for(Objectif unObjectif : listeDesObjectifsTerminerMain) {
                String leLibelle = unObjectif.libelle;
                Sport leSport = unSportAvecId(unObjectif.idSport);
                leLibelle += " (" + leSport.libelle + ")";
                listeLibelleObjectifsTerminerMain.add(leLibelle);
            }
        }
        arrayAdapterListeObjectifsTerminerMain.notifyDataSetChanged();
    }

    // pour fermer le menu lors de l'appuie sur la touche retour
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // fonction qui récupère toutes les activités
    private List<Activite> toutesLesActivites() {
        return appDb().activiteDao().trouverToutesLesActivites();
    }

    // fonction qui récupère tous les objectifs terminés
    private List<Objectif> tousLesObjectifsTerminer() {
        return appDb().objectifDao().trouverTousLesObjectifsTerminer();
    }

    // fonction qui recupere un sport via un ID
    public Sport unSportAvecId(int idSport) {
        return appDb().sportDao().trouverSportParId(idSport);
    }
}