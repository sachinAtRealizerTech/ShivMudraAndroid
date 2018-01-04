package com.realizertech.shivmudra;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.realizertech.shivmudra.model.ChangeContactNoInput;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.MasterData;
import com.realizertech.shivmudra.model.MasterDataInputs;
import com.realizertech.shivmudra.model.ReferralReminderInput;
import com.realizertech.shivmudra.model.RegistrationModel;
import com.realizertech.shivmudra.model.SocietyModel;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.model.ValidationRequest;
import com.realizertech.shivmudra.model.ValidationResult;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;
import com.realizertech.shivmudra.utils.SaveData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OTPConfirmationActivity extends BaseActivity {

    Button confirm,resend;
    EditText mobileNo, otp;
    TextView editLink,message,refMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_otpconfirmation);
        initiateView();

    }

    public void initiateView(){

        confirm = (Button) findViewById(R.id.btnconfirm);
        resend = (Button) findViewById(R.id.btnresend);
        otp = (EditText) findViewById(R.id.edtotp);
        mobileNo = (EditText) findViewById(R.id.edtmobileno);
        //editLink = (TextView) findViewById(R.id.txteditlink);
        message = (TextView) findViewById(R.id.txtmessage);
        refMessage = (TextView) findViewById(R.id.txtrefmsg);

        final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(OTPConfirmationActivity.this);

        mobileNo.setText(sharedpreferences.getString("ContactNo",""));
        if(getIntent().getStringExtra("OTP") != null){
            otp.setText(getIntent().getStringExtra("OTP"));
        }
        if(Config.isConnectingToInternet(OTPConfirmationActivity.this)){
            MasterDataInputs masterDataInputs = new MasterDataInputs();
            masterDataInputs.setMTCategories("ConfirmScreenBottom");
            masterDataInputs.setMTKeys("");
            List<MasterData> masterData = Config.getMasterText(OTPConfirmationActivity.this,masterDataInputs);
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
                    refMessage.setText(ref);
                }
                else {
                    refMessage.setText("");
                }
            }
        }

        //editLink.setPaintFlags(editLink.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
/*        editLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNo.setEnabled(true);
            }
        });*/

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.isConnectingToInternet(OTPConfirmationActivity.this))
                {
                   if(mobileNo.getText().toString().trim().isEmpty()){
                    Config.alertDialog(OTPConfirmationActivity.this,"Error","Please Enter Mobile Number");
                }
                else if(otp.getText().toString().trim().isEmpty()){
                    Config.alertDialog(OTPConfirmationActivity.this,"Error","Please Enter OTP");
                }
                   else if(mobileNo.getText().toString().trim().length() < 10){
                       Config.alertDialog(OTPConfirmationActivity.this,"Error","Please Enter Valid Mobile Number");
                   }
                else {
                           ValidationRequest validationRequest = new ValidationRequest();
                           validationRequest.setContactNo(mobileNo.getText().toString().trim());
                           validationRequest.setOTP(otp.getText().toString().trim());
                           confirmOtp(validationRequest);
                }

                }
                else
                {
                    Config.alertDialog(OTPConfirmationActivity.this,"Network Error","Please check yout internet connection");
                }


            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobileNo.getText().toString().trim().isEmpty()){
                    Config.alertDialog(OTPConfirmationActivity.this,"Error","Please Enter Mobile Number");
                }
                else if(mobileNo.getText().toString().trim().length() < 10){
                    Config.alertDialog(OTPConfirmationActivity.this,"Error","Please Enter Valid Mobile Number");
                }
                else {
                    if (Config.isConnectingToInternet(OTPConfirmationActivity.this))
                    {
                        if(!sharedpreferences.getString("ContactNo","").isEmpty() && (!sharedpreferences.getString("ContactNo","").equalsIgnoreCase(mobileNo.getText().toString().trim()))){
                                modifyNumber();
                        }
                        else {
                            resendOtp();
                        }

                    }
                    else
                    {
                        Config.alertDialog(OTPConfirmationActivity.this,"Network Error","Please check yout internet connection");
                    }

                }
            }
        });


    }

    public void modifyNumber(){
            SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(OTPConfirmationActivity.this);
            ChangeContactNoInput changeContactNoInput = new ChangeContactNoInput();
            changeContactNoInput.setOldContactNo(sharedpreferences.getString("ContactNo",""));
            changeContactNoInput.setNewContactNo(mobileNo.getText().toString().trim());
            EnqueueWrapper.with(this).enqueue(ApiService.getService().updateContactNo(changeContactNoInput), new EnqueueWrapper.EnqueueSuccess() {
                @Override
                public void onSuccess(String responseString) {
                    hideLoading();

                    if(responseString != null){
                        if(responseString.equalsIgnoreCase("success")){
                            Config.alertDialog(OTPConfirmationActivity.this,"Info","Otp sent to the given mobile number.");
                        }
                        else if(responseString.contains("SHOW")) {
                            if (responseString.split("-").length >= 3) {
                                String otptext = responseString.split("-")[2];
                                otp.setText(otptext);
                                Config.alertDialog(OTPConfirmationActivity.this, "Info", responseString.split("-")[1]);
                            }
                        }
                        else if(responseString.equalsIgnoreCase("INVALID-OLD-CONTACT-NO")){
                            Config.alertDialog(OTPConfirmationActivity.this,"Error","Error while modifying contact number");

                        }
                    }


                }
            });

    }

    public void resendOtp(){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(OTPConfirmationActivity.this);
        UserInputs userInputs = new UserInputs();
        userInputs.setUserToken(sharedpreferences.getString("UserToken",""));
        userInputs.setContactNo(mobileNo.getText().toString().trim());
        EnqueueWrapper.with(this).enqueue(ApiService.getService().sendConsumerOTP(userInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();

                if(responseString != null){
                    if(responseString.equalsIgnoreCase("success")){
                        Config.alertDialog(OTPConfirmationActivity.this,"Info","Otp sent to the given mobile number.");
                    }
                    else if(responseString.contains("SHOW")){
                        if(responseString.split("-").length >= 3){
                            String otptext = responseString.split("-")[2];
                            otp.setText(otptext);
                            Config.alertDialog(OTPConfirmationActivity.this,"Info",responseString.split("-")[1]);
                        }



                    }
                    else if(responseString.equalsIgnoreCase("CONTACT-NO-NOT-REGISTERED")){
                        Config.alertDialog(OTPConfirmationActivity.this,"Error","This mobile number is not registered.");

                    }
                }


            }
        });
    }

    public void confirmOtp(final ValidationRequest validationRequest){
        EnqueueWrapper.with(OTPConfirmationActivity.this).enqueue(ApiService.getService().validateToken(validationRequest), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                hideLoading();
                if(responseString != null){
                    Gson gson = new Gson();
                    ValidationResult validationResult = gson.fromJson(responseString,ValidationResult.class);
                    if(validationResult != null){
                        if(validationResult.getResult().equalsIgnoreCase("OTP-DID-NOT-MATCH")){
                            Config.alertDialog(OTPConfirmationActivity.this,"Error","You entered wrong OTP");
                           // showError("Error","You Entered Wrong Otp");
                        }
                        else if(validationResult.getResult().equalsIgnoreCase("SUCCESS")){
                                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(OTPConfirmationActivity.this);
                                SharedPreferences.Editor edit = sharedpreferences.edit();
                                edit.putString("UserToken", validationResult.getUserToken());
                                edit.putString("Name", validationResult.getName());
                                edit.putInt("SocietyId", validationResult.getSocietyId());
                                edit.putInt("LocationType", validationResult.getLocationType());
                                edit.putString("SocietyName",validationResult.getSocietyName());
                                edit.putString("FlatNo", validationResult.getFlatNo());
                                edit.putString("ContactNo", mobileNo.getText().toString().trim());
                                edit.putBoolean("IsLogin",true);
                                edit.commit();
                                Intent intent = new Intent(OTPConfirmationActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                        }
                    }

                }
                else {
                    Log.e("branch List", "null");
                }

            }
        });
    }


}
