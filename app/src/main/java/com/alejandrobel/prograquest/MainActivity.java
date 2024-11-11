package com.alejandrobel.prograquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnvNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnvNavigation = findViewById(R.id.bottom_navigation);

        bnvNavigation = findViewById(R.id.bottom_navigation);

        bnvNavigation.setOnItemSelectedListener(item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_lessons) {
                    selectedFragment = new LessonFragment();
                } else if (item.getItemId() == R.id.nav_evaluation) {
                    selectedFragment = new EvaluationFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, selectedFragment)
                            .commit();
                }

                return true;
            });

            // Cargar el fragmento inicial si no hay un estado guardado
            if (savedInstanceState == null) {
                bnvNavigation.setSelectedItemId(R.id.nav_lessons);
            }
        }
}

