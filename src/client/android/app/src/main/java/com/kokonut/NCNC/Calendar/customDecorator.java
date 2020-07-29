package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class customDecorator implements DayViewDecorator {

    private CalendarDay date;
    private Drawable drawable;
    //private String checkedList;
    private String[] checkedList;
    // private Fragment fragment;
    private Activity activity;
    private MaterialCalendarView materialCalendarView;
    private int cleancar_part_num;

    public customDecorator(Activity context, Drawable drawable, CalendarDay date, String checkedList) {

        this.activity = context;
        this.date = date;
        this.checkedList = checkedList.split("_");

        decideCircle();

    }

    private void decideCircle(){
        /*동그라미를 치기 위한 for문*/
        Log.d("wow", "customDecorator: is null 1177");
        drawable = activity.getResources().getDrawable(R.drawable.circle_light_purple);

        //초기화 안하면 에러뜸
        cleancar_part_num = 1;

        if(checkedList.length == 0) return;

        for(int i=0; i<checkedList.length; i++){

            if(checkedList[i] == null) {
                Log.d("hello", "1");
                break;
            }
            else {
                Log.d("hello", "2 "+checkedList[i]);

                //drawable = activity.getResources().getDrawable(R.drawable.calendar_emptycircle_inside_purple);
                //drawable = ContextCompat.getDrawable(activity,R.drawable.calendar_emptycircle_inside_purple);

                if(checkedList[i].equals("내부 세차")){
                    Log.d("hello11", "2 "+cleancar_part_num);
                    cleancar_part_num = 1;
                }
                else if(checkedList[i].equals("외부 세차")) {
                    Log.d("hello22", "2 "+cleancar_part_num);
                    cleancar_part_num = 2;
                }
                else if(checkedList[i].equals("전체 세차")){
                    Log.d("hello33", "2 "+cleancar_part_num);
                    cleancar_part_num = 3;
                }
            }
        }

        Log.d("hello", "clean nu m : "+ cleancar_part_num);


    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        Log.d("we are loco", "decorate: ");
        view.setBackgroundDrawable(drawable);
        view.addSpan(new customSelectedDate(activity, cleancar_part_num));
    }

}

class customSelectedDate implements LineBackgroundSpan{

    String flag_text;
    int color_light_purple;
    Activity activity;

    public customSelectedDate(Activity activity, int cleancar_part){
        this.activity = activity;
        color_light_purple = activity.getColor(R.color.color_calender_lightpurple);

        if(cleancar_part == 1){
            //내부세차
            this.flag_text = "내부";
        }
        else if(cleancar_part == 2){
            //외부세차
            this.flag_text = "외부";
        }
        else if(cleancar_part == 3){
            //전체세차
            this.flag_text = "전체";
        }

    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top,
                               int baseline, int bottom, CharSequence text, int start, int end, int lnum) {

        paint.setColor(color_light_purple);
        paint.setTextSize(36);
        canvas.drawText(flag_text,(left+right)/4, (bottom+24),paint);

        //paint.setColor(Color.RED); 선택된 날짜 색깔

    }
}