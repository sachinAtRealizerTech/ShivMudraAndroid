package com.realizertech.shivmudra;


import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.realizertech.shivmudra.adapter.SocietyNameAdapter;
import com.realizertech.shivmudra.model.SocietyModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class ReportPoorQualityActivity extends AppCompatActivity implements OnTaskCompleted{

    public Button report;
    public ImageView imgQuality;
    private Uri picUri;
    private static Bitmap convertedImage;
    public final int CROP_PIC = 3;
    public String imagebase64 = "";
    public static boolean isRespondingAPI=false;
    public TextView orderDate;
    public DatePickerDialog.OnDateSetListener date;
    public Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_quality_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Config.actionBarTitle("Report Poor Quality", this));
        initiateView();

    }



    public void initiateView(){

        imgQuality = (ImageView) findViewById(R.id.img);
        report = (Button) findViewById(R.id.btnReport);
        orderDate = (TextView) findViewById(R.id.orderDate);

        imgQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.alertDialog(ReportPoorQualityActivity.this,"In Progress","This feature will be coming soon");
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.alertDialog(ReportPoorQualityActivity.this,"In Progress","This feature will be coming soon");
            }
        });

        orderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportPoorQualityActivity.this, date, year, month, day);
                Calendar calendar = new GregorianCalendar();
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR,year);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                upDateLable();
            }

        };

    }

    public void upDateLable() {
        String myFormat = "dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        orderDate.setText("Order Date: "+sdf.format(myCalendar.getTime()));
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportPoorQualityActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    try {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                        File imageFile = new File(imageFilePath);
                        picUri = Uri.fromFile(imageFile); // convert path to Uri
                        takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );
                        startActivityForResult(takePictureIntent, 1);
                    } catch(ActivityNotFoundException anfe){
                        //display an error message
                        String errorMessage = "Whoops - your device doesn't support capturing images!";
                        // Toast.makeText(UserProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Config.alertDialog(ReportPoorQualityActivity.this, "Error",errorMessage+".");
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 2);//one can be replaced with any action code
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            String path="";
            if (requestCode == 2) {
                picUri = data.getData();
                try
                {
                    convertedImage = MediaStore.Images.Media.getBitmap(getContentResolver() , picUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    convertedImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                }
                catch (Exception e)
                {
                    //handle exception
                }
                performCrop();

            }
            else if (requestCode == 1) {
                //get the Uri for the captured image
                try
                {
                    convertedImage = MediaStore.Images.Media.getBitmap(getContentResolver() , picUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    convertedImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                }
                catch (Exception e)
                {
                    //handle exception
                }
                //carry out the crop operation
                performCrop();
            }
            else if (requestCode == CROP_PIC) {
                Bundle extras = data.getExtras();
                if(extras != null ) {
                    Bitmap photo = extras.getParcelable("data");
                    setPhoto(photo);
                    imgQuality.setImageBitmap(photo);
                    path = encodephoto(photo);
                    imagebase64 = path;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ProfilePicPath", path);
                    editor.commit();
                    UploadThumbnail();
                }
                else
                {
                    setPhoto(convertedImage);
                    imgQuality.setImageBitmap(convertedImage);
                    path = encodephoto(convertedImage);
                    imagebase64 = path;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ProfilePicPath", path);
                    editor.commit();
                    UploadThumbnail();
                }
            }
        } else if (resultCode == RESULT_CANCELED) {

            // user cancelled Image capture
            //Toast.makeText(getApplicationContext(),"User cancelled image capture", Toast.LENGTH_SHORT).show();
            if (requestCode != CROP_PIC) {
                Config.alertDialog(this, "Image Cancelled", "User cancelled image capture.");
            }

        } else {
            // failed to capture image
            //Toast.makeText(getApplicationContext(),"Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            Config.alertDialog(this, "Image Error","Sorry! Failed to capture image.");
        }

    }

    //Encode image to Base64 to send to server
    private void setPhoto(Bitmap bitmapm) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                /*Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");*/

            }
        }
        else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmapm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            //4
            File file = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpeg");
            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                //5
                fo.write(bytes.toByteArray());
                fo.close();
                //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Intent mediaScanIntent = new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);
                } else {
                    sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://"
                                    + Environment.getExternalStorageDirectory())));
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
    }

    //Encode image to Base64 to send to server
    private String encodephoto(Bitmap bitmapm) {
        String imagebase64string = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] byteArrayImage = baos.toByteArray();
            imagebase64string = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagebase64string;
    }

    public void UploadThumbnail()
    {
        if (Config.isConnectingToInternet(ReportPoorQualityActivity.this)) {
            //loading.setVisibility(View.VISIBLE);
            //imgQuality.setEnabled(false);
            //thumbnailPut = new UserProfileAsyncTaskPut(UserID, imagebase64, UserProfileActivity.this, UserProfileActivity.this);
            //thumbnailPut.execute();
            isRespondingAPI=false;
            new CountDownTimer( 15000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
/*                    loading.setVisibility(View.GONE);
                    if (!isRespondingAPI)
                    {
                        if (thumbnailPut != null && thumbnailPut.getStatus() != AsyncTask.Status.FINISHED) {
                            thumbnailPut.cancel(true);
                            Config.alertDialog(UserProfileActivity.this, "Error", "Server is not responding.Please try again after sometime.");
                        }
                    }*/
                }
            }.start();
        }
        else {
           // Config.alertDialog(UserProfileActivity.this,"Internet Connection","Make sure your device is connected to the internet.");
        }
    }

    /**
     * this function does the crop operation.
     */
    private void performCrop(){


        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            //indicate output X and Y
            cropIntent.putExtra("outputX",512);
            cropIntent.putExtra("outputY", 512);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!.Setting original image to profile.";
            Config.alertDialog(ReportPoorQualityActivity.this, "Error",errorMessage+".");
            String path="";
            setPhoto(convertedImage);
            imgQuality.setImageBitmap(convertedImage);
            path = encodephoto(convertedImage);
            imagebase64 = path;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ProfilePicPath", path);
            editor.commit();
            UploadThumbnail();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            String path="";
            setPhoto(convertedImage);
            imgQuality.setImageBitmap(convertedImage);
            path = encodephoto(convertedImage);
            imagebase64 = path;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ProfilePicPath", path);
            editor.commit();
            UploadThumbnail();
        }
    }
    @Override
    public void onTaskCompleted(String apiName,String s) {

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
