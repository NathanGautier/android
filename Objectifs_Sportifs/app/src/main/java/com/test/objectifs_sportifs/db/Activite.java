package com.test.objectifs_sportifs.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Activite {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public int date;

    @ColumnInfo(name = "duree")
    public Float duree;

    @ColumnInfo(name = "distance")
    public Float distance;

    @ColumnInfo(name = "terminer")
    public boolean terminer;

    @ColumnInfo(name = "idSport")
    public int idSport;
}
