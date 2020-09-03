package com.kokonut.NCNC.Home.Tab1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Tab1CarWashInfo_Viewpager2Adapter extends FragmentStateAdapter {
    int VIEW_CNT = 3;
    public Tab1CarWashInfo_Viewpager2Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0)
            return new Tab1_CarWashInfo1();
        else if(index==1)
            return new Tab1_CarWashInfo2();
        else
            return new Tab1_CarWashInfo3();
    }

    @Override
    public int getItemCount() {
        return VIEW_CNT;
    }



    public int getRealPosition(int position){
        return position % VIEW_CNT;
    }
}
