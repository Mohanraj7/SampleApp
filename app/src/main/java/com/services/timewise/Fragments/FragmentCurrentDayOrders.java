package com.services.timewise.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.services.timewise.Activities.CurrentDayOrderDetails;
import com.services.timewise.Activities.Dashboard;
import com.services.timewise.Activities.ActivityOrderDetails;
import com.services.timewise.Adapters.CurrentDayOrderAdapter;
import com.services.timewise.Interfaces.OnOrderSelectedListener;
import com.services.timewise.Model.CurrentDayOrderModel;
import com.services.timewise.Model.UserDetailsModel;
import com.services.timewise.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.services.timewise.Services.Constants.OP_GET_ORDERS;
import static com.services.timewise.Services.Constants.WEBSERVICE_URL;


public class FragmentCurrentDayOrders extends Fragment implements OnOrderSelectedListener {

    private Dashboard mActivity;

    @BindView(R.id.btn_stayatyard)
    Button btn_stayatyard;
    @BindView(R.id.btn_gotoyard)
    Button btn_gotoyard;
    @BindView(R.id.btn_reachedyard)
    Button btn_reachedyard;
    @BindView(R.id.order_recyclerview)
    RecyclerView order_recyclerview;

    ArrayList<CurrentDayOrderModel> currentDayOrderArrayList = new ArrayList<>();

    public FragmentCurrentDayOrders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homefragment = inflater.inflate(R.layout.frag_orderhome_activity , container , false);
        ButterKnife.bind(this, homefragment);
        mActivity = (Dashboard) getActivity();
//        btn_stayatyard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateDriverStatus();
//            }
//        });
////        btn_gotoyard.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onPressedGotoYard(btn_gotoyard,R.drawable.rounded_corner_lightorange_button);
////            }
////        });
////        btn_reachedyard.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onPressedReachedYard(btn_reachedyard,R.drawable.rounded_corner_lightorange_button);
////            }
////        });
        RecyclerView.LayoutManager llm = new LinearLayoutManager(mActivity);
        order_recyclerview.setLayoutManager(llm);
        getCurrentDayOrders();
        return homefragment;
    }

    private void getCurrentDayOrders() {
        final ProgressDialog pDialog = new ProgressDialog(mActivity);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(mActivity);

        String url = WEBSERVICE_URL+OP_GET_ORDERS;
        Log.e("CurrentDayOrderURL", url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    Log.e("CurrentDayOrderResponse", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray orderArray = jsonResponse.getJSONArray("ResponseData");

                    currentDayOrderArrayList.clear();
                    for(int i =0;i<orderArray.length();i++)
                    {
                        CurrentDayOrderModel singleOrder = new CurrentDayOrderModel(orderArray.getJSONObject(i));
                        currentDayOrderArrayList.add(singleOrder);
                    }
                    if(currentDayOrderArrayList.size()>0)
                    {
                        CurrentDayOrderAdapter myAdapter = new CurrentDayOrderAdapter(mActivity,currentDayOrderArrayList,FragmentCurrentDayOrders.this);
                        order_recyclerview.setAdapter(myAdapter);
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
                params.put("Type", "1");
                Log.e("CurrentDayOrderParams",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    @OnClick({R.id.btn_stayatyard,R.id.btn_gotoyard,R.id.btn_reachedyard})
    void onStayGoReachedSelected(View v)
    {
        Button btn = (Button) v;
        if((btn.getBackground().getConstantState()== mActivity.getResources().getDrawable(R.drawable.rounded_corner_black_button).getConstantState()))
        {
            if(btn.getId()== R.id.btn_stayatyard)
            {
                btn_stayatyard.setBackgroundResource(R.drawable.selected_drawable);
                btn_gotoyard.setBackgroundResource(R.drawable.rounded_corner_black_button);
                btn_reachedyard.setBackgroundResource(R.drawable.rounded_corner_black_button);
                updateDriverStatus("2");
            }
            else if(btn.getId()== R.id.btn_gotoyard)
            {
                btn_stayatyard.setBackgroundResource(R.drawable.rounded_corner_black_button);
                btn_gotoyard.setBackgroundResource(R.drawable.selected_drawable);
                btn_reachedyard.setBackgroundResource(R.drawable.rounded_corner_black_button);
                updateDriverStatus("3");
            }
            else if(btn.getId()== R.id.btn_reachedyard)
            {
                btn_stayatyard.setBackgroundResource(R.drawable.rounded_corner_black_button);
                btn_gotoyard.setBackgroundResource(R.drawable.rounded_corner_black_button);
                btn_reachedyard.setBackgroundResource(R.drawable.selected_drawable);
                updateDriverStatus("4");
            }
        }
    }

    @Override
    public void onFirstOrderSelected() {
        Log.e("21212121","FirstOrderSelected");
        Intent i = new Intent(mActivity, ActivityOrderDetails.class);
        mActivity.startActivity(i);
    }

    @Override
    public void onOtherOrderSelected() {
        Log.e("21212121","OtherOrderSelected");
        Intent i = new Intent(mActivity, CurrentDayOrderDetails.class);
        mActivity.startActivity(i);
    }


    public void updateDriverStatus(final String btnStatus) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/UpdateDriverStatus";
        Log.e("UpdateDriverURL",url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try
                {
                    Log.e("UpdateDriverResponse", response);

                }
                catch (Exception e)
                {
                    pDialog.dismiss();
                    Log.e("Catch",e+"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("Error :",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("DriverId", UserDetailsModel.Id);
                params.put("OrderStatus",btnStatus);
                params.put("Latitude",UserDetailsModel.Latitude);
                params.put("Longitude", UserDetailsModel.Longitude);
                Log.e("UpdateDriverParams",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);

    }
}






