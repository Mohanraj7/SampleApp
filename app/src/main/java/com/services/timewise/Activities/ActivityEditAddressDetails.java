package com.services.timewise.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.services.timewise.Model.TimewiseMemory;
import com.services.timewise.Model.UserDetailsModel;
import com.services.timewise.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityEditAddressDetails extends AppCompatActivity
{
    ImageView img_backbtneditaddressdetails;
    EditText edt_addrline1;
    EditText edt_addrline2;
    EditText edt_city;
    EditText edt_state;
    EditText edt_postalcode;
    EditText edt_country;
    TextView txt_savechanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_details);
        edt_addrline1 = findViewById(R.id.edt_addrline1);
        edt_addrline2 = findViewById(R.id.edt_addrline2);
        edt_city = findViewById(R.id.edt_city);
        edt_state = findViewById(R.id.edt_state);
        edt_country = findViewById(R.id.edt_country);
        edt_postalcode = findViewById(R.id.edt_postalcode);
        txt_savechanges = findViewById(R.id.txt_savechanges);
        img_backbtneditaddressdetails = findViewById(R.id.img_backbtneditaddressdetails);

        setDatas();

        txt_savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddressDetails();
            }
        });

        img_backbtneditaddressdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditAddressDetails.this, ActivityProfile.class);
                startActivity(intent);
            }
        });
    }

    private void updateAddressDetails()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/UpdateProfile";
        Log.e("212121",url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try
                {
                    Log.e("ReceivedData", response);
                    JSONObject json = new JSONObject(response);
                    JSONObject ResponseData = new JSONObject(json.getString("ResponseData"));

                    UserDetailsModel.FirstName = ResponseData.getString("FirstName");
                    UserDetailsModel.MiddleName = ResponseData.getString("MiddleName");
                    UserDetailsModel.LastName = ResponseData.getString("LastName");
                    UserDetailsModel.NickName = ResponseData.getString("NickName");
                    UserDetailsModel.ProfilePath = ResponseData.getString("ProfilePath");
                    UserDetailsModel.LicenseNumber = ResponseData.getString("LicenseNumber");
                    UserDetailsModel.LicenseState = ResponseData.getString("LicenseState");
                    UserDetailsModel.Phone = ResponseData.getString("Phone");
                    UserDetailsModel.Email = ResponseData.getString("Email");
                    UserDetailsModel.Address1 = ResponseData.getString("Address1");
                    UserDetailsModel.Address2 = ResponseData.getString("Address2");
                    UserDetailsModel.City = ResponseData.getString("City");
                    UserDetailsModel.State = ResponseData.getString("State");
                    UserDetailsModel.ZipCode = ResponseData.getString("ZipCode");
                    UserDetailsModel.Dob = ResponseData.getString("Dob");
                    UserDetailsModel.LicenseExpiry = ResponseData.getString("LicenseExpiry");
                    UserDetailsModel.UserId = ResponseData.getString("UserId");
                    UserDetailsModel.Password = ResponseData.getString("Password");
                    UserDetailsModel.Latitude = ResponseData.getString("Latitude");
                    UserDetailsModel.Longitude = ResponseData.getString("Longitude");
                    UserDetailsModel.Status = ResponseData.getString("OrderStatus");
                    onBackPressed();
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("DriverId", UserDetailsModel.Id);
                params.put("Type","3");
                params.put("Address1",edt_addrline1.getText().toString());
                params.put("Address2",edt_addrline2.getText().toString());
                params.put("City",edt_city.getText().toString());
                params.put("State",edt_state.getText().toString());
                params.put("ZipCode",edt_postalcode.getText().toString());
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
    void setDatas()
    {
        edt_addrline1.setText(UserDetailsModel.Address1);
        edt_addrline2.setText(UserDetailsModel.Address2);
        edt_city.setText(UserDetailsModel.City);
        edt_state.setText(UserDetailsModel.State);
        edt_country.setText("Canada");
        edt_postalcode.setText(UserDetailsModel.ZipCode);
    }
    public  void onBackPressed()
    {
        super.onBackPressed();
    }
}
