package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
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
    private String date_string;
    private Drawable drawable;
    //private String checkedList;
    int checkedList;
    // private Fragment fragment;
    private Activity activity;
    private MaterialCalendarView materialCalendarView;
    //private int cleancar_part_num;
    private int cleancar_color;
    private CalendarDBHelper CalendardbHelper;
    private customSelectedDate customSelectedDate;

    public customDecorator(Activity context, Drawable drawable, CalendarDay date, int checkedList, CalendarDBHelper CalendardbHelper) {

        this.activity = context;
        this.date = date;
        this.date_string = date.toString();
        this.checkedList = checkedList;
        this.CalendardbHelper = CalendardbHelper;

        decideCircle();
    }

    private void decideCircle(){
        /*동그라미를 치기 위한 for문*/
        Log.d("wow", "customDecorator: is null 1177");
        drawable = activity.getResources().getDrawable(R.drawable.circle_light_purple);

        //초기화 안하면 에러뜸

        if(checkedList == 0) return;

        //drawable = activity.getResources().getDrawable(R.drawable.calendar_emptycircle_inside_purple);
        //drawable = ContextCompat.getDrawable(activity,R.drawable.calendar_emptycircle_inside_purple);

        if(checkedList == 1){ //내부세차
            cleancar_color = 1;
        }
        else if(checkedList == 2) { //외부세차
            cleancar_color = 1;
        }
        else if(checkedList == 3) { //전체 세차
            cleancar_color = 2;
        }

        calendarDB(date_string, checkedList, cleancar_color);

    }


    public void calendarDB(String date, int part, int color){
        CalendardbHelper.insertRecord(date, part, color);
        printTable();
    }

    private void printTable() {

        Cursor cursor = CalendardbHelper.readRecordOrderByAge();
        String result = "";

        result += "row 개수 : " + cursor.getCount() + "\n";
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry._ID));
            String one = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_DATE));
            int two = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_PART));
            int three = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_COLOR));

            result += itemId + " " + one + " " + Integer.toString(two) + " " + Integer.toString(three) + "\n";
            Log.d("*******", "printTable: "+one);

        }

        //Log.d("*******", "printTable: "+result);
        cursor.close();
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        Log.d("we are loco", "decorate: ");
        view.setBackgroundDrawable(drawable);
        view.addSpan(new customSelectedDate(activity, checkedList));
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