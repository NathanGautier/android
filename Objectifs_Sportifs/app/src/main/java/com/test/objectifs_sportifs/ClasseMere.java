package com.test.objectifs_sportifs;

import android.app.AlertDialog;
import android.content.Context;
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

    //creation d'une popin
    public void appelAlerteDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle("Attention")
                .setMessage(message)
                .setPositiveButton("ok", null)
                .show();
    }

    // fonction qui met en forme la date
    public String miseEnFormeDeLaDate(Integer uneDate) {
        String laDate = uneDate.toString();
        laDate = laDate.substring(0,2) + "/" + laDate.substring(2,4) + "/" + laDate.substring(4);

        return laDate;
    }

    public AppDatabase appDb(){
        return db;
    }
}
