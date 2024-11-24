package com.alejandrobel.prograquest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LessonFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        // Configurar el ViewPager con el adapter
        LessonsPagerAdapter adapter = new LessonsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Conectar el TabLayout con el ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("IF");
                    break;
                case 1:
                    tab.setText("FOR");
                    break;
                case 2:
                    tab.setText("WHILE");
                    break;
                case 3:
                    tab.setText("SWITCH");
                    break;
            }
        }).attach();

        return view;
    }
}
