package com.kokonut.NCNC;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScoreContents {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }


    public class Content {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("regID")
        @Expose
        private String regID;
        @SerializedName("rn_lv")
        @Expose
        private Integer rnLv;
        @SerializedName("ta_lv")
        @Expose
        private Integer taLv;
        @SerializedName("score")
        @Expose
        private Integer score;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRegID() {
            return regID;
        }

        public void setRegID(String regID) {
            this.regID = regID;
        }

        public Integer getRnLv() {
            return rnLv;
        }

        public void setRnLv(Integer rnLv) {
            this.rnLv = rnLv;
        }

        public Integer getTaLv() {
            return taLv;
        }

        public void setTaLv(Integer taLv) {
            this.taLv = taLv;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }

}
