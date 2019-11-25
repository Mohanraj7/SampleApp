package com.services.timewise.Model;

import android.util.Log;

import org.json.JSONObject;

public class NextDayOrderModel{

    public String Company;
    public String OrderId;
    public String OrderInfoId;
    public String Phone;
    public String PickDate;
    public String PickTime;
    public String Type;
    public String PickFrom;
    public String PickSplIns;
    public String StopAddr;
    public String DropOff;
    public String Sno;
    public String TotalHours;
    public String PickContactName;
    public String PickContactNumber;
    public String PickNoUnits;
    public String StopOverDate;
    public String StopSplIns;
    public String StopContactName;
    public String StopNoUnits;
    public String DtopSplIns;
    public String DropContactName;

    public NextDayOrderModel(JSONObject data)
    {
        try {
            OrderId = data.getString("OrderId");
            Phone = data.getString("Phone");
            OrderInfoId = data.getString("OrderInfoId");
            Sno = data.getString("Sno");
            Type = data.getString("Type");
            PickDate = data.getString("PickDate");
            PickTime = data.getString("PickTime");
            Company = data.getString("Company");
            PickContactName = data.getString("PickContactName");
            PickContactNumber = data.getString("PickContactNumber");
            PickSplIns = data.getString("PickSplIns");
            TotalHours = "Total Hours : "+data.getString("TotalHours");
            PickFrom = data.getString("PickFrom");
            PickNoUnits = data.getString("PickNoUnits");
            StopAddr = data.getString("StopAddr");
            StopOverDate = data.getString("StopOverDate");
            StopSplIns = data.getString("StopSplIns");
            StopContactName = data.getString("StopContactName");
            StopNoUnits = data.getString("StopNoUnits");
            DropOff = data.getString("DropOff");
            DtopSplIns = data.getString("DtopSplIns");
            DropContactName = data.getString("DropContactName");
        }
        catch (Exception e)
        {
            Log.e("NextDayOrderModelCatch",e+"");
        }
    }
}
