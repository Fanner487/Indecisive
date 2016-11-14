package com.example.user.indecisive.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.RandomPick;
import com.example.user.indecisive.adapters.RandomPickAdapter;
import com.example.user.indecisive.business.ItemChoice;
import com.example.user.indecisive.db.DBManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RandomPickActivity extends AppCompatActivity {

    ListView listView;
    Button buttonMakeChoice;
    TextView choiceTextView;
    ArrayList<ItemChoice> items = null;

    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_pick);

        listView = (ListView) findViewById(R.id.randomActivityListView);
        buttonMakeChoice = (Button) findViewById(R.id.buttonMakeChoice);
        choiceTextView = (TextView) findViewById(R.id.choiceTextView);

        db = new DBManager(this).open();

        Bundle bundle = getIntent().getExtras();
        String listName = bundle.getString("LIST_NAME");

        items = db.getListItems(listName);

        RandomPickAdapter adapter = new RandomPickAdapter(this, 0, items);

        listView.setAdapter(adapter);

        buttonMakeChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String randomChoice = new RandomPick().getRandomFromList(items);

                choiceTextView.setText("Choice: " + randomChoice);
            }
        });
    }
}
