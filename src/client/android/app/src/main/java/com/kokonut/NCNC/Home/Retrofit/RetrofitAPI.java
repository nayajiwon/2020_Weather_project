package com.kokonut.NCNC.Home.Retrofit;

import com.kakao.sdk.user.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @GET("score/11B10101")
    Call<ScoreContents> fetchScore();

    @GET("weather/11B10101")
    Call<WeatherContents> fetchWeather();

    @GET("car_wash/list")
    Call<CarWashContents> fetchCarWash();

    @FormUrlEncoded
    @POST("account/check")
    Call<UserContents> fetchUser(@FieldMap Map<String, String> params);
}
