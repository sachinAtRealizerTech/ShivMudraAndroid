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

import com.realizertech.shivmudra.ModifyLastOrderActivity;
import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.VegitableListActivity;
import com.realizertech.shivmudra.model.OrderItems;
import com.realizertech.shivmudra.model.VegetableModel;

import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class OrderedListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<OrderItems> vegies;
    List<String> unitTypes;

    public OrderedListAdapter(List<OrderItems> vegies,Context context){
        this.context = context;
        this.vegies = vegies;
        this.unitTypes = unitTypes;
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
            this.convertView = layoutInflater.inflate(R.layout.vegies_orderedlist_item_layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) this.convertView.findViewById(R.id.txt_vegiename);
            holder.ourPrice = (TextView) this.convertView.findViewById(R.id.txt_ourprice);
            holder.value = (TextView) this.convertView.findViewById(R.id.txtquant);
            holder.total = (TextView) this.convertView.findViewById(R.id.txttotal);
            holder.vegiImage = (ImageView) this.convertView.findViewById(R.id.img_vegieImage);
            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }

        if(vegies.get(position).getQuantity() < 1 && vegies.get(position).getQuantity() > 0){
           String display = Double.toString(vegies.get(position).getQuantity() * 1000).split("\\.")[0];
            holder.value.setText(display+" GM");
        }
        else {
            holder.value.setText(vegies.get(position).getQuantity()+" KG");
        }
        if(vegies.get(position).getMeasurementType().equalsIgnoreCase("QTY")){
           String display = Double.toString(vegies.get(position).getQuantity()).split("\\.")[0];
            holder.value.setText(display+" QTY");
        }

        holder.name.setText(vegies.get(position).getItemName());
        holder.ourPrice.setText("₹"+vegies.get(position).getPerUnitCost()+"/kg");

        double totalprice = vegies.get(position).getCostPerItem();
        holder.total.setText("₹"+totalprice);
        holder.vegiImage.setImageResource(context.getResources().getIdentifier(
                vegies.get(position).getItemKey(), "drawable", "com.realizertech.shivmudra"));
        return this.convertView;
    }
    static class ViewHolder{

        ImageView vegiImage;
        TextView name,ourPrice,total,value;
    }
}


