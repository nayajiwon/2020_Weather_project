package com.kokonut.NCNC.Calendar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.ArrayList;
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
    int[] dates_info;
    private CalendarDBHelper CalendardbHelper;

    customDecorator CustomDecorator;
    ArrayList<CalendarInfo> CalendarList;

    SQLiteDatabase sqlDB;

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
        CalendarList = new ArrayList<CalendarInfo>();
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

            Log.d("cur_part00 ", "devidepopupValue: ");

            Log.d("cur_part22 ", "devidepopupValue: " + checkedList);

            Log.d("cur_part33 ", "devidepopupValue: " + CalendarList.get(p).getPart());


            Log.d("cur_part44 ", "devidepopupValue: " + (CalendarList.get(p).getDate().toString()));

            Log.d("cur_part55 ", "devidepopupValue: " + clickedDate);

            Log.d(" 1 1 1 ", "\n\n\n\n");


            if(CalendarList.get(p).getDate().toString().equals(clickedDate.toString())){
                cur_part = CalendarList.get(p).getPart();

                Log.d("cur_part1 ", "devidepopupValue: ");

                Log.d("cur_part2 ", "devidepopupValue: " + checkedList);

                Log.d("cur_part3 ", "devidepopupValue: " + cur_part);

                if(checkedList != cur_part){
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
        CalendardbHelper.insertRecord(clickedDate.toString(), CustomDecorator.getColor(), CustomDecorator.getpart());

    }



    private void initCalendarDB(){

        //1. 빼고
        //2. 뺀 정보를 info class 에 나눠서 넣기
        Cursor cursor = CalendardbHelper.readRecordOrderByAge();

        int i = 0;
        while (cursor.moveToNext()) {

            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry._ID)); //?
            String gotDate = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_DATE));
            int gotPart = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_PART));
            String getCalendarObject = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_CALENDAROBJECT));

            //db 초기화 과정*
            parceDate(gotDate);
            CalendarDay CalendarDay_date = CalendarDay.from(dates_info[0], dates_info[1], dates_info[2]);
            customDecorator CustomDecorator = new customDecorator(getActivity(), drawable, CalendarDay_date , gotPart);

            // 1. arraylist 에 날짜대입
            CalendarList.add(new CalendarInfo(CustomDecorator, CalendarDay_date, gotPart));

            // 2. view 추가
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

        result = dates_string[i].replace("}", "");
        dates_info[i] = Integer.parseInt(result);

        for(i=0; i<3; i++)
            Log.d("$$$$$", "parceDate: " + dates_info[i]);

    }

    public void removeCustomDecorator(int checkedList) {

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