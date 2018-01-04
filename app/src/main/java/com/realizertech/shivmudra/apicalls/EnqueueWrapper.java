package com.realizertech.shivmudra.apicalls;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.realizertech.shivmudra.BaseActivity;
import com.realizertech.shivmudra.RegistrationActivity;
import com.realizertech.shivmudra.exceptionhandler.NetworkException;
import com.realizertech.shivmudra.fragments.BaseFragment;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.Convert;
import com.realizertech.shivmudra.utils.ErrorUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anh on 19/Feb/2017.
 */

public class EnqueueWrapper {

    private BaseActivity baseActivity;
    private BaseFragment baseFragment;
    private boolean inFragment = false;

    private boolean showLoading = true;
    private boolean hideLoadingWhenSuccess = true;
    private String loadingTitle = "";

    private EnqueueWrapper() {
    }

    public static EnqueueWrapper with(BaseActivity baseActivity) {
        EnqueueWrapper enqueueWrapper = new EnqueueWrapper();
        enqueueWrapper.baseActivity = baseActivity;
        enqueueWrapper.inFragment = false;
        return enqueueWrapper;
    }

    public static EnqueueWrapper with(BaseFragment baseFragment) {
        EnqueueWrapper enqueueWrapper = new EnqueueWrapper();
        enqueueWrapper.baseFragment = baseFragment;
        enqueueWrapper.inFragment = true;
        return enqueueWrapper;
    }

    public EnqueueWrapper showLoading(boolean isShow){
        this.showLoading = isShow;
        return this;
    }

    public EnqueueWrapper hideLoadingWhenSuccess(boolean hideLoadingWhenSuccess){
        this.hideLoadingWhenSuccess = hideLoadingWhenSuccess;
        return this;
    }

    public EnqueueWrapper loadingTitle(String loadingTitle){
        this.loadingTitle = loadingTitle;
        return this;
    }

    public void enqueue(Call<ResponseBody> call, final EnqueueSuccess enqueueSuccess) {
        showLoading();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String body = "";
                    try {
                        if (hideLoadingWhenSuccess) hideLoading();
                        body = response.body().string();
                        body = body.replaceAll("^\"|\"$", "");
                        if (enqueueSuccess != null){
                            if(body == null || body.isEmpty() || body.trim().equalsIgnoreCase("null")){
                                Log.e("Error", "No response");
                            }
                            else enqueueSuccess.onSuccess(body);
                        }

                    } catch (IOException e) {
                        hideLoading();
                        e.printStackTrace();
                    } catch (JsonSyntaxException e) {
                        hideLoading();
                        e.printStackTrace();
                        String error = new ErrorUtils<String>().parseError(String.class, response, body);
                        error = error.replaceAll("^\"|\"$", "");
                        error = getError(error);
                        showError("Error", error,call.request());
                    }
                } else {
                    hideLoading();
                    String error = new ErrorUtils<String>().parseError(String.class, response);
                    error = error.replaceAll("^\"|\"$", "");
                    error = getError(error);
                    showError("Error", error, call.request());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideLoading();
                String errMsg = Convert.getThrowableMessage(t);
                errMsg = getError(errMsg);

                showError("Error", errMsg, call.request());
            }
        });
    }

    private String getError(String error){
        JSONObject obj = null;
        try {
            obj = new JSONObject(error);
            if(obj.getString("Message").equalsIgnoreCase("CONTACTNO-PRE-REGISTERED")){
                error = "Mobile Number already registered";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return error;
        }
        return error;
    }
    private String bodyToString(final Request request) {
        try {
            final RequestBody copy = request.body();
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
    private void showError(String title, String error, Request request) {
        if(request != null){
            String exception = "";
            if(request.body() != null){

                exception = "API Name: "+request.url().toString()+"\nInput: "+bodyToString(request)+"\nError"+error;
            }
            else {
                exception = "API Name: "+request.url().toString()+"\nInput: Null"+"\nError"+error;
            }

            if (inFragment){
                baseFragment.showError(title, error);
                NetworkException.insertNetworkException(baseFragment.getActivity(), exception);
            }
            else {
                baseActivity.showError(title, error);
                NetworkException.insertNetworkException(baseActivity.getBaseContext(), exception);
            }
        }

    }

    private void hideLoading() {
        if (inFragment) baseFragment.hideLoading();
        else baseActivity.hideLoading();
    }

    private void showLoading() {
        if(!showLoading) return;
        if (inFragment) baseFragment.showLoading(loadingTitle);
        else baseActivity.showLoading(loadingTitle);
    }

    public static interface EnqueueSuccess {
        public void onSuccess(String responseString);
    }
}
