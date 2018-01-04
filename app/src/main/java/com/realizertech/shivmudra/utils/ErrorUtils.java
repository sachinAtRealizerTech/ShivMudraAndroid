package com.realizertech.shivmudra.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.realizertech.shivmudra.apicalls.ApiService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by anh on 03/Feb/2017.
 */

public class ErrorUtils<T> {

    public T parseError(Type type, Response<ResponseBody> response, String body) {
        Converter<ResponseBody, T> converter =
                ApiService.getRetrofit()
                        .responseBodyConverter(type, new Annotation[0]);

        T error;
        if (response.errorBody() != null) {
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            try {
                error = new Gson().fromJson(body, type);
            }
            catch (JsonSyntaxException e){
                e.printStackTrace();
                return null;
            }
        }
        return error;
    }


    public T parseError(Type type, Response<ResponseBody> response) {
        Converter<ResponseBody, T> converter =
                ApiService.getRetrofit()
                        .responseBodyConverter(type, new Annotation[0]);

        T error;
        if (response.errorBody() != null) {
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            try {
                String body = response.body().string();
                error = new Gson().fromJson(body, type);
            }
            catch (JsonSyntaxException e){
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return error;
    }
}
