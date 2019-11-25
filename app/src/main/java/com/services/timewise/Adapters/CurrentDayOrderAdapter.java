package com.services.timewise.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.services.timewise.Activities.ActivityOrderDetails;
import com.services.timewise.Activities.CurrentDayOrderDetails;
import com.services.timewise.Interfaces.OnOrderSelectedListener;
import com.services.timewise.Model.CurrentDayOrderModel;
import com.services.timewise.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentDayOrderAdapter extends RecyclerView.Adapter<CurrentDayOrderAdapter.MyViewHolder> {

    OnOrderSelectedListener onOrderSelectedListener;

    ArrayList<CurrentDayOrderModel> currentDayOrderArrayList;
    Activity mActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.txt_order_no)
        TextView txt_order_no;
        @BindView(R.id.txt_order_company_name)
        TextView txt_order_company_name;
        @BindView(R.id.txt_order_date)
        TextView txt_order_date;
        @BindView(R.id.txt_order_time)
        TextView txt_order_time;
        @BindView(R.id.txt_order_status)
        TextView txt_order_status;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public CurrentDayOrderAdapter(Activity a, ArrayList<CurrentDayOrderModel> currentDayOrderArrayList, OnOrderSelectedListener listenerFrom) {
        this.mActivity = a;
        this.currentDayOrderArrayList = currentDayOrderArrayList;
        this.onOrderSelectedListener = listenerFrom;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        CardView itemview = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_adapter, parent, false);
        return new MyViewHolder(itemview);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CurrentDayOrderModel singleOrder = currentDayOrderArrayList.get(position);
        holder.txt_order_no.setText(String.valueOf(position+1));
        holder.txt_order_company_name.setText(singleOrder.Company);
        holder.txt_order_date.setText(singleOrder.PickDate);
        holder.txt_order_time.setText(singleOrder.PickTime);
        holder.txt_order_status.setText("New");
        if(Integer.parseInt(singleOrder.OrderStatus)>=5)
        {
            holder.txt_order_status.setText("In Service");
            holder.txt_order_status.setTextColor(mActivity.getResources().getColor(R.color.light_orange));
        }


        if(position!=0)
        {
            holder.layout.setBackgroundColor(mActivity.getResources().getColor(R.color.grey));
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(position==0)
                    {
                        ActivityOrderDetails.selectedOrder = singleOrder;
                        onOrderSelectedListener.onFirstOrderSelected();
                    }
                    else
                    {
                        CurrentDayOrderDetails.selectedOrder = singleOrder;
                        onOrderSelectedListener.onOtherOrderSelected();
                    }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return currentDayOrderArrayList.size();
    }
}
