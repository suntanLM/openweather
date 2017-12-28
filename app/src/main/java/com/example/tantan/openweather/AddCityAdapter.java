package com.example.tantan.openweather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanTan on 10/9/17.
 */

public class AddCityAdapter extends BaseAdapter  implements Filterable {

    private Activity mActivity;
    private List<City> mList = new ArrayList<City>();
    private CityFilter cityFilter;
    private List<City> filteredList = new ArrayList<City>();

    public AddCityAdapter(Activity mActivity, List<City> mList) {
        this.mActivity = mActivity;
        this.mList.addAll(mList);
        this.filteredList.addAll(mList);
        getFilter();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        convertView = inflater.inflate(R.layout.add_city_item,null);
        TextView name = (TextView) convertView.findViewById(R.id.tv_acname);
        name.setText(filteredList.get(position).getName()+", "+filteredList.get(position).getCountry());
        TextView id = (TextView) convertView.findViewById(R.id.tv_acid);
        id.setText(filteredList.get(position).getId());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (cityFilter == null) {
            cityFilter = new CityFilter();
        }

        return cityFilter;
    }

    public City getCity(int pos)
    {
        return filteredList.get(pos);
    }

    private class CityFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<City> tempList = new ArrayList<City>();

                for (City ct : mList) {
                    if (ct.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(ct);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mList.size();
                filterResults.values = mList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<City>) results.values;
            notifyDataSetChanged();
        }


    }


}



