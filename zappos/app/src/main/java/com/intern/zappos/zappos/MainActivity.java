package com.intern.zappos.zappos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{
    private TextView welcometext;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       ActionBar br = getSupportActionBar();
       br.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#45a4af")));
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search) .getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Product");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Product pr = new Product();
        welcometext = (TextView) findViewById(R.id.welcome);
        welcometext.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ((Product)getFragmentManager().findFragmentById(R.id.fragment_place)).showText(query);
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
