package com.kokonut.NCNC;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kokonut.NCNC.Map.CarWashInfoActivity;

import java.util.ArrayList;


public class CarWashAdapter extends RecyclerView.Adapter<CarWashAdapter.CustomViewHolder> {

    private ArrayList<CarWashInfoData> mList = null;
    private Activity context = null;
    public static final int sub = 1001;
    private OnItemClickListener mListener = null;



    public CarWashAdapter(Activity context, ArrayList<CarWashInfoData> list) {
        this.context = context;
        this.mList = list;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView latitude;
        protected TextView longitude;
        protected TextView name;
        protected TextView address;
        protected TextView phone;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
//            this.latitude = (TextView) view.findViewById(R.id.textView_list_latitude);
//            this.longitude = (TextView) view.findViewById(R.id.textView_list_longitude);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.address = (TextView) view.findViewById(R.id.textView_list_address);
            this.phone = (TextView) view.findViewById(R.id.textView_list_phone);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });


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
//        viewholder.latitude.setText(mList.get(position).getMember_latitude());
//        viewholder.longitude.setText(mList.get(position).getMember_longitude());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.address.setText(mList.get(position).getMember_address());
        viewholder.phone.setText(mList.get(position).getMember_phone());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public Activity getContext() {
        return context;
    }

    public ArrayList<CarWashInfoData> getmList() {
        return mList;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public void setmList(ArrayList<CarWashInfoData> mList) {
        this.mList = mList;
    }

}