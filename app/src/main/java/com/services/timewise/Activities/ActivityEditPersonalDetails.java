package com.services.timewise.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityEditPersonalDetails extends AppCompatActivity {

    ImageView img_backbtneditpersonaldetails;
    EditText edt_firstname;
    EditText edt_middlename;
    EditText edt_lastname;
    EditText edt_nickname;
    EditText edt_email;
    EditText edt_phone;
    EditText edt_dob;
    TextView txt_savechanges;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_details);

        edt_firstname = findViewById(R.id.edt_firstname);
        edt_middlename = findViewById(R.id.edt_middlename);
        edt_lastname = findViewById(R.id.edt_lastname);
        edt_nickname = findViewById(R.id.edt_nickname);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_dob = findViewById(R.id.edt_dob);
        txt_savechanges = findViewById(R.id.txt_savechanges);
        img_backbtneditpersonaldetails = findViewById(R.id.img_backbtneditpersonaldetails);

        setDatas();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityEditPersonalDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txt_savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePersonalDetails();
            }
        });

        img_backbtneditpersonaldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityEditPersonalDetails.this, ActivityProfile.class);
                startActivity(intent);
            }
        });
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = myCalendar.getTime();
        String convertedDate = sdf.format(d);
        edt_dob.setText(convertedDate);
    }

    private void updatePersonalDetails()
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
                    JSONObject j = new JSONObject(response);
                    JSONObject ResponseData = new JSONObject(j.getString("ResponseData"));

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
                params.put("Type","1");
                params.put("FirstName",edt_firstname.getText().toString());
                params.put("MiddleName",edt_middlename.getText().toString());
                params.put("LastName",edt_lastname.getText().toString());
                params.put("NickName",edt_nickname.getText().toString());
                params.put("Email",edt_email.getText().toString());
                params.put("Phone",edt_phone.getText().toString());
                params.put("Dob",edt_dob.getText().toString());
                Log.e("Params",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
    void setDatas()
    {
        edt_firstname.setText(UserDetailsModel.FirstName);
        edt_middlename.setText(UserDetailsModel.MiddleName);
        edt_lastname.setText(UserDetailsModel.LastName);
        edt_phone.setText(UserDetailsModel.Phone);
        edt_dob.setText(UserDetailsModel.Dob);
        edt_email.setText(UserDetailsModel.Email);
        edt_nickname.setText(UserDetailsModel.NickName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
