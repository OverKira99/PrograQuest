package com.alejandrobel.prograquest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LessonsAdapter extends FragmentStateAdapter {

    public LessonsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IfFragment(); // Fragmento para IF
            case 1:
                return new WhileFragment(); // Fragmento para WHILE
            case 2:
                return new ForFragment(); // Fragmento para FOR
            case 3:
                return new SwitchFragment(); // Fragmento para SWITCH
            default:
                return new IfFragment(); // Fragmento por defecto
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Número de pestañas (IF, WHILE, FOR, SWITCH)
    }
}
