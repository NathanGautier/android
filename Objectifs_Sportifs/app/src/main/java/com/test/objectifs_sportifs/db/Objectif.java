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
    public int dateDu;

    @ColumnInfo(name = "dateAu")
    public int dateAu;

    @ColumnInfo(name = "distance")
    public boolean distance;

    @ColumnInfo(name = "avancee")
    public float avancee;

    @ColumnInfo(name = "total")
    public float total;

    @ColumnInfo(name = "terminer")
    public boolean terminer;

    @ColumnInfo(name = "idSport")
    public int idSport;
}
