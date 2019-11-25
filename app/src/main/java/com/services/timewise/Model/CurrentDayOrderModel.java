package com.services.timewise.Model;

import android.util.Log;

import org.json.JSONObject;

public class CurrentDayOrderModel {

    public String OrderId;
    public String OrderInfoId;
    public String Sno;
    public String CallerName;
    public String Company;
    public String PickDate;
    public String PickTime;
    public String OrderStatus;
    public String StatusTitle;
    public String Phone;
    public String Type;
    public String PickContactName;
    public String PickContactNumber;
    public String PickSplIns;
    public String TotalHours;
    public String PickFrom;
    public String PickNoUnits;
    public String PickupAllotUnit;
    public String StopOverAllotUnit;
    public String StopAddr;
    public String StopOverDate;
    public String StopOverTime;
    public String StopSplIns;
    public String StopContactName;
    public String StopContactNumber;
    public String StopNoUnits;
    public String DropOff;
    public String DtopSplIns;
    public String DropContactName;
    public String DropContactNumber;
    public String DispatchTime;

    public CurrentDayOrderModel(JSONObject data)
    {
        try
        {
            OrderId = data.getString("OrderId");
            CallerName = data.getString("CallerName");
            Company = data.getString("Company");
            PickDate = data.getString("PickDate");
            PickTime = data.getString("PickTime");
            StatusTitle = data.getString("StatusTitle");
            OrderStatus = data.getString("Status");
            Phone = data.getString("Phone");
            OrderInfoId = data.getString("OrderInfoId");
            Sno = data.getString("Sno");
            Type = data.getString("Type");
            PickContactName = data.getString("PickContactName");
            PickContactNumber = data.getString("PickContactNumber");
            PickSplIns = data.getString("PickSplIns");
            TotalHours = "Total Hours : "+data.getString("TotalHours");
            PickFrom = data.getString("PickFrom");
            PickNoUnits = data.getString("PickNoUnits");
            PickupAllotUnit = data.getString("PickupAllotUnit");
            StopOverAllotUnit = data.getString("StopOverAllotUnit");
            StopAddr = data.getString("StopAddr");
            StopOverDate = data.getString("StopOverDate");
            StopOverTime = data.getString("StopOverTime");
            StopSplIns = data.getString("StopSplIns");
            StopContactName = data.getString("StopContactName");
            StopContactNumber = data.getString("StopContactNumber");
            StopNoUnits = data.getString("StopNoUnits");
            DropOff = data.getString("DropOff");
            DtopSplIns = data.getString("DtopSplIns");
            DropContactName = data.getString("DropContactName");
            DropContactNumber = data.getString("DropContactNumber");
            DispatchTime = data.getString("DispatchTime");
        }
        catch (Exception e)
        {
            Log.e("OrderModelException",e+"");
        }
    }

}
