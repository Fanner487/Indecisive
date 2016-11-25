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

import java.util.ArrayList;

public class RandomPickActivity extends AppCompatActivity {

    final String TAG = RandomPickActivity.class.getSimpleName();

    ListView listView;
    Button buttonMakeChoice;
    TextSwitcher choiceTextView = null;
    ArrayList<ItemChoice> items = null;
    RandomPickAdapter adapter;

    Animation in;
    Animation out;

    DBManager db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_pick);

        listView = (ListView) findViewById(R.id.randomActivityListView);
        buttonMakeChoice = (Button) findViewById(R.id.buttonMakeChoice);
        choiceTextView = (TextSwitcher) findViewById(R.id.choiceTextView);

        in = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_fast);
        out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right_fast);

        choiceTextView.setInAnimation(in);
        choiceTextView.setOutAnimation(out);

        choiceTextView.setText(getResources().getString(R.string.make_a_choice));

        db = new DBManager(this).open();
        bundle = getIntent().getExtras();

        final String listName = bundle.getString(BundleConstants.LIST_NAME);

        //sets title of activity to list name
        setTitle(listName);

        items = db.getListItems(listName);

        setAdapterAndListener(items);

        //picker list operation
        if(bundle.getInt(BundleConstants.IS_DRAWER) == 0){

            buttonMakeChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    choiceTextView.setText(getRandomFromList(items).getItem());
                }
            });
        }
        //drawer list operation
        else{

            buttonMakeChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //while the list still has values
                    //otherwise, give option to reload list
                    if(items.size() > 0){

                        ItemChoice randomChoice = getRandomFromList(items);
                        choiceTextView.setText(randomChoice.getItem());

                        items.remove(randomChoice);

                        //reloads adapter and listener with item out of list
                        setAdapterAndListener(items);
                    }
                    else{

                        AlertDialog.Builder dialog = new AlertDialog.Builder(RandomPickActivity.this);

                        dialog.setTitle("List is Empty");

                        dialog
                                .setMessage("Would you like to reload the list?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        setAdapterAndListener(db.getListItems(listName));
                                        choiceTextView.setText(getResources().getString(R.string.make_a_choice));
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        // do I need this?

                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = dialog.create();

                        alertDialog.show();
                    }


                }//end on click

            });//end button make choice listener

        }//end else

    }//end onCreate


    private void setAdapterAndListener(ArrayList<ItemChoice> listItems){
        items = listItems;
        adapter = new RandomPickAdapter(this, 0, listItems);
        listView.setAdapter(adapter);

    }

    private ItemChoice getRandomFromList(ArrayList<ItemChoice> items){

        return new RandomPick().getRandomFromList(items);
    }
}
