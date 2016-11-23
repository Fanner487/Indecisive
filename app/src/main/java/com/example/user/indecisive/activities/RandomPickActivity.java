package com.example.user.indecisive.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.RandomPick;
import com.example.user.indecisive.adapters.RandomPickAdapter;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.constants.BundleConstants;
import com.example.user.indecisive.db.DBManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RandomPickActivity extends AppCompatActivity {

    final String TAG = RandomPickActivity.class.getSimpleName();

    ListView listView;
    Button buttonMakeChoice;
    TextView choiceTextView;
    ArrayList<ItemChoice> items = null;
    RandomPickAdapter adapter;

    DBManager db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_pick);

        listView = (ListView) findViewById(R.id.randomActivityListView);
        buttonMakeChoice = (Button) findViewById(R.id.buttonMakeChoice);
        choiceTextView = (TextView) findViewById(R.id.choiceTextView);

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

                    choiceTextView.setText("Choice: " + getRandomFromList(items).getItem());
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
                        choiceTextView.setText("Choice: " + randomChoice.getItem());

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
