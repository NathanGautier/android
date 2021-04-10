package com.test.objectifs_sportifs.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sport.class, Objectif.class, Activite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SportDao sportDao();
    public abstract ObjectifDao objectifDao();
    public abstract ActiviteDao activiteDao();
}
