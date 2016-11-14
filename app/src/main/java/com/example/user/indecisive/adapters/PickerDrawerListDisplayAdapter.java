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

public class PickerDrawerListDisplayAdapter extends BaseListItemAdapter {

    //TODO: make a base adapter for the list choices



    public PickerDrawerListDisplayAdapter(Context context, int resource, ArrayList<ListChoice> objects) {
        super(context, resource, objects);

    }


    private static class ViewHolder{
        public TextView tvListName;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        final ViewHolder holder;

        try {

            if(convertView == null){

                //can put this is a method. Might be complicated
                view = inflater.inflate(R.layout.picker_drawer_list_row, null);
                holder = new ViewHolder();

                holder.tvListName = (TextView) view.findViewById(R.id.pickerDrawerListName);

                holder.tvListName.setText(items.get(position).getListName());

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
