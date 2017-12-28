package com.example.tantan.openweather;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    List<City> cityList = new ArrayList<City>();
    List<String> nameList=new ArrayList<>();
    private ListView listView;
    private WeatherAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHelper(this);
        initview();
        initList();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int x = info.position;
        Log.e("Pos",String.valueOf(x));
        String del=cityList.get(x).getId();
        db.deleteCity(del);
        cityList.remove(x);
        nameList.remove(x);
        adapter.notifyDataSetChanged();

        return super.onContextItemSelected(item);
    }

    private void initList()
    {
        cityList.addAll(db.getCity());
        for(City i : cityList)
        {
            nameList.add(i.getName());
        }
        adapter.notifyDataSetChanged();
    }

    private void initview() {
        listView = (ListView) findViewById(R.id.lv_listview);
        adapter = new WeatherAdapter(this,nameList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,WeatherDetail.class);
                String temp = cityList.get(i).getId();
                intent.putExtra("ID",temp);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddCity.class);
                startActivityForResult(intent,111);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
            if(requestCode==111)
            {
                String id = data.getStringExtra("ID");
                String name = data.getStringExtra("NAME");
                String country = data.getStringExtra("COUNTRY");
                db.insertCity(id,name,country);
                cityList.clear();
                nameList.clear();
                initList();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_navi_drawer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_horo) {
            Intent intent = new Intent(MainActivity.this,HoroActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.nav_info)
        {
            Intent intent = new Intent(MainActivity.this,Infomation.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
