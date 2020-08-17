package com.kokonut.NCNC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UsingScoreData{
    //서버에서 받아온 세차점수 -> 순서대로 scoreList에 add

    public String[] score = new String[] {};
    public Integer[] rn_lv = new Integer[] {}; //강수
    public Integer[] ta_lv = new Integer[] {}; //기온

    public UsingScoreData(List<ScoreContents.Content> mlist){
        for(int i=0; i<8; i++){
            rn_lv[i] = mlist.get(i).getRnLv();
            ta_lv[i] = mlist.get(i).getTaLv();

            score[i] = String.valueOf(rn_lv[i]*9 + ta_lv[i]*2); //강수:기온=9:1
        }
    }

    /*
    public UsingScoreData(List<ScoreContents.Content> mlist) {
        for(int i=0; i<8; i++){
            score[i] = mlist.get(i).getScore().toString();
            //scoreList.add(i, mlist.get(i).getScore().toString());
        }
    }*/

    public String[] getScoreArr(){
        return score;
    }


}
