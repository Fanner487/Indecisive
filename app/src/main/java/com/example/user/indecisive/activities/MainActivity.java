package com.example.user.indecisive.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.adapters.PickerDrawerListDisplayAdapter;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.constants.BundleConstants;
import com.example.user.indecisive.fragments.DrawerFragment;
import com.example.user.indecisive.fragments.PickerFragment;
import com.example.user.indecisive.adapters.SectionsPagerAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements
        PickerFragment.OnFragmentInteractionListener, DrawerFragment.OnFragmentInteractionListener{

    final String TAG = MainActivity.class.getSimpleName();

    FloatingActionButton fab;

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
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        //fab creates the new list activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityWithBundle(MainActivity.this, AddEditListActivity.class, null, 0, false);
            }
        });

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

    public static void startActivityWithBundle(Context context, Class activity, String list, int isDrawer, boolean isEditList){

        Intent i = new Intent(context, activity);

        Bundle bundle = new Bundle();
        bundle.putString(BundleConstants.LIST_NAME, list);
        bundle.putInt(BundleConstants.IS_DRAWER, isDrawer);
        bundle.putBoolean(BundleConstants.IS_EDIT_LIST, isEditList);
        i.putExtras(bundle);
        context.startActivity(i);

    }




    public static void setListAdapterAndListener(final Context context, ListView listView, final ArrayList<ListChoice> lists){
        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(context, 0, lists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                MainActivity.startActivityWithBundle(context, RandomPickActivity.class,
                        lists.get(position).getListName(), lists.get(position).getIsDrawer(), false);
            }
        });
    }

}
