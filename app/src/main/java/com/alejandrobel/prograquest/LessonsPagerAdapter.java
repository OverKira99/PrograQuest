package com.alejandrobel.prograquest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LessonsPagerAdapter extends FragmentStateAdapter {

    public LessonsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IfFragment();
            case 1:
                return new ForFragment();
            case 2:
                return new WhileFragment();
            case 3:
                return new SwitchFragment();
            default:
                return new IfFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // 4 fragments
    }
}
