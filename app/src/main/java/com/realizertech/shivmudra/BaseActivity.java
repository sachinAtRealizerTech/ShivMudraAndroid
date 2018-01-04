package com.realizertech.shivmudra;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anh on 03/Feb/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private MaterialDialog loadingDialog;

    //protected int count  = 0;
    public void showLoading(String... msg) {
        if(isFinishing() )return;
        String content = "";
        if (msg.length == 0 || msg[0].length() == 0) content = getString(R.string.please_wait);
        else content = msg[0];

        if (loadingDialog == null) {
            loadingDialog = new MaterialDialog.Builder(this)
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
        if(isFinishing() )return;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void showError(String title, String message) {
        showError(title, message, null);
    }

    public void showError(String title, String message, DialogInterface.OnDismissListener callback) {
        if (isFinishing()) return;
        new MaterialDialog.Builder(this)
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
        if(loadingDialog != null){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }


    public interface AfterLogin{
        public void onAfterLogin();
    }

    public void replaceFragment(int resourceId, Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragmentCurrent = fragmentManager.findFragmentById(resourceId);
        if(fragmentCurrent != null && fragmentCurrent.getClass().equals(fragment.getClass())) return;
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(resourceId, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(int resourceId, Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragmentCurrent = fragmentManager.findFragmentById(resourceId);
        if(fragmentCurrent != null && fragmentCurrent.getClass().equals(fragment.getClass())) return;
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.add(resourceId, fragment);
        fragmentTransaction.commit();
    }

    public Fragment getFragmentFromView(int resourceId){
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragmentCurrent = fragmentManager.findFragmentById(resourceId);
        return fragmentCurrent;
    }

}
