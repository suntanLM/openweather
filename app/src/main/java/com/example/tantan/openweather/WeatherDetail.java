package com.example.tantan.openweather;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WeatherDetail extends AppCompatActivity {
    private String ID;
    private ImageView ivicon;
    private TextView tvtemp,tvname,tvdes,tvpres,tvhumid,tvspeed;
    private List<Forecast_Weather> forecastList = new ArrayList<Forecast_Weather>();
    private ListView listView;
    private ForecastAdapter mAdapter;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        getdatafromintent();
        getJSONdata();
        getJSONforecastdata();
        initview();
    }

    private void getJSONdata() {
        Ion.with(this)
                .load("http://api.openweathermap.org/data/2.5/weather?id="+ID+"&appid=c0269134cfe1d389366cc3aa47f54818&units=metric&lang=vi")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if(result!= null && !result.equals("")){
                            JSONObject jsonObject = null;
                            Log.d("RESULT",result);
                            try{
                                jsonObject=new JSONObject(result);

                                JSONArray weatherobj = jsonObject.getJSONArray("weather");
                                JSONObject mainobj = jsonObject.getJSONObject("main");
                                JSONObject windobj=jsonObject.getJSONObject("wind");
                                String icon = weatherobj.getJSONObject(0).getString("icon");
                                String temp = mainobj.getString("temp");
                                String description = weatherobj.getJSONObject(0).getString("description");
                                String pressure = mainobj.getString("pressure");
                                String humidity = mainobj.getString("humidity");
                                String speed = windobj.getString("speed");
                                String name = jsonObject.getString("name");

                                Ion.with(ivicon)
                                        .placeholder(R.mipmap.ic_launcher)
                                        .error(R.mipmap.ic_launcher)
                                        .load("http://openweathermap.org/img/w/"+icon+".png");
                                tvtemp.setText(temp+" \u2103");
                                tvname.setText(name);
                                tvdes.setText(description);
                                tvpres.setText(pressure+" hpa");
                                tvhumid.setText(humidity+"%");
                                tvspeed.setText(speed+" m/s");

                                switch (icon)
                                {
                                    case "01d":
                                        mLayout.setBackground(getDrawable(R.drawable.d01));
                                        break;
                                    case "01n":
                                        mLayout.setBackground(getDrawable(R.drawable.d01));
                                        break;
                                    case "02d":
                                        mLayout.setBackground(getDrawable(R.drawable.d02));
                                        break;
                                    case "02n":
                                        mLayout.setBackground(getDrawable(R.drawable.d02));
                                        break;
                                    case "03d":
                                        mLayout.setBackground(getDrawable(R.drawable.d03));
                                        break;
                                    case "03n":
                                        mLayout.setBackground(getDrawable(R.drawable.d03));
                                        break;
                                    case "04d":
                                        mLayout.setBackground(getDrawable(R.drawable.d04));
                                        break;
                                    case "04n":
                                        mLayout.setBackground(getDrawable(R.drawable.d04));
                                        break;
                                    case "09d":
                                        mLayout.setBackground(getDrawable(R.drawable.d09));
                                        break;
                                    case "09n":
                                        mLayout.setBackground(getDrawable(R.drawable.d09));
                                        break;
                                    case "10d":
                                        mLayout.setBackground(getDrawable(R.drawable.d09));
                                        break;
                                    case "10n":
                                        mLayout.setBackground(getDrawable(R.drawable.d09));
                                        break;
                                    case "11d":
                                        mLayout.setBackground(getDrawable(R.drawable.d11));
                                        break;
                                    case "11n":
                                        mLayout.setBackground(getDrawable(R.drawable.d11));
                                        break;
                                    case "13d":
                                        mLayout.setBackground(getDrawable(R.drawable.d13));
                                        break;
                                    case "13n":
                                        mLayout.setBackground(getDrawable(R.drawable.d13));
                                        break;
                                    case "50d":
                                        mLayout.setBackground(getDrawable(R.drawable.d50));
                                        break;
                                    case "50n":
                                        mLayout.setBackground(getDrawable(R.drawable.d50));
                                        break;
                                    default:
                                        mLayout.setBackground(getDrawable(R.drawable.johncena));
                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void getJSONforecastdata()
    {
        Ion.with(this)
                .load("http://api.openweathermap.org/data/2.5/forecast/daily?id="+ID+"&appid=c0269134cfe1d389366cc3aa47f54818&units=metric")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        JSONObject jsonObject = null;
                        Forecast_Weather mWeather;
                        try {
                            Log.d("RESULT_F", result);
                            jsonObject=new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String date = dateparser(object.getLong("dt"));
                                String tmin = object.getJSONObject("temp").getString("min");
                                String tmax = object.getJSONObject("temp").getString("max");
                                String icon = object.getJSONArray("weather").getJSONObject(0).getString("icon");
                                mWeather = new Forecast_Weather(date,icon,tmin,tmax);
                                forecastList.add(mWeather);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Log.e("Adapter",String.valueOf(String.valueOf(forecastList.size())));
                        mAdapter = new ForecastAdapter(WeatherDetail.this,forecastList);
                        listView.setAdapter(mAdapter);
                        Log.e("Adapter",String.valueOf(mAdapter.getCount())+String.valueOf(forecastList.size()));
                    }

                });
    }

    private void getdatafromintent() {
        ID=getIntent().getStringExtra("ID");
    }

    private void initview() {
        ivicon = (ImageView)findViewById(R.id.iv_icon);
        tvtemp = (TextView)findViewById(R.id.tv_temp);
        tvname = (TextView)findViewById(R.id.tv_name);
        tvdes = (TextView)findViewById(R.id.tv_des);
        tvpres = (TextView)findViewById(R.id.tv_pressure);
        tvhumid = (TextView)findViewById(R.id.tv_humidity);
        tvspeed = (TextView)findViewById(R.id.tv_windspeed);
        listView = (ListView) findViewById(R.id.lv_forecast);
        mLayout = (LinearLayout) findViewById(R.id.layout_weatherdetail);
    }

    private String dateparser(Long d)
    {
        Date dt = new Date(d*1000);
        return String.valueOf(dt.getDate());
    }
}
