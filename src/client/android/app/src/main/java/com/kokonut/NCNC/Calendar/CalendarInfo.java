package com.kokonut.NCNC.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class CalendarInfo implements Parcelable {


    String dayOfWeek;
    String week;
    String yearOfWeek;

/*
    CalendarDay
    CalendarDay parseCalendarDay(){

        return new CalendarDay();
    }
*/
    public CalendarInfo(String a, int b, int c){
        this.dayOfWeek = a;
        this.week = Integer.toString(b);
        this.yearOfWeek = Integer.toString(c);
    }


    protected CalendarInfo(Parcel in) {
        dayOfWeek = in.readString();
        week = in.readString();
        yearOfWeek = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(dayOfWeek);
        dest.writeString(week);
        dest.writeString(yearOfWeek);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalendarInfo> CREATOR = new Creator<CalendarInfo>() {
        @Override
        public CalendarInfo createFromParcel(Parcel in) {
            return new CalendarInfo(in);
        }

        @Override
        public CalendarInfo[] newArray(int size) {
            return new CalendarInfo[size];
        }
    };
}
