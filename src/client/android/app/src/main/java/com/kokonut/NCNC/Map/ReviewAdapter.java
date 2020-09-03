package com.kokonut.NCNC.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.ReviewContents;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder> {

    private List<ReviewContents.Content> mList = null;
    //private Activity context = null;

    public ReviewAdapter(List<ReviewContents.Content> list) {
        //this.context = context;
        this.mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvUserId;
        protected TextView tvDate;
        protected TextView tvReviewText;

        public CustomViewHolder(View view) {
            super(view);
            this.tvUserId = (TextView) view.findViewById(R.id.review_user_id);
            this.tvDate = (TextView) view.findViewById(R.id.review_date);
            this.tvReviewText = (TextView) view.findViewById(R.id.review_text_view);
        }
    }


    @Override
    public ReviewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_review, null);

        ReviewAdapter.CustomViewHolder viewHolder = new ReviewAdapter.CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

//        viewholder.tvUserId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        viewholder.tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        viewholder.tvReviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//
//        viewholder.tvUserId.setGravity(Gravity.CENTER);
//        viewholder.tvDate.setGravity(Gravity.CENTER);
//        viewholder.tvReviewText.setGravity(Gravity.CENTER);

        viewholder.tvUserId.setText(mList.get(position).getUser_name());
        viewholder.tvDate.setText(mList.get(position).getDate());
        viewholder.tvReviewText.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

//    public Activity getContext() {
//        return context;
//    }

    public List<ReviewContents.Content> getmList() {
        return mList;
    }

//    public void setContext(Activity context) {
//        this.context = context;
//    }

    public void setmList(List<ReviewContents.Content> mList) {
        this.mList = mList;
    }

}