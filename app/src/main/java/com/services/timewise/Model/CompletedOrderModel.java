package com.services.timewise.Model;

import android.util.Log;

import org.json.JSONObject;

public class CompletedOrderModel {

    public String OrderId;
    public String ContainerInfo;
    public String PickDate;
    public String PickTime;
    public String Company;
    public String PickContactName;
    public String PickContactNumber;
    public String PickSplIns;
    public String TotalHours;
    public String PickFrom;
    public String StopAddr;
    public String DropOff;

    public CompletedOrderModel(JSONObject data)
    {
        try {
            OrderId = data.getString("OrderId");
            ContainerInfo = data.getString("OrderInfoId")+"-"+data.getString("Sno");
            PickDate = data.getString("PickDate");
            PickTime = data.getString("PickTime");
            Company = data.getString("Company");
            PickContactName = data.getString("PickContactName");
            PickContactNumber = data.getString("PickContactNumber");
            PickSplIns = data.getString("PickSplIns");
            TotalHours = "Total Hours : "+data.getString("TotalHours");
            PickFrom = data.getString("PickFrom");
            StopAddr = data.getString("StopAddr");
            DropOff = data.getString("DropOff");
        }
        catch (Exception e)
        {
            Log.e("ComplOrderModelCatch",e+"");
        }
    }
}
