package com.realizertech.shivmudra.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.BaseActivity;
import com.realizertech.shivmudra.MainActivity;
import com.realizertech.shivmudra.ModifyLastOrderActivity;
import com.realizertech.shivmudra.OTPConfirmationActivity;
import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.TempModifyLastOrderActivity;
import com.realizertech.shivmudra.VegitableListActivity;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.fragments.BaseFragment;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.MasterData;
import com.realizertech.shivmudra.model.MasterDataInputs;
import com.realizertech.shivmudra.model.ValidationRequest;
import com.realizertech.shivmudra.model.ValidationResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Win on 11/3/2015.
 */
public class Config {
    // File upload url (replace the ip with your server address)
    public static final String URL = "http://45.35.4.250/Pathology/api/Path/";
    public static Context configContext;
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "SM";
    public static final int VEGETABLE = 1;
    public static final int FRUIT = 2;
    public static List<MasterData> masterData = new ArrayList<>();

    public static SpannableString actionBarTitle(String title, Context context) {
        SpannableString s = new SpannableString(title);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");
        s.setSpan(new CustomTypefaceSpan("", face), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return s;
    }

    public static void hideSoftKeyboardWithoutReq(Context context, View view) {
        try {
            if (view != null) {
                final InputMethodManager inputMethodManager =
                        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    /**
     * @param context
     * @param view
     */
    public static void showSoftKeyboard(Context context, View view) {
        try {
            if (view.requestFocus()) {
                InputMethodManager imm = (InputMethodManager)
                        context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getfromDate(String webDate) {
        String datetimevalue = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy");
            Date temp = df.parse(webDate);
            datetimevalue = df1.format(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return datetimevalue;
    }

    public static String getDate() {
        String datetimevalue = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            datetimevalue = df.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return datetimevalue;
    }
    public static Date getInputDate(Calendar date) {
        Date outDateinput = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int day = date.get(Calendar.DATE);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        String today = year+"-"+(month+1)+"-"+day;
        try {
            outDateinput = df.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDateinput;
    }

    public static int getDayOfWeek(String day) {
        int dayOfWeek = 0;

        switch (day) {
            case "sunday":
                dayOfWeek = Calendar.SUNDAY;
                break;
            case "monday":
                dayOfWeek = Calendar.MONDAY;
                break;
            case "tuesday":
                dayOfWeek = Calendar.TUESDAY;
                break;
            case "wednesday":
                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case "thursday":
                dayOfWeek = Calendar.THURSDAY;
                break;
            case "friday":
                dayOfWeek = Calendar.FRIDAY;
                break;
            case "saturday":
                dayOfWeek = Calendar.SATURDAY;
                break;
        }

        return dayOfWeek;
    }

    public static boolean isConnectingToInternet(Context context){
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
          //  Toast.makeText(context, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                NetworkInfo.State.DISCONNECTED  ) {
            //Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


    /**
     * @param context
     * @param title
     * @param message
     */
    public static void alertDialog(final Context context, String title, String message) {
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
                alertDialog.dismiss();
            }
        });

        titleName.setText(title);
        alertMsg.setText(message);

        alertDialog.show();

    }

    public static void updateDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialoglayout = inflater.inflate(R.layout.alert_custom, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);

        Button buttonok= (Button) dialoglayout.findViewById(R.id.alert_btn_ok);
        TextView titleName=(TextView) dialoglayout.findViewById(R.id.alert_dialog_title);
        TextView alertMsg=(TextView) dialoglayout.findViewById(R.id.alert_dialog_message);
        buttonok.setText("New version available");


        final AlertDialog alertDialog = builder.create();

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        titleName.setText("Update SHIV-MUDRA");
        alertMsg.setText("Please, update SHIV-MUDRA to new version for better experience.");

        alertDialog.show();

    }

    /**
     * @param context
     */
    public static void alertOtp(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialoglayout = inflater.inflate(R.layout.alert_otp, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);

        Button buttonok= (Button) dialoglayout.findViewById(R.id.alert_btn_ok);
        TextView titleName=(TextView) dialoglayout.findViewById(R.id.alert_dialog_title);
        final EditText otp=(EditText) dialoglayout.findViewById(R.id.edtotp);


        final AlertDialog alertDialog = builder.create();

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otp.getText().toString().trim().isEmpty()){
                    //Call API
                }
                alertDialog.dismiss();
            }
        });

        titleName.setText("Please Enter Otp to complete Registration");
        alertDialog.show();

    }


    public static void placeOrder(final Context context) {
        configContext=context;
        // new MyFirebaseMessagingService().setCountZero("Emergency");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialoglayout = inflater.inflate(R.layout.place_order_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);

        //Button ack= (Button) dialoglayout.findViewById(R.id.btn_ack);
        Button newOrder = (Button) dialoglayout.findViewById(R.id.btnnewOrder);
        Button modifyOrder = (Button) dialoglayout.findViewById(R.id.btnModifyOrder);

        final AlertDialog alertDialog = builder.create();

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VegitableListActivity.class);
                context.startActivity(intent);
                alertDialog.dismiss();

            }
        });

        modifyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ModifyLastOrderActivity.class);
                context.startActivity(intent);
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    public static List<MasterData> getMasterText(final BaseActivity baseActivity, final MasterDataInputs masterDataInputs){

        EnqueueWrapper.with(baseActivity).enqueue(ApiService.getService().getMasterTexts(masterDataInputs), new EnqueueWrapper.EnqueueSuccess() {
            @Override
            public void onSuccess(String responseString) {
                baseActivity.hideLoading();
                if(responseString != null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<MasterData>>() { }.getType();
                     masterData = gson.fromJson(responseString,type);
                    if(masterData != null){
                        if(masterData.size()>0){

                          }
                        }
                    }

                else {
                    Log.e("branch List", "null");
                }

            }
        });
        return masterData;
    }


    public static String getMonth(int mon) {
        String dayOfWeek = "";

        switch (mon) {
            case 1:
                dayOfWeek = "Jan";
                break;
            case 2:
                dayOfWeek = "Feb";
                break;
            case 3:
                dayOfWeek = "Mar";
                break;
            case 4:
                dayOfWeek = "Apr";
                break;
            case 5:
                dayOfWeek = "May";
                break;
            case 6:
                dayOfWeek = "Jun";
                break;
            case 7:
                dayOfWeek = "Jul";
                break;
            case 8:
                dayOfWeek = "Aug";
                break;
            case 9:
                dayOfWeek = "Sept";
                break;
            case 10:
                dayOfWeek = "Oct";
                break;
            case 11:
                dayOfWeek = "Nov";
                break;
            case 12:
                dayOfWeek = "Dec";
                break;
        }

        return dayOfWeek;
    }

}
