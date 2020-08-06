package com.kokonut.NCNC.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.kokonut.NCNC.MainActivity;
import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

public class CalendarFragment extends Fragment{
    TextView textView1;
    TextView bottom_explain;
    ViewGroup viewGroup;
    CalendarDay clickedDate;
    MaterialCalendarView materialCalendarView;
    Drawable drawable;
    FragmentTransaction ft;
    String popupResult;
    TextView bottomeText1;
    TextView bottomeText2;
    TextView bottomeText3;
    TextView initbutton;
    int[] dates_info;
    ArrayList<CalendarInfo> CalendarInfoList;
    private CalendarDBHelper CalendardbHelper;

    customDecorator CustomDecorator;

    ArrayList<CalendarInfo> CalendarList = new ArrayList<CalendarInfo>();

    SQLiteDatabase sqlDB;


    private static final int LOADER_ID = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /** 캘린더 뷰 외에 유지돼야 할 데이터 이곳에 저장하기!
     @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("calendarview",  );
    }
     **/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("\n\n\n-----------\n\n\n", "onCreateView: ");
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar,container,false);
        materialCalendarView = viewGroup.findViewById(R.id.calendar_view);
        textView1 = viewGroup.findViewById(R.id.plan_text1);

        bottomeText1 = viewGroup.findViewById(R.id.bottom1_1);
        bottomeText2 = viewGroup.findViewById(R.id.bottom2_2);
        bottomeText3 = viewGroup.findViewById(R.id.bottom3_3);
        initbutton = viewGroup.findViewById(R.id.initbutton);


        drawable = this.getResources().getDrawable(R.drawable.calendar_circle_inside);
        CalendardbHelper = CalendarDBHelper.getInstance(getActivity());


        initCalendarDB();
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

        bottomeText1.setText("세차 예정일 ");
        bottomeText2.setText("세차한 날 ");
        bottomeText3.setText("세차하기 좋은 날");

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        materialCalendarView.state().edit()
                    //.setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2020, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        /*오늘 날짜에 초록색 효과를 줌*/
        OneDayDecorator oneDayDecorator = new OneDayDecorator("세차새차");
        materialCalendarView.addDecorators(oneDayDecorator);
        //materialCalendarView.addDecorators(oneDayDecorator);



        /**달력 날짜를 클릭했을 때 이벤트 처리**/
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                Calendar_PopupFragment calendar_popupfragment = new Calendar_PopupFragment();
                calendar_popupfragment.show(getFragmentManager(), "tag");
                clickedDate = date;

                //CalendarList.get(0).getCustomDecorator()

            }


        });


        initbutton.setText("초기화");
        initbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //1. view 의 데코레이터 모두 삭제
                materialCalendarView.removeDecorators();

                //2. db 데이터 모두 삭제
                sqlDB = CalendardbHelper.getWritableDatabase();
                CalendardbHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();

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

    //CalendarInfo calendarInfo;
    public void devidepopupValue(int checkedList){
        /*value 가 내부 , 외부 , 전체 인지에 따라 동그라미 아래 text 달리해줄것 */
        CustomDecorator = new customDecorator(getActivity(), drawable, clickedDate, checkedList, CalendardbHelper);
        materialCalendarView.addDecorators(CustomDecorator);

        /** arraylist 에 날짜대입 **/
        CalendarList.add(new CalendarInfo(CustomDecorator, clickedDate));
        /*
        if(CalendarList.size()>2) {
            materialCalendarView.removeDecorator(CalendarList.get(2).getCustomDecorator());
            Log.d("!!!!!!!!!!!!!!!!!!!", "devidepopupValue: ");
        }
        */


    }

    private void initCalendarDB(){

        //1. 빼고
        //2. 뺀 정보를 info class 에 나눠서 넣기
        Cursor cursor = CalendardbHelper.readRecordOrderByAge();
        CalendarInfoList = new ArrayList<CalendarInfo>();

        int i = 0;


        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry._ID)); //?
            String gotDate = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_DATE));
            int gotPart = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_PART));

            /**db 초기화 과정**/
            parceDate(gotDate);
            CalendarDay CalendarDay_date = CalendarDay.from(dates_info[0], dates_info[1], dates_info[2]);

            customDecorator CustomDecorator = new customDecorator(getActivity(), drawable, CalendarDay_date , gotPart, CalendardbHelper);
            materialCalendarView.addDecorators(CustomDecorator);

        }

        cursor.close();

    }

    private void parceDate(String calendar_date){

        int i=0; int last = 0;
        String result;
        dates_info = new int[3];

        result = calendar_date.substring(12);
        String[] dates_string = result.split("-");

        for(i=0; i<2; i++)
            dates_info[i] = Integer.parseInt(dates_string[i]);

        //dates_info[i] = Integer.parseInt(dates_string[i].substring(0,2));
        result = dates_string[i].replace("}", "");
        dates_info[i] = Integer.parseInt(result);

        for(i=0; i<3; i++)
            Log.d("$$$$$", "parceDate: " + dates_info[i]);

    }

    public void removeCustomDecorator(int checkedList){

        Log.d("remove -------------", "removeCustomDecorator: "+ checkedList);

        if(CalendarList.size() > 0 && checkedList == 4) {
            for (int i = 0; i < CalendarList.size(); i++) {
                Log.d("getDate", "date is" + CalendarList.get(i).getDate());
                Log.d("clickedDate", "date is" + clickedDate.toString());

                if (CalendarList.get(i).getDate().equals(clickedDate.toString())) {
                    Log.d("isthesame", "removeCustomDecorator: ");
                    materialCalendarView.removeDecorator(CalendarList.get(i).getCustomDecorator());
                    materialCalendarView.invalidateDecorators();
                    //CalendarList.remove(i);
                }
            }
        }
    }

    private void decodeCalendarDay(){

    }


}