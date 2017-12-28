package com.example.tantan.openweather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanTan on 10/1/17.
 */

public class WeatherAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<String> mList = new ArrayList<String>();

    public WeatherAdapter(Activity mActivity, List<String> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
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
        view = vi.inflate(R.layout.listview_item, null);
        TextView tvName = (TextView) view.findViewById(R.id.tv_lvitem);
        tvName.setText(mList.get(i));
        return view;
    }
}