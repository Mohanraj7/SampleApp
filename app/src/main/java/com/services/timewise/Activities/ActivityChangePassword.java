package com.services.timewise.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ActivityChangePassword extends AppCompatActivity {

    ImageView img_backbtnchangepwd;
    EditText edt_oldpwd;
    EditText edt_newpwd;
    EditText edt_confirmpwd;
    TextView btn_savechanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btn_savechanges = findViewById(R.id.btn_savechanges);
        edt_newpwd = findViewById(R.id.edt_newpwd);
        edt_confirmpwd = findViewById(R.id.edt_confirmpwd);
        edt_oldpwd = findViewById(R.id.edt_oldpwd);
        img_backbtnchangepwd = findViewById(R.id.img_backbtnchangepwd);
        img_backbtnchangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChangePassword.this, ActivityProfile.class);
                startActivity(intent);
            }
        });
        btn_savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edt_confirmpwd.getText().toString()).equals(edt_newpwd.getText().toString())) {
                    changePassword();
                    Toast.makeText(ActivityChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    {
//                        Toast toast = new Toast(ActivityChangePassword.this);
//                        toast.setDuration(Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                        toast.setView(null);
//                        toast.show();
                        Toast.makeText(ActivityChangePassword.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void changePassword() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/ChangePassword";
        Log.e("212121", url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    Log.e("cpReceivedData", response);

//                    if (status==1)
//                    {
//                        int driverid = response_data.getInt("Id");
//                        String firstname = response_data.getString("FirstName");
//                    }
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
                params.put("OldPassword", edt_oldpwd.getText().toString());
                params.put("NewPassword", edt_newpwd.getText().toString());
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

}
