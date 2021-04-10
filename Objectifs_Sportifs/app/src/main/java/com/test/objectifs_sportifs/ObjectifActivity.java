package com.test.objectifs_sportifs;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.test.objectifs_sportifs.db.Objectif;
import com.test.objectifs_sportifs.db.Sport;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ObjectifActivity extends ClasseMere {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        ////////////////////////////// Partie Visualisation //////////////////////////////


        // variables affichage des objectifs en cours
        ListView afficheEnCoursObjectifs = findViewById(R.id.listeEnCoursVisualisationObjectif);
        List<String> listeLibelleEnCoursObjectifs = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapterListeEnCoursObjectifs = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listeLibelleEnCoursObjectifs);
        afficheEnCoursObjectifs.setAdapter(arrayAdapterListeEnCoursObjectifs);

        // variables affichage historique objectifs
        ListView afficheHistoriqueObjectifs = findViewById(R.id.listeHistoriqueVisualisationObjectif);
        List<String> listeLibelleHistoriqueObjectifs = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapterListeHistoriqueObjectifs = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listeLibelleHistoriqueObjectifs);
        afficheHistoriqueObjectifs.setAdapter(arrayAdapterListeHistoriqueObjectifs);

        // affichage des objectifs en cours
        arrayAdapterListeEnCoursObjectifs.clear();
        List<Objectif> listeDesObjectifsEnCours = tousLesObjectifsEnCours();

        if(listeDesObjectifsEnCours.size() <= 0) {
            listeLibelleEnCoursObjectifs.add("Ajoute vite un Objectif !!");
        } else {
            for(Objectif unObjectif : listeDesObjectifsEnCours) {
                String leLibelle = unObjectif.libelle;
                Sport leSport = unSportAvecId(unObjectif.idSport);
                leLibelle += " (" + leSport.libelle + ")";
                leLibelle += " [" + calculProgressionObjectif(unObjectif.avancee, unObjectif.total) + "%]";
                listeLibelleEnCoursObjectifs.add(leLibelle);
            }
        }
        arrayAdapterListeEnCoursObjectifs.notifyDataSetChanged();

        // affichage de l'historique des objectifs
        arrayAdapterListeHistoriqueObjectifs.clear();
        List<Objectif> listeDesObjectifsHistorique = tousLesObjectifsTerminer();

        if(listeDesObjectifsHistorique.size() <= 0) {
            listeLibelleHistoriqueObjectifs.add("Aucun objectif terminé ... tu attends quoi ??");
        } else {
            for(Objectif unObjectif : listeDesObjectifsHistorique) {
                if (unObjectif.terminer){
                    String leLibelle = unObjectif.libelle;
                    Sport leSport = unSportAvecId(unObjectif.idSport);
                    leLibelle += " (" + leSport.libelle + ")";
                    listeLibelleHistoriqueObjectifs.add(leLibelle);
                }
            }
        }
        arrayAdapterListeHistoriqueObjectifs.notifyDataSetChanged();

        // ecouteur du bouton nouveauObjectif
        findViewById(R.id.nouveauObjectif).setOnClickListener(v -> {
            // on masque l'écran de visualisation pour rendre visible l'ajout / ou l'inverse
            modifierVisibiliteObjectif();
        });


        ////////////////////////////// Partie Ajout //////////////////////////////


        // variables
        final Spinner afficheSportAjoutObjectifs = findViewById(R.id.listeSportsAjoutObjectif);
        final EditText saisieDistanceAjoutObjectif = findViewById(R.id.saisieDistanceAjoutObjectif);
        final EditText libelleAjoutObjectif = findViewById(R.id.libelleAjoutObjectif);
        final EditText dateAuAjoutObjectif = findViewById(R.id.dateAuAjoutObjectif);
        final TimePicker saisieDureeAjoutObjectif = findViewById(R.id.choixDureeAjoutObjectif);
        final CheckBox chkDistanceAjoutObjectif = findViewById(R.id.chkDistanceAjoutObjectif);

        // remplissage de la liste des sports
        List<String> listeSportsAjoutObjectifs = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapterListeSport = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, listeSportsAjoutObjectifs);
        afficheSportAjoutObjectifs.setAdapter(arrayAdapterListeSport);

        arrayAdapterListeSport.clear();
        List<Sport> listeDesSports = tousLesSports();
        if(listeDesSports.size() <= 0) {
            listeSportsAjoutObjectifs.add("Aucun sport créé");
        } else {
            // on ajoute un texte pour la première valeur
            listeSportsAjoutObjectifs.add("-- Sélectionner un sport --");
            for(Sport unSport : listeDesSports) {
                listeSportsAjoutObjectifs.add(unSport.libelle);
            }
        }
        arrayAdapterListeSport.notifyDataSetChanged();

        // on met le timePicker pour le choix de la durée en format 24h avec une initialisation a 0 heure et 0 minutes
        saisieDureeAjoutObjectif.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= 23 ) {
            saisieDureeAjoutObjectif.setHour(0);
            saisieDureeAjoutObjectif.setMinute(0);
        } else {
            saisieDureeAjoutObjectif.setCurrentHour(0);
            saisieDureeAjoutObjectif.setCurrentMinute(0);
        }

        // ecouteur de la liste des sports
        afficheSportAjoutObjectifs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Sport leSportSelectionner;

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // on masque tout
                saisieDistanceAjoutObjectif.setVisibility(View.INVISIBLE);
                saisieDureeAjoutObjectif.setVisibility(View.INVISIBLE);
                chkDistanceAjoutObjectif.setVisibility(View.INVISIBLE);
                if (position != 0) {
                    leSportSelectionner = unSportAvecId(position);
                    int variablePourSavoirSiPasseDansLesDeuxIf = 0;
                    if (leSportSelectionner.duree) {
                        // affiche la zone de saisie pour la duree
                        saisieDureeAjoutObjectif.setVisibility(View.VISIBLE);
                        variablePourSavoirSiPasseDansLesDeuxIf += 1;
                    }
                    if (leSportSelectionner.distance) {
                        // affiche la zone de saisie pour la distance
                        if (variablePourSavoirSiPasseDansLesDeuxIf < 1) {
                            saisieDistanceAjoutObjectif.setVisibility(View.VISIBLE);
                        }
                        variablePourSavoirSiPasseDansLesDeuxIf += 1;
                    }
                    // si on est passé dans les deux conditions, on affiche la checkbox
                    if (variablePourSavoirSiPasseDansLesDeuxIf == 2) {
                        chkDistanceAjoutObjectif.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // vraibles stockant l'etat de la checkbox pour le choix du type d'objectif (Distance ou Durée)
        final boolean[] checkedDistanceAjoutObjectif = new boolean[1];

        // ecouteur pour la checkbox pour le choix du type d'objectif (Distance ou Durée)
        chkDistanceAjoutObjectif.setOnClickListener(v -> {
            checkedDistanceAjoutObjectif[0] = ((CheckBox) v).isChecked();
            // Check which checkbox was clicked
            if (checkedDistanceAjoutObjectif[0]){
                // affiche la zone de saisie pour la distance
                saisieDistanceAjoutObjectif.setVisibility(View.VISIBLE);
                // masque la saisie pour la duree
                saisieDureeAjoutObjectif.setVisibility(View.INVISIBLE);
            }
            else{
                // affiche la saisie pour la duree
                saisieDureeAjoutObjectif.setVisibility(View.VISIBLE);
                // masque la zone de saisie pour la distance
                saisieDistanceAjoutObjectif.setVisibility(View.INVISIBLE);
            }
        });


        // ecouteur pour le bouton de retour
        findViewById(R.id.retourAjoutObjectif).setOnClickListener(v -> {
            // on masque l'écran de visualisation pour rendre visible l'ajout / ou l'inverse
            modifierVisibiliteObjectif();
        });

        // ecouteur du bouton nouveauObjectif
        findViewById(R.id.validerAjoutObjectif).setOnClickListener(new View.OnClickListener() {
            int monSport_id, maDateDu, maDateAu;
            String monLibelle;
            boolean estDistance, estTerminer;
            float monAvancee, leTotal;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v){
                // on recupere le libelle
                monLibelle = libelleAjoutObjectif.getText().toString();
                if (monLibelle.equals("")) {
                    appelAlerteDialog(ObjectifActivity.this, "Veuillez renseigner le libelle de l'Objectif");
                    return;
                }

                // on récupère la DateAu (date limite pour finir l'objectif)
                String tempDate = dateAuAjoutObjectif.getText().toString();
                maDateAu=0;
                if (!"".equals(tempDate)) {
                    maDateAu = miseEnFormeEnregistreDate(tempDate);
                } else {
                    appelAlerteDialog(ObjectifActivity.this, "Veuillez renseigner une date");
                    return;
                }

                // on met maDateDu a la date du jour
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    maDateDu = miseEnFormeEnregistreDate(dtf.format(now));
                } catch (Exception e) {
                    appelAlerteDialog(ObjectifActivity.this, "On n'est pas le bon jour aujourd'hui ! Mince");
                    return;
                }

                // on récupère le sport
                monSport_id = (int) afficheSportAjoutObjectifs.getSelectedItemId();
                if (monSport_id <= 0) {
                    appelAlerteDialog(ObjectifActivity.this, "Veuillez sélectionner un sport");
                    return;
                }

                // on renseigne si l'objectif est de distance ou pas (duree par defaut)
                estDistance = checkedDistanceAjoutObjectif[0];

                // on met estTerminer a false car l'Objectif est entrain d'etre créé
                estTerminer = false;

                // on met monAvancee a 0 car l'Objectif est entrain d'etre créé
                monAvancee = 0;

                if (checkedDistanceAjoutObjectif[0]) {
                    // on récupère la distance a faire
                    leTotal = Float.parseFloat(saisieDistanceAjoutObjectif.getText().toString());
                } else {
                    // on récupère la durée a faire
                    leTotal = miseEnFormeDuree();
                }
                if (leTotal <= 0) {
                    appelAlerteDialog(ObjectifActivity.this, "Veuillez renseigner un Objectif supérieur à 0");
                    return;
                }
                // TODO => quand on rentre 0.39, ca devient 0.38999998569488525 en base

                // on masque l'écran de visualisation pour rendre visible l'ajout / ou l'inverse
                long idEnregistrer = enregistreNouvelObjectif(monLibelle, maDateDu, maDateAu, estDistance, monAvancee, leTotal, estTerminer, monSport_id);
                if (idEnregistrer == 0) {
                    Toast.makeText(ObjectifActivity.this, "Erreur lors de l'enregistrement de l'objectif.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ObjectifActivity.this, "Objectif " + monLibelle + " enregistrée avec l'id : " + idEnregistrer, Toast.LENGTH_LONG).show();
                    // TODO => viderZonesSaisies();
                    // TODO => mettre a jour la liste d'objectifs
                }
            }
        });
    }



    // fonction qui calcul la progression d'un objectif en %
    private String calculProgressionObjectif(float monAvancee, float monTotal) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(100 * monAvancee / monTotal);
    }

    // fonction qui met en forme la date
    private int miseEnFormeEnregistreDate(String uneDate) {
        String laDate;

        laDate = uneDate.substring(0, 2);
        laDate += uneDate.substring(3, 5);
        laDate += uneDate.substring(6);

        return Integer.parseInt(laDate);
    }

    // fonction qui met en forme la durée
    private Float miseEnFormeDuree() {
        TimePicker view = findViewById(R.id.choixDureeAjoutObjectif);
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

    // fonction qui modifie la visibilitée de la partie Visualisation et Ajout
    private void modifierVisibiliteObjectif() {
        //partie visualisation
        View displayVisualisation = findViewById(R.id.layoutVisualisationObjectif);
        displayVisualisation.setVisibility(displayVisualisation.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);

        // partie ajout
        View displayAjout = findViewById(R.id.layoutAjoutObjectif);
        displayAjout.setVisibility(displayAjout.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
    }

    // fonction qui récupère tous les sports
    public List<Sport> tousLesSports() {
        return appDb().sportDao().trouverTousLesSports();
    }

    // fonction qui recupere un sport via un ID
    public Sport unSportAvecId(int idSport) {
        return appDb().sportDao().trouverSportParId(idSport);
    }

    // fonction qui récupère tous les objectifs en cours
    private List<Objectif> tousLesObjectifsEnCours() {
        return appDb().objectifDao().trouverTousLesObjectifsEnCours();
    }

    // fonction qui récupère tous les objectifs Terminés
    private List<Objectif> tousLesObjectifsTerminer() {
        return appDb().objectifDao().trouverTousLesObjectifsTerminer();
    }

    // on enregistre un nouvel Objectif en BDD
    private long enregistreNouvelObjectif(String leLibelle, int laDateDu, int laDateAu, boolean estDistance, float lAvancee, float leTotal, boolean estTerminer, int leIdSport) {
        long res;
        try {
            Objectif o = new Objectif();
            o.libelle = leLibelle;
            o.dateDu = laDateDu;
            o.dateAu = laDateAu;
            o.distance = estDistance;
            o.avancee = lAvancee;
            o.total = leTotal;
            o.terminer = estTerminer;
            o.idSport = leIdSport;
            res = appDb().objectifDao().insert(o);
        } catch (Exception e) {
            res = 0;
        }
        return res;
    }

}
