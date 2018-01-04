package com.realizertech.shivmudra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.model.LocationDeliverySlabs;
import com.realizertech.shivmudra.model.OrderDateModel;

import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class SelectOrderDateGridAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<LocationDeliverySlabs> timeSlot;

    public SelectOrderDateGridAdapter(List<LocationDeliverySlabs> timeSlot, Context context){
        this.context = context;
        this.timeSlot = timeSlot;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return timeSlot.size();
    }

    @Override
    public Object getItem(int position) {
        return timeSlot.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final ViewHolder holder;
        this.convertView = convertView;

        if (convertView == null) {
            this.convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);

            holder = new ViewHolder();
            holder.slot = (TextView) this.convertView.findViewById(R.id.txt_slot);
            holder.day = (TextView) this.convertView.findViewById(R.id.txt_day);
            holder.date = (TextView) this.convertView.findViewById(R.id.txt_date);

            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }
        holder.slot.setText(timeSlot.get(position).getSlabTiming());
        holder.day.setText(timeSlot.get(position).getWeekDay());
        holder.date.setText(timeSlot.get(position).getShowDate());


        return this.convertView;
    }
    static class ViewHolder{

        TextView slot,day, date;
    }
}


