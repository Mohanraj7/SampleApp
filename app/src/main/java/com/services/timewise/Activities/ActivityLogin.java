package com.services.timewise.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.google.gson.Gson;
import com.services.timewise.Model.TimewiseMemory;
import com.services.timewise.Model.UserDetailsModel;
import com.services.timewise.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    /*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    */
    EditText edt_driverid;
    EditText edt_password;
    ImageView btn_login;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/bauhaus93.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        */
        setContentView(R.layout.activity_login);
        pref = getApplication().getSharedPreferences("MyTimewisePref",MODE_PRIVATE);
        editor = pref.edit();

        Gson gson = new Gson();
        String json_data =  pref.getString("TW_USER_OBJECT","");
        Log.e("212121",json_data);

        if(!json_data.equals(""))
        {
            try
            {
                UserDetailsModel userDetailsVO = new UserDetailsModel(new JSONObject(json_data));

                Intent launchActivity1 = new Intent(ActivityLogin.this, Dashboard.class);
                startActivity(launchActivity1);
                finish();
            }
            catch (Exception e)
            {
                Log.e("PrefLoginCatch",e+"");
            }
        }

        TextView login_txt = findViewById(R.id.login_txt);

        Typeface c = Typeface.createFromAsset(getAssets(), "font/bauhaus93.ttf");
        login_txt.setTypeface(c);
        edt_driverid = findViewById(R.id.edt_driverid);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v1) {
                loginTask();

            }
        });

    }

    private void loginTask()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/Login";
        Log.e("212121",url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try
                {
                    Log.e("ReceivedData", response);
                    JSONObject jsonobj = new JSONObject(response);
                    int response_code = jsonobj.getInt("ResponseCode");
                    String response_message = jsonobj.getString("ResponseMessage");
                    JSONObject response_data = jsonobj.getJSONObject("ResponseData");
                    int status = response_data.getInt("OrderStatus");

                    if (status==1)
                    {
                        UserDetailsModel userDetailsVO = new UserDetailsModel(response_data);

                        editor.putString("TW_USER_OBJECT",response_data.toString());
                        editor.commit();

                        Intent launchActivity1= new Intent(ActivityLogin.this, Dashboard.class);
                        startActivity(launchActivity1);
                        finish();
                    }
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
                params.put("UserId",edt_driverid.getText().toString());
                params.put("Password",edt_password.getText().toString());
                params.put("Latitude","0.0");
                params.put("Longitude","0.0");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
