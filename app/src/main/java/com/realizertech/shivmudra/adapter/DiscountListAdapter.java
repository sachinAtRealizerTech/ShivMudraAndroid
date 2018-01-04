package com.realizertech.shivmudra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.model.DiscountModel;
import com.realizertech.shivmudra.model.DiscountsReceived;
import com.realizertech.shivmudra.model.ReferralModel;

import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class DiscountListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<DiscountsReceived> referral;

    public DiscountListAdapter(List<DiscountsReceived> referral, Context context){
        this.context = context;
        this.referral = referral;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return referral.size();
    }

    @Override
    public Object getItem(int position) {
        return referral.get(position);
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
            this.convertView = layoutInflater.inflate(R.layout.discount_list_item_layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) this.convertView.findViewById(R.id.txtname);
            holder.percenatge = (TextView) this.convertView.findViewById(R.id.txtpercentage);
            holder.date = (TextView) this.convertView.findViewById(R.id.txtdate);
            holder.value = (TextView) this.convertView.findViewById(R.id.txtvalue);
            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }

        holder.name.setText(referral.get(position).getDiscountType());
        holder.date.setText(referral.get(position).getCreateTS());
        holder.percenatge.setText("Discount Percentage: "+referral.get(position).getDiscountPercentage()+"%");
        holder.value.setText("Discount Value: ₹" +referral.get(position).getDiscountAmount());

        return this.convertView;
    }
    static class ViewHolder{

        TextView name,date,percenatge,value;
    }
}


