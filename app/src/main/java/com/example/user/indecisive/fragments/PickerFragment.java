package com.example.user.indecisive.fragments;

import android.content.Context;
import android.content.Intent;
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

        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        listView = (ListView) view.findViewById(R.id.pickerListView);
        db = new DBManager(getContext()).open();

        pickerList = db.getListsOfType(ListType.PICKER_LIST);

        MainActivity.setListAdapterAndListener(getActivity(), listView, pickerList);

        return view;
    }

    @Override
    public void onResume() {
        pickerList = db.getListsOfType(ListType.PICKER_LIST);

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
        void onFragmentInteraction(Uri uri);
    }
}
