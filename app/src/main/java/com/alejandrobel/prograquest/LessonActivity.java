package com.alejandrobel.prograquest;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LessonActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private LessonsAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Enlazar vistas
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Configurar el adaptador del ViewPager
        pagerAdapter = new LessonsAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Configurar el TabLayout con el ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("IF");
                    break;
                case 1:
                    tab.setText("WHILE");
                    break;
                case 2:
                    tab.setText("FOR");
                    break;
                case 3:
                    tab.setText("SWITCH");
                    break;
            }
        }).attach();
    }
}