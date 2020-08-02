package com.kokonut.NCNC;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;


public class DataInputActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.21.20.24";
    private static String TAG = "phptest";

    private EditText mEditTextId;
    private EditText mEditTextName;
    private EditText mEditTextLatitude;
    private EditText mEditTextLongitude;
    private TextView mTextViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        mEditTextId = (EditText)findViewById(R.id.editText_input_id);
        mEditTextName = (EditText)findViewById(R.id.editText_input_name);
        mEditTextLatitude = (EditText)findViewById(R.id.editText_input_latitude);
        mEditTextLongitude = (EditText)findViewById(R.id.editText_input_longitude);
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id =  Integer.parseInt(mEditTextId.getText().toString());
                String name = mEditTextName.getText().toString();
                //float latitude = Float.parseFloat(mEditTextLatitude.getText().toString());
                //float longitude = Float.parseFloat(mEditTextLongitude.getText().toString());
                String id = mEditTextId.getText().toString();
                String latitude = mEditTextLatitude.getText().toString();
                String longitude = mEditTextLongitude.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", id, name, latitude, longitude);

                mEditTextId.setText("");
                mEditTextName.setText("");
                mEditTextLatitude.setText("");
                mEditTextLongitude.setText("");


            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DataInputActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {


            int id = Integer.parseInt(params[1]);
            String name = (String)params[2];
            float latitude =  Float.parseFloat(params[3]);
            float longitude = Float.parseFloat(params[4]);

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&name=" + name + "&latitude=" + latitude + "&longitude=" + longitude;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}
