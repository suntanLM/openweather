package com.example.tantan.openweather;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<City> cityList = new ArrayList<City>();
    private AddCityAdapter adapter;
    private ListView listView;
    private SearchView searchView;
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        listView = (ListView)findViewById(R.id.lv_addcity);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (searchView.isShown()) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }

                Intent intent = new Intent();
                intent.putExtra("ID",adapter.getCity(position).getId());
                intent.putExtra("NAME",adapter.getCity(position).getName());
                intent.putExtra("COUNTRY",adapter.getCity(position).getCountry());
                setResult(20, intent);
                finish();
            }
        });
        loadData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void loadData() {
        String result = loadJSONFromAsset();
        try {
            JSONArray array = new JSONArray(result);
            JSONObject obj;
            City city;
            for(int i =0;i<array.length();i++)
            {
                obj=array.getJSONObject(i);
                String id = obj.getString("id");
                String name=obj.getString("name");
                String country=obj.getString("country");
                city=new City(id,name,country);
                cityList.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter=new AddCityAdapter(AddCity.this,cityList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(false);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("city.list.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
