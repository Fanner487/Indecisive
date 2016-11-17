package com.example.user.indecisive.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.activities.MainActivity;
import com.example.user.indecisive.activities.RandomPickActivity;
import com.example.user.indecisive.adapters.PickerDrawerListDisplayAdapter;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;

public class DrawerFragment extends Fragment {


    ListView listView;
    DBManager db;

    ArrayList<ListChoice> drawerList;

    private OnFragmentInteractionListener mListener;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        db = new DBManager(getContext()).open();

        drawerList = db.getListsOfType(1);

        listView = (ListView) view.findViewById(R.id.drawerListView);

        //Todo: shit here needs to be changed
        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(getContext(), 0, drawerList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), drawerList.get(position).getListName(), Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).startActivityWithBundle(getActivity(), RandomPickActivity.class,
                        drawerList.get(position).getListName(), drawerList.get(position).getIsDrawer(), false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        drawerList = db.getListsOfType(1);

        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(getContext(), 1, drawerList);
        listView.setAdapter(adapter);

        super.onResume();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
