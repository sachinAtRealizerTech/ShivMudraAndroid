package com.realizertech.shivmudra;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.realizertech.shivmudra.fragments.DashboardFragment;
import com.realizertech.shivmudra.fragments.ViewDiscountFragment;
import com.realizertech.shivmudra.fragments.ViewLastOrderFragment;
import com.realizertech.shivmudra.fragments.ViewReferralFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    TextView name,societyflat,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.txtname);
        societyflat = (TextView) header.findViewById(R.id.txtflatsociety);
        contact = (TextView) header.findViewById(R.id.txtcontactno);

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        name.setText(sharedpreferences.getString("Name",""));
        societyflat.setText(sharedpreferences.getString("FlatNo","")+", "+sharedpreferences.getString("SocietyName",""));
        contact.setText(sharedpreferences.getString("ContactNo",""));


                /* Display First Fragment at Launch*/
        navigationView.setCheckedItem(R.id.nav_dashboard);
        Fragment frag = new DashboardFragment();
        if(getIntent().getStringExtra("From")!= null){
            if(getIntent().getStringExtra("From").equalsIgnoreCase("ConfirmOrder"))
            frag = new ViewLastOrderFragment();
        }

        //Singlton.setSelectedFragment(frag);
        if (frag != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, frag).commit();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fragment= null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
          fragment = new DashboardFragment();
        }
        else if (id == R.id.nav_reffer) {
            fragment = new ViewReferralFragment();
        }
        if (id == R.id.nav_lastorder) {
            fragment = new ViewLastOrderFragment();
        }
        else if (id == R.id.nav_discount) {
            fragment = new ViewDiscountFragment();
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
