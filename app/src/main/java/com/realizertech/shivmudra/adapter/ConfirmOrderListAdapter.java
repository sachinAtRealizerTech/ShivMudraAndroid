package com.realizertech.shivmudra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.VegitableListActivity;
import com.realizertech.shivmudra.model.VegetableModel;

import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class ConfirmOrderListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<VegetableModel> vegies;

    public ConfirmOrderListAdapter(List<VegetableModel> vegies, Context context){
        this.context = context;
        this.vegies = vegies;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return vegies.size();
    }

    @Override
    public Object getItem(int position) {
        return vegies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        this.convertView = convertView;

        if (convertView == null) {
            this.convertView = layoutInflater.inflate(R.layout.confirm_order_list_item_layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) this.convertView.findViewById(R.id.txtvegName);
            holder.price = (TextView) this.convertView.findViewById(R.id.txtvegprice);
            holder.quantity = (TextView) this.convertView.findViewById(R.id.txtquantity);
            holder.imgveg = (ImageView) this.convertView.findViewById(R.id.imgVeg);
            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }

        vegies.get(position).setUnitType(vegies.get(position).getMeasurementType());
        String display = Double.toString(vegies.get(position).getQuantity());
        if(vegies.get(position).getQuantity() < 1 && vegies.get(position).getQuantity() > 0){
            display = Double.toString(vegies.get(position).getQuantity() * 1000).split("\\.")[0];
            vegies.get(position).setUnitType("GM");
        }
        else {
            vegies.get(position).setUnitType("KG");
        }
         if(vegies.get(position).getMeasurementType().equalsIgnoreCase("QTY")){
            display = Double.toString(vegies.get(position).getQuantity()).split("\\.")[0];
        }
        vegies.get(position).setDisplayQuantity(display);

        holder.name.setText(vegies.get(position).getName());
        holder.price.setText("₹"+vegies.get(position).getTotalPrice());
        holder.quantity.setText(""+vegies.get(position).getDisplayQuantity()+" "+vegies.get(position).getUnitType());
        holder.imgveg.setImageResource(context.getResources().getIdentifier(
                vegies.get(position).getItemKey(), "drawable", "com.realizertech.shivmudra"));
        return this.convertView;
    }
    static class ViewHolder{

        TextView name,price,quantity;
        ImageView imgveg;
    }
}


