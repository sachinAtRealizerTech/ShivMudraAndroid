package com.realizertech.shivmudra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.adapter.VegetableListAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.model.Item;
import com.realizertech.shivmudra.model.ItemListInput;
import com.realizertech.shivmudra.model.ItemsList;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.VegetableModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.realizertech.shivmudra.R.styleable.ProgressWheel;

public class VegitableListActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{


    public ToggleButton fruits, vegetable, spilled;
    public ListView vegies;
    public List<VegetableModel> vegetablelist;
    public List<VegetableModel> fruitList;
    public List<VegetableModel> spilledList;
    public List<VegetableModel> selecedVeg;
    public List<VegetableModel> selecedfruit;
    public List<VegetableModel> selecedspilled;
    public List<VegetableModel> adapterList;
    public VegetableListAdapter vegetableListAdapter;
    public double totPrice,mallSave,marketSave,homeDCharge,homeDThreashhold;
    public TextView txtprice,txthomedelivey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vegies_list_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Config.actionBarTitle("Vegetable", this));
        initiateView();
        if (Config.isConnectingToInternet(VegitableListActivity.this))
        {
            loadData(1);
        }
        else
        {
            Config.alertDialog(VegitableListActivity.this,"Network Error","Please check your internet connection");
        }

    }

    public void initiateView(){
        vegies = (ListView) findViewById(R.id.listVeg);
        vegetable = (ToggleButton) findViewById(R.id.veg);
        fruits = (ToggleButton) findViewById(R.id.fruit);
        spilled = (ToggleButton) findViewById(R.id.spilled);
        txtprice = (TextView) findViewById(R.id.txtTotalPrice);
        txthomedelivey = (TextView) findViewById(R.id.txthd);

        vegetable.setOnCheckedChangeListener(this);
        fruits.setOnCheckedChangeListener(this);
        spilled.setOnCheckedChangeListener(this);

        vegetablelist = new ArrayList<>();
        fruitList = new ArrayList<>();
        spilledList = new ArrayList<>();
        selecedspilled = new ArrayList<>();
        selecedVeg = new ArrayList<>();
        selecedfruit = new ArrayList<>();
        adapterList = new ArrayList<>();
        totPrice = mallSave = marketSave = 0;
    }

    public void loadData(int type){
        if(getIntent().getStringExtra("ActivityName") != null){
            if(getIntent().getStringExtra("ActivityName").equalsIgnoreCase("Modify")){
                List<VegetableModel> tempList = new ArrayList<>();
                tempList = (List<VegetableModel>)getIntent().getExtras().getSerializable("SelectedList");
                marketSave = getIntent().getDoubleExtra("MarketSave",0);
                totPrice = getIntent().getDoubleExtra("Total",0);
                mallSave = getIntent().getDoubleExtra("MallSave",0);
                txtprice.setText("Total Price: ₹"+totPrice);

               if(tempList != null){
                   if(tempList.size()>0){
                       for (int i=0;i<tempList.size();i++){
                           if(tempList.get(i).getQuantity() > 0){
                               if(tempList.get(i).getItemType() == 1){
                                   selecedVeg.add(tempList.get(i));
                               }
                               else if(tempList.get(i).getItemType() == 2){
                                   selecedfruit.add(tempList.get(i));
                               }
                               else if(tempList.get(i).getItemType() == 3){
                                   selecedspilled.add(tempList.get(i));
                               }
                           }
                       }
                   }
               }
            }
        }



        if(type == 1){
            if(vegetablelist.size()==0){
                //Invoke API
                loadListAPI(1,selecedVeg);
            }
            else {
                if(selecedVeg.size()>0){
                    for (int i=0;i<selecedVeg.size();i++){
                        if(vegetablelist.contains(selecedVeg.get(i))){
                            int index = vegetablelist.indexOf(selecedVeg.get(i));
                            vegetablelist.set(index,selecedVeg.get(i));
                        }
                    }
                }
                vegetableListAdapter = new VegetableListAdapter(vegetablelist,VegitableListActivity.this);
                vegies.setAdapter(vegetableListAdapter);
                vegies.setVisibility(View.VISIBLE);
            }
        }
        else if(type == 2) {
            if(fruitList.size()==0){
                loadListAPI(2,selecedfruit);
            }
            else {
                if (selecedfruit.size()>0){
                    for (int i=0;i<selecedfruit.size();i++){
                        if(fruitList.contains(selecedfruit.get(i))){
                            int index = fruitList.indexOf(selecedfruit.get(i));
                            fruitList.set(index,selecedfruit.get(i));
                        }
                    }
                }

                vegetableListAdapter = new VegetableListAdapter(fruitList,VegitableListActivity.this);
                vegies.setAdapter(vegetableListAdapter);
                vegies.setVisibility(View.VISIBLE);

            }

        }

        else  {
            if(spilledList.size()==0){
                loadListAPI(3,selecedspilled);
            }
            else {
                if (selecedspilled.size()>0){
                    for (int i=0;i<selecedspilled.size();i++){
                        if(spilledList.contains(selecedspilled.get(i))){
                            int index = fruitList.indexOf(selecedspilled.get(i));
                            spilledList.set(index,selecedspilled.get(i));
                        }
                    }
                }

                vegetableListAdapter = new VegetableListAdapter(spilledList,VegitableListActivity.this);
                vegies.setAdapter(vegetableListAdapter);
                vegies.setVisibility(View.VISIBLE);
            }

        }

    }

    public void loadListAPI(final int type, final List<VegetableModel> selecedList){

        String token = PreferenceManager.getDefaultSharedPreferences(VegitableListActivity.this).getString("UserToken","");
        int societytype = PreferenceManager.getDefaultSharedPreferences(VegitableListActivity.this).getInt("LocationType",0);
        final ItemListInput itemListInput = new ItemListInput();
        itemListInput.setUserToken(token);
        itemListInput.setItemType(type);
        itemListInput.setSocietyType(societytype);

        EnqueueWrapper.with(VegitableListActivity.this).enqueue(ApiService.getService().getItemsList(itemListInput), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        ItemsList itemsList = gson.fromJson(responseString, ItemsList.class);
                        homeDCharge = itemsList.getDeliveryCharges();
                        homeDThreashhold = itemsList.getFreeDeliveryThreshold();
                        //txthomedelivey.setText("₹"+itemsList.getDeliveryCharges()+" Delivery charge.\nMin order ₹"+itemsList.getFreeDeliveryThreshold()+" for free delivery.");
                        if(itemsList != null){
                            List<Item> items = itemsList.getItems();
                           if(items.size()>0){
                               vegies.setVisibility(View.VISIBLE);
                               for (int i=0;i<items.size();i++){
                                   VegetableModel vegetableModel = new VegetableModel();
                                   vegetableModel.setId(items.get(i).getItemId());
                                   vegetableModel.setName(items.get(i).getItemName());
                                   vegetableModel.setItemType(items.get(i).getItemType());
                                   vegetableModel.setItemDesc(items.get(i).getItemDesc());
                                   vegetableModel.setItemKey(items.get(i).getItemKey());
                                   vegetableModel.setImage(items.get(i).getImageUrl());
                                   vegetableModel.setMeasurementType(items.get(i).getMeasurmentType());
                                   vegetableModel.setItemDesc(items.get(i).getItemDesc());
                                   vegetableModel.setOurPrice(items.get(i).getOurPrice());
                                   vegetableModel.setMallPrice(items.get(i).getMallPrice());
                                   vegetableModel.setMarketPrice(items.get(i).getMarketPrice());
                                   vegetableModel.setTotalMallSave(0);
                                   vegetableModel.setTotalMarketSave(0);
                                   vegetableModel.setQuantity(0);
                                   vegetableModel.setTotalPrice(0);
                                   vegetableModel.setItemDefaultQty(items.get(i).getItemDefaultQty());
                                   vegetableModel.setIncrementBy(items.get(i).getIncrementBy());
                                   if(type == 1){
                                       vegetablelist.add(vegetableModel);
                                   }
                                   else if(type == 2){
                                       fruitList.add(vegetableModel);
                                   }
                                   else {
                                       spilledList.add(vegetableModel);
                                   }
                               }
                               if(selecedList.size()>0){
                                   for(int i=0;i<selecedList.size();i++){
                                       if(type == 1){
                                           if(vegetablelist.contains(selecedList.get(i))){
                                               int index = vegetablelist.indexOf(selecedList.get(i));
                                               vegetablelist.set(index,selecedList.get(i));
                                           }
                                       }
                                       else if(type == 2){
                                           if(fruitList.contains(selecedList.get(i))){
                                               int index = fruitList.indexOf(selecedList.get(i));
                                               fruitList.set(index,selecedList.get(i));
                                           }
                                       }
                                       else{
                                           if(spilledList.contains(selecedList.get(i))){
                                               int index = spilledList.indexOf(selecedList.get(i));
                                               spilledList.set(index,selecedList.get(i));
                                           }
                                       }

                                   }
                               }

                               if(type == 1){
                                       vegetableListAdapter = new VegetableListAdapter(vegetablelist,VegitableListActivity.this);
                                       vegies.setAdapter(vegetableListAdapter);
                                       vegies.setVisibility(View.VISIBLE);

                               }
                               else if(type == 2){
                                       vegetableListAdapter = new VegetableListAdapter(fruitList,VegitableListActivity.this);
                                       vegies.setAdapter(vegetableListAdapter);
                                       vegies.setVisibility(View.VISIBLE);

                               }
                               else {

                                       vegetableListAdapter = new VegetableListAdapter(spilledList,VegitableListActivity.this);
                                       vegies.setAdapter(vegetableListAdapter);
                                       vegies.setVisibility(View.VISIBLE);

                               }


                           }
                            else {
                               vegies.setVisibility(View.GONE);
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

    public void orderItem(VegetableModel veg,String type,double price,double mallsaving, double marketsaving){
        if(type.equalsIgnoreCase("add")){
            if(vegetable.isChecked()){
                selecedVeg.add(veg);
            }
            else {
                selecedfruit.add(veg);
            }
            totPrice = totPrice + veg.getTotalPrice();
            mallSave = mallSave + veg.getTotalMallSave();
            marketSave = marketSave + veg.getTotalMarketSave();

        }
        else if(type.equalsIgnoreCase("remove")){
            if(vegetable.isChecked()) {
                for (int i = 0; i < selecedVeg.size(); i++) {
                    if (veg.getId() == selecedVeg.get(i).getId()) {
                        selecedVeg.remove(i);
                        break;
                    }
                }
            }
            else {
                for (int i = 0; i < selecedfruit.size(); i++) {
                    if (veg.getId() == selecedfruit.get(i).getId()) {
                        selecedfruit.remove(i);
                        break;
                    }
                }
            }
            totPrice = totPrice - price;
            mallSave = mallSave - mallsaving;
            marketSave = marketSave - marketsaving;
        }
        else {
            if(vegetable.isChecked()) {
                for (int i = 0; i < selecedVeg.size(); i++) {
                    if (veg.getId() == selecedVeg.get(i).getId()) {
                        totPrice = totPrice - price;
                        mallSave = mallSave - mallsaving;
                        marketSave = marketSave - marketsaving;
                        mallSave = mallSave +veg.getTotalMallSave();
                        marketSave = marketSave + veg.getTotalMarketSave();
                        totPrice = totPrice + veg.getTotalPrice();
                        selecedVeg.set(i,veg);
                        break;
                    }
                }
            }
            else {
                for (int i = 0; i < selecedfruit.size(); i++) {
                    if (veg.getId() == selecedfruit.get(i).getId()) {
                        totPrice = totPrice - price;
                        mallSave = mallSave - mallsaving;
                        marketSave = marketSave - marketsaving;
                        totPrice = totPrice + veg.getTotalPrice();
                        mallSave = mallSave +veg.getTotalMallSave();
                        marketSave = marketSave + veg.getTotalMarketSave();
                        selecedfruit.set(i,veg);
                        break;
                    }
                }
            }
        }

        if(totPrice < homeDThreashhold && totPrice > 100){
            txthomedelivey.setVisibility(View.VISIBLE);
            txthomedelivey.setText("₹"+(homeDThreashhold - totPrice)+" away from free delivery");
        }
        else {
            txthomedelivey.setVisibility(View.GONE);
        }

        txtprice.setText("Total Price: ₹"+totPrice);
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
        if(selecedVeg.size()>0 || selecedfruit.size()>0 || selecedspilled.size()>0){
            List<VegetableModel> allList = new ArrayList<>();
            if(totPrice >= homeDThreashhold){
                homeDCharge =0;
            }
            allList.addAll(selecedfruit);
            allList.addAll(selecedVeg);
            Intent intent = new Intent(VegitableListActivity.this,ConfirmOrderListActivity.class);
            intent.putExtra("TotalPrice",totPrice);
            intent.putExtra("TotalMallSave",mallSave);
            intent.putExtra("TotalMarketSave",marketSave);
            intent.putExtra("HdCharg",homeDCharge);
            intent.putExtra("OrderId",getIntent().getIntExtra("OrderId",0));
            intent.putExtra("FirstOrderDisPer",getIntent().getDoubleExtra("FirstOrderDisPer",0));
            intent.putExtra("FirstOrderDisVal",getIntent().getDoubleExtra("FirstOrderDisVal",0));
            intent.putExtra("ReferDisPer",getIntent().getDoubleExtra("ReferDisPer",0));
            intent.putExtra("ReferDisVal",getIntent().getDoubleExtra("ReferDisVal",0));
            intent.putExtra("SelectedList", (Serializable) allList);
            startActivity(intent);
        }
        else {
            Config.alertDialog(VegitableListActivity.this,"Error","Please select at least one Item");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.veg:
                if (isChecked) {
                    fruits.setChecked(false);
                    spilled.setChecked(false);
                    vegetable.setBackgroundResource(R.drawable.buttongradient);
                    fruits.setBackgroundColor(Color.TRANSPARENT);
                    spilled.setBackgroundColor(Color.TRANSPARENT);
                    invalidateOptionsMenu();
                    if (Config.isConnectingToInternet(VegitableListActivity.this))
                    {
                        loadData(1);
                    }
                    else
                    {
                        Config.alertDialog(VegitableListActivity.this,"Network Error","Please check your internet connection");
                    }

                }
                break;
            case R.id.fruit:
                if (isChecked) {
                    vegetable.setChecked(false);
                    spilled.setChecked(false);
                    fruits.setBackgroundResource(R.drawable.buttongradient);
                    vegetable.setBackgroundColor(Color.TRANSPARENT);
                    spilled.setBackgroundColor(Color.TRANSPARENT);
                    invalidateOptionsMenu();
                    if (Config.isConnectingToInternet(VegitableListActivity.this))
                    {
                        loadData(2);
                    }
                    else
                    {
                        Config.alertDialog(VegitableListActivity.this,"Network Error","Please check your internet connection");
                    }
                }
                break;
            case R.id.spilled:
                if (isChecked) {
                    vegetable.setChecked(false);
                    fruits.setChecked(false);
                    spilled.setBackgroundResource(R.drawable.buttongradient);
                    fruits.setBackgroundColor(Color.TRANSPARENT);
                    vegetable.setBackgroundColor(Color.TRANSPARENT);
                    invalidateOptionsMenu();
                    if (Config.isConnectingToInternet(VegitableListActivity.this))
                    {
                        loadData(3);
                    }
                    else
                    {
                        Config.alertDialog(VegitableListActivity.this,"Network Error","Please check your internet connection");
                    }

                }
                break;

        }
    }
}
