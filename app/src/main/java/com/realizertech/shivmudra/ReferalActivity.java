package com.realizertech.shivmudra;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.MasterData;
import com.realizertech.shivmudra.model.MasterDataInputs;
import com.realizertech.shivmudra.model.ReferFriendInput;
import com.realizertech.shivmudra.model.SocietyModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReferalActivity extends BaseActivity {

    Button refer;
    AutoCompleteTextView societyName;
    EditText mobileNo, flatNo, name;
    TextView title;
    SocietyNameAdapter societyNameAdapter;
    public ArrayList<Location> locations;
    public int societyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refferfriend_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Config.actionBarTitle("Refer Friend", this));
        initiateView();

    }



    public void initiateView(){

        societyName = (AutoCompleteTextView) findViewById(R.id.societyname);
        refer = (Button) findViewById(R.id.btnRefer);
        mobileNo = (EditText) findViewById(R.id.edtmobileno);
        name = (EditText) findViewById(R.id.edtname);
        title = (TextView) findViewById(R.id.txttitle);

        refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.isConnectingToInternet(ReferalActivity.this))
                {
                    if(societyName.getText().toString().trim().isEmpty()){
                        Config.alertDialog(ReferalActivity.this,"Error","Please Enter Society Name");
                    }
                    else if(mobileNo.getText().toString().trim().isEmpty()){
                        Config.alertDialog(ReferalActivity.this,"Error","Please Enter Mobile Number");
                    }
                    else if(mobileNo.getText().toString().trim().length() < 10){
                        Config.alertDialog(ReferalActivity.this,"Error","Please Enter Valid Mobile Number");
                    }
                    else if(name.getText().toString().trim().isEmpty()){
                        Config.alertDialog(ReferalActivity.this,"Error","Please Enter Your friend's Name");
                    }

                    else {
                        String token = PreferenceManager.getDefaultSharedPreferences(ReferalActivity.this).getString("UserToken","");
                        ReferFriendInput referFriendInput = new ReferFriendInput();
                        referFriendInput.setUserToken(token);
                        referFriendInput.setReferralContactNo(mobileNo.getText().toString().trim());
                        referFriendInput.setReferralName(name.getText().toString().trim());
                        referFriendInput.setReferralToken("");
                        referFriendInput.setSocietyID(societyId);
                        referFriendInput.setSocietyName(societyName.getText().toString().trim());
                        refferFriend(referFriendInput);
                    }
                }
                else
                {
                    Config.alertDialog(ReferalActivity.this,"Network Error","Please check your internet connection");
                }
            }
        });



        societyName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location societyModel = (Location) parent.getItemAtPosition(position);
                societyName.setText(societyModel.getName());
                societyId = societyModel.getLocationId();
            }
        });
        if (Config.isConnectingToInternet(ReferalActivity.this))
        {
            loadSocietyNames();
            MasterDataInputs masterDataInputs = new MasterDataInputs();
            masterDataInputs.setMTCategories("ConfirmScreenBottom");
            masterDataInputs.setMTKeys("");
            List<MasterData> masterData = Config.getMasterText(ReferalActivity.this,masterDataInputs);
            if(masterData != null){
                if(masterData.size() > 0){
                    String ref = "";
                    for (int i=0;i<masterData.size();i++){
                        if(i==0){
                            ref = masterData.get(i).getMTText();
                        }
                        else {
                            ref = ref +"\n"+ masterData.get(i).getMTText();
                        }
                    }
                    title.setText(ref);
                }
                else {
                    title.setText("");
                }
            }
        }
        else
        {
            Config.alertDialog(ReferalActivity.this,"Network Error","Please check yout internet connection");
        }

    }

    public void loadSocietyNames(){
        EnqueueWrapper.with(this).enqueue(ApiService.getService().getSocietyList(), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(!responseString.equalsIgnoreCase("")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Location>>() { }.getType();
                        locations = gson.fromJson(responseString,type);
                        if(locations != null){
                            societyNameAdapter = new SocietyNameAdapter(locations,ReferalActivity.this);
                            societyName.setAdapter(societyNameAdapter);
                            societyName.setThreshold(1);
                        }
                        else {
                            Log.e("branch List", "null");
                        }
                    }
                }


            }
        });

    }

    public void refferFriend(ReferFriendInput referFriendInput){
        if (Config.isConnectingToInternet(ReferalActivity.this))
        {
            EnqueueWrapper.with(this).enqueue(ApiService.getService().referFriend(referFriendInput), new EnqueueWrapper.EnqueueSuccess() {
                @Override
                public void onSuccess(String responseString) {
                    hideLoading();

                    if(responseString != null){
                        if(!responseString.equalsIgnoreCase("")){
                            if(responseString != null){
                                if(responseString.equalsIgnoreCase("SUCCESS")){
                                    mobileNo.setText("");
                                    name.setText("");
                                    societyName.setHint("Type Your Friends Society Name");
                                    Config.alertDialog(ReferalActivity.this,"Refer Friend","Your Referral will be activated once your Referred user logs in with Referral code and have ordered something on the Shiv-Mudra Platform");
                                }
                                else if(responseString.equalsIgnoreCase("ALREADY-REFERRED")){
                                    Config.alertDialog(ReferalActivity.this,"Refer Friend"," You have already referred this friend.");
                                }
                                else if (responseString.equalsIgnoreCase("INVALID-TOKEN")){
                                    Config.alertDialog(ReferalActivity.this,"Refer Friend"," Invalid Token");
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
        else
        {
            Config.alertDialog(ReferalActivity.this,"Network Error","Please check yout internet connection");
        }


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

}
