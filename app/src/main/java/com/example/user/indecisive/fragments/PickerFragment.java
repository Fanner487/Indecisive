package com.example.user.indecisive.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.db.DBManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickerFragment extends Fragment {

    final String TAG = PickerFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button button;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickerFragment newInstance(String param1, String param2) {
        PickerFragment fragment = new PickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_picker, container, false);



        button = (Button) view.findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBManager db = new DBManager(getContext()).open();

                db.insertItem(new ItemChoice("Rock", "Rock Paper Scissors", 1));

                Cursor c = db.getAllItems();

                while(c.moveToNext()){
                    Log.d(TAG, "-----------------");
                    Log.d(TAG, "ID: " + c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_ROWID)));
                    Log.d(TAG, "Item: " + c.getString(c.getColumnIndexOrThrow(DBManager.KEY_ITEM)));
                    Log.d(TAG, "Table: " + c.getString(c.getColumnIndexOrThrow(DBManager.KEY_LIST)));
                    Log.d(TAG, "Table: " + c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_DRAWER)));
                }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
