package com.example.user.indecisive.adapters;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.business.ListChoice;

import java.util.ArrayList;

/**
 * Created by Eamon on 10/11/2016.
 */

public class SearchListAdapter extends BaseListItemAdapter{

    //Todo: put icon for drawer or picker


    //changed the list parameter
    public SearchListAdapter(Context context, int resource, ArrayList<ListChoice> objects) {
        super(context, resource, objects);

    }

    //can add new item later
    public static class ViewHolder{
        public TextView searchTextView;
        public ImageButton testButton;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        final ViewHolder holder;

        try {

            if(convertView == null){

                view = inflater.inflate(R.layout.search_activity_list_row, null);
                holder = new ViewHolder();

                holder.searchTextView = (TextView) view.findViewById(R.id.searchTextView);
                holder.testButton = (ImageButton) view.findViewById(R.id.buttonTest);

                holder.searchTextView.setText(items.get(position).getListName());

                holder.testButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "Edit " + items.get(position).getListName(), Toast.LENGTH_SHORT).show();


                        notifyDataSetChanged();

                    }
                });

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
