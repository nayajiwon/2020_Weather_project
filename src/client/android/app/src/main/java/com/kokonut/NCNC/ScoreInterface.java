package com.kokonut.NCNC;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScoreInterface {
    @GET("score/11B10101")
    //Call<RawResponseData> getScore(@Query("date")String date, @Query("score")String score);
    Call<ScoreContents> fetchScore();
    //Call<List<ScoreContents>> fetchScore();
    //Call<ScoreContents> getScore(@Query("date") String date);
}
