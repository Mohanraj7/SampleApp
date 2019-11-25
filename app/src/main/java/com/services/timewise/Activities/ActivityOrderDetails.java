package com.services.timewise.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.services.timewise.Model.CurrentDayOrderModel;
import com.services.timewise.Model.UserDetailsModel;
import com.services.timewise.R;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

import static com.services.timewise.Services.Constants.OP_UPDATE_ORDER_STATUS;
import static com.services.timewise.Services.Constants.WEBSERVICE_URL;

public class ActivityOrderDetails extends AppCompatActivity {

    public static CurrentDayOrderModel selectedOrder;

    TextView txt_ref;
    TextView txt_vehicle_type;
    TextView txt_pickup_details;
    TextView txt_stopover_details;
    TextView txt_dropoff_details;
    TextView txt_pickupdetails_time;
    TextView txt_pickupdetails_addrs;
    TextView txt_pickupdetails_spl;
    TextView txt_pickupdetails_spl_details;
    TextView txt_pickupdetails_name;
    TextView txt_pickupdetails_phone;
    TextView txt_pickupdetails_arrive;
    TextView txt_pickupdetails_units;
    TextView txt_pickupdetails_docs;
    TextView txt_pickupdetails_load;
    TextView txt_pickupdetails_upload;
    TextView txt_pickupdetails_go;
    TextView txt_stopoverdetails_time;
    TextView txt_stopoverdetails_addrs;
    TextView txt_stopoverdetails_spl;
    TextView txt_stopoverdetails_spl_details;
    TextView txt_stopoverdetails_name;
    TextView txt_stopoverdetails_phone;
    TextView txt_stopoverdetails_arrive;
    TextView txt_stopoverdetails_units;
    TextView txt_stopoverdetails_docs;
    TextView txt_stopoverdetails_load;
    TextView txt_stopoverdetails_upload;
    TextView txt_stopoverdetails_go;
    TextView txt_dropoffdetails_time;
    TextView txt_dropoffdetails_address;
    TextView txt_dropoffdetails_spl;
    TextView txt_dropoffdetails_spl_details;
    TextView txt_dropoffdetails_name;
    TextView txt_dropoffdetails_phone;
    TextView txt_dropoffdetails_arrive;
    TextView txt_dropoffdetails_unload;
    TextView txt_dropoffdetails_docs;
    TextView txt_dropoffdetails_upload;
    TextView txt_dropoffdetails_go;
    EditText edt_comments;
    EditText edt_od_name;
    TextView txt_signature;
    ImageView img_signature;
    ImageView img_startbuttonblack;
    TextView txt_start;

    Button btn_complete;
    Button btn_deadrun;
    LinearLayout layout_pickup_details;
    LinearLayout layout_stopover_details;
    LinearLayout layout_dropoff_details;

    boolean back_btn_enable = true;

    GPSTracker gps;
    double latitude = 0.0;
    double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        txt_ref = findViewById(R.id.txt_ref);
        txt_vehicle_type = findViewById(R.id.txt_vehicle_type);
        txt_pickup_details = findViewById(R.id.txt_pickup_details);
        txt_stopover_details = findViewById(R.id.txt_stopover_details);
        txt_dropoff_details = findViewById(R.id.txt_dropoff_details);
        layout_pickup_details = findViewById(R.id.layout_pickup_details);
        layout_stopover_details = findViewById(R.id.layout_stopover_details);
        layout_dropoff_details = findViewById(R.id.layout_dropoff_details);
        txt_pickupdetails_time = findViewById(R.id.txt_pickupdetails_time);
        txt_pickupdetails_addrs = findViewById(R.id.txt_pickupdetails_addrs);
        txt_pickupdetails_time = findViewById(R.id.txt_pickupdetails_time);
        txt_pickupdetails_spl = findViewById(R.id.txt_pickupdetails_spl);
        txt_pickupdetails_spl_details = findViewById(R.id.txt_pickupdetails_spl_details);
        txt_pickupdetails_name = findViewById(R.id.txt_pickupdetails_name);
        txt_pickupdetails_phone = findViewById(R.id.txt_pickupdetails_phone);
        txt_pickupdetails_arrive = findViewById(R.id.txt_pickupdetails_arrive);
        txt_pickupdetails_units = findViewById(R.id.txt_pickupdetails_units);
        txt_pickupdetails_docs = findViewById(R.id.txt_pickupdetails_docs);
        txt_pickupdetails_load = findViewById(R.id.txt_pickupdetails_load);
        txt_pickupdetails_upload = findViewById(R.id.txt_pickupdetails_upload);
        txt_pickupdetails_go = findViewById(R.id.txt_pickupdetails_go);
        txt_stopoverdetails_time = findViewById(R.id.txt_stopoverdetails_time);
        txt_stopoverdetails_addrs = findViewById(R.id.txt_stopoverdetails_addrs);
        txt_stopoverdetails_spl = findViewById(R.id.txt_stopoverdetails_spl);
        txt_stopoverdetails_spl_details = findViewById(R.id.txt_stopoverdetails_spl_details);
        txt_stopoverdetails_name = findViewById(R.id.txt_stopoverdetails_name);
        txt_stopoverdetails_phone = findViewById(R.id.txt_stopoverdetails_phone);
        txt_stopoverdetails_arrive = findViewById(R.id.txt_stopoverdetails_arrive);
        txt_stopoverdetails_units = findViewById(R.id.txt_stopoverdetails_units);
        txt_stopoverdetails_docs = findViewById(R.id.txt_stopoverdetails_docs);
        txt_stopoverdetails_load = findViewById(R.id.txt_stopoverdetails_load);
        txt_stopoverdetails_upload = findViewById(R.id.txt_stopoverdetails_upload);
        txt_stopoverdetails_go = findViewById(R.id.txt_stopoverdetails_go);
        txt_dropoffdetails_time = findViewById(R.id.txt_dropoffdetails_time);
        txt_dropoffdetails_address = findViewById(R.id.txt_dropoffdetails_address);
        txt_dropoffdetails_spl = findViewById(R.id.txt_dropoffdetails_spl);
        txt_dropoffdetails_spl_details = findViewById(R.id.txt_dropoffdetails_spl_details);
        txt_dropoffdetails_name = findViewById(R.id.txt_dropoffdetails_name);
        txt_dropoffdetails_phone = findViewById(R.id.txt_dropoffdetails_phone);
        txt_dropoffdetails_arrive = findViewById(R.id.txt_dropoffdetails_arrive);
        txt_dropoffdetails_unload = findViewById(R.id.txt_dropoffdetails_unload);
        txt_dropoffdetails_docs = findViewById(R.id.txt_dropoffdetails_docs);
        txt_dropoffdetails_upload = findViewById(R.id.txt_dropoffdetails_upload);
        txt_dropoffdetails_go = findViewById(R.id.txt_dropoffdetails_go);
        edt_comments = findViewById(R.id.edt_comments);
        edt_od_name = findViewById(R.id.edt_od_name);
        txt_signature = findViewById(R.id.txt_signature);
        img_signature = findViewById(R.id.img_signature);
        img_startbuttonblack = findViewById(R.id.img_startbuttonblack);
        txt_start = findViewById(R.id.txt_start);
        btn_complete = findViewById(R.id.btn_complete);
        btn_deadrun = findViewById(R.id.btn_deadrun);

        txt_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSignature();
            }
        });

        img_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSignature();
            }
        });

        img_startbuttonblack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_startbuttonblack.setImageResource(R.drawable.startbuttongreen);
                back_btn_enable = false;

                txt_pickupdetails_arrive.setTextColor(getResources().getColor(R.color.black));
                txt_pickupdetails_arrive.setEnabled(true);

                sendPickStopDropArriveGoLocation(5);
            }
        });

        txt_pickupdetails_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickUploadPopUp();
            }
        });

        txt_pickupdetails_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickDocsPopUp();
            }
        });


        txt_pickupdetails_arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pickupdetails_arrive.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_pickupdetails_load.setEnabled(true);
                txt_pickupdetails_load.setTextColor(getResources().getColor(R.color.black));
                sendPickStopDropArriveGoLocation(6);
            }
        });

        txt_pickupdetails_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickLoadPopUp();
            }
        });

        txt_pickupdetails_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickUnitsPopUp();
            }
        });

        txt_pickupdetails_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickGoPopUp();
            }
        });

        txt_pickup_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_pickup_details.getVisibility() == View.GONE) {
                    layout_pickup_details.setVisibility(View.VISIBLE);
                    layout_stopover_details.setVisibility(View.GONE);
                    layout_dropoff_details.setVisibility(View.GONE);
                } else {
                    layout_pickup_details.setVisibility(View.GONE);
                }
            }
        });
        txt_stopover_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_stopover_details.getVisibility() == View.GONE) {
                    layout_pickup_details.setVisibility(View.GONE);
                    layout_stopover_details.setVisibility(View.VISIBLE);
                    layout_dropoff_details.setVisibility(View.GONE);
                } else {
                    layout_stopover_details.setVisibility(View.GONE);
                }
            }
        });
        txt_dropoff_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout_dropoff_details.getVisibility()==View.GONE) {
                    layout_pickup_details.setVisibility(View.GONE);
                    layout_stopover_details.setVisibility(View.GONE);
                    layout_dropoff_details.setVisibility(View.VISIBLE);
                }
                else{layout_dropoff_details.setVisibility(View.GONE);}
            }
        });

        txt_stopoverdetails_arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pickupdetails_go.setTextColor(getResources().getColor(R.color.grey));
                txt_pickupdetails_go.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
                txt_stopoverdetails_load.setTextColor(getResources().getColor(R.color.black));
                txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_stopoverdetails_load.setEnabled(true);
                sendPickStopDropArriveGoLocation(9);
            }
        });

        txt_stopoverdetails_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStopoverLoadPopUp();
            }
        });

        txt_stopoverdetails_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStopoverUnitsPopUp();
            }
        });

        txt_stopoverdetails_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStopoverDocsPopUp();
            }
        });

        txt_stopoverdetails_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStopoverUploadPopUp();
            }
        });

        txt_stopoverdetails_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStopoverGoPopUp();
                sendPickStopDropArriveGoLocation(11);
            }
        });

        txt_dropoffdetails_arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_dropoffdetails_arrive.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_dropoffdetails_unload.setEnabled(true);
                txt_dropoffdetails_unload.setTextColor(getResources().getColor(R.color.black));
                txt_dropoffdetails_go.setTextColor(getResources().getColor(R.color.black));
                sendPickStopDropArriveGoLocation(12);
            }
        });

        txt_dropoffdetails_unload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropoffUnloadPopUp();
            }
        });

        txt_dropoffdetails_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropoffDocsPopUp();
            }
        });

        txt_dropoffdetails_upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDropoffUploadPopUp();
            }
        });

        txt_dropoffdetails_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropoffGoPopUp();
                DropGoLocation();
            }
        });

        setDatas();
    }

    private void selectSignature() {

        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Signature");

        LayoutInflater inflator = LayoutInflater.from(this);
        View terms_layout =inflator.inflate(R.layout.signature_layout,null);

        final SignaturePad signaturePad = (SignaturePad) terms_layout.findViewById(R.id.signaturePad);
        final Button saveButton = (Button) terms_layout.findViewById(R.id.saveButton);
        final Button clearButton = (Button) terms_layout.findViewById(R.id.clearButton);

        alertDialogBuilder.setView(terms_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = signaturePad.getTransparentSignatureBitmap();
                alertDialog.dismiss();
                img_signature.setImageBitmap(bm);
                txt_signature.setVisibility(View.GONE);
                img_signature.setVisibility(View.VISIBLE);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
    }

    void setDatas()
    {
        txt_ref.setText(selectedOrder.OrderInfoId+"-"+selectedOrder.Sno);
        txt_vehicle_type.setText(selectedOrder.Type);
        txt_pickupdetails_time.setText(selectedOrder.PickTime);
        txt_pickupdetails_addrs.setText(selectedOrder.PickFrom);
        txt_pickupdetails_spl_details.setText(selectedOrder.PickSplIns);
        txt_pickupdetails_name.setText(selectedOrder.PickContactName);
        txt_pickupdetails_phone.setText(selectedOrder.PickContactNumber);
        txt_stopoverdetails_time.setText(selectedOrder.StopOverTime);
        txt_stopoverdetails_addrs.setText(selectedOrder.StopAddr);
        txt_stopoverdetails_spl_details.setText(selectedOrder.StopSplIns);
        txt_stopoverdetails_name.setText(selectedOrder.StopContactName);
        txt_stopoverdetails_phone.setText(selectedOrder.StopContactNumber);
        txt_dropoffdetails_time.setText(selectedOrder.DispatchTime);
        txt_dropoffdetails_address.setText(selectedOrder.DropOff);
        txt_dropoffdetails_spl_details.setText(selectedOrder.DtopSplIns);
        txt_dropoffdetails_name.setText(selectedOrder.DropContactName);
        txt_dropoffdetails_phone.setText(selectedOrder.DropContactNumber);

        /*Pickup Details*/
        if(selectedOrder.OrderStatus.equals("5")) // Start
        {
            layout_pickup_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttongreen);

            txt_pickupdetails_arrive.setEnabled(true);
            txt_pickupdetails_arrive.setTextColor(getResources().getColor(R.color.black));

            txt_pickupdetails_load.setEnabled(true);
            txt_pickupdetails_load.setTextColor(getResources().getColor(R.color.black));
        }
        else if(selectedOrder.OrderStatus.equals("6")) //Arrive Pickup
        {
            layout_pickup_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.black));

            txt_pickupdetails_arrive.setEnabled(false);
            txt_pickupdetails_arrive.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
        }
        else if(selectedOrder.OrderStatus.equals("7")) //Load Pickup
        {
            layout_pickup_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.grey));

            txt_pickupdetails_arrive.setEnabled(false);
            txt_pickupdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_pickupdetails_arrive.setTextColor(getResources().getColor(R.color.grey));

            txt_pickupdetails_load.setEnabled(true);
            txt_pickupdetails_load.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            txt_pickupdetails_load.setTextColor(getResources().getColor(R.color.black));

            txt_pickupdetails_units.setEnabled(true);
            txt_pickupdetails_units.setTextColor(getResources().getColor(R.color.black));

            txt_pickupdetails_go.setEnabled(true);
            txt_pickupdetails_go.setTextColor(getResources().getColor(R.color.black));
        }
        else if(selectedOrder.OrderStatus.equals("8")) //Leave Pickup
        {
            layout_stopover_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.grey));

            txt_pickupdetails_arrive.setEnabled(false);
            txt_pickupdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_pickupdetails_arrive.setTextColor(getResources().getColor(R.color.grey));

            txt_pickupdetails_load.setEnabled(false);
            txt_pickupdetails_load.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_pickupdetails_load.setTextColor(getResources().getColor(R.color.grey));

            txt_pickupdetails_units.setEnabled(false);
            txt_pickupdetails_units.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_pickupdetails_units.setTextColor(getResources().getColor(R.color.grey));

            txt_pickupdetails_go.setEnabled(false);
            txt_pickupdetails_go.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            txt_pickupdetails_go.setTextColor(getResources().getColor(R.color.black));

            //Stopover Details
            txt_stopoverdetails_arrive.setEnabled(true);
            txt_stopoverdetails_arrive.setTextColor(getResources().getColor(R.color.black));
        }

        /*Stopover Details*/
        else if(selectedOrder.OrderStatus.equals("9")) //Arrive Stop Over
        {
            layout_stopover_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_arrive.setEnabled(false);
            txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));

            txt_stopoverdetails_load.setEnabled(true);
            txt_stopoverdetails_load.setTextColor(getResources().getColor(R.color.black));
        }
        else if(selectedOrder.OrderStatus.equals("10")) //Load Stop Over
        {
            layout_pickup_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.grey));

            txt_stopoverdetails_arrive.setEnabled(false);
            txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_stopoverdetails_arrive.setTextColor(getResources().getColor(R.color.grey));

            txt_stopoverdetails_load.setEnabled(false);
            txt_stopoverdetails_load.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            txt_stopoverdetails_load.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_units.setEnabled(true);
            txt_stopoverdetails_units.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_go.setEnabled(true);
            txt_stopoverdetails_go.setTextColor(getResources().getColor(R.color.black));
        }
        else if(selectedOrder.OrderStatus.equals("11")) //Leave Stop Over
        {
            layout_dropoff_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.grey));

            txt_dropoffdetails_arrive.setEnabled(false);
            txt_dropoffdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_dropoffdetails_arrive.setTextColor(getResources().getColor(R.color.grey));

            txt_dropoffdetails_unload.setEnabled(false);
            txt_dropoffdetails_unload.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_dropoffdetails_unload.setTextColor(getResources().getColor(R.color.grey));

            txt_dropoffdetails_go.setEnabled(false);
            txt_dropoffdetails_go.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            txt_dropoffdetails_go.setTextColor(getResources().getColor(R.color.black));
        }

        /*Dropoff Details*/
        else if(selectedOrder.OrderStatus.equals("12")) //Drop Stop Over
        {
            layout_stopover_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_arrive.setEnabled(false);
            txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
        }
        else if(selectedOrder.OrderStatus.equals("13")) //Drop Stop Over
        {
            layout_pickup_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.grey));

            txt_stopoverdetails_arrive.setEnabled(false);
            txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_stopoverdetails_arrive.setTextColor(getResources().getColor(R.color.grey));

            txt_stopoverdetails_load.setEnabled(true);
            txt_stopoverdetails_load.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            txt_stopoverdetails_load.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_units.setEnabled(true);
            txt_stopoverdetails_units.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_go.setEnabled(true);
            txt_stopoverdetails_go.setTextColor(getResources().getColor(R.color.black));
        }
        else if(selectedOrder.OrderStatus.equals("14")) //Drop Stop Over
        {
            layout_pickup_details.setVisibility(View.VISIBLE);
            img_startbuttonblack.setEnabled(false);
            img_startbuttonblack.setImageResource(R.drawable.startbuttonblack);
            txt_start.setTextColor(getResources().getColor(R.color.grey));

            txt_stopoverdetails_arrive.setEnabled(false);
            txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            txt_stopoverdetails_arrive.setTextColor(getResources().getColor(R.color.grey));

            txt_stopoverdetails_load.setEnabled(true);
            txt_stopoverdetails_load.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            txt_stopoverdetails_load.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_units.setEnabled(true);
            txt_stopoverdetails_units.setTextColor(getResources().getColor(R.color.black));

            txt_stopoverdetails_go.setEnabled(true);
            txt_stopoverdetails_go.setTextColor(getResources().getColor(R.color.black));
        }
    }

    void showPickUploadPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View upload_layout =inflator.inflate(R.layout.popup_upload_pick,null);
        ButterKnife.bind(this);

        ImageView img_close = upload_layout.findViewById(R.id.img_close);
        Button btn_customer_sign = upload_layout.findViewById(R.id.btn_customer_sign);
        Button btn_upload_docs = upload_layout.findViewById(R.id.btn_upload_docs);

        alertDialogBuilder.setView(upload_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_customer_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_upload_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showPickDocsPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View docs_layout =inflator.inflate(R.layout.popup_docs_pickup,null);
        ButterKnife.bind(this);

        ImageView img_close = docs_layout.findViewById(R.id.img_close);
        RecyclerView docs_recyclerview = docs_layout.findViewById(R.id.docs_recyclerview);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        docs_recyclerview.setLayoutManager(llm);

        alertDialogBuilder.setView(docs_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showPickLoadPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View load_layout =inflator.inflate(R.layout.popup_load_pick,null);
        ButterKnife.bind(this);

        ImageView img_close = load_layout.findViewById(R.id.img_close);
        Button btn_start_loading = load_layout.findViewById(R.id.btn_start_loading);
        Button btn_deadrun = load_layout.findViewById(R.id.btn_deadrun);

        alertDialogBuilder.setView(load_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_start_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                txt_pickupdetails_arrive.setEnabled(false);
                txt_pickupdetails_arrive.setTextColor(getResources().getColor(R.color.grey));
                txt_pickupdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));

                txt_pickupdetails_load.setEnabled(false);
                txt_pickupdetails_load.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));

                txt_pickupdetails_units.setEnabled(true);
                txt_pickupdetails_units.setTextColor(getResources().getColor(R.color.black));

                txt_pickupdetails_go.setEnabled(true);
                txt_pickupdetails_go.setTextColor(getResources().getColor(R.color.black));
            }
        });

        btn_deadrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showPickUnitsPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View units_layout =inflator.inflate(R.layout.popup_units_pick,null);
        ButterKnife.bind(this);

        ImageView img_close = units_layout.findViewById(R.id.img_close);
        TextView txt_alloted_units = units_layout.findViewById(R.id.txt_alloted_units);
        final LinearLayout cb_linearlayout = units_layout.findViewById(R.id.cb_linearlayout);
        Button btn_submit = units_layout.findViewById(R.id.btn_submit);

        txt_alloted_units.setText("Alloted Units - "+selectedOrder.PickupAllotUnit);
        txt_alloted_units.setVisibility(View.VISIBLE);

        alertDialogBuilder.setView(units_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedNoOfUnit = 0;
                for (int i = 0; i < cb_linearlayout.getChildCount(); i++)
                {
                    if (((CheckBox) cb_linearlayout.getChildAt(i)).isChecked())
                    {
                        selectedNoOfUnit = selectedNoOfUnit + 1;
                    }
                }

                if (selectedNoOfUnit == Integer.parseInt(selectedOrder.PickupAllotUnit))
                {

                    alertDialog.dismiss();
                    txt_pickupdetails_units.setEnabled(true);
                    txt_pickupdetails_units.setTextColor(getResources().getColor(R.color.black));

                    String selectedUnits = "";

                    for (int i = 0; i < cb_linearlayout.getChildCount(); i++)
                    {
                        CheckBox focused_cb = ((CheckBox) cb_linearlayout.getChildAt(i));
                        if (focused_cb.isChecked())
                        {
                            if (selectedUnits.equals(""))
                            {
                                selectedUnits = selectedUnits + focused_cb.getText();
                            }
                            else
                            {
                                selectedUnits = selectedUnits + ", " + focused_cb.getText();
                            }
                        }
                    }
                    Log.e("selectedUnits ", selectedUnits);

                    showPickUnitsAlertPop(selectedUnits);
                }
                else if(selectedNoOfUnit > Integer.parseInt(selectedOrder.PickupAllotUnit))
                {
                    Toast.makeText(ActivityOrderDetails.this, "Please Select Only Alloted Number of Units", Toast.LENGTH_SHORT).show();
                }
                else if(selectedNoOfUnit < Integer.parseInt(selectedOrder.PickupAllotUnit))
                {
                    Toast.makeText(ActivityOrderDetails.this, "Please Select Alloted Number of Units", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_deadrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showPickUnitsAlertPop(String selectedUnits)
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View unit_alert_layout =inflator.inflate(R.layout.popup_pickup_unit_alert,null);
        ButterKnife.bind(this);

        TextView txt_message = unit_alert_layout.findViewById(R.id.txt_message);
        TextView txt_submit = unit_alert_layout.findViewById(R.id.txt_submit);
        TextView txt_recheck = unit_alert_layout.findViewById(R.id.txt_recheck);
        txt_message.setText("You have selected "+selectedUnits+". Are you sure to submit?");

        alertDialogBuilder.setView(unit_alert_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                txt_pickupdetails_units.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_pickupdetails_units.setEnabled(false);
                txt_pickupdetails_units.setTextColor(getResources().getColor(R.color.grey));

                txt_pickupdetails_load.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
                txt_pickupdetails_load.setTextColor(getResources().getColor(R.color.grey));
            }
        });

        txt_recheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showPickGoPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View go_layout =inflator.inflate(R.layout.popup_go_pick,null);
        ButterKnife.bind(this);

        ImageView img_close = go_layout.findViewById(R.id.img_close);
        Button btn_stopover = go_layout.findViewById(R.id.btn_stopover);
        Button btn_go_to_yard = go_layout.findViewById(R.id.btn_go_to_yard);

        alertDialogBuilder.setView(go_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_stopover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                txt_pickupdetails_go.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_pickupdetails_go.setEnabled(false);

                layout_stopover_details.setVisibility(View.VISIBLE);
                txt_stopoverdetails_arrive.setEnabled(true);
                txt_stopoverdetails_arrive.setTextColor(getResources().getColor(R.color.black));
                sendPickStopDropArriveGoLocation(8);
            }
        });

        btn_go_to_yard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showStopoverLoadPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View load_layout =inflator.inflate(R.layout.popup_load_stopover,null);
        ButterKnife.bind(this);

        ImageView img_close_stopover = load_layout.findViewById(R.id.img_close_stopover);
        Button btn_start_loading_stopover = load_layout.findViewById(R.id.btn_start_loading_stopover);
        Button btn_deadrun_stopover = load_layout.findViewById(R.id.btn_deadrun_stopover);

        alertDialogBuilder.setView(load_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close_stopover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_start_loading_stopover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                txt_stopoverdetails_arrive.setEnabled(false);
                txt_stopoverdetails_arrive.setTextColor(getResources().getColor(R.color.grey));
                txt_stopoverdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));

                txt_stopoverdetails_units.setEnabled(true);
                txt_stopoverdetails_units.setTextColor(getResources().getColor(R.color.black));
                txt_stopoverdetails_go.setTextColor(getResources().getColor(R.color.black));
            }
        });

        btn_deadrun_stopover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showStopoverUnitsPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View units_layout =inflator.inflate(R.layout.popup_units_stopover,null);
        ButterKnife.bind(this);

        ImageView img_close = units_layout.findViewById(R.id.img_close);
        TextView txt_alloted_units = units_layout.findViewById(R.id.txt_alloted_units);
        final LinearLayout cb_linearlayout = units_layout.findViewById(R.id.cb_linearlayout);
        Button btn_submit = units_layout.findViewById(R.id.btn_submit);

        txt_alloted_units.setText("Alloted Units - "+selectedOrder.StopOverAllotUnit);
        txt_alloted_units.setVisibility(View.VISIBLE);

        alertDialogBuilder.setView(units_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedNoOfUnit = 0;
                for (int i = 0; i < cb_linearlayout.getChildCount(); i++)
                {
                    if (((CheckBox) cb_linearlayout.getChildAt(i)).isChecked())
                    {
                        selectedNoOfUnit = selectedNoOfUnit + 1;
                    }
                }

                if (selectedNoOfUnit == Integer.parseInt(selectedOrder.StopOverAllotUnit))
                {

                    alertDialog.dismiss();
                    txt_pickupdetails_units.setEnabled(true);
                    txt_pickupdetails_units.setTextColor(getResources().getColor(R.color.black));

                    String selectedUnits = "";

                    for (int i = 0; i < cb_linearlayout.getChildCount(); i++)
                    {
                        CheckBox focused_cb = ((CheckBox) cb_linearlayout.getChildAt(i));
                        if (focused_cb.isChecked())
                        {
                            if (selectedUnits.equals(""))
                            {
                                selectedUnits = selectedUnits + focused_cb.getText();
                            }
                            else
                            {
                                selectedUnits = selectedUnits + ", " + focused_cb.getText();
                            }
                        }
                    }
                    Log.e("selectedUnits ", selectedUnits);

                    showStopoverUnitsAlertPop(selectedUnits);
                }
                else if(selectedNoOfUnit > Integer.parseInt(selectedOrder.StopOverAllotUnit))
                {
                    Toast.makeText(ActivityOrderDetails.this, "Please Select Only Alloted Number of Units", Toast.LENGTH_SHORT).show();
                }
                else if(selectedNoOfUnit < Integer.parseInt(selectedOrder.StopOverAllotUnit))
                {
                    Toast.makeText(ActivityOrderDetails.this, "Please Select Alloted Number of Units", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_deadrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showStopoverUnitsAlertPop(String selectedUnits)
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View unit_alert_layout =inflator.inflate(R.layout.popup_stopover_unit_alert,null);
        ButterKnife.bind(this);

        TextView txt_message = unit_alert_layout.findViewById(R.id.txt_message);
        TextView txt_submit = unit_alert_layout.findViewById(R.id.txt_submit);
        TextView txt_recheck = unit_alert_layout.findViewById(R.id.txt_recheck);
        txt_message.setText("You have selected "+selectedUnits+". Are you sure to submit?");

        alertDialogBuilder.setView(unit_alert_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                txt_stopoverdetails_units.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_stopoverdetails_units.setEnabled(false);
                txt_stopoverdetails_units.setTextColor(getResources().getColor(R.color.grey));

                txt_stopoverdetails_load.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
                txt_stopoverdetails_load.setTextColor(getResources().getColor(R.color.grey));
                txt_stopoverdetails_go.setEnabled(true);
            }
        });

        txt_recheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showStopoverGoPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View go_layout =inflator.inflate(R.layout.popup_go_stopover,null);
        ButterKnife.bind(this);

        ImageView img_close = go_layout.findViewById(R.id.img_close);
        Button btn_dropoff = go_layout.findViewById(R.id.btn_dropoff);
        Button btn_go_to_yard = go_layout.findViewById(R.id.btn_go_to_yard);

        alertDialogBuilder.setView(go_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                txt_stopoverdetails_go.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
                txt_stopoverdetails_go.setEnabled(false);

                layout_stopover_details.setVisibility(View.GONE);
                layout_dropoff_details.setVisibility(View.VISIBLE);
                txt_dropoffdetails_arrive.setEnabled(true);
                txt_dropoffdetails_arrive.setTextColor(getResources().getColor(R.color.black));
            }
        });

        btn_go_to_yard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showStopoverDocsPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View docs_layout =inflator.inflate(R.layout.popup_docs_pickup,null);
        ButterKnife.bind(this);

        ImageView img_close = docs_layout.findViewById(R.id.img_close);
        RecyclerView docs_recyclerview = docs_layout.findViewById(R.id.docs_recyclerview);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        docs_recyclerview.setLayoutManager(llm);

        alertDialogBuilder.setView(docs_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showStopoverUploadPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View upload_layout =inflator.inflate(R.layout.popup_upload_stopover,null);
        ButterKnife.bind(this);

        ImageView img_close = upload_layout.findViewById(R.id.img_close);
        Button btn_customer_sign_stopover = upload_layout.findViewById(R.id.btn_customer_sign_stopover);
        Button btn_upload_docs_stopover = upload_layout.findViewById(R.id.btn_upload_docs_stopover);

        alertDialogBuilder.setView(upload_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_customer_sign_stopover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_upload_docs_stopover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showDropoffUnloadPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View load_layout =inflator.inflate(R.layout.popup_unload_dropoff,null);
        ButterKnife.bind(this);

        ImageView img_close = load_layout.findViewById(R.id.img_close);
        Button btn_start_unloading_dropoff = load_layout.findViewById(R.id.btn_start_unloading_dropoff);
        Button btn_deadrun_dropoff = load_layout.findViewById(R.id.btn_deadrun_dropoff);

        alertDialogBuilder.setView(load_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_start_unloading_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                txt_dropoffdetails_arrive.setEnabled(false);
                txt_dropoffdetails_arrive.setTextColor(getResources().getColor(R.color.grey));
                txt_dropoffdetails_arrive.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
                txt_dropoffdetails_go.setEnabled(true);
                txt_dropoffdetails_go.setTextColor(getResources().getColor(R.color.black));
                txt_dropoffdetails_unload.setTextColor(getResources().getColor(R.color.grey));
                txt_dropoffdetails_unload.setBackground(getResources().getDrawable(R.drawable.light_orange_button_drawable));
            }
        });

        btn_deadrun_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showDropoffGoPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View go_layout =inflator.inflate(R.layout.popup_go_dropoff,null);
        ButterKnife.bind(this);

        ImageView img_close = go_layout.findViewById(R.id.img_close);
        Button btn_continue_goto_yard_dropoff = go_layout.findViewById(R.id.btn_continue_goto_yard_dropoff);
        Button btn_comple_to_yard_dropoff = go_layout.findViewById(R.id.btn_comple_to_yard_dropoff);

        alertDialogBuilder.setView(go_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_continue_goto_yard_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                txt_dropoffdetails_go.setBackground(getResources().getDrawable(R.drawable.green_button_drawable));
            }
        });

        btn_comple_to_yard_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showDropoffDocsPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View docs_layout =inflator.inflate(R.layout.popup_docs_dropoff,null);
        ButterKnife.bind(this);

        ImageView img_close = docs_layout.findViewById(R.id.img_close);
        RecyclerView docs_recyclerview = docs_layout.findViewById(R.id.docs_recyclerview);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        docs_recyclerview.setLayoutManager(llm);

        alertDialogBuilder.setView(docs_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    void showDropoffUploadPopUp()
    {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        LayoutInflater inflator = LayoutInflater.from(this);
        View upload_layout =inflator.inflate(R.layout.popup_upload_dropoff,null);
        ButterKnife.bind(this);

        ImageView img_close = upload_layout.findViewById(R.id.img_close);
        Button btn_customer_sign_dropoff = upload_layout.findViewById(R.id.btn_customer_sign_dropoff);
        Button btn_upload_docs_dropoff = upload_layout.findViewById(R.id.btn_upload_docs_dropoff);

        alertDialogBuilder.setView(upload_layout);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        alertDialog.getWindow().setLayout((int)(displayWidth*0.7f), ViewGroup.LayoutParams.WRAP_CONTENT); //Controlling width and height.

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_customer_sign_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_upload_docs_dropoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void sendPickStopDropArriveGoLocation(final int selectedAction){

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = WEBSERVICE_URL+OP_UPDATE_ORDER_STATUS;
        Log.e("U_DriverLocationURL",url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    Log.e("U_DriverLocationRespons", response);

                }
                catch (Exception e)
                {
                    Log.e("Catch",e+"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("DriverId",UserDetailsModel.Id);
                params.put("Status",String.valueOf(selectedAction));
                params.put("OrderId",selectedOrder.OrderId);
                gps = new GPSTracker(ActivityOrderDetails.this);
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                params.put("Latitude",String.valueOf(latitude));
                params.put("Longitude",String.valueOf(longitude));
                Log.e("U_DriverLocationPar",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    public void DropGoLocation(){
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/GetTotalOrderHours";
        Log.e("U_DriverLocationURL",url);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    Log.e("U_DriverLocationRespons", response);

                }
                catch (Exception e)
                {
                    Log.e("Catch",e+"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("DriverId",UserDetailsModel.Id);
                params.put("OrderId",selectedOrder.OrderId);
                params.put("Latitude",String.valueOf(latitude));
                params.put("Longitude",String.valueOf(longitude));
                Log.e("U_DriverLocationPar",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    @Override
    public void onBackPressed() {
        if(back_btn_enable==false)
        {

        }
        else
        {
            finish();
        }
    }
}
