package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.kokonut.NCNC.MainActivity;
import com.kokonut.NCNC.R;

public class Calendar_PopupFragment extends DialogFragment {
    View view;
    TextView textView_Date;
    ImageButton buttonAdd;
    Context context;

    uploadDialogInterface interfaceObj;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view  = inflater.inflate(R.layout.activity_calendar_popup, container);
        textView_Date = view.findViewById(R.id.calendar_textview_date);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        Log.d("wow", "customDecorator: is null 88");

        setCancelable(false); //popup에서 여백을 만져도 꺼지지 않게 함
        checkList();
        Log.d("wow", "customDecorator: is null 99");

        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interfaceObj = (uploadDialogInterface)activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement interfaceObj");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        textView_Date.setText("2020년 7월 23일");
    //  textView_Date.setPaintFlags(textView_Date.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

}

    //.//.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
       //..? interfaceObj= (uploadDialogInterface) getTargetFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interfaceObj = null;
    }

    private void checkList(){
        Log.d("wow", "customDecorator: is null 1111");

        final CheckBox checkBox1 = view.findViewById(R.id.checkBox1);
        final CheckBox checkBox2 = view.findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = view.findViewById(R.id.checkBox3);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = "_";
                if(checkBox1.isChecked() == true) result += (checkBox1.getText().toString() + "_");
                if(checkBox2.isChecked() == true) result += (checkBox2.getText().toString() + "_");
                if(checkBox3.isChecked() == true) result += (checkBox3.getText().toString() + "_");

                Log.d("wow", "customDecorator: is null 1122");


                if(interfaceObj != null && result != "_") {
                    Log.d("wow7", "customDecorator: is null 1122-2");
                    interfaceObj.senddatatoCalendarFragment(result);
                }
                else
                    Log.d("wow7", "customDecorator: is null 1122-3");

                //test중
/*
                Intent data =  new Intent();
                data.putExtra("at", result);
                Log.d("wow", "customDecorator: is null 1122-2");
                //Fragment frag = getTargetFragment();
                Fragment frag = getFragmentManager().findFragmentById(R.id.popup_screen);
                if(frag == null)
                    Log.d("444", "onClick: ");
                else
                    Log.d("4441", "onClick: ");
                //frag.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                Log.d("wow", "customDecorator: is null 1122-3");
*/
                dismiss();

                Log.d("wow", "customDecorator: is null 1133");


            }
        });

    }
    public interface uploadDialogInterface
    {
        //자동으로 public 으로 선언되기 때문에 public 안써도 됨
        void senddatatoCalendarFragment(String title);
    }
}
