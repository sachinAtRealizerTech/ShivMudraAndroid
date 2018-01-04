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
import com.realizertech.shivmudra.fragments.ViewLastOrderFragment;
import com.realizertech.shivmudra.model.VegetableModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class VegetableListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<VegetableModel> vegies;

    public VegetableListAdapter(List<VegetableModel> vegies,Context context){
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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return vegies.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        this.convertView = convertView;

        if (convertView == null) {
            this.convertView = layoutInflater.inflate(R.layout.vegies_list_item_layout, null);
            holder = new ViewHolder();
            holder.vegiImage = (ImageView) this.convertView.findViewById(R.id.img_vegieImage);
            holder.name = (TextView) this.convertView.findViewById(R.id.txt_vegiename);
            holder.desc = (TextView) this.convertView.findViewById(R.id.txt_vegdesc);
            holder.ourPrice = (TextView) this.convertView.findViewById(R.id.txt_ourprice);
            holder.MallPrice = (TextView) this.convertView.findViewById(R.id.txt_mallprice);
            holder.MarketPrice = (TextView) this.convertView.findViewById(R.id.txt_marketprice);
            holder.unittype = (TextView) this.convertView.findViewById(R.id.txtunitType);
           // holder.unitType = (Spinner) this.convertView.findViewById(R.id.spinnerunit);
            holder.value = (TextView) this.convertView.findViewById(R.id.txt_value);
            holder.total = (TextView) this.convertView.findViewById(R.id.txtotalprice);
            holder.plus = (Button) this.convertView.findViewById(R.id.btn_plus);
            holder.minus = (Button) this.convertView.findViewById(R.id.btn_minus);
            holder.defaultVal = (TextView) this.convertView.findViewById(R.id.txtDefaultVal);
            holder.plus.setTag(position);
            holder.minus.setTag(position);
            //holder.unitType.setTag(position);
            this.convertView.setTag(holder);
        }

        else {

            holder = (ViewHolder) this.convertView.getTag();
            holder.plus.setTag(position);
            holder.minus.setTag(position);
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
        holder.desc.setText(vegies.get(position).getItemDesc());
        holder.unittype.setText(vegies.get(position).getUnitType());
        if(vegies.get(position).getItemDefaultQty() < 1.0){
            holder.defaultVal.setText(((vegies.get(position).getItemDefaultQty()) * 1000)+" gm");
        }
        else {
            holder.defaultVal.setText(vegies.get(position).getItemDefaultQty()+" "+ vegies.get(position).getMeasurementType());
        }

        holder.ourPrice.setText("₹"+(vegies.get(position).getOurPrice() * vegies.get(position).getItemDefaultQty()));
        holder.MallPrice.setText("₹"+(vegies.get(position).getMallPrice() * vegies.get(position).getItemDefaultQty()));
        holder.MarketPrice.setText("₹"+(vegies.get(position).getMarketPrice() * vegies.get(position).getItemDefaultQty()));
        holder.value.setText(vegies.get(position).getDisplayQuantity());
        holder.vegiImage.setImageResource(context.getResources().getIdentifier(
                vegies.get(position).getItemKey(), "drawable", "com.realizertech.shivmudra"));
        double ourprice = vegies.get(position).getOurPrice();
        double mallprice = vegies.get(position).getMallPrice();
        double marketprice = vegies.get(position).getMarketPrice();
        double marketsave,mallsave=0;
        holder.total.setText(""+(vegies.get(position).getQuantity() * ourprice));
        mallsave = (mallprice * vegies.get(position).getQuantity()) - (vegies.get(position).getQuantity() * ourprice);
        marketsave = (marketprice * vegies.get(position).getQuantity()) - (vegies.get(position).getQuantity() * ourprice);
        vegies.get(position).setTotalMallSave(mallsave);
        vegies.get(position).setTotalMarketSave(marketsave);


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                double price = vegies.get(pos).getOurPrice();
                double totPrice = vegies.get(pos).getTotalPrice();
                double mallprice = vegies.get(pos).getMallPrice();
                double marketprice = vegies.get(pos).getMarketPrice();
                double marketsave = vegies.get(pos).getTotalMarketSave();
                double mallsave= vegies.get(pos).getTotalMallSave();
                double tempMarketsave = 0.0;
                double tempmallsave= 0.0;

                double quant = vegies.get(pos).getQuantity();
                quant = Double.valueOf(String.format("%.2f",(quant + vegies.get(pos).getIncrementBy())));
                vegies.get(pos).setQuantity(quant);

                tempmallsave =Double.valueOf(String.format("%.2f",((mallprice * vegies.get(pos).getQuantity()) - (vegies.get(pos).getQuantity() * price))));
                tempMarketsave =Double.valueOf(String.format("%.2f",((marketprice * vegies.get(pos).getQuantity()) - (vegies.get(pos).getQuantity() * price))));
                vegies.get(pos).setTotalPrice(Double.valueOf(String.format("%.2f",(vegies.get(pos).getQuantity() * price))));
                vegies.get(pos).setTotalMallSave(tempmallsave);
                vegies.get(pos).setTotalMarketSave(tempMarketsave);

                String display = Double.toString(vegies.get(pos).getQuantity());
                if(vegies.get(pos).getQuantity() < 1){
                    display = Double.toString(vegies.get(pos).getQuantity() * 1000).split("\\.")[0];
                    vegies.get(pos).setUnitType("GM");
                }
                else {
                    vegies.get(pos).setUnitType("KG");
                }

                 if(vegies.get(pos).getMeasurementType().equalsIgnoreCase("QTY")){
                    display = Double.toString(vegies.get(pos).getQuantity()).split("\\.")[0];
                }
                vegies.get(pos).setDisplayQuantity(display);
                holder.value.setText((vegies.get(pos).getDisplayQuantity()));
                holder.total.setText("₹"+vegies.get(pos).getTotalPrice());
                holder.unittype.setText(vegies.get(pos).getUnitType());

                if(quant == vegies.get(pos).getIncrementBy()){
                    if(context instanceof VegitableListActivity){
                        ((VegitableListActivity)context).orderItem(vegies.get(pos),"add",0,0,0);
                    }
                    else if(context instanceof ModifyLastOrderActivity){
                        ((ModifyLastOrderActivity)context).orderItem(vegies.get(pos),"add",0,0,0);
                    }
                }
                else {
                    if (context instanceof VegitableListActivity) {
                        ((VegitableListActivity) context).orderItem(vegies.get(pos), "update",totPrice,mallsave,marketsave);
                    }
                    else if (context instanceof ModifyLastOrderActivity) {
                        ((ModifyLastOrderActivity) context).orderItem(vegies.get(pos), "update",totPrice,mallsave,marketsave);
                    }
                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                double quant = vegies.get(pos).getQuantity();
                double totPrice = vegies.get(pos).getTotalPrice();
                double mallprice = vegies.get(pos).getMallPrice();
                double marketprice = vegies.get(pos).getMarketPrice();
                double marketsave = vegies.get(pos).getTotalMarketSave();
                double mallsave= vegies.get(pos).getTotalMallSave();
                double tempMarketsave = 0.0;
                double tempmallsave= 0.0;
                if(vegies.get(pos).getQuantity() != 0){
                    double price = vegies.get(pos).getOurPrice();
                    quant = Double.valueOf(String.format("%.2f",(quant - vegies.get(pos).getIncrementBy())));
                    vegies.get(pos).setQuantity(quant);
                    tempmallsave =Double.valueOf(String.format("%.2f",((mallprice * vegies.get(pos).getQuantity()) - (vegies.get(pos).getQuantity() * price))));
                    tempMarketsave =Double.valueOf(String.format("%.2f",((marketprice * vegies.get(pos).getQuantity()) - (vegies.get(pos).getQuantity() * price))));
                    vegies.get(pos).setTotalPrice(Double.valueOf(String.format("%.2f",(vegies.get(pos).getQuantity() * price))));
                    vegies.get(pos).setTotalMallSave(tempmallsave);
                    vegies.get(pos).setTotalMarketSave(tempMarketsave);

                    String display = Double.toString(vegies.get(pos).getQuantity());
                    if(vegies.get(pos).getQuantity() < 1){
                        display = Double.toString(vegies.get(pos).getQuantity() * 1000).split("\\.")[0];
                        vegies.get(pos).setUnitType("GM");
                    }
                    else {
                        vegies.get(pos).setUnitType("KG");
                    }
                     if(vegies.get(pos).getMeasurementType().equalsIgnoreCase("QTY")){
                        display = Double.toString(vegies.get(pos).getQuantity()).split("\\.")[0];
                    }
                    vegies.get(pos).setDisplayQuantity(display);

                    holder.value.setText(vegies.get(pos).getDisplayQuantity());
                    holder.total.setText("₹"+vegies.get(pos).getTotalPrice());
                    holder.unittype.setText(vegies.get(pos).getUnitType());
                    if(quant == 0){
                        if(context instanceof VegitableListActivity){
                            ((VegitableListActivity)context).orderItem(vegies.get(pos),"remove",totPrice,mallsave,marketsave);
                        }
                        else if(context instanceof ModifyLastOrderActivity){
                            ((ModifyLastOrderActivity)context).orderItem(vegies.get(pos),"remove",totPrice,mallsave,marketsave);
                        }
                    }
                    else {
                        if (context instanceof VegitableListActivity) {
                            ((VegitableListActivity) context).orderItem(vegies.get(pos), "update",totPrice,mallsave,marketsave);
                        }
                        else if (context instanceof ModifyLastOrderActivity) {
                            ((ModifyLastOrderActivity) context).orderItem(vegies.get(pos), "update",totPrice,mallsave,marketsave);
                        }
                    }
                }
            }
        });

        return this.convertView;
    }
    static class ViewHolder{

        ImageView vegiImage;
        TextView name,ourPrice,MarketPrice,MallPrice,total,value,desc,unittype,defaultVal;
        Button plus,minus;
    }
}


