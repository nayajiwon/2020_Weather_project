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

    @GET("seoul/?token=0610c5df7c85fe03547cb636204d1f4c6e87bd28")
    Call<RealTimeWeatherContents> fetchRealtimeWeather();

    //@GET("getSiGunGuList?ServiceKey=GXmd7CyHCZHX4C42RPWEDDQ7k3vAV6JfoKhWYloFs0iCho%2BxNaXY1Eod1FpctxTHNoJqtajSqpgbfUiyFltYVQ%3D%3D&brtcCd=서울")
    //Call<Location1Contents> fetchLocation1();
}
