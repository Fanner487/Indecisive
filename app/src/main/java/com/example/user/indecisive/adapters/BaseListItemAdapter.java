package com.example.user.indecisive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.example.user.indecisive.activities.SearchActivity;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.business.ListChoice;

import java.util.ArrayList;


/**
 * Created by Eamon on 11/11/2016.
 */

public abstract class BaseListItemAdapter extends ArrayAdapter<ListChoice>{

    final String TAG = BaseListItemAdapter.class.getSimpleName();

    protected Context context;
    protected ArrayList<ListChoice> items;
    protected LayoutInflater inflater = null;

    public BaseListItemAdapter(Context context, int resource, ArrayList<ListChoice> objects) {
        super(context, resource, objects);

        try{
            this.context = context;
            this.items = objects;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return items.size();
    }


}
