package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class customDecorator implements DayViewDecorator {

    private CalendarDay date;
    private Drawable drawable;
    private String checkedList;
    // private Fragment fragment;
    private Activity activity;
    private MaterialCalendarView materialCalendarView;


    public customDecorator(Activity context, Drawable drawable, CalendarDay date, String checkedList) {
        //public customDecorator(Fragment fragment, CalendarDay date, String[] checkedList) {
        //date = CalendarDay.today();
        //this.fragment = fragment;

        if(drawable == null){
            Log.d("wow", "customDecorator: is null 1144");
        }
        else
            Log.d("wow", "customDecorator: is null 1155");


        this.activity = context;
        this.date = date;
        this.checkedList = checkedList;

        decideCircleColor();

    }

    private void decideCircleColor(){
        /*동그라미를 치기 위한 for문*/
        Log.d("wow", "customDecorator: is null 1177");
        drawable = activity.getResources().getDrawable(R.drawable.calendar_emptycircle_inside_purple);

        /*
        if(checkedList.length == 0) return;

        for(int i=0; i<checkedList.length; i++){
            if(checkedList[i] == null)
                break;
            else {
                Log.d("wow", "customDecorator: is null 1188");

                drawable = activity.getResources().getDrawable(R.drawable.calendar_emptycircle_inside_purple);
                //drawable = ContextCompat.getDrawable(activity,R.drawable.calendar_emptycircle_inside_purple);

                if(checkedList[i] == "내부 세차"){
                }
                else if(checkedList[i] == "외부 세차"){
                }
                else if(checkedList[i] == "전체 세차"){
                }

            }
        }
*/


    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);

    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }

}