package com.realizertech.shivmudra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.model.Location;
import com.realizertech.shivmudra.model.SocietyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class SocietyNameAdapter extends BaseAdapter implements Filterable{

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    List<Location>societyNames;
    private List<Location> suggestions = new ArrayList<>();
    private Filter filter = new CustomFilter();

    public SocietyNameAdapter(List<Location> societyNames, Context context){
        this.context = context;
        this.societyNames = societyNames;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        this.convertView = convertView;

        if (convertView == null) {
            this.convertView = layoutInflater.inflate(R.layout.society_list_item_layout, null);

            holder = new ViewHolder();
            holder.name = (TextView) this.convertView.findViewById(R.id.txtsocietyName);

            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }

        holder.name.setText(suggestions.get(position).getName()+","+suggestions.get(position).getAddress());

        return this.convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class CustomFilter extends Filter {

/*        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((SocietyModel) resultValue).getSocietyName();
            return str;
        }*/

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();

            if (societyNames != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < societyNames.size(); i++) {
                    if (societyNames.get(i).getName().toLowerCase().contains(constraint)) { // Compare item in original list if it contains constraints.
                        suggestions.add(societyNames.get(i)); // If TRUE add item in Suggestions.
                    }
                }
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    static class ViewHolder{
        TextView name;
    }
}


