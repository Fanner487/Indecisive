package com.example.user.indecisive.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ItemChoice;

import java.util.ArrayList;

/**
 * Created by Eamon on 11/11/2016.
 */

public class RandomPickAdapter extends ArrayAdapter<ItemChoice>{

    final String TAG = RandomPickAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<ItemChoice> items;
    private static LayoutInflater inflater = null;

    public RandomPickAdapter(Context context, int resource, ArrayList<ItemChoice> objects) {
        super(context, resource, objects);


        //do i need this???
        try{
            this.context = context;
            this.items = objects;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    //can add new item later
    public static class ViewHolder{

        //row layout
        public TextView tvItemChoice;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = null;
        ViewHolder holder;

        try {

            if(convertView == null){

                row = inflater.inflate(R.layout.random_picker_drawer_item_row, null);

            }
            else{
                row = convertView;
            }

            holder = new ViewHolder();
            holder.tvItemChoice = (TextView) row.findViewById(R.id.tvChoiceItem);
            holder.tvItemChoice.setText(items.get(position).getItem());

        }
        catch (Exception e){
            e.printStackTrace();
        }



        return row;
    }
}
