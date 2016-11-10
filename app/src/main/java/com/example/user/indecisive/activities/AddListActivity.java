package com.example.user.indecisive.activities;

import android.app.LauncherActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;
import java.util.Arrays;

public class AddListActivity extends AppCompatActivity {

    final String TAG = AddListActivity.class.getSimpleName();

    Button addListButton;
    Switch drawerSwitch;
    EditText listName;
    EditText listItems;

    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        addListButton = (Button) findViewById(R.id.buttonAddList);
        drawerSwitch = (Switch) findViewById(R.id.drawerSwitch);
        listName = (EditText) findViewById(R.id.etListName);
        listItems = (EditText) findViewById(R.id.etListItems);

        db = new DBManager(this).open();

        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(allFieldsFilled()){

                    String list = listName.getText().toString();
                    ArrayList<String> listOfItems = toList(listItems.getText().toString());
                    int isDrawer = 0;

                    if(drawerSwitch.isChecked()){
                        isDrawer = 1;
                    }
                    else{
                        isDrawer = 0;
                    }

                    db.insertList(list, listOfItems, isDrawer);

                    Cursor c = db.getAllItems();

                    while(c.moveToNext()){
                        Log.d(TAG, "-----------------");
                        Log.d(TAG, "ID: " + c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_ROWID)));
                        Log.d(TAG, "Item: " + c.getString(c.getColumnIndexOrThrow(DBManager.KEY_ITEM)));
                        Log.d(TAG, "Table: " + c.getString(c.getColumnIndexOrThrow(DBManager.KEY_LIST)));
                        Log.d(TAG, "Drawer: " + c.getInt(c.getColumnIndexOrThrow(DBManager.KEY_DRAWER)));
                    }


                }
            }
        });


    }

    public ArrayList<String> toList(String value){

        String[] tempList = value.split("\n");

        return new ArrayList<String>(Arrays.asList(tempList));
    }

    private boolean allFieldsFilled(){

        boolean result = false;

        if(listName.getText().toString().length() > 0 && listItems.getText().toString().length() >0){

            result = true;
        }
        else{
            result = false;
        }

        return result;
    }

}
