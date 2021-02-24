package com.test.objectifs_sportifs.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ObjectifDao {
    @Query("SELECT * FROM objectif WHERE id = :first LIMIT 1")
    Objectif trouverObjectifParId(int first);

    @Query("SELECT * FROM objectif")
    List<Objectif> trouverTousLesObjectifs();

    @Query("SELECT * FROM objectif WHERE terminer = false")
    List<Objectif> trouverObjectifsEnCours();

    @Insert
    long insert(Objectif objectif);

    @Delete
    void delete(Objectif objectif);
}
