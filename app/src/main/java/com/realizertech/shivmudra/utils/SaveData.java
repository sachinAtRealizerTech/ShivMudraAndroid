package com.realizertech.shivmudra.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anh on 02/Feb/2017.
 */

public class SaveData {

    private static SharedPreferences sharedPreferences;
    public static void init(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE);
        }
    }

    public static String Token;
    public static String UserId;
    public static String userName;
    public static String SocietyName;

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        SaveData.sharedPreferences = sharedPreferences;
    }

    public static String getToken() {
        return Token;
    }

    public static void setToken(String token) {
        SaveData.Token = token;
    }

    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String userId) {
        UserId = userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        SaveData.userName = userName;
    }

    public static String getSocietyName() {
        return SocietyName;
    }

    public static void setSocietyName(String societyName) {
        SocietyName = societyName;
    }
}
