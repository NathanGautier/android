package com.test.objectifs_sportifs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // variables
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.nouvelleActivite).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActiviteActivity.class);
            startActivity(intent);
        });

        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
    }

    // pour fermer le menu lors de l'appuie sur la touche retour
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}