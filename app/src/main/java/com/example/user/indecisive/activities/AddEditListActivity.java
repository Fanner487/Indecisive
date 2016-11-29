package com.example.user.indecisive.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.example.user.indecisive.interfaces.ListenerOperation;

import java.util.ArrayList;
import java.util.Arrays;

/*
* Add a new list or update and existing one
*
* */
public class AddEditListActivity extends AppCompatActivity implements ListenerOperation{

//    todo: change the toast mechanism for update or create list
    final String TAG = AddEditListActivity.class.getSimpleName();

    Button addEditListButton;
    Button deleteListButton;
    Switch drawerSwitch;
    EditText etListName;
    EditText etListItems;

    DBManager db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_list);

        addEditListButton = (Button) findViewById(R.id.activity_add_edit_list_btn_add);
        deleteListButton = (Button) findViewById(R.id.activity_add_edit_list_btn_delete);
        drawerSwitch = (Switch) findViewById(R.id.activity_add_edit_list_switch_drawer);
        etListName = (EditText) findViewById(R.id.activity_add_edit_list_et_list_name);
        etListItems = (EditText) findViewById(R.id.activity_add_edit_list_et_list_items);

        db = new DBManager(this).open();
        bundle = getIntent().getExtras();

        //hides button when in add list mode
        deleteListButton.setVisibility(View.GONE);

        listenerOperation();

    }

    private String itemsToString(ArrayList<ItemChoice> listItems) {

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

    //checks if list name and values field null
    private boolean allFieldsFilled(){

        if(etListName.getText().toString().length() > 0 && etListItems.getText().toString().length() > 0){

            return true;
        }
        else{
            return false;
        }

    }

    private void createListAndStartActivity(){

        if(allFieldsFilled()){

            String listName = etListName.getText().toString();
            ArrayList<String> listOfItems = toList(etListItems.getText().toString());
            int isDrawer;

            if(drawerSwitch.isChecked()){
                isDrawer = 1;
            }
            else{
                isDrawer = 0;
            }

            if(db.insertList(listName, listOfItems, isDrawer)){

                //toasts if list has been updated or created
                if(bundle.getBoolean(BundleConstants.IS_EDIT_LIST)){

                    Toast.makeText(AddEditListActivity.this, R.string.list_updated, Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(AddEditListActivity.this, R.string.list_created, Toast.LENGTH_SHORT).show();

                }


                MainActivity.startActivityWithBundle(AddEditListActivity.this, RandomPickActivity.class,
                        listName, isDrawer, false);

                finish();

            }
            else{

                Toast.makeText(AddEditListActivity.this, R.string.list_exists, Toast.LENGTH_SHORT).show();
            }

        }//end allFieldsFilled
        else{

            Toast.makeText(AddEditListActivity.this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
        }
    }//end createListAndStartActivity

    @Override
    public void listenerOperation() {

        //if list coming in is to be updated, set the EditText fields to the
        //data and change button implementation and add delete list button
        if(bundle.getBoolean(BundleConstants.IS_EDIT_LIST)){

            etListName.setText(bundle.getString(BundleConstants.LIST_NAME));

            if(bundle.getInt(BundleConstants.IS_DRAWER) == 0){
                drawerSwitch.setChecked(false);
            }
            else{
                drawerSwitch.setChecked(true);
            }


            String listItemsString = itemsToString(db.getItemsFromList(bundle.getString(BundleConstants.LIST_NAME)));

            etListItems.setText(listItemsString);

            addEditListButton.setText(R.string.update_list);
            deleteListButton.setVisibility(View.VISIBLE);

            deleteListOperation();

        }//end if IS_EDIT_LIST

        addOrEditListOperation();

    }

    private void addOrEditListOperation() {

        addEditListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if update version of activity, delete list and create it again
                if(bundle.getBoolean(BundleConstants.IS_EDIT_LIST)){

                    if(db.deleteList(bundle.getString(BundleConstants.LIST_NAME)) != -1){

                        createListAndStartActivity();
                    }
                }
                else{

                    createListAndStartActivity();
                }

            }
        });
    }


    private void deleteListOperation() {

        //prompts user if they want to delete list
        deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(AddEditListActivity.this);

                dialog.setTitle(R.string.delete_list_popup);

                dialog
                        .setMessage(R.string.delete_list_prompt)
                        .setCancelable(true)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //delete list
                                if(db.deleteList(bundle.getString(BundleConstants.LIST_NAME)) != -1){

                                    Toast.makeText(AddEditListActivity.this, getResources().getString(R.string.deleted) + " " + bundle.get(BundleConstants.LIST_NAME), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{

                                    Toast.makeText(AddEditListActivity.this, getResources().getString(R.string.error_deleting_list), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = dialog.create();

                alertDialog.show();

            }
        });//end button listener

    }//end delete list operation


    //converts long string and converts them to ItemChoice objects
    public ArrayList<String> toList(String value){

        String[] tempList = value.split("\n");

        return new ArrayList<>(Arrays.asList(tempList));
    }

}
