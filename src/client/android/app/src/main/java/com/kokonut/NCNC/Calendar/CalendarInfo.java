package com.kokonut.NCNC.Calendar;


import com.prolificinteractive.materialcalendarview.CalendarDay;

public class CalendarInfo{

    customDecorator CustomDecorator;
    CalendarDay date;

    public CalendarInfo(customDecorator CustomDecorator, CalendarDay date){
        this.CustomDecorator = CustomDecorator;
        this.date = date;
    }

    public customDecorator getCustomDecorator() {
        return CustomDecorator;
    }

    public void setCustomDecorator(customDecorator customDecorator) {
        CustomDecorator = customDecorator;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(CalendarDay date) {
        this.date = date;
    }
}
