package com.test.objectifs_sportifs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.test.objectifs_sportifs.db.AppDatabase;

public abstract class ClasseMere extends AppCompatActivity {

    //variables
    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    public AppDatabase appDb(){
        return db;
    }
}
