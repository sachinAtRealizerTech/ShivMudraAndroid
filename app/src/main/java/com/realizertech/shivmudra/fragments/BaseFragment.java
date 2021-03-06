package com.realizertech.shivmudra.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anh on 10/Nov/2016.
 */
public class BaseFragment extends Fragment {
    private MaterialDialog loadingDialog;

    //protected int count  = 0;
    public void showLoading(String... msg) {
        if (getActivity() == null) return;

        String content = "";
        if (msg.length == 0 || msg[0].length() == 0) content = getString(R.string.please_wait);
        else content = msg[0];

        if (loadingDialog == null) {
            loadingDialog = new MaterialDialog.Builder(getActivity())
                    .content(content)
                    .progress(true, 0)
                    .show();

        } else {
            if (!loadingDialog.isShowing()) {
                loadingDialog.setContent(content);
                loadingDialog.show();
            }
        }

    }

    public void hideLoading() {
        if (getActivity() == null) return;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void showError(String title, String message) {
        showError(title, message, null);
    }

    public void showError(String title, String message, DialogInterface.OnDismissListener callback) {
        if (getActivity() == null) return;
        new MaterialDialog.Builder(getActivity())
                .positiveColorRes(R.color.colorPrimary)
                .positiveText("OK")
                .content(message)
                .title(title)
                .dismissListener(callback)
                .show();
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }


    public void replaceFragment(int resourceId, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragmentCurrent = fragmentManager.findFragmentById(resourceId);
        if (fragmentCurrent != null && fragmentCurrent.getClass().equals(fragment.getClass())) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(resourceId, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(int resourceId, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragmentCurrent = fragmentManager.findFragmentById(resourceId);
        if (fragmentCurrent != null && fragmentCurrent.getClass().equals(fragment.getClass())) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(resourceId, fragment);
        fragmentTransaction.commit();
    }

    public Fragment getFragmentFromView(int resourceId) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragmentCurrent = fragmentManager.findFragmentById(resourceId);
        return fragmentCurrent;
    }


}
