package com.example.user.indecisive.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.user.indecisive.R;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.constants.BundleConstants;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;
import java.util.Arrays;

public class AddListActivity extends AppCompatActivity {

    final String TAG = AddListActivity.class.getSimpleName();

    Button addEditListButton;
    Button deleteListButton;
    Switch drawerSwitch;
    EditText listName;
    EditText listItems;

    DBManager db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        addEditListButton = (Button) findViewById(R.id.buttonAddEditList);
        deleteListButton = (Button) findViewById(R.id.buttonDeleteList);
        drawerSwitch = (Switch) findViewById(R.id.drawerSwitch);
        listName = (EditText) findViewById(R.id.etListName);
        listItems = (EditText) findViewById(R.id.etListItems);

        db = new DBManager(this).open();
        bundle = getIntent().getExtras();

        //hides button when in add list mode
        deleteListButton.setVisibility(View.GONE);

        //if list coming in is to be edited, set the EditText fields to the
        //data and change button implementation and add delete list button
        if(bundle.getBoolean(BundleConstants.IS_EDIT_LIST)){


            listName.setText(bundle.getString(BundleConstants.LIST_NAME));

            if(bundle.getInt(BundleConstants.IS_DRAWER) == 0){
                drawerSwitch.setChecked(false);
            }
            else{
                drawerSwitch.setChecked(true);
            }


            String listItemsString = changeToString(db.getListItems(bundle.getString(BundleConstants.LIST_NAME)));

            listItems.setText(listItemsString);

            addEditListButton.setText("Update");
            deleteListButton.setVisibility(View.VISIBLE);


            //add alertdialog to deleteListButton
        }



        //TODO: if update/new list for button and change text
        addEditListButton.setOnClickListener(new View.OnClickListener() {
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

    private String changeToString(ArrayList<ItemChoice> listItems) {

        StringBuilder returnString = new StringBuilder();

        for(int i = 0; i < listItems.size(); i++){

            returnString.append(listItems.get(i).getItem());

            //will not add new line character to end of list
            if(i != listItems.size() - 1){

                returnString.append("\n");
            }
        }

        return returnString.toString();
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
