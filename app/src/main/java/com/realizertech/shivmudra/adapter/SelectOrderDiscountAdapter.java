package com.realizertech.shivmudra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.model.Discount;
import com.realizertech.shivmudra.model.LocationDeliverySlabs;

import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class SelectOrderDiscountAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<Discount> timeSlot;

    public SelectOrderDiscountAdapter(List<Discount> timeSlot, Context context){
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
            this.convertView = layoutInflater.inflate(R.layout.applicable_dicount_item_layout, null);

            holder = new ViewHolder();
            holder.name = (TextView) this.convertView.findViewById(R.id.txtname);
            holder.percent = (TextView) this.convertView.findViewById(R.id.txtpercentage);
            holder.value = (TextView) this.convertView.findViewById(R.id.txtvalueactual);
            holder.count = (TextView) this.convertView.findViewById(R.id.txtcount);
            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }
        holder.name.setText(timeSlot.get(position).getDiscountType());
        holder.percent.setText(timeSlot.get(position).getDiscountPercentage()+"%");
        holder.value.setText("₹"+timeSlot.get(position).getDiscountValue());
        holder.count.setText(""+timeSlot.get(position).getAvailableDiscountCnt());
        return this.convertView;
    }
    static class ViewHolder{

        TextView name,percent,value,count;
    }
}


