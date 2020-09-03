package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewContents {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("reviews")
    @Expose
    private List<Content> reviews = null;

    public Integer getId() {
        return id;
    }

    public List<Content> getReviews() { return reviews; }

    public class Content {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("wash_id")
        @Expose
        private String wash_id;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("user_name")
        @Expose
        private String user_name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("date")
        @Expose
        private String date;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getContent() {
            return content;
        }
        public String getDate() {
            return date;
        }
        public String getWash_id() { return wash_id;}
    }
}