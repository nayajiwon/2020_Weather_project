package com.kokonut.NCNC.Home.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getStatus() {return status;}
    public Integer getId() {
        return id;
    }


}
