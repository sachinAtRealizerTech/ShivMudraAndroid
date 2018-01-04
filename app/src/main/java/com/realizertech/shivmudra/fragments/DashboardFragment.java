package com.realizertech.shivmudra.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.MainActivity;
import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.ReferalActivity;
import com.realizertech.shivmudra.RegistrationActivity;
import com.realizertech.shivmudra.ReportPoorQualityActivity;
import com.realizertech.shivmudra.VegitableListActivity;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.model.ConsumerStatistics;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.SaveData;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Win on 12/19/2017.
 */

public class DashboardFragment extends BaseFragment {

    private ViewFlipper mViewFlipper;
    int[] resources = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
    };

    private Button placeOrder,referFriend,reportQuality;
    public TextView mallsave,marketsave,refferal,refferalearned;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(Config.actionBarTitle("Shiv-Mudra", getActivity()));
        ((MainActivity) getActivity()).getSupportActionBar().show();

        initiateView(rootView);
        // Add all the images to the ViewFlipper
        for (int i = 0; i < resources.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resources[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewFlipper.addView(imageView);
            //imageRef = storage.getReference().child(Constants.ACTIVE_DASHBOARd_IMAGE+"image"+i+".png");
            // uploadImage(imageView);
        }

        return rootView;
    }

    public void initiateView(View rootView){
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
        placeOrder = (Button) rootView.findViewById(R.id.btnplaceorder);
        referFriend = (Button) rootView.findViewById(R.id.btnrefferfriend);
        reportQuality = (Button) rootView.findViewById(R.id.btnreport);
        mallsave = (TextView) rootView.findViewById(R.id.txtsavemall);
        marketsave = (TextView) rootView.findViewById(R.id.txtsavemarket);
        refferal =(TextView) rootView.findViewById(R.id.txtrefferal);
        refferalearned = (TextView) rootView.findViewById(R.id.txtearned);
        // Set in/out flipping animations
        mViewFlipper.setInAnimation(getActivity(), R.anim.left_in);
        mViewFlipper.setOutAnimation(getActivity(), R.anim.left_out);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(5000);

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(sharedpreferences.getString("UserToken",""));

        if (Config.isConnectingToInternet(getActivity()))
        {
            loadData(userInputs);
        }
        else
        {
            Config.alertDialog(getActivity(),"Network Error","Please check your internet connection");
        }


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.placeOrder(getActivity());
            }
        });

        referFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReferalActivity.class);
                startActivity(intent);
            }
        });

        reportQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportPoorQualityActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadData(UserInputs userInputs){

        EnqueueWrapper.with(this).enqueue(ApiService.getService().getConsumerStatistics(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();

                        ConsumerStatistics consumerStatistics  = gson.fromJson(responseString,ConsumerStatistics.class);
                        if(consumerStatistics != null){
                            mallsave.setText("₹"+consumerStatistics.getMallCostSaved());
                            marketsave.setText("₹"+consumerStatistics.getMarketCostSaved());
                            refferal.setText(""+consumerStatistics.getReferralsCnt());
                            refferalearned.setText(""+consumerStatistics.getReferralsUsed());
                            if(getActivity() != null){
                                try {

                                    PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                                    int version = pInfo.versionCode;
                                    if(version != consumerStatistics.getAppCurrentVersionNo()){
                                        Config.updateDialog(getActivity());
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        else {
                            Log.e("branch List", "null");
                        }
                    }
                }


            }
        });
    }



}
