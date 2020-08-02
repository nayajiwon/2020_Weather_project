package com.kokonut.NCNC;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class CarWashAdapter extends RecyclerView.Adapter<CarWashAdapter.CustomViewHolder> {

    private ArrayList<CarWashInfoData> mList = null;
    private Activity context = null;


    public CarWashAdapter(Activity context, ArrayList<CarWashInfoData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView name;
        protected TextView latitude;
        protected TextView longitude;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.latitude = (TextView) view.findViewById(R.id.textView_list_latitude);
            this.longitude = (TextView) view.findViewById(R.id.textView_list_longitude);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_output, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_id());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.latitude.setText(mList.get(position).getMember_latitude());
        viewholder.longitude.setText(mList.get(position).getMember_longitude());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}