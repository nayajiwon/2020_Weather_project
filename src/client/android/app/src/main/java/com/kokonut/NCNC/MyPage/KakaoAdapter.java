package com.kokonut.NCNC.MyPage;

import android.content.Context;
import android.util.Log;

import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.AccessTokenInfo;
import com.kakao.sdk.user.model.User;

public class KakaoAdapter {
    Context mContext = null;
    OAuthToken oAuthToken;
    String appKey = "161c3ebdf4c87831197714a8529765a4";
    String TAG = "OAUTH";
    User user = null;
    AccessTokenInfo accessTokenInfo = null;


    private static KakaoAdapter kakaoAdapter = null;

    public KakaoAdapter(Context mContext){
        this.mContext = mContext;
        KakaoSdk.init(mContext,this.appKey);
    }

    public static KakaoAdapter getInstance(Context mContext){
        if(kakaoAdapter == null) kakaoAdapter = new KakaoAdapter(mContext);
        return kakaoAdapter;
    }

    private void loadTokenInfo(){
        UserApiClient.getInstance().accessTokenInfo((accessTokenInfo, throwable1) -> {
            if (throwable1 != null) {
                Log.e(TAG, "토큰 정보 보기 실패", throwable1);
            }
            else if (accessTokenInfo != null) {
                this.accessTokenInfo = accessTokenInfo;
                Log.i(TAG, "토큰 정보 보기 성공 \n회원번호: "+this.accessTokenInfo.getId());
            }
            return null;
        });
    }

    private void callback1(OAuthToken oAuthToken, Throwable throwable){
        if (throwable != null) {
            Log.e(TAG, "로그인 실패", throwable);
        }
        else if (oAuthToken != null) {
            Log.i(TAG, "로그인 성공 "+oAuthToken);
            this.oAuthToken = oAuthToken;
            loadProfile();
        }
    }

    public void kakaoLogin(){

        if (LoginClient.getInstance().isKakaoTalkLoginAvailable(mContext)) {
            LoginClient.getInstance().loginWithKakaoTalk(mContext, (oAuthToken1, throwable) -> {
                callback1(oAuthToken1,throwable);
                return null;
            });
        } else {
            LoginClient.getInstance().loginWithKakaoAccount(mContext, (oAuthToken1, throwable) -> {
                callback1(oAuthToken1,throwable);
                return null;
            });
        }

    }

    public void loadProfile(){
        if(this.accessTokenInfo == null) loadTokenInfo();
        UserApiClient.getInstance().me(((user, throwable) -> {
            if (throwable != null) {
                Log.e(TAG, "사용자 정보 요청 실패", throwable);
            }
            else if (user != null) {
                this.user = user;
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호:" +user.getId()+
                        "\n이메일: " +user.getKakaoAccount().getProfile().getNickname());
            }
            return null;
        }));

    }


    public User getUser() {
        return user;
    }

    public OAuthToken getoAuthToken() {
        return oAuthToken;
    }
}
