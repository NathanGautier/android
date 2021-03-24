package com.test.objectifs_sportifs.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActiviteDao {
    @Query("SELECT * FROM activite WHERE id = :first LIMIT 1")
    Activite trouverActiviteParId(int first);

    @Query("SELECT * FROM activite WHERE libelle = :first")
    Activite trouverActiviteParLibelle(String first);

    @Query("SELECT * FROM activite")
    List<Activite> trouverToutesLesActivites();

    @Query("SELECT * FROM activite WHERE terminer = false")
    List<Activite> trouverActivitesEnCours();

    @Query("SELECT * FROM activite WHERE terminer = true")
    List<Activite> trouverActivitesTerminer();

    @Insert
    long insert(Activite activite);

    @Delete
    void delete(Activite activite);
}
