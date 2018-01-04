package com.realizertech.shivmudra.adapter;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.realizertech.shivmudra.R;
import com.realizertech.shivmudra.ReferalActivity;
import com.realizertech.shivmudra.VegitableListActivity;
import com.realizertech.shivmudra.apicalls.ApiService;
import com.realizertech.shivmudra.apicalls.EnqueueWrapper;
import com.realizertech.shivmudra.fragments.BaseFragment;
import com.realizertech.shivmudra.fragments.ViewReferralFragment;
import com.realizertech.shivmudra.model.ReferFriendInput;
import com.realizertech.shivmudra.model.ReferralModel;
import com.realizertech.shivmudra.model.ReferralReminderInput;
import com.realizertech.shivmudra.model.Referrals;
import com.realizertech.shivmudra.model.VegetableModel;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Win on 09-05-2017.
 */

public class ReferralListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    public View convertView;
    public Context context;
    public List<Referrals> referral;
    public BaseFragment fragment;
    public  String UserToken;

    public ReferralListAdapter(List<Referrals> referral, Context context, BaseFragment fragment, String UserToken){
        this.context = context;
        this.referral = referral;
        layoutInflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.UserToken = UserToken;
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
            this.convertView = layoutInflater.inflate(R.layout.referral_list_item_layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) this.convertView.findViewById(R.id.txtReferalName);
            holder.societyName = (TextView) this.convertView.findViewById(R.id.txtsocietyName);
            holder.msg = (TextView) this.convertView.findViewById(R.id.txtmsg);
            //holder.orderedDate = (TextView) this.convertView.findViewById(R.id.txtOrderDate);
            holder.mobno = (TextView) this.convertView.findViewById(R.id.txtmobNo);
            holder.refer = (Button) this.convertView.findViewById(R.id.btnReferagain);
            holder.refer.setTag(position);
            this.convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) this.convertView.getTag();
        }

        holder.name.setText(referral.get(position).getName());
        holder.societyName.setText(referral.get(position).getSocietyName());
        holder.mobno.setText(referral.get(position).getReferralContactNo());
        if(referral.get(position).getReferralRegisteredDate() == null){
            referral.get(position).setReferralRegisteredDate("");
        }
         if(referral.get(position).getReferralRegisteredOrderDate() == null){
            referral.get(position).setReferralRegisteredOrderDate("");
        }
        if(!referral.get(position).getReferralRegisteredDate().isEmpty() && !referral.get(position).getReferralRegisteredOrderDate().isEmpty()){
            holder.msg.setText("Hurray!! Your friend has placed first order.");
            holder.refer.setVisibility(View.GONE);
        }
        else if(referral.get(position).getReferralRegisteredDate().isEmpty() && referral.get(position).getReferralRegisteredOrderDate().isEmpty()){
            holder.msg.setText("Friend has not Registered yet.");
            holder.refer.setVisibility(View.VISIBLE);
        }
        else if(!referral.get(position).getReferralRegisteredDate().isEmpty() && referral.get(position).getReferralRegisteredOrderDate().isEmpty()){
            holder.msg.setText("Friend has Registered but didn't Order yet.");
            holder.refer.setVisibility(View.VISIBLE);
        }

        holder.refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Referrals refer = referral.get(pos);
                ReferralReminderInput referinput = new ReferralReminderInput();
                referinput.setReferralId(refer.getReferralId());
                referinput.setUserToken(UserToken);
                if(fragment instanceof ViewReferralFragment){
                    ((ViewReferralFragment)fragment).sendReminder(referinput);
                }
            }
        });

        return this.convertView;
    }



    static class ViewHolder{

        TextView name,societyName,mobno,msg;
        Button refer;
    }
}


