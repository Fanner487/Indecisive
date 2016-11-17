package com.example.user.indecisive.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;


public class PickerFragment extends Fragment {

    final String TAG = PickerFragment.class.getSimpleName();

    ListView listView;
    DBManager db;

    ArrayList<ListChoice> pickerList;

    private OnFragmentInteractionListener mListener;

    public PickerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        db = new DBManager(getContext()).open();


        pickerList = db.getListsOfType(0);

        listView = (ListView) view.findViewById(R.id.pickerListView);

        //Todo: can be made into function call with picker
        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(getContext(), 0, pickerList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                MainActivity.startActivityWithBundle(getActivity(), RandomPickActivity.class,
                        pickerList.get(position).getListName(), pickerList.get(position).getIsDrawer(), false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        pickerList = db.getListsOfType(0);

        PickerDrawerListDisplayAdapter adapter = new PickerDrawerListDisplayAdapter(getContext(), 0, pickerList);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
