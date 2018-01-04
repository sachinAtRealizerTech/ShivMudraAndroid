package com.realizertech.shivmudra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.realizertech.shivmudra.adapter.OrderedListAdapter;
import com.realizertech.shivmudra.adapter.VegetableListAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.fragments.ViewLastOrderFragment;
import com.realizertech.shivmudra.model.Order;
import com.realizertech.shivmudra.model.OrderItems;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.model.VegetableModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModifyLastOrderActivity extends BaseActivity{


    public ListView vegies;
    public List<VegetableModel> adapterList;
    public VegetableListAdapter vegetableListAdapter;
    public FloatingActionButton addNew;
    public LinearLayout layoutToggel;
    public TextView txtprice,txthomedelivey;
    public double totPrice,marketSave,mallSave,homeDCharge,homeDThreashhold,firstorderdisper,firstorderval,referdisper,referdisval;
    public int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vegies_list_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Config.actionBarTitle("Vegetable", this));

        initiateView();
        loadData();
    }

    public void initiateView(){
        layoutToggel = (LinearLayout) findViewById(R.id.toggleLayout);
        layoutToggel.setVisibility(View.GONE);
        addNew = (FloatingActionButton) findViewById(R.id.btnAddNew);
        addNew.setVisibility(View.VISIBLE);
        vegies = (ListView) findViewById(R.id.listVeg);
        txtprice = (TextView) findViewById(R.id.txtTotalPrice);
        adapterList = new ArrayList<>();
        totPrice = mallSave = marketSave = homeDCharge = homeDThreashhold =0;
        txthomedelivey = (TextView) findViewById(R.id.txthd);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyLastOrderActivity.this,VegitableListActivity.class);
                intent.putExtra("SelectedList",(Serializable)adapterList);
                intent.putExtra("Total",totPrice);
                intent.putExtra("MallSave",mallSave);
                intent.putExtra("MarketSave",marketSave);
                intent.putExtra("FirstOrderDisPer",firstorderdisper);
                intent.putExtra("FirstOrderDisVal",firstorderval);
                intent.putExtra("ReferDisPer",referdisper);
                intent.putExtra("ReferDisVal",referdisval);
                intent.putExtra("OrderId",orderId);
                intent.putExtra("ActivityName","Modify");
                startActivity(intent);
            }
        });
    }

    public void loadData(){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(ModifyLastOrderActivity.this);
        String usertoken = sharedpreferences.getString("UserToken","");
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(usertoken);
        EnqueueWrapper.with(ModifyLastOrderActivity.this).enqueue(ApiService.getService().orders(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Order order = gson.fromJson(responseString,Order.class);
                        if(order != null){
                            List<OrderItems> items = order.getItems();
                            if(items.size()>0){
                                for (int i=0;i<order.getItems().size();i++){
                                    VegetableModel vegetableModel = new VegetableModel();
                                    vegetableModel.setId(items.get(i).getItemId());
                                    vegetableModel.setName(items.get(i).getItemName());
                                    vegetableModel.setItemType(items.get(i).getItemType());
                                    vegetableModel.setItemDesc(items.get(i).getItemDesc());
                                    vegetableModel.setItemKey(items.get(i).getItemKey());
                                    vegetableModel.setImage("");
                                    vegetableModel.setMeasurementType(items.get(i).getMeasurementType());
                                    vegetableModel.setItemDesc(items.get(i).getItemDesc());
                                    vegetableModel.setOurPrice(items.get(i).getOurPrice());
                                    vegetableModel.setMallPrice(items.get(i).getMallPrice());
                                    vegetableModel.setMarketPrice(items.get(i).getMarketPrice());
                                    vegetableModel.setTotalMallSave((items.get(i).getMallPrice() * items.get(i).getQuantity()) - items.get(i).getCostPerItem());
                                    vegetableModel.setTotalMarketSave((items.get(i).getMarketPrice() * items.get(i).getQuantity()) - items.get(i).getCostPerItem());
                                    vegetableModel.setQuantity(items.get(i).getQuantity());
                                    vegetableModel.setTotalPrice(items.get(i).getCostPerItem());
                                    vegetableModel.setItemDefaultQty(items.get(i).getItemDefaultQty());
                                    vegetableModel.setIncrementBy(items.get(i).getIncrementBy());
                                    adapterList.add(vegetableModel);
                                }
                                if(adapterList.size()>0){
                                    vegetableListAdapter = new VegetableListAdapter(adapterList,ModifyLastOrderActivity.this);
                                    vegies.setAdapter(vegetableListAdapter);
                                    vegies.setVisibility(View.VISIBLE);
                                }
                                orderId = order.getOrderId();
                                totPrice = order.getTotalCost();
                                mallSave = order.getMallCostSaved();
                                marketSave = order.getMarketCostSaved();
                                referdisper = order.getReferralDiscountPercentage();
                                referdisval = order.getReferralCostSaved();
                                firstorderdisper = order.getFirstOrderDiscountPercentage();
                                firstorderval = order.getFirstOrderDiscount();
                                txtprice.setText("Total Price: ₹"+totPrice);

                            }
                            else {
                                vegies.setVisibility(View.GONE);
                            }
                        }
                        else {
                            vegies.setVisibility(View.GONE);
                        }
                    }
                }


            }
        });



    }


    public void orderItem(VegetableModel veg,String type,double price,double mallsaving, double marketsaving){
        if(type.equalsIgnoreCase("add")){
            totPrice = totPrice + veg.getTotalPrice();
            mallSave = mallSave + veg.getTotalMallSave();
            marketSave = marketSave + veg.getTotalMarketSave();
        }
        else if(type.equalsIgnoreCase("remove")){

                for (int i = 0; i < adapterList.size(); i++) {
                    if (veg.getId() == adapterList.get(i).getId()) {
                        adapterList.set(i,veg);
                        break;
                    }
                }

            totPrice = totPrice - price;
            mallSave = mallSave - mallsaving;
            marketSave = marketSave - marketsaving;
        }
        else {

                for (int i = 0; i < adapterList.size(); i++) {
                    if (veg.getId() == adapterList.get(i).getId()) {
                        totPrice = totPrice - price;
                        mallSave = mallSave - mallsaving;
                        marketSave = marketSave - marketsaving;
                        mallSave = mallSave +veg.getTotalMallSave();
                        marketSave = marketSave + veg.getTotalMarketSave();
                        totPrice = totPrice + veg.getTotalPrice();
                        adapterList.set(i,veg);
                        break;
                    }
                }

        }
        txtprice.setText("Total Price: ₹"+totPrice);
        if(totPrice < homeDThreashhold && totPrice > 100){
            txthomedelivey.setVisibility(View.VISIBLE);
            txthomedelivey.setText("₹"+(homeDThreashhold - totPrice)+" away from free delivery");
        }
        else {
            txthomedelivey.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            case R.id.action_done:
                // app icon in action bar clicked; go home
                sendListConfirm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendListConfirm(){
        Intent intent = new Intent(ModifyLastOrderActivity.this,ConfirmOrderListActivity.class);
        intent.putExtra("SelectedList", (Serializable) adapterList);
        intent.putExtra("TotalPrice",totPrice);
        intent.putExtra("TotalMallSave",mallSave);
        intent.putExtra("TotalMarketSave",marketSave);
        intent.putExtra("HdCharg",homeDCharge);
        intent.putExtra("OrderId",orderId);
        intent.putExtra("FirstOrderDisPer",firstorderdisper);
        intent.putExtra("FirstOrderDisVal",firstorderval);
        intent.putExtra("ReferDisPer",referdisper);
        intent.putExtra("ReferDisVal",referdisval);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
