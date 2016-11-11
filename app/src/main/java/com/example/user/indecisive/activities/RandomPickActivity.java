package com.example.user.indecisive.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.adapters.RandomPickAdapter;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.db.DBManager;

import java.util.ArrayList;

public class RandomPickActivity extends AppCompatActivity {

    ListView listView;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_pick);

        listView = (ListView) findViewById(R.id.randomActivityListView);

        db = new DBManager(this).open();

        Bundle bundle = getIntent().getExtras();
        String listName = bundle.getString("LIST_NAME");

        ArrayList<ItemChoice> items = db.getListItems(listName);

        RandomPickAdapter adapter = new RandomPickAdapter(this, 0, items);

        listView.setAdapter(adapter);
    }
}
