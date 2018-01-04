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
import com.realizertech.shivmudra.VegitableListActivity;
import com.realizertech.shivmudra.adapter.OrderedListAdapter;
import com.realizertech.shivmudra.adapter.VegetableListAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.model.Item;
import com.realizertech.shivmudra.model.Order;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.model.VegetableModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win on 12/19/2017.
 */

public class ViewLastOrderFragment extends BaseFragment{


    public ListView vegies;
    public List<VegetableModel> adapterList;
    public OrderedListAdapter orderedListAdapter;
    public TextView txtprice,txtmarketSave,txtmallSave,txtcount,noData,orderStatus;
    public double totPrice,marketSave,mallSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_view_lastorder, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(Config.actionBarTitle("Last Order", getActivity()));
        ((MainActivity) getActivity()).getSupportActionBar().show();

        initiateView(rootView);
        loadData();
        return rootView;
    }

    public void initiateView(View rootView){

        vegies = (ListView) rootView.findViewById(R.id.listVeg);
        txtprice = (TextView) rootView.findViewById(R.id.txtTotalPrice);
        txtmarketSave = (TextView) rootView.findViewById(R.id.txtmarketSave);
        txtmallSave = (TextView) rootView.findViewById(R.id.txtmallsave);
        txtcount = (TextView) rootView.findViewById(R.id.txtvegCount);
        noData = (TextView) rootView.findViewById(R.id.txtnoData);
        orderStatus = (TextView) rootView.findViewById(R.id.txtorderStatus);
        adapterList = new ArrayList<>();
        totPrice = mallSave = marketSave = 0;
    }

    public void loadData(){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String usertoken = sharedpreferences.getString("UserToken","");
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(usertoken);
        EnqueueWrapper.with(ViewLastOrderFragment.this).enqueue(ApiService.getService().orders(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Order order = gson.fromJson(responseString,Order.class);
                        if(order != null){
                            if(order.getItems().size()>0){
                                noData.setVisibility(View.GONE);
                                vegies.setVisibility(View.VISIBLE);
                                totPrice = order.getTotalCost();
                                mallSave = order.getMallCostSaved();
                                marketSave = order.getMarketCostSaved();
                                orderedListAdapter = new OrderedListAdapter(order.getItems(),getActivity());
                                vegies.setAdapter(orderedListAdapter);
                                txtprice.setText("Total Price: ₹"+totPrice);
                                txtmallSave.setText("₹ "+mallSave);
                                txtmarketSave.setText("₹ "+marketSave);
                                orderStatus.setText("Order Status: "+order.getOrderStatus());
                                if(order.getItems().size()>1)
                                txtcount.setText(order.getItems().size()+" Items purchased");
                                else
                                    txtcount.setText(order.getItems().size()+" Item purchased");
                            }
                            else {
                                noData.setVisibility(View.VISIBLE);
                                vegies.setVisibility(View.GONE);
                            }
                        }
                        else {
                            noData.setVisibility(View.VISIBLE);
                            vegies.setVisibility(View.GONE);
                        }
                    }
                }


            }
        });



    }
}
