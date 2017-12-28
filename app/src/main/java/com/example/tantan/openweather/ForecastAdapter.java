package com.example.tantan.openweather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanTan on 10/8/17.
 */

public class ForecastAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<Forecast_Weather> mList = new ArrayList<Forecast_Weather>();

    public ForecastAdapter(Activity mActivity, List<Forecast_Weather> mList) {
        this.mActivity = mActivity;
        this.mList.addAll(mList);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater vi = LayoutInflater.from(mActivity);
        view = vi.inflate(R.layout.forecast_item, null);
        TextView tv_date = (TextView) view.findViewById(R.id.tv_forcast_date);
        tv_date.setText(mList.get(i).getDt());
        TextView tv_tmin = (TextView) view.findViewById(R.id.tv_forcast_tempmin);
        tv_tmin.setText(mList.get(i).getTmin()+" \u2103");
        TextView tv_tmax = (TextView) view.findViewById(R.id.tv_forcast_tempmax);
        tv_tmax.setText(mList.get(i).getTmax()+" \u2103");
        ImageView iv_icon = (ImageView)view.findViewById(R.id.iv_forecast_icon);
        String icon = mList.get(i).getIcon();
        Ion.with(iv_icon)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .load("http://openweathermap.org/img/w/"+icon+".png");
        return view;
    }
}
