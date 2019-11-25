package com.services.timewise.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.services.timewise.Activities.ActivityNextDayOrders;
import com.services.timewise.Activities.NextDayOrderDetails;
import com.services.timewise.Interfaces.OnCompletedOrderSelectedListener;
import com.services.timewise.Interfaces.OnNextDayOrderSelectedListener;
import com.services.timewise.Model.CompletedOrderModel;
import com.services.timewise.Model.NextDayOrderModel;
import com.services.timewise.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NextDayOrderAdapter extends RecyclerView.Adapter<NextDayOrderAdapter.MyViewHolder> {

    Button btn_details;

    OnNextDayOrderSelectedListener onNextDayOrderSelectedListener;

    ArrayList<NextDayOrderModel> NextDayOrderArrayList = new ArrayList<>();
    Activity mActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_nextdayorderadapter)
        LinearLayout layout_nextdayorderadapter;
        @BindView(R.id.txt_datetime)
        TextView txt_datetime;
        @BindView(R.id.txt_containerno)
        TextView txt_containerno;
        @BindView(R.id.txt_company)
        TextView txt_company;
        @BindView(R.id.txt_customerid)
        TextView txt_customerid;
        @BindView(R.id.txt_type)
        TextView txt_type;
        @BindView(R.id.txt_pickFrom)
        TextView txt_pickFrom;
        @BindView(R.id.txt_stopAddr)
        TextView txt_stopAddr;
        @BindView(R.id.txt_dropOff)
        TextView txt_dropOff;
        @BindView(R.id.btn_details)
        Button btn_details;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public NextDayOrderAdapter(Activity a, ArrayList<NextDayOrderModel> NextDayOrderArrayList, OnNextDayOrderSelectedListener listenerFrom) {
        this.mActivity = a;
        this.NextDayOrderArrayList = NextDayOrderArrayList;
        this.onNextDayOrderSelectedListener = listenerFrom;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        LinearLayout itemview = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_day_order_adapter, parent, false);
        return new MyViewHolder(itemview);

    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final NextDayOrderModel singleOrder = NextDayOrderArrayList.get(position);

        holder.txt_datetime.setText(singleOrder.PickDate +" "+singleOrder.PickTime);
        holder.txt_containerno.setText(singleOrder.OrderInfoId +"-"+singleOrder.Sno);
        holder.txt_company.setText(singleOrder.Company);
        holder.txt_customerid.setText(singleOrder.PickContactName +"/"+singleOrder.PickContactNumber);
        holder.txt_type.setText(singleOrder.Type);
        holder.txt_pickFrom.setText(singleOrder.PickFrom);
        holder.txt_stopAddr.setText(singleOrder.StopAddr);
        holder.txt_dropOff.setText(singleOrder.DropOff);

        holder.layout_nextdayorderadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextDayOrderSelectedListener.onNextDayOrderSelected();
            }
        });
        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextDayOrderDetails.selectedOrder = singleOrder;
                onNextDayOrderSelectedListener.onNextDayOrderSelected();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return NextDayOrderArrayList.size();
    }
}
