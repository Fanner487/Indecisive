package com.example.user.indecisive.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.user.indecisive.R;
import com.example.user.indecisive.adapters.SearchListAdapter;
import com.example.user.indecisive.business.ListChoice;
import com.example.user.indecisive.db.DBManager;
import com.example.user.indecisive.interfaces.ListenerOperation;

import java.util.ArrayList;
import java.util.List;
/*
*
*   User makes search for a list
* */

public class SearchActivity extends AppCompatActivity implements ListenerOperation{

    final String TAG = SearchActivity.class.getSimpleName();

    ListView listView;
    ListAdapter adapter;
    ArrayList<ListChoice> arrayLists;
    DBManager db;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new DBManager(this).open();
        listView = (ListView) findViewById(R.id.activity_search_list_view);

        arrayLists = db.getListNames();

        setAdapterAndListener(arrayLists);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);

        //opens search box
        item.expandActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        //makes searchView autofocus and open the keyboard
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setQueryHint(getResources().getString(R.string.app_search));
        searchView.requestFocusFromTouch();
        listenerOperation();

        return super.onCreateOptionsMenu(menu);
    }

    //changes list and sets new adapter when query changed
    public void textChangeOperation(String newText){

        if(newText != null && !newText.isEmpty()){

            final List<ListChoice> listFound = new ArrayList<>();

            for(ListChoice item:arrayLists){

                //adds list name to new list array if it contains search text
                if(item.getListName().toLowerCase().contains(newText.toLowerCase())){
                    listFound.add(item);

                }

                setAdapterAndListener(listFound);
            }

        }
        else{

            setAdapterAndListener(arrayLists);
        }
    }

    @Override
    public void listenerOperation() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                textChangeOperation(newText);

                return false;
            }
        });
    }

    public void setAdapterAndListener(final List<ListChoice> listItems){

        adapter = new SearchListAdapter(SearchActivity.this, (ArrayList<ListChoice>) listItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                MainActivity.startActivityWithBundle(SearchActivity.this, RandomPickActivity.class,
                        listItems.get(position).getListName(), listItems.get(position).getIsDrawer(), false);

                finish();

            }
        });
    }
}
