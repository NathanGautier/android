package com.test.objectifs_sportifs.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SportDao {
    @Query("SELECT * FROM sport WHERE id = :first LIMIT 1")
    Sport trouverSportParId(int first);

    @Query("SELECT * FROM sport WHERE libelle LIKE :first")
    List<Sport> trouverSportParLibelle(String first);

    @Query("SELECT * FROM sport")
    List<Sport> trouverTousLesSports();

    @Insert
    long insert(Sport sport);

    @Delete
    void delete(Sport sport);
}
