package com.services.timewise.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.services.timewise.Adapters.CompletedOrderAdapter;
import com.services.timewise.Adapters.NextDayOrderAdapter;
import com.services.timewise.Interfaces.OnNextDayOrderSelectedListener;
import com.services.timewise.Model.CompletedOrderModel;
import com.services.timewise.Model.NextDayOrderModel;
import com.services.timewise.Model.UserDetailsModel;
import com.services.timewise.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityNextDayOrders extends AppCompatActivity implements OnNextDayOrderSelectedListener {


    @BindView(R.id.nextDayOrderRecyclerview)
    RecyclerView nextDayOrderRecyclerview;

    ArrayList<NextDayOrderModel> nextDayOrderArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_day_orders);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        nextDayOrderRecyclerview.setLayoutManager(llm);

        getNextDayOrders();
    }

    private void getNextDayOrders() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/GetOrders";
        Log.e("NextDayOrderURL", url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    Log.e("NextDayOrderResponse", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray orderArray = jsonResponse.getJSONArray("ResponseData");

                    nextDayOrderArrayList.clear();
                    for(int i =0;i<orderArray.length();i++)
                    {
                        NextDayOrderModel singleOrder = new NextDayOrderModel(orderArray.getJSONObject(i));
                        nextDayOrderArrayList.add(singleOrder);
                    }
                    if(nextDayOrderArrayList.size()>0)
                    {
                        NextDayOrderAdapter myAdapter = new NextDayOrderAdapter(ActivityNextDayOrders.this,nextDayOrderArrayList,ActivityNextDayOrders.this);
                        nextDayOrderRecyclerview.setAdapter(myAdapter);
                    }
                } catch (Exception e) {
                    pDialog.dismiss();
                    Log.e("Catch", e + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("Error :", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("DriverId", UserDetailsModel.Id);
                params.put("Type", "2");
                Log.e("NextDayOrderParams",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    @OnClick(R.id.img_back)
    void onBackSelected(View v)
    {
        onBackPressed();
    }

    @Override
    public void onNextDayOrderSelected() {
        Intent i = new Intent(this, NextDayOrderDetails.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}
