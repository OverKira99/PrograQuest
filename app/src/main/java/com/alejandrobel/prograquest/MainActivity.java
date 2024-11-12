package com.alejandrobel.prograquest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnvNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnvNavigation = findViewById(R.id.bottom_navigation);

        // Configurar el listener del BottomNavigationView
        bnvNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_lessons) {
                selectedFragment = new LessonFragment();
            } else if (item.getItemId() == R.id.nav_evaluation) {
                selectedFragment = new EvaluationFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }


            // Reemplazar el fragmento actual con el seleccionado
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

