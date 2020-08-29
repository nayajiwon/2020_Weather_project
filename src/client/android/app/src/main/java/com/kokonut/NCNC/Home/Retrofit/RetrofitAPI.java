package com.kokonut.NCNC.Home.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {
    @GET("score/11B10101")
    Call<ScoreContents> fetchScore();

    @GET("weather/11B10101")
    Call<WeatherContents> fetchWeather();

    @GET("car_wash/list")
    Call<CarWashContents> fetchCarWash();
}
