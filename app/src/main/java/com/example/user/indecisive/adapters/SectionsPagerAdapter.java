package com.example.user.indecisive.adapters;

/**
 * Created by Eamon on 06/11/2016.
 *
 * Adapter for tab layout
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.indecisive.R;
import com.example.user.indecisive.activities.MainActivity;
import com.example.user.indecisive.fragments.DrawerFragment;
import com.example.user.indecisive.fragments.PickerFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Fragment fragment = null;

        if(position == 0){
            fragment = new PickerFragment();
        }
        else if(position == 1){
            fragment = new DrawerFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    //displays title on tabs
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0:
                return "PICKER";
            case 1:
                return "DRAWER";
        }
        return null;
    }
}