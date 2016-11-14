package com.example.user.indecisive.fragments;

import android.content.Context;
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
import com.example.user.indecisive.adapters.PickerDrawerListDisplayAdapter;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;

public class DrawerFragment extends Fragment {


    ListView listView;
    DBManager db;

    private OnFragmentInteractionListener mListener;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        db = new DBManager(getContext()).open();

        Cursor c = db.getAllItems();



        final ArrayList<ListChoice> pickerList = db.getListsOfType(1);

        listView = (ListView) view.findViewById(R.id.drawerListView);

        //Todo: shit here needs to be changed
        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(getContext(), 0, pickerList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), pickerList.get(position).getListName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
