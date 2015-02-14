package com.example.faizan.touristguide;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faizan.touristguide.model.CustomeDirectory;
import com.example.faizan.touristguide.model.RecFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles = {"Home","Search","Map","About"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle  = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle("Closed");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ///getActionBar().setTitle("Opened");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        this.invalidateOptionsMenu();
//        List<DashBoardInfo> list = GetData();
//        //String [] myDataset = new String[]{"1","2","3"};
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(list,this);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setOnClickListener(this);

        selectItem(0);

    }


    @Override
    protected void onResume() {
        super.onResume();
//        if(SpinFrag.queryinDomain == null)
//            return;
//        if(SpinFrag.queryinDomain[0] == null || SpinFrag.queryinDomain[0].isEmpty())
//            return;
//
//        if(SpinFrag.queryinDomain[0].contains(SpinFrag.queryinValue[0]))
//        {
//
//            mDrawerList.setSelection(0);
//            mDrawerList.callOnClick();
//        }
//        else if(SpinFrag.queryinDomain[0].contains(SpinFrag.queryinValue[1]))
//        {
//            mDrawerList.setSelection(1);
//            mDrawerList.callOnClick();
//        }
//        else if(SpinFrag.queryinDomain[0].contains(SpinFrag.queryinValue[2]))
//        {
//            mDrawerList.setSelection(2);
//            mDrawerList.callOnClick();
//        }
//        else
//        {
//            mDrawerList.setSelection(3);
//            mDrawerList.callOnClick();
//        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public String getLocation() {

        MyLocationListener locationListener = new MyLocationListener(this);
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 1000, (float) 10.0, locationListener);
        return locationListener.currRealLoca;

    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        if(position == 0) {
            Fragment fragment = new RecFragment();

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            //setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else if(position == 1)
        {
            Fragment fragment = new SpinFrag();

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.content_frame, fragment)
                    .commit();


            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            //setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if(position == 2)
        {
            Locate(null);

        }else if(position == 3)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(getString(R.string.lrpsm_long));
            builder1.setCancelable(true);

            AlertDialog alert11 = builder1.create();
            alert11.show();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            //setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void Locate(View view)
    {
        if(isConnected()) {

            String currentLocation = getLocation();
            String locc = "http://maps.google.com/maps?saddr="+currentLocation;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locc));

            try {
                this.startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(this, "Install Android Map App First", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Network Connectivity Failed", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }



}

