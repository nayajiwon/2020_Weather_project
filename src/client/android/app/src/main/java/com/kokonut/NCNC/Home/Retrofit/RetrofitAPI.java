package com.kokonut.NCNC.Home.Retrofit;

import com.kakao.sdk.user.model.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @GET("score/11B10101")
    Call<ScoreContents> fetchScore();

    @GET("weather/11B10101")
    Call<WeatherContents> fetchWeather();

    @GET("car_wash/list")
    Call<CarWashContents> fetchCarWash();

    @FormUrlEncoded
    @POST("car_wash/review/{id}")
    Call<ReviewResponse> writeReview (@Path("id") String id, @FieldMap HashMap<String, String> params);

    @GET("car_wash/review/{id}")
    Call<ReviewContents> fetchReview (@Path("id") String id);

    @FormUrlEncoded
    @POST("account/check")
    Call<UserContents> fetchUser(@FieldMap HashMap<String, String> params);


}
