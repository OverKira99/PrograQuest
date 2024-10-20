package com.alejandrobel.prograquest;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Barra de navegacion
       /* bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_lessons:
                    loadActivity(LessonsActivity.class);
                    return true;
                case R.id.nav_evaluation:
                    loadActivity(EvaluationActivity.class);
                    return true;
                case R.id.nav_profile:
                    loadActivity(ProfileActivity.class);
                    return true;
                default:
                    return false;
            }
        });*/

        // mostrar primero la activity lessons
        bottomNavigationView.setSelectedItemId(R.id.nav_lessons);
    }

    private void loadActivity(Class<?> activityClass) {
        startActivity(new Intent(MainActivity.this, activityClass));
    }
}

