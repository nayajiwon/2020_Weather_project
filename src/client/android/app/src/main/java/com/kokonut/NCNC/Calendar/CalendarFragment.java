package com.kokonut.NCNC.Calendar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarFragment extends Fragment{
    TextView textView1;
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
    TextView text1, text2, text3;
    int[] dates_info;
    private CalendarDBHelper CalendardbHelper;
    int maxScoreDay;

    customDecorator CustomDecorator;
    ArrayList<CalendarInfo> CalendarList;

    SQLiteDatabase sqlDB;

    Calendar calToday = Calendar.getInstance();
    private CalendarInfo CalendarInfo;

    private static final int LOADER_ID = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar,container,false);
        materialCalendarView = viewGroup.findViewById(R.id.calendar_view);
        textView1 = viewGroup.findViewById(R.id.plan_text1);
        bottomeText1 = viewGroup.findViewById(R.id.bottom1_1);
        bottomeText2 = viewGroup.findViewById(R.id.bottom2_2);
        bottomeText3 = viewGroup.findViewById(R.id.bottom3_3);
        initbutton = viewGroup.findViewById(R.id.initbutton);
        text1 = viewGroup.findViewById(R.id.text1);
        text2 = viewGroup.findViewById(R.id.text2);
        text3 = viewGroup.findViewById(R.id.text3);
        CalendarList = new ArrayList<CalendarInfo>();
        drawable = this.getResources().getDrawable(R.drawable.calendar_circle_inside);
        CalendardbHelper = CalendarDBHelper.getInstance(getActivity());

        maxScoreDay = getArguments().getInt("maxScoreDay");
        Log.d("끝0", "initCalendarDB: " + maxScoreDay);
        Log.d("끝1", "initCalendarDB: " + out_minmilli);
        Log.d("끝2", "initCalendarDB: " + in_minmilli);


        initCalendarDB();
        initCalendar();

        //세차추천일
        String maxDayString = getDate(maxScoreDay);
        text3.setText(maxDayString);
        text3.setPaintFlags(text1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

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


    public static String getDate(int weekday){
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM월dd일");
        Calendar calendar = Calendar.getInstance(); //현재 날짜
        calendar.add(Calendar.DAY_OF_MONTH, weekday); //오늘로부터 일주일일때
        String day = format.format(calendar.getTime());
        Log.d("끝5", "getDate: "+day);

        return day;
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

        /**달력 날짜를 클릭했을 때 이벤트 처리**/
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                Calendar_PopupFragment calendar_popupfragment = new Calendar_PopupFragment();
                calendar_popupfragment.show(getFragmentManager(), "tag");
                clickedDate = date;
            }
        });


        initbutton.setText("초기화");
        initbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //1. view 의 데코레이터 모두 삭제
                materialCalendarView.removeDecorators();

                //2. arraylist 의 정보 모두 삭제
                CalendarList.clear();

                //3. db 데이터 모두 삭제
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

    //캘린더 뷰에 동그라미 뷰를 추가할 경우
    public void devidepopupValue(int checkedList){
        /*value 가 내부 , 외부 , 전체 인지에 따라 동그라미 아래 text 달리해줄것 */
        int cur_part;

        //이미 '내부'나 '외부' 중 하나가 체크돼있을 경우 '전체'로 바꿀지 말지 결정
        for(int p=0; p<CalendarList.size(); p++){

            Log.d("~!~!", "devidepopupValue: "+ CalendarList.get(p).getPart());
            //이미 동그라미가 처져있는 날짜를 또다시 클릭했을 때 전체로 바꿀지 판단
            if(CalendarList.get(p).getDate().toString().equals(clickedDate.toString())){
                cur_part = CalendarList.get(p).getPart(); //내부, 외부 여부를 가져옴

                if(checkedList != cur_part){ //내부, 외부를 겹쳐 선택 했을 경우
                    //기존뷰를 삭제해야함
                    checkedList = 3;
                }
            }
        }

        // 1. view 추가
        CustomDecorator = new customDecorator(getActivity(), drawable, clickedDate, checkedList);
        materialCalendarView.addDecorators(CustomDecorator);

        // 2. arraylist 에 날짜대입
        CalendarList.add(new CalendarInfo(CustomDecorator, clickedDate, checkedList));

        // 3. db에 날짜 추가
        Log.d("csg", "devidepopupValue: " + checkedList);
        CalendardbHelper.insertRecord(clickedDate.toString(), CustomDecorator.getColor(), checkedList);

    }


    long in_minmilli = -1; //내부세차
    long out_minmilli = -1; //외부세차
    long calmilli = 0;

    private void initCalendarDB(){

        Calendar calTemp;
        calTemp = calToday;


        //1. 빼고
        //2. 뺀 정보를 info class 에 나눠서 넣기
        Cursor cursor = CalendardbHelper.readRecordOrderByAge();

        int i = 0;
        while (cursor.moveToNext()) {

            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry._ID)); //?
            String gotDate = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_DATE));
            int gotPart = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_PART));
            Log.d("gjkd1", "initCalendarDB: "+gotPart);
            String getCalendarObject = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_CALENDAROBJECT));

            //db 초기화 과정*
            parceDate(gotDate);
            CalendarDay CalendarDay_date = CalendarDay.from(dates_info[0], dates_info[1], dates_info[2]);
            Log.d("222223", "initCalendarDB: " + CalendarDay_date.toString());


            //가장 최근 날짜를 찾음
            //milisecond 로 변환하기 위해 calendar type 설정
            calTemp.set(Calendar.YEAR , dates_info[0]);
            calTemp.set(Calendar.MONTH, dates_info[1]);
            calTemp.set(Calendar.DATE, dates_info[2]);


            calmilli = calTemp.getTimeInMillis(); //millisecond 형태로 비교

            //가장 최근 날짜로 저장한 내부, 외부 세차 날짜를 알기위해 최대값을 찾음
            Log.d("쭉", "initCalendarDB: " + in_minmilli);
            Date date2 = new Date(in_minmilli);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("포맷 쭉", "getGapDay: " + format2.format(date2));
            Log.d("포맷 쭉", "getGapDay: " + in_minmilli);

            if(gotPart == 1){
                if(calmilli > in_minmilli){
                    in_minmilli = calmilli;
                    Log.d("77771", "initCalendarDB: " + in_minmilli);
                }
            }

            if(gotPart == 2){
                if(calmilli > out_minmilli){
                    out_minmilli = calmilli;
                    Log.d("77772", "initCalendarDB: " + out_minmilli);
                }
            }

            customDecorator CustomDecorator = new customDecorator(getActivity(), drawable, CalendarDay_date , gotPart);

            // 1. arraylist 에 날짜대입
            CalendarList.add(new CalendarInfo(CustomDecorator, CalendarDay_date, gotPart));

            // 2. view 추가
            materialCalendarView.addDecorators(CustomDecorator);

        }
        cursor.close();


        Date date = new Date(calToday.getTimeInMillis());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(date));
        Log.d("포맷 데이트3", "getGapDay: " + format1.format(date));

        Log.d("끝1", "initCalendarDB: " + out_minmilli);
        Log.d("끝2", "initCalendarDB: " + in_minmilli);



        long subCal;

        if(in_minmilli > -1){
            subCal = getGapDay(in_minmilli);
            text1.setText(Long.toString(subCal)+"일 경과");
            text1.setPaintFlags(text1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        }
        else{ // 가장 최근에 한 내부세차를 찾음

        }

        if(out_minmilli > -1){
            subCal = getGapDay(out_minmilli);
            text2.setText(Long.toString(subCal)+"일 경과");
            text2.setPaintFlags(text1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        }
        else{

        }




    }

    private long getGapDay(long calendaree){

        Calendar calendarToday = Calendar.getInstance();
/*
        Date date = new Date(calendarToday.getTimeInMillis());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date2 = new Date(calendaree);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
  */

        long sub = (calendaree - calendarToday.getTimeInMillis());
        Log.d("포맷 데이트4410", "getGapDay: " + sub);

        if(sub < 0){
            sub *= -1;
        }

        long oneday = 1000 * 60 * 60 * 24;
        long day = sub / oneday;
        Log.d("차이", "getGapDay: " + day);

        return day;
    }

    private void parceDate(String calendar_date){

        int i=0; int last = 0;
        String result;
        dates_info = new int[3];

        result = calendar_date.substring(12);
        String[] dates_string = result.split("-");

        for(i=0; i<2; i++)
            dates_info[i] = Integer.parseInt(dates_string[i]);

        result = dates_string[i].replace("}", "");
        dates_info[i] = Integer.parseInt(result);

        for(i=0; i<3; i++)
            Log.d("$$$$$", "parceDate: " + dates_info[i]);

    }

    public void removeCustomDecorator(int checkedList) {

        //삭제 버튼을 눌렀을 때
        if (CalendarList.size() > 0 && checkedList == 4) {
            for (int i = 0; i < CalendarList.size(); i++) {
                if (CalendarList.get(i).getDate().equals(clickedDate.toString())) {
                    Log.d("2-----isthesame", "removeCustomDecorator: " + CalendarList.get(i).getDate());
                    //1. Arraylist 에서 삭제
                    materialCalendarView.removeDecorator(CalendarList.get(i).getCustomDecorator());
                    materialCalendarView.invalidateDecorators();
                    CalendarList.remove(CalendarList.get(i));

                    //2. DB에서 삭제
                    CalendardbHelper.deleteRecord(clickedDate.toString());
                }
            }
        }


    }
}