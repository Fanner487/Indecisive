package com.example.user.indecisive.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextSwitcher;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.RandomPick;
import com.example.user.indecisive.adapters.RandomPickAdapter;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.constants.BundleConstants;
import com.example.user.indecisive.db.DBManager;
import com.example.user.indecisive.interfaces.ListenerOperation;

import java.util.ArrayList;

/*
*
* Picks random from list or draws "out of hat" from list
*
* */

public class RandomPickActivity extends AppCompatActivity implements ListenerOperation {

    final String TAG = RandomPickActivity.class.getSimpleName();

    ListView listView;
    Button btnMakeChoice;
    TextSwitcher tvChoice = null;
    ArrayList<ItemChoice> items = null;
    RandomPickAdapter adapter;
    String listName;

    Animation in;
    Animation out;

    DBManager db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_pick);

        listView = (ListView) findViewById(R.id.activity_random_pick_list_view);
        btnMakeChoice = (Button) findViewById(R.id.activity_random_pick_btn_make_choice);
        tvChoice = (TextSwitcher) findViewById(R.id.activity_random_pick_tv_choice);

        bundle = getIntent().getExtras();
        db = new DBManager(this).open();

        //animations run whenever text changes
        in = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_fast);
        out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right_fast);

        tvChoice.setInAnimation(in);
        tvChoice.setOutAnimation(out);

        tvChoice.setText(getResources().getString(R.string.make_a_choice));

        listName = bundle.getString(BundleConstants.LIST_NAME);

        //sets title of activity to list name
        setTitle(listName);

        items = db.getItemsFromList(listName);

        setAdapterAndListener(items);

        listenerOperation();

    }//end onCreate

    @Override
    public void listenerOperation() {

        btnMakeChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //picker list operation
                if(bundle.getInt(BundleConstants.IS_DRAWER) == 0){

                    tvChoice.setText(getRandomFromList(items).getItem());
                }
                //drawer list operation
                else{

                    //while the list still has values
                    //otherwise, give option to reload list
                    if(items.size() > 0){

                        ItemChoice randomChoice = getRandomFromList(items);
                        tvChoice.setText(randomChoice.getItem());

                        items.remove(randomChoice);

                        //reloads adapter and listener with item out of list
                        setAdapterAndListener(items);

                    }
                    else{

                        AlertDialog.Builder dialog = new AlertDialog.Builder(RandomPickActivity.this);

                        dialog.setTitle(getResources().getString(R.string.empty_list));

                        dialog
                                .setMessage(getResources().getString(R.string.reload_list))
                                .setCancelable(true)
                                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        setAdapterAndListener(db.getItemsFromList(listName));
                                        tvChoice.setText(getResources().getString(R.string.make_a_choice));

                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = dialog.create();

                        alertDialog.show();
                    }
                }//end else
            }
        });

    }

    private void setAdapterAndListener(ArrayList<ItemChoice> listItems){
        items = listItems;
        adapter = new RandomPickAdapter(this, listItems);
        listView.setAdapter(adapter);

    }

    private ItemChoice getRandomFromList(ArrayList<ItemChoice> items){

        return new RandomPick().getRandomFromList(items);
    }


}
