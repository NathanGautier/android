package com.test.objectifs_sportifs.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Objectif {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "libelle")
    public String libelle;

    @ColumnInfo(name = "dateDu")
    public String dateDu;

    @ColumnInfo(name = "dateAu")
    public String dateAu;

    @ColumnInfo(name = "heure")
    public int heure;

    @ColumnInfo(name = "heureAvancee")
    public int heureAvancee;

    @ColumnInfo(name = "minute")
    public int minute;

    @ColumnInfo(name = "minuteAvancee")
    public int minuteAvancee;

    @ColumnInfo(name = "distance")
    public boolean distance;

    @ColumnInfo(name = "distanceAvancee")
    public int distanceAvancee;

    @ColumnInfo(name = "terminer")
    public boolean terminer;

    @ColumnInfo(name = "idSport")
    public int idSport;
}
