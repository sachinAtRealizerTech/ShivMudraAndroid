package com.realizertech.shivmudra;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.SocietyModel;
import com.realizertech.shivmudra.model.User;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends BaseActivity{

    public Button signup;
    public AutoCompleteTextView societyName;
    public EditText mobileNo, flatNo, name;
    public TextView txtWhat,txtWhy,txtlogin;
    public SocietyNameAdapter societyNameAdapter;
    public static String fireBasetoken=null;
    public String referCode ="",socname;
    public int societyId=0;
    public Switch refaral;
    public ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.registration_activity);
        initiateView();
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP) {
            if (!checkIfAlreadyhavePermission())
            {
                requestForSpecificPermission();
            }
        }

    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {
                        android.Manifest.permission.INTERNET,
                        android.Manifest.permission.READ_PHONE_STATE,
                        android.Manifest.permission.ACCESS_NETWORK_STATE,
                        android.Manifest.permission.WAKE_LOCK,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA

                }, 101);
    }

    public void initiateView(){

        societyName = (AutoCompleteTextView) findViewById(R.id.societyname);
        signup = (Button) findViewById(R.id.btnRegister);
        flatNo = (EditText) findViewById(R.id.edtflatno);
        name = (EditText) findViewById(R.id.edtname);
        mobileNo = (EditText) findViewById(R.id.edtmobileno);
        txtWhat = (TextView) findViewById(R.id.txtwhat);
        txtWhy = (TextView) findViewById(R.id.txtwhy);
        txtlogin = (TextView) findViewById(R.id.txtlogin);
        txtlogin.setPaintFlags(txtlogin.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //referralCode = (EditText) findViewById(R.id.edtReferralCode);
        refaral = (Switch) findViewById(R.id.rbreferral);
        if (Config.isConnectingToInternet(RegistrationActivity.this))
        {
            loadSocietyNames();
        }
        else
        {
            Config.alertDialog(RegistrationActivity.this,"Network Error","Please check yout internet connection");
        }

        refaral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  showReferDialog();
                }
                else {
                    referCode = "";
                }
            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,OTPConfirmationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.isConnectingToInternet(RegistrationActivity.this))
                {
                    //FireBaseInitialization();
                    // if (fireBasetoken != null && !fireBasetoken.isEmpty() && !fireBasetoken.equals("null"))
                    // {
                    if(societyName.getText().toString().trim().isEmpty()){
                        Config.alertDialog(RegistrationActivity.this,"Error","Please Enter Society Name");
                    }
                    else if(name.getText().toString().trim().isEmpty()){
                        Config.alertDialog(RegistrationActivity.this,"Error","Please Enter Your Name");
                    }
                    else if(flatNo.getText().toString().trim().isEmpty()){
                        Config.alertDialog(RegistrationActivity.this,"Error","Please Enter Your Flat No.");
                    }
                    else if(mobileNo.getText().toString().trim().isEmpty()){
                        Config.alertDialog(RegistrationActivity.this,"Error","Please Enter Mobile Number");
                    }
                    else if(mobileNo.getText().toString().trim().length() < 10){
                        Config.alertDialog(RegistrationActivity.this,"Error","Please Enter Valid Mobile Number");
                    }
                    else {
                        User user = new User();
                        user.setFlatNo(flatNo.getText().toString().trim());
                        user.setSocietyName(socname);
                        user.setName(name.getText().toString().trim());
                        user.setContactNo(mobileNo.getText().toString().trim());
                        user.setSocietyId(societyId);
                        user.setReferralToken(referCode);
                        user.setOtp(0);
                        user.setId(0);
                        user.setRegistered(false);
                        user.setUserToken("");
                        Log.d("data",user.toString());
                        registerUser(user);
                    }
                    // }
                    // else
                    // {
                    // Config.alertDialog(RegistrationActivity.this,"FCM Error","FCM ID Not Generated....");
                    // }
                }
                else
                {
                    Config.alertDialog(RegistrationActivity.this,"Network Error","Please check your internet connection");
                }


            }
        });

        txtWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.shiv-mudra.com/#why"));
                startActivity(intent);
            }
        });

        txtWhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.shiv-mudra.com/#what"));
                startActivity(intent);
            }
        });

        societyName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location societyModel = (Location) parent.getItemAtPosition(position);
                socname = societyModel.getName();
                societyName.setText(societyModel.getName());
                societyId = societyModel.getLocationId();
                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.putInt("LocationType",societyModel.getLocationType());
                edit.putInt("LocationId",societyModel.getLocationId());
                edit.commit();
            }
        });


    }

    public void showReferDialog(){
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialoglayout = inflater.inflate(R.layout.alert_otp, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setView(dialoglayout);

        Button buttonok= (Button) dialoglayout.findViewById(R.id.alert_btn_ok);
        final EditText refer=(EditText) dialoglayout.findViewById(R.id.edtotp);


        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!refer.getText().toString().trim().isEmpty()){
                    referCode = refer.getText().toString().trim();
                    alertDialog.dismiss();
                }

            }
        });

        alertDialog.show();
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
                            societyNameAdapter = new SocietyNameAdapter(locations,RegistrationActivity.this);
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

    public void FireBaseInitialization() {
        fireBasetoken = FirebaseInstanceId.getInstance().getToken();
        if(fireBasetoken != null){
            if (fireBasetoken.equalsIgnoreCase(null) || fireBasetoken.isEmpty())
            {
                try {
                    fireBasetoken=FirebaseInstanceId.getInstance().getToken(getApplication().getResources().getString(R.string.gcm_defaultSenderId), FirebaseMessaging.INSTANCE_ID_SCOPE);
                    FirebaseMessaging.getInstance().subscribeToTopic("ShivMudra");
                } catch (IOException e) {
                    e.printStackTrace();
                    FireBaseInitialization();
                }
                if (fireBasetoken.equalsIgnoreCase(null) || fireBasetoken.isEmpty())
                {
                    FireBaseInitialization();
                }
            }
        }

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        SharedPreferences.Editor edit = sharedpreferences.edit();
        edit.putString("TokenID", fireBasetoken);
        edit.commit();
    }

    private void registerUser(final User user) {

        EnqueueWrapper.with(this).enqueue(ApiService.getService().registration(user), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(responseString.equalsIgnoreCase("Success") && societyId != 0){

                            LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            View dialoglayout = inflater.inflate(R.layout.alert_custom, null);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                            builder.setView(dialoglayout);

                            Button buttonok= (Button) dialoglayout.findViewById(R.id.alert_btn_ok);
                            TextView titleName=(TextView) dialoglayout.findViewById(R.id.alert_dialog_title);
                            TextView alertMsg=(TextView) dialoglayout.findViewById(R.id.alert_dialog_message);


                        final AlertDialog alertDialog = builder.create();
                        alertDialog.setCancelable(false);

                            buttonok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                                    SharedPreferences.Editor edit = sharedpreferences.edit();
                                    edit.putString("ContactNo", mobileNo.getText().toString().trim());
                                    edit.commit();
                                    Intent intent = new Intent(RegistrationActivity.this,OTPConfirmationActivity.class);
                                    startActivity(intent);
                                    finish();
                                    alertDialog.dismiss();
                                }
                            });

                            titleName.setText("Register");
                            alertMsg.setText("Congratulations, You have Registered.\nYou will receive OTP in the message");

                            alertDialog.show();

                    }
                    else if(responseString.equalsIgnoreCase("CONTACTNO-PRE-REGISTERED")){
                        Config.alertDialog(RegistrationActivity.this,"Error","This Mobile Number is already registered");
                    }
                    else if(responseString.equalsIgnoreCase("ADDRESS-PRE-REGISTERED")){
                        Config.alertDialog(RegistrationActivity.this,"Error","This address is already registered");
                    }
                    else if(responseString.equalsIgnoreCase("SOCIETY-NOT-REGISTERED")){
                        Config.alertDialog(RegistrationActivity.this,"Info","As your Society is still not in our Delivery Network, We can not accept your order at the moment. However Our Representative will get in touch with you.");
                    }
                    else if(responseString.contains("SHOW")) {
                        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                        SharedPreferences.Editor edit = sharedpreferences.edit();
                        edit.putString("ContactNo", mobileNo.getText().toString().trim());
                        if (responseString.split("-").length >= 3) {
                            edit.putString("OTP", responseString.split("-")[2]);
                            edit.commit();
                            Intent intent = new Intent(RegistrationActivity.this,OTPConfirmationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else {
                        Config.alertDialog(RegistrationActivity.this,"Error","Error occurred on registration");
                    }
                }
                else {
                    Log.e("branch List", "null");
                }

            }
        });
    }

}
