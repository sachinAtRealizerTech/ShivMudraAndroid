package com.realizertech.shivmudra.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.MainActivity;
import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.ReferalActivity;
import com.realizertech.shivmudra.adapter.DiscountListAdapter;
import com.realizertech.shivmudra.adapter.ReferralListAdapter;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.model.DiscountModel;
import com.realizertech.shivmudra.model.DiscountsReceived;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.ReferralModel;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.utils.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win on 12/19/2017.
 */

public class ViewDiscountFragment extends BaseFragment {

    public ListView referrals;
    public List<DiscountsReceived> discountModelList;
    public DiscountListAdapter discountListAdapter;
    public TextView noData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_referral_activity, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(Config.actionBarTitle("Discount Received", getActivity()));
        ((MainActivity) getActivity()).getSupportActionBar().show();

        initiateView(rootView);
        return rootView;
    }

    public void initiateView(View rootView){

        referrals = (ListView) rootView.findViewById(R.id.listreferral);
        noData = (TextView) rootView.findViewById(R.id.txtnoData);
        noData.setText("No Discounts Received As of Now !");


        discountModelList = new ArrayList<>();
        if (Config.isConnectingToInternet(getActivity()))
        {
            loadData();
        }
        else
        {
            Config.alertDialog(getActivity(),"Network Error","Please check your internet connection");
        }

    }

    public void loadData(){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(sharedpreferences.getString("UserToken",""));

        EnqueueWrapper.with(this).enqueue(ApiService.getService().getDiscountsReceived(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DiscountsReceived>>() { }.getType();
                        discountModelList = gson.fromJson(responseString,type);
                        if(discountModelList.size()>0){
                            discountListAdapter = new DiscountListAdapter(discountModelList,getActivity());
                            referrals.setAdapter(discountListAdapter);
                            noData.setVisibility(View.GONE);
                            referrals.setVisibility(View.VISIBLE);
                        }
                        else {
                            noData.setVisibility(View.VISIBLE);
                            referrals.setVisibility(View.GONE);
                            Log.e("branch List", "null");
                        }
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        referrals.setVisibility(View.GONE);
                    }
                }


            }
        });

    }

}
