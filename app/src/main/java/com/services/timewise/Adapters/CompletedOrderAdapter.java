package com.services.timewise.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.services.timewise.Interfaces.OnCompletedOrderSelectedListener;
import com.services.timewise.Model.CompletedOrderModel;
import com.services.timewise.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedOrderAdapter extends RecyclerView.Adapter<CompletedOrderAdapter.MyViewHolder> {


    OnCompletedOrderSelectedListener onCompletedOrderSelectedListener;

    ArrayList<CompletedOrderModel> completedOrderArrayList = new ArrayList<>();
    Activity mActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_completedordersadapter)
        LinearLayout layout_completedordersadapter;
        @BindView(R.id.txt_datetime)
        TextView txt_datetime;
        @BindView(R.id.txt_containerno)
        TextView txt_containerno;

        @BindView(R.id.txt_company)
        TextView txt_company;

        @BindView(R.id.txt_pick_contact)
        TextView txt_pick_contact;

        @BindView(R.id.txt_spl_instruction)
        TextView txt_spl_instruction;
        @BindView(R.id.txt_totalhours)
        TextView txt_totalhours;

        @BindView(R.id.txt_addrsline1)
        TextView txt_addrsline1;
        @BindView(R.id.txt_addrsline2)
        TextView txt_addrsline2;
        @BindView(R.id.txt_addrsline3)
        TextView txt_addrsline3;
        @BindView(R.id.btn_remove)
        Button btn_remove;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public CompletedOrderAdapter(Activity a, ArrayList<CompletedOrderModel> completedOrderArrayList, OnCompletedOrderSelectedListener listenerFrom) {
        this.mActivity = a;
        this.completedOrderArrayList = completedOrderArrayList;
        this.onCompletedOrderSelectedListener = listenerFrom;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout itemview = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_order_adapter, parent, false);
        return new MyViewHolder(itemview);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final CompletedOrderModel singleOrder = completedOrderArrayList.get(position);

        holder.txt_datetime.setText(singleOrder.PickDate + " " + singleOrder.PickTime);
        holder.txt_containerno.setText(singleOrder.ContainerInfo);
        holder.txt_company.setText(singleOrder.Company);
        holder.txt_pick_contact.setText(singleOrder.PickContactName + " / " + singleOrder.PickContactNumber);
        holder.txt_spl_instruction.setText(singleOrder.PickSplIns);
        holder.txt_totalhours.setText(singleOrder.TotalHours);

        holder.txt_addrsline1.setText(singleOrder.PickFrom);
        holder.txt_addrsline2.setText(singleOrder.StopAddr);
        holder.txt_addrsline3.setText(singleOrder.DropOff);

        holder.layout_completedordersadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompletedOrderSelectedListener.onOrderSelected();
            }
        });

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveOrderTask(singleOrder.OrderId);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return completedOrderArrayList.size();
    }

    public void RemoveOrderTask(final String orderId)
    {
        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this.mActivity);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/RemoveOrder";
        Log.e("212121", url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("ReceivedData", response);


                } catch (Exception e) {
                    Log.e("Catch", e + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("OrderId",orderId);
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}

