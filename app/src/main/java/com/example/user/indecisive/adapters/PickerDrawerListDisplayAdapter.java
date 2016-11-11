package com.example.user.indecisive.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ListChoice;


import java.util.ArrayList;

/**
 * Created by Eamon on 10/11/2016.
 */

public class PickerDrawerListDisplayAdapter extends ArrayAdapter<ListChoice> {

    //TODO: make a base adapter for the list choices

    private Context context;
    private ArrayList<ListChoice> lists;
    private static LayoutInflater inflater = null;

    public PickerDrawerListDisplayAdapter(Context context, int resource, ArrayList<ListChoice> objects) {
        super(context, resource, objects);

        //do i need this???
        try{
            this.context = context;
            this.lists = objects;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private static class ViewHolder{
        public TextView tvListName;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return lists.size();
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        final ViewHolder holder;

        try {

            if(convertView == null){

                view = inflater.inflate(R.layout.picker_drawer_list_row, null);
                holder = new ViewHolder();

                holder.tvListName = (TextView) view.findViewById(R.id.pickerDrawerListName);

                holder.tvListName.setText(lists.get(position).getListName());

            }
            else{
                view = convertView;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }
}
