package com.realizertech.shivmudra.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.BaseActivity;
import com.realizertech.shivmudra.MainActivity;
import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.ReferalActivity;
import com.realizertech.shivmudra.RegistrationActivity;
import com.realizertech.shivmudra.ReportPoorQualityActivity;
import com.realizertech.shivmudra.adapter.ReferralListAdapter;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.ReferFriendInput;
import com.realizertech.shivmudra.model.ReferralModel;
import com.realizertech.shivmudra.model.ReferralReminderInput;
import com.realizertech.shivmudra.model.Referrals;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.utils.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win on 12/19/2017.
 */

public class ViewReferralFragment extends BaseFragment {

    public ListView referrals;
    public List<Referrals> referralModelList;
    public ReferralListAdapter referralListAdapter;
    public String usertoken;
    public TextView noData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_referral_activity, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(Config.actionBarTitle("View Referrals", getActivity()));
        ((MainActivity) getActivity()).getSupportActionBar().show();

        initiateView(rootView);
        return rootView;
    }

    public void initiateView(View rootView){

        referrals = (ListView) rootView.findViewById(R.id.listreferral);
        noData = (TextView) rootView.findViewById(R.id.txtnoData);

        referralModelList = new ArrayList<>();
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        usertoken = sharedpreferences.getString("UserToken","");
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(usertoken);
        if (Config.isConnectingToInternet(getActivity()))
        {
            loadData(userInputs);
        }
        else
        {
            Config.alertDialog(getActivity(),"Network Error","Please check your internet connection");
        }


    }

    public void loadData(final UserInputs userInputs){
        EnqueueWrapper.with(this).enqueue(ApiService.getService().getReferrals(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Referrals>>() { }.getType();
                        referralModelList = gson.fromJson(responseString,type);
                        if(referralModelList != null){
                            if(referralModelList.size()>0){
                                referrals.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.GONE);
                                referralListAdapter = new ReferralListAdapter(referralModelList,getActivity(),ViewReferralFragment.this,usertoken);
                                referrals.setAdapter(referralListAdapter);
                            }
                            else {
                                noData.setVisibility(View.VISIBLE);
                                referrals.setVisibility(View.GONE);
                            }

                        }
                        else {
                            referrals.setVisibility(View.GONE);
                        }
                    }
                }


            }
        });

    }

    public void sendReminder(ReferralReminderInput referFriendInput){
        if (Config.isConnectingToInternet(getActivity()))
        {
            EnqueueWrapper.with(this).enqueue(ApiService.getService().sendReferralReminder(referFriendInput), new EnqueueWrapper.EnqueueSuccess() {
                @Override
                public void onSuccess(String responseString) {
                    hideLoading();

                    if(responseString != null){
                        if(!responseString.equalsIgnoreCase("true")){
                            Config.alertDialog(getActivity(),"Reminder","Sent Reminder successfully");
                        }
                    }


                }
            });
        }
        else
        {
            Config.alertDialog(getActivity(),"Network Error","Please check your internet connection");
        }

    }

}
