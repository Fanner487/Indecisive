package com.example.user.indecisive.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.activities.AddEditListActivity;
import com.example.user.indecisive.activities.MainActivity;
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
        public ImageButton btnEdit;
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
                holder.btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);

                holder.tvListName.setText(items.get(position).getListName());

                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Edit pressed", Toast.LENGTH_SHORT).show();

                        MainActivity.startActivityWithBundle(v.getContext(), AddEditListActivity.class,
                                items.get(position).getListName(), items.get(position).getIsDrawer(), true);

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
