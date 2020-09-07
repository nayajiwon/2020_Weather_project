package com.kokonut.NCNC.Home.Tab1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.R;

import java.util.ArrayList;

public class Tab1_RecyclerAdapter_Horizontal extends RecyclerView.Adapter<Tab1_ItemViewHolder> {

private ArrayList<CarWashInfoData> mydatalist1 = null;
public Tab1_RecyclerAdapter_Horizontal(ArrayList<CarWashInfoData> datalist1){
        this.mydatalist1 = datalist1;
        }

@NonNull
@Override
public Tab1_ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_carwashlist2, null);

        Tab1_ItemViewHolder itemViewHolder_hor = new Tab1_ItemViewHolder(view);

        return itemViewHolder_hor;
        }


    @Override
public void onBindViewHolder(@NonNull Tab1_ItemViewHolder viewholder, int position) {
        //viewholder.imageView.setImageResource(R.drawable.box_image);
        viewholder.name.setText(mydatalist1.get(position).getName());
        viewholder.address.setText(mydatalist1.get(position).getAddress());
        viewholder.wash.setText(mydatalist1.get(position).getWash());
        viewholder.time1.setText(mydatalist1.get(position).getOpenSat());
        viewholder.time2.setText(mydatalist1.get(position).getOpenSun());
        viewholder.time3.setText(mydatalist1.get(position).getOpenWeek());

        }

@Override
public int getItemCount() {
        //adapter가 관리하는 전체 데이터 개수
        return (mydatalist1==null) ? 0 : mydatalist1.size();
        }
}
