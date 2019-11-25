package com.services.timewise.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.services.timewise.Model.CurrentDayOrderModel;
import com.services.timewise.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentDayOrderDetails extends AppCompatActivity {
    @BindView(R.id.txt_ref_no)
    TextView txt_ref_no;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_type)
    TextView txt_type;
    @BindView(R.id.txt_company)
    TextView txt_company;
    @BindView(R.id.txt_contact)
    TextView txt_contact;
    @BindView(R.id.txt_pickup_location)
    TextView txt_pickup_location;
    @BindView(R.id.txt_pickup_date)
    TextView txt_pickup_date;
    @BindView(R.id.txt_pickup_spl_inst)
    TextView txt_pickup_spl_inst;
    @BindView(R.id.txt_pickup_contact)
    TextView txt_pickup_contact;
    @BindView(R.id.txt_pickup_unit)
    TextView txt_pickup_unit;
    @BindView(R.id.stop_layout)
    LinearLayout stop_layout;
    @BindView(R.id.txt_stopover_location)
    TextView txt_stopover_location;
    @BindView(R.id.txt_stopover_date)
    TextView txt_stopover_date;
    @BindView(R.id.txt_stopover_spl_inst)
    TextView txt_stopover_spl_inst;
    @BindView(R.id.txt_stopover_contact)
    TextView txt_stopover_contact;
    @BindView(R.id.txt_stopover_unit)
    TextView txt_stopover_unit;
    @BindView(R.id.txt_dropoff_location)
    TextView txt_dropoff_location;
    @BindView(R.id.txt_dropoff_spl_inst)
    TextView txt_dropoff_spl_inst;
    @BindView(R.id.txt_dropoff_contact)
    TextView txt_dropoff_contact;

    ImageView img_backbtn;

    public static CurrentDayOrderModel selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentday_orders_details);

        ButterKnife.bind(this);

        img_backbtn = findViewById(R.id.img_backbtn);

        img_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CurrentDayOrderDetails.this,Dashboard.class);
                startActivity(i);
            }
        });
        setDatas();
    }

    void setDatas()
    {
        txt_ref_no.setText(selectedOrder.OrderInfoId+"-"+selectedOrder.Sno);
        txt_date.setText(selectedOrder.PickDate);
        txt_type.setText(selectedOrder.Type);
        txt_company.setText(selectedOrder.Company);
        txt_contact.setText(selectedOrder.CallerName+" / "+selectedOrder.Phone);
        txt_pickup_location.setText(selectedOrder.PickFrom);
        txt_pickup_date.setText(selectedOrder.PickDate+" "+selectedOrder.PickTime);
        txt_pickup_spl_inst.setText(selectedOrder.PickSplIns);
        txt_pickup_contact.setText(selectedOrder.PickContactName+" /. "+selectedOrder.PickContactNumber);
        txt_pickup_unit.setText(selectedOrder.PickupAllotUnit);
        txt_stopover_location.setText(selectedOrder.StopAddr);
        txt_stopover_date.setText(selectedOrder.StopOverDate+" "+selectedOrder.StopOverTime);
        txt_stopover_spl_inst.setText(selectedOrder.StopSplIns);
        txt_stopover_contact.setText(selectedOrder.StopContactName+" / "+selectedOrder.StopContactNumber);
        txt_stopover_unit.setText(selectedOrder.StopOverAllotUnit);
        if(selectedOrder.StopOverAllotUnit.equals("0"))
        {
            stop_layout.setVisibility(View.GONE);
        }
        txt_dropoff_location.setText(selectedOrder.DropOff);
        txt_dropoff_spl_inst.setText(selectedOrder.DtopSplIns);
        txt_dropoff_contact.setText(selectedOrder.DropContactName+" / "+selectedOrder.DropContactNumber);

    }

}
