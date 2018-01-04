package com.realizertech.shivmudra;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.realizertech.shivmudra.adapter.VegetableListAdapter;
import com.realizertech.shivmudra.customview.ProgressWheel;
import com.realizertech.shivmudra.model.VegetableModel;
import com.realizertech.shivmudra.utils.Config;
import com.realizertech.shivmudra.utils.OnTaskCompleted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TempModifyLastOrderActivity extends AppCompatActivity {

    public TextView noData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_referral_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Config.actionBarTitle("Vegetable", this));

        initiateView();

    }

    public void initiateView(){
        noData = (TextView) findViewById(R.id.txtnoData);
        noData.setText("This feature will be available from 15th of January");

        noData.setVisibility(View.VISIBLE);
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
