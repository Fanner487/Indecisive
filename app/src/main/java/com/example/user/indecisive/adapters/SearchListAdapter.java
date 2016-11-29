package com.example.user.indecisive.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.activities.AddEditListActivity;
import com.example.user.indecisive.activities.MainActivity;
import com.example.user.indecisive.business.ListChoice;

import java.util.ArrayList;

/**
 * Created by Eamon on 10/11/2016.
 *
 * Adapter for search view list
 */

public class SearchListAdapter extends BaseListItemAdapter{

    final String TAG = SearchListAdapter.class.getSimpleName();

    View view;
    //changed the list parameter
    public SearchListAdapter(Context context, ArrayList<ListChoice> objects) {
        super(context, objects);

    }

    //can add new item later
    public static class ViewHolder{
        public TextView searchTextView;
        //todo: change this
        public ImageButton editButton;
        public ImageView icon;
    }


    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        view = null;
        final ViewHolder holder;

        try {

            if(convertView == null){

                view = inflater.inflate(R.layout.row_search_activity_list_view, null);

            }
            else{
                view = convertView;
            }


            holder = new ViewHolder();

            holder.searchTextView = (TextView) view.findViewById(R.id.adapter_search_list_tv_search);
            holder.editButton = (ImageButton) view.findViewById(R.id.adapter_search_list_btn_edit);
            holder.icon = (ImageView) view.findViewById(R.id.adapter_search_list_image_icon);

            //set icon depending on list type
            if(items.get(position).getIsDrawer() == 0){
                holder.icon.setImageResource(R.drawable.shuffle);
            }
            else{
                holder.icon.setImageResource(R.drawable.magic_hat);
            }

            holder.searchTextView.setText(items.get(position).getListName());

            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity.startActivityWithBundle(view.getContext(), AddEditListActivity.class,
                            items.get(position).getListName(),  items.get(position).getIsDrawer(), true);

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
