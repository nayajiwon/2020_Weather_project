package com.kokonut.NCNC.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tab_Viewpager2Adapter extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    int PAGE_CNT = 2;

    public Tab_Viewpager2Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new Tab1Fragment();
        else
            return new Tab2Fragment();
    }

    @Override
    public int getItemCount() {
        return PAGE_CNT;
    }
}
