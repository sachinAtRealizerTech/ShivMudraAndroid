package com.realizertech.shivmudra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.adapter.ConfirmOrderListAdapter;
import com.realizertech.shivmudra.adapter.SelectOrderDateGridAdapter;
import com.realizertech.shivmudra.adapter.SelectOrderDiscountAdapter;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.adapter.VegetableListAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.model.Discount;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.LocationDeliverySlabs;
import com.realizertech.shivmudra.model.OrderDateModel;
import com.realizertech.shivmudra.model.OrderInput;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.model.VegetableModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConfirmOrderListActivity extends BaseActivity {


    public ListView vegies;
    public List<VegetableModel> vegetablelist;
    public ConfirmOrderListAdapter confirmOrderListAdapter;
    public TextView dateTime,quantity,totalPrice,confirmOrder,marketSave,mallSave,noDiscount,title,hdCharge;
    public ListView slotgrid,dicountList;
    public SelectOrderDateGridAdapter selectOrderDateGridAdapter;
    public SelectOrderDiscountAdapter selectOrderDiscountAdapter;
    public List<LocationDeliverySlabs> dateSlots;
    public List<Discount> discounts;
    public String selectedDate="",selectedSlot,select="";
    public double dispercentage=0,disvalue=0,disreferpercentage=0,disrefervalue=0,total,totaldisCost=0;
    public int orderId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Config.actionBarTitle("Cart", this));

        initiateView();
        loadData();

    }

    public void initiateView(){
        vegies = (ListView) findViewById(R.id.listselectedveg);
        dateTime = (TextView) findViewById(R.id.txtdatetime);
        quantity = (TextView) findViewById(R.id.txtvegCount);
        totalPrice = (TextView) findViewById(R.id.txtTotalPrice);
        noDiscount = (TextView) findViewById(R.id.txtnoDiscount);
        title = (TextView) findViewById(R.id.txtdistitle);
        confirmOrder = (TextView) findViewById(R.id.txt_order);
        slotgrid = (ListView) findViewById(R.id.grid_slot);
        dicountList = (ListView) findViewById(R.id.listdiscount);
        marketSave = (TextView) findViewById(R.id.txtmarketSave);
        mallSave = (TextView) findViewById(R.id.txtmallsave);
        hdCharge = (TextView) findViewById(R.id.txthdcharges);
        if(getIntent().getDoubleExtra("HdCharg",0) != 0){
            hdCharge.setVisibility(View.VISIBLE);
            hdCharge.setText("Home Delivery Charges: ₹"+getIntent().getDoubleExtra("HdCharg",0));
        }
        if(getIntent().getIntExtra("OrderId",0) != 0){
            orderId = getIntent().getIntExtra("OrderId",0);
            confirmOrder.setText("Update");
        }

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!select.isEmpty()){
                   if(Config.isConnectingToInternet(ConfirmOrderListActivity.this)){
                       String csv = "";
                       for (int i=0;i<vegetablelist.size();i++){
                           double quantity = vegetablelist.get(i).getQuantity();
                           if (quantity>99){
                               quantity = (quantity/100);
                           }

                           String item = (i+1)+"-"+vegetablelist.get(i).getId()+"-"+vegetablelist.get(i).getOurPrice()+"-"+quantity+"-"+vegetablelist.get(i).getTotalPrice();
                           if(i==0){
                               csv = item;
                           }
                           else {
                               csv = csv+","+item;
                           }

                       }

                       String token = PreferenceManager.getDefaultSharedPreferences(ConfirmOrderListActivity.this).getString("UserToken","");
                       OrderInput orderInput = new OrderInput();
                       orderInput.setUserToken(token);
                       orderInput.setOrderId(orderId);
                       orderInput.setDeliverySlot(selectedSlot);
                       orderInput.setMallCostSaved(Double.valueOf(mallSave.getText().toString().split("₹")[1]));
                       orderInput.setMarketCostSaved(Double.valueOf(marketSave.getText().toString().split("₹")[1]));
                       orderInput.setOrderDate(Config.getDate());
                       if(totaldisCost != 0){
                           orderInput.setTotatlCost(totaldisCost);
                       }
                       else {
                           orderInput.setTotatlCost(total);
                       }

                       orderInput.setDeliveryDate(select);
                       orderInput.setFirstOrderDiscount(disvalue);
                       orderInput.setFirstOrderDiscountPercentage((int) dispercentage);
                       orderInput.setReferralCostSaved(disrefervalue);
                       orderInput.setReferralCountApplied(0);
                       orderInput.setReferralDiscountPercentage((int)disreferpercentage);
                       orderInput.setItemCSV(csv);
                       orderInput.setOrderId(getIntent().getIntExtra("OrderId",0));
                       orderInput.setDeliveryCost(getIntent().getIntExtra("HdCharg",0));
                       placeOrder(orderInput);
                   }
                   else {
                       Config.alertDialog(ConfirmOrderListActivity.this,"Network Error","Please check yout internet connection");
                   }
               }
                else {
                   Config.alertDialog(ConfirmOrderListActivity.this,"Error","Please select delivery date");
               }


            }
        });
        slotgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationDeliverySlabs orderDateModel = (LocationDeliverySlabs) parent.getItemAtPosition(position);
                selectedDate = orderDateModel.getWeekDay();
                selectedSlot = orderDateModel.getSlabTiming();
                select = orderDateModel.getSlabDate();
            }
        });

        dicountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Discount discount = (Discount) parent.getItemAtPosition(position);
                if(discount.getDiscountType().equalsIgnoreCase("FIRST-ORDER")){
                    dispercentage = discount.getDiscountPercentage();
                    disvalue = discount.getDiscountValue();
                    disreferpercentage =0;
                    disrefervalue = 0;
                    totaldisCost = total - disvalue;
                    //total = total - disvalue;
                    totalPrice.setText("₹"+total+" - "+"₹"+disvalue+" = ₹"+totaldisCost);
                }
                else {
                    dispercentage = 0;
                    disvalue = 0;
                    disreferpercentage =discount.getDiscountPercentage();
                    disrefervalue = discount.getDiscountValue();
                    totaldisCost = total - disrefervalue;
                    //total = total - disrefervalue;
                    totalPrice.setText("₹"+total+" - "+"₹"+disrefervalue+" = ₹"+totaldisCost);
                }
            }
        });
    }

    public void loadData(){
        vegetablelist = (List<VegetableModel>) getIntent().getSerializableExtra("SelectedList");
        confirmOrderListAdapter = new ConfirmOrderListAdapter(vegetablelist,ConfirmOrderListActivity.this);
        vegies.setAdapter(confirmOrderListAdapter);
        total = getIntent().getDoubleExtra("TotalPrice",0);
        quantity.setText(vegetablelist.size()+" Item in cart");
        totalPrice.setText("₹"+total);
        mallSave.setText("₹"+getIntent().getDoubleExtra("TotalMallSave",0));
        marketSave.setText("₹"+getIntent().getDoubleExtra("TotalMarketSave",0));
        disvalue = getIntent().getDoubleExtra("FirstOrderDisVal",0);
        dispercentage = getIntent().getDoubleExtra("FirstOrderDisPer",0);
        disreferpercentage = getIntent().getDoubleExtra("ReferDisPer",0);
        disrefervalue = getIntent().getDoubleExtra("ReferDisVal",0);
        if(disvalue != 0){
            totaldisCost = total - disvalue;
            totalPrice.setText("₹"+total+" - "+"₹"+disvalue+" = ₹"+totaldisCost);
        }
        else if(disrefervalue != 0){
            totaldisCost = total - disrefervalue;
            totalPrice.setText("₹"+total+" - "+"₹"+disrefervalue+" = ₹"+totaldisCost);
        }
        dateSlots = new ArrayList<>();
        if(Config.isConnectingToInternet(ConfirmOrderListActivity.this)){
            fillDateSlab();
            loadDiscounts();

        }
        else {
            Config.alertDialog(ConfirmOrderListActivity.this,"Network Error","Please check yout internet connection");
        }

    }

    public void fillDateSlab(){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(ConfirmOrderListActivity.this);
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(sharedpreferences.getString("UserToken",""));
        EnqueueWrapper.with(this).enqueue(ApiService.getService().getLocationDeliverySlabs(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<LocationDeliverySlabs>>() { }.getType();
                        dateSlots  = gson.fromJson(responseString,type);
                        if(dateSlots != null){
                            for(int i=0;i< dateSlots.size();i++){
                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.DATE, 1);
                                int selectedday = Config.getDayOfWeek(dateSlots.get(i).getWeekDay().toLowerCase());
                                int todayDay = c.get(Calendar.DAY_OF_WEEK);
                                while (todayDay != selectedday) {
                                    c.add(Calendar.DATE, 1);
                                    todayDay = c.get(Calendar.DAY_OF_WEEK);
                                }
                                dateSlots.get(i).setSlabDate(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH) + 1)+"-"+c.get(Calendar.DATE));
                                dateSlots.get(i).setShowDate((c.get(Calendar.MONTH) + 1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR));
                            }
                            selectOrderDateGridAdapter = new SelectOrderDateGridAdapter(dateSlots,ConfirmOrderListActivity.this);
                            slotgrid.setAdapter(selectOrderDateGridAdapter);
                            slotgrid.setSelection(0);
                        }
                        else {
                            Log.e("branch List", "null");
                        }
                    }
                }

            }
        });
    }

    public void loadDiscounts(){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(ConfirmOrderListActivity.this);
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(sharedpreferences.getString("UserToken",""));
        userInputs.setContactNo(sharedpreferences.getString("ContactNo",""));
        EnqueueWrapper.with(this).enqueue(ApiService.getService().getAvailableDiscounts(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Discount>>() { }.getType();
                        List<Discount>discnt  = gson.fromJson(responseString,type);
                        discounts = new ArrayList<Discount>();

                        if(discnt != null){
                            int counter =0;
                            for(int i=0;i< discnt.size();i++){
                                if(discnt.get(i).isApplicable()){
                                    double val = (discnt.get(i).getDiscountPercentage()/100) * total;
                                    discnt.get(i).setDiscountValue(val);
                                    counter = counter + 1;
                                    discounts.add(discnt.get(i));
                                }
                            }
                            if(counter > 0){
                                dicountList.setVisibility(View.VISIBLE);
                                title.setVisibility(View.VISIBLE);
                                selectOrderDiscountAdapter = new SelectOrderDiscountAdapter(discounts,ConfirmOrderListActivity.this);
                                dicountList.setAdapter(selectOrderDiscountAdapter);
                                dicountList.setSelection(0);
                            }
                            else {
                                noDiscount.setVisibility(View.VISIBLE);
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


    public void placeOrder(OrderInput order){
        EnqueueWrapper.with(this).enqueue(ApiService.getService().placeOrder(order), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                  successDialog(ConfirmOrderListActivity.this);
                }
            }
        });
    }

    public  void successDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialoglayout = inflater.inflate(R.layout.alert_custom, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);

        Button buttonok= (Button) dialoglayout.findViewById(R.id.alert_btn_ok);
        TextView titleName=(TextView) dialoglayout.findViewById(R.id.alert_dialog_title);
        TextView alertMsg=(TextView) dialoglayout.findViewById(R.id.alert_dialog_message);


        final AlertDialog alertDialog = builder.create();

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderListActivity.this,MainActivity.class);
                intent.putExtra("From","ConfirmOrder");
                startActivity(intent);
                alertDialog.dismiss();
                finish();
            }
        });

        titleName.setText("Order Placed");
        alertMsg.setText("Your order is placed Successfully");

        alertDialog.show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }





}
