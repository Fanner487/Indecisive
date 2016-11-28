package com.example.user.indecisive.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.activities.MainActivity;
import com.example.user.indecisive.activities.RandomPickActivity;
import com.example.user.indecisive.adapters.PickerDrawerListDisplayAdapter;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.constants.ListType;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;

public class DrawerFragment extends Fragment {

    final String TAG = DrawerFragment.class.getSimpleName();

    ListView listView;
    DBManager db;

    ArrayList<ListChoice> drawerList;

    private OnFragmentInteractionListener mListener;

    public DrawerFragment() {
        //required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        db = new DBManager(getContext()).open();

        drawerList = db.getListsOfType(ListType.DRAWER_LIST);

        listView = (ListView) view.findViewById(R.id.drawerListView);

        MainActivity.setListAdapterAndListener(getActivity(), listView, drawerList);

        return view;
    }

    @Override
    public void onResume() {
        drawerList = db.getListsOfType(ListType.DRAWER_LIST);

        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(getContext(), 1, drawerList);
        listView.setAdapter(adapter);

        super.onResume();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {

            mListener = (OnFragmentInteractionListener) context;

        } else {

            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
