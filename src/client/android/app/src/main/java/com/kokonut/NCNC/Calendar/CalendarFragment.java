package com.kokonut.NCNC.Calendar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.util.Date;
import java.util.HashSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class CalendarFragment extends Fragment {
    TextView textView1;
    ViewGroup viewGroup;
    CalendarDay clickedDate;
    MaterialCalendarView materialCalendarView;
    Drawable drawable;
    FragmentTransaction ft;

    String popupResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar,container,false);
        materialCalendarView = viewGroup.findViewById(R.id.calendar_view);
        textView1 = viewGroup.findViewById(R.id.plan_text1);
//      drawable = this.getResources().getDrawable(R.drawable.calendar_emptycircle_inside_purple);
        drawable = this.getResources().getDrawable(R.drawable.calendar_circle_inside);

        initCalendar();

        return viewGroup;
    }



    @Override
    public void onStart(){
        super.onStart();
        textView1.setText("나의 세차 일정");
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void initCalendar() {

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        materialCalendarView.state().edit()
                    //.setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2020, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        /*오늘 날짜에 초록색 효과를 줌*/
        OneDayDecorator oneDayDecorator = new OneDayDecorator();
        materialCalendarView.addDecorators(oneDayDecorator);
        materialCalendarView.addDecorators(oneDayDecorator);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                Calendar_PopupFragment calendar_popupfragment = new Calendar_PopupFragment();
                calendar_popupfragment.show(getFragmentManager(), "tag");
                clickedDate = date;

            }
        });
    }

    private void callUploadDialog()
    {

        Calendar_PopupFragment fragment = new Calendar_PopupFragment();
        fragment.setTargetFragment(this, 0);

        FragmentManager manager;

        manager = getFragmentManager();
        ft = manager.beginTransaction();

        fragment.show(ft, "UploadDialogFragment");
        fragment.setCancelable(false);
    }


    public void devidepopupValue(String checkedList){
        /*value 가 내부 , 외부 , 전체 인지에 따라 동그라미 아래 text 달리해줄것 */
        customDecorator CustomDecorator = new customDecorator(getActivity(), drawable, clickedDate, checkedList);
        materialCalendarView.addDecorators(CustomDecorator);

    }
}