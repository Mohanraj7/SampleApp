package com.services.timewise.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.services.timewise.Fragments.FragmentCurrentDayOrders;
import com.services.timewise.Model.UserDetailsModel;
import com.services.timewise.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Dashboard extends AppCompatActivity {


    @BindView(R.id.nav_profile_layout)
    LinearLayout nav_profile_layout;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawer;
    @BindView(R.id.img_menu)
    ImageView img_menu;
    FragmentCurrentDayOrders mFragmentCurrentDayOrders;
    TextView edt_profile;
    LinearLayout nav_book_now_layout;
    LinearLayout nav_reservation_layout;
    @BindView(R.id.txt_next_day_orders)
    TextView txt_next_day_orders;
    @BindView(R.id.txt_completed_orders)
    TextView txt_completed_orders;
    @BindView(R.id.txt_current_day_orders)
    TextView txt_current_day_orders;
    @BindView(R.id.btn_logout)
    TextView btn_logout;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    boolean fromOtherFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        pref = getApplication().getSharedPreferences("MyTimewisePref",MODE_PRIVATE);
        editor = pref.edit();
        mFragmentCurrentDayOrders = new FragmentCurrentDayOrders();

        edt_profile = findViewById(R.id.edit_profile);
        nav_book_now_layout = findViewById(R.id.nav_book_now_layout);
        nav_reservation_layout = findViewById(R.id.nav_reservation_layout);
        edt_profile = findViewById(R.id.edit_profile);

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        edt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ActivityProfile.class);
                startActivity(intent);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        txt_current_day_orders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigationMenu(1);
            }
        });

        txt_completed_orders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigationMenu(2);
            }
        });

        txt_next_day_orders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigationMenu(3);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                logoutTask();
            }
        });

        navigationMenu(0);
    }

    void navigationMenu(int menu)
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(menu==0)
        {
                ft.replace(R.id.fragment_container, mFragmentCurrentDayOrders, mFragmentCurrentDayOrders.getTag());
                ft.commit();
        }
        else if(menu==1)
        {
            if (fromOtherFragment)
            {
                // Replacing from other Fragment
                ft.replace(R.id.fragment_container, mFragmentCurrentDayOrders, mFragmentCurrentDayOrders.getTag());
                ft.commit();
                fromOtherFragment = false;
            }
            else
            {
                // Refreshing from current Fragment
                Intent intent = new Intent(Dashboard.this, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        else if(menu==2)
        {
            Intent intent = new Intent(Dashboard.this, ActivityCompletedOrders.class);
            startActivity(intent);
        }
        else if(menu==3)
        {
            Intent intent = new Intent(Dashboard.this, ActivityNextDayOrders.class);
            startActivity(intent);
        }

    }

    private void logoutTask()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/Logoff";
        Log.e("LogoutRequest",url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try
                {
                    Log.e("LogoutResponse", response);
                    editor.clear();
                    editor.commit();
                    new UserDetailsModel();
                    Intent intent = new Intent(Dashboard.this, ActivityLogin.class);
                    startActivity(intent);
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
                params.put("DriverId",UserDetailsModel.Id);
                params.put("Latitude","1.2");
                params.put("Longitude","1.4");
                Log.e("Error :",params.toString());
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



