package com.test.objectifs_sportifs.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sport {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "libelle")
    public String libelle;

    @ColumnInfo(name = "distance")
    public boolean distance = false;
}
