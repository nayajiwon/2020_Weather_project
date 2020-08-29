package com.kokonut.NCNC.Home.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BaseURL = "http://3.131.33.128:8000/";
    private static Retrofit mRetrofit = null;


    public static Retrofit getClient(){
        Gson gson = new GsonBuilder().setLenient().create();

        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }
}