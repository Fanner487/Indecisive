package com.example.user.indecisive.activities;

import android.app.LauncherActivity;
import android.content.Intent;
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

        //TODO: if update/new list for button
        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(allFieldsFilled()){

                    String list = listName.getText().toString();
                    ArrayList<String> listOfItems = toList(listItems.getText().toString());
                    int isDrawer;

                    if(drawerSwitch.isChecked()){
                        isDrawer = 1;
                    }
                    else{
                        isDrawer = 0;
                    }


                    if(db.insertList(list, listOfItems, isDrawer)){
                        Toast.makeText(AddListActivity.this, "List Created", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(AddListActivity.this, RandomPickActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("LIST_NAME", list);
                        bundle.putInt("IS_DRAWER", isDrawer);
                        i.putExtras(bundle);
                        startActivity(i);

                        clearFields();
                        finish();
                    }
                    else{
                        Toast.makeText(AddListActivity.this, "List already exists", Toast.LENGTH_SHORT).show();
                    }






                }
            }
        });


    }

    private void clearFields() {

        listName.setText("");
        listItems.setText("");
    }

    public ArrayList<String> toList(String value){

        //todo:if any null elements

        String[] tempList = value.split("\n");

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(tempList));

        for(int i = 0 ; i < arrayList.size(); i++){

            //todo: this needs to be changed to take out blank items
            if(arrayList.get(i).equals("") || arrayList.get(i).equals(null) || arrayList.get(i).equals("\n")){
                arrayList.remove(i);
            }
        }

        return new ArrayList<String>(Arrays.asList(tempList));
    }

    private boolean allFieldsFilled(){

        boolean result = false;

        if(listName.getText().toString().length() > 0 && listItems.getText().toString().length() > 0){

            result = true;
        }
        else{
            result = false;
        }

        return result;
    }

}
