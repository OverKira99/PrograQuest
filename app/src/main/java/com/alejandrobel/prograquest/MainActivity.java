package com.alejandrobel.prograquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnvNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnvNavigation = findViewById(R.id.bottom_navigation);

        // Barra de navegacion
        bnvNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_lessons) {
                    startActivity(new Intent(MainActivity.this, LessonActivity.class));
                    return true;
                } else if (itemId == R.id.nav_evaluation) {
                    startActivity(new Intent(MainActivity.this, EvaluationActivity.class));
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });

        // mostrar primero la activity lessons
        bnvNavigation.setSelectedItemId(R.id.nav_lessons);
    }

    private void loadActivity(Class<?> activityClass) {
        startActivity(new Intent(MainActivity.this, activityClass));
    }
}

