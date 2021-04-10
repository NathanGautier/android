# Projet Andoid de Nathan Gautier LP DEV CLOUD

/!\ Il y a un panel latéral de navigation, il faut swipper vers la droite pour l'ouvrir /!\

## Normes

Langue utilisée :
- Francais

Normes de nommage :
- Standard Java
- variables : première lettre en minuscule

Organisation de package
- ajout d'un package db comportant :
  - models
  - dao

## GitHub

J'ai créé une branche develop qui comporte le code en développement.
La branche develop a été merge sur master au moment de rendre le projet, avec un code éxécutable.

Je n'ai pas fais de tag / version / release. Surtout par manque de temps pour y réfléchir.

## Librairie

J'ai utilisé la librairie **ROOM** pour la base de données, ce qui m'a facilité le travail pour la création de la base de données, ca m'a aussi fait gagner du temps.

## Tests

Pour tester mon application, j'ai utilisé l'émulateur téléphone Pixel_3a_API_30_x86 (celui qui était par défaut sur Android Studio) en format portrait.

Je n'ai pas pris le temps de tester sur d'autres appareils.(Par manque de temps principalement)

Je n'ai pas eu le temps de faire des tests unitaires.

## Bug connus

- Dans le panel latéral de navigation, le bouton *Quitter*, fait un retour à la page précédente. Il fonctionnera donc que si on reste sur la page d'accueil.
- Pour les saisies de Date dans la création d'Activité **et** dans la création d'Objectif, il faut la mettre sous format **dd/mm/yyyy**. (ne pas oublier les / )

## Fonctionnalitées manquantes

### Sports

- On ne peut pas modifier ni supprimer un Sport.
- Lorsqu'on clique sur rechercher et qu'après on ajoute un nouveau sport, la liste ne se mettra pas à jour.

### Objectifs

- On ne peut pas modifier ni supprimer un Objectif.
- Lorsqu'on créé un objectif, les champs de saisie ne sont vidés.
- L'avancement des objectifs n'est pas développé.

### Activités

- On ne peut pas modifier ni supprimer une Activité.
- Pour la création d'activité :
  - on aura dans tous les cas à renseigner la distance ainsi que la durée, ce n'est pas en fonction du Sport.
- La page de lancement d'activité n'est pas développée.
  - Il n'y a pas de suivi GPS / chronomètre
- L'affichage d'une carte pour voir le suivi GPS n'est pas développé

### Maquettes

*Page d'accueil* (activity_main.xml)
![Screenshot](images\accueil.png)

*Page Sports* (activity_sport.xml)
![Screenshot](images\lesSports.png)

*Page de visualisation Objectifs* (activity_objectif.xml)
![Screenshot](images\lesObjectifs.png)

*Page de visualisation Acvtivités* (activity_activite.xml)
![Screenshot](images\lesActivites.png)

*Page d'ajout Activités* (activity_activite.xml)
![Screenshot](images\lesActivitesAjouter.png)
