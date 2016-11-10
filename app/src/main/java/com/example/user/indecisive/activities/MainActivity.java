package com.example.user.indecisive.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.db.DBManager;
import com.example.user.indecisive.fragments.DrawerFragment;
import com.example.user.indecisive.fragments.PickerFragment;
import com.example.user.indecisive.adapters.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        PickerFragment.OnFragmentInteractionListener, DrawerFragment.OnFragmentInteractionListener{

    final String TAG = MainActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        DBManager db = new DBManager(this).open();
//
//                String[] names = new String[]{"Bunsen", "Pinocchios", "Ginos", "Bobos", "Aussie BBQ"};
//                db.insertList("Restaurants", new ArrayList<>(Arrays.asList(names)), 0);

        //Todo: change icon on fab

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createActivityAndStart(AddListActivity.class);
            }
        });

//        DBManager db = new DBManager(this).open();

//        ArrayList<ListChoice> list = db.getListNames();
//
//        for(ListChoice l: list){
//            Log.d(TAG, "----------");
//            Log.d(TAG, "List Name: " + l.getListName());
//            Log.d(TAG, "Is Drawer: " + l.getIsDrawer());
//
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_action) {

            createActivityAndStart(SearchActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    //Used to recognise the fragments
    @Override
    public void onFragmentInteraction(Uri uri) {}

    public void createActivityAndStart(Class activity){

        Intent i = new Intent(getApplicationContext(), activity);
        startActivity(i);
    }

}
