package com.kokonut.NCNC.Home.Tab1;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.R;

import java.util.ArrayList;

public class Tab1_RecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private ArrayList<CarWashInfoData> mydatalist = null;
    public Tab1_RecyclerAdapter(ArrayList<CarWashInfoData> datalist){
        this.mydatalist = datalist;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_carwashlist, null);

        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewholder, int position) {
        //viewholder.imageView.setImageResource(R.drawable.box_image);
        viewholder.name.setText(mydatalist.get(position).getName());
        viewholder.address.setText(mydatalist.get(position).getAddress());
        viewholder.wash.setText(mydatalist.get(position).getWash());
        viewholder.time1.setText(mydatalist.get(position).getOpenSat());
        viewholder.time2.setText(mydatalist.get(position).getOpenSun());
        viewholder.time3.setText(mydatalist.get(position).getOpenWeek());

    }

    @Override
    public int getItemCount() {
        //adapter가 관리하는 전체 데이터 개수
        return (mydatalist==null) ? 0 : mydatalist.size();
    }
}
