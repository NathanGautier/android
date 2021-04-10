package com.test.objectifs_sportifs;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NavigationMenu extends FrameLayout {

    public NavigationMenu(@NonNull Context context) {
        super(context);
        init();
    }

    public NavigationMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public NavigationMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.navigation_menu, this);

        // ecoute du bouton accueil
        findViewById(R.id.accueil).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
        });

        // ecoute du bouton mesSports
        findViewById(R.id.mesSports).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SportActivity.class);
            getContext().startActivity(intent);
        });

        // ecoute du bouton mesObjectifs
        findViewById(R.id.mesObjectifs).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ObjectifActivity.class);
            getContext().startActivity(intent);
        });

        // ecoute du bouton mesActivites
        findViewById(R.id.mesActivites).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ActiviteActivity.class);
            getContext().startActivity(intent);
        });

        // ecoute du bouton quitter
        findViewById(R.id.quitter).setOnClickListener(v -> {
            // TODO => ne quitte pas vraiment si on navigue dans plusieurs onglets
            System.exit(0);
        });

    }
}
