package com.realizertech.shivmudra.utils;

import android.content.Context;
import android.content.res.Resources;

import android.util.DisplayMetrics;

import java.net.UnknownHostException;

import java.util.Locale;


/**
 * Created by TheAnh on 1/24/2017.
 */

public class Convert {
    public static float dpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    public static float pixelToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static String double2dot(double value){
        String text =  String.format(Locale.US, "%.2f", value);
        for (int i = 0; i < 3; i++){
            if(text.endsWith(".") || text.endsWith(",")){
                text = text.substring(0, text.length() - 1);
                break;
            }
            if(text.endsWith("0")){
                text = text.substring(0, text.length() - 1);
            }
        }
        return text;
    }

    public static String notNull(String text){
        return text != null ? text : "";
    }

    public static String shortText(String text){
        return (text == null || text.isEmpty())?  "" : String.valueOf(text.charAt(0));
    }



    public static String getThrowableMessage(Throwable throwable){
        if(throwable == null) return "";
        else{
            if(throwable instanceof UnknownHostException){
                return "There was a problem completing your request. Please check your internet connection and try again.";
            }

            String message = throwable.getMessage();
            if(message != null)
                return message;
            else {
                return throwable.getClass().getName();
            }
        }
    }

}
