package com.services.timewise.Model;

import android.util.Log;

import org.json.JSONObject;

public class UserDetailsModel
{
    public static String Id = "";
    public static String FirstName = "";
    public static String MiddleName = "";
    public static String LastName = "";
    public static String NickName = "";

    public static String ProfilePath = "";

    public static String LicenseNumber = "";
    public static String LicenseState = "";
    public static String LicenseExpiry = "";

    public static String Phone = "";
    public static String Email = "";
    public static String Address1 = "";
    public static String Address2 = "";
    public static String City = "";
    public static String State = "";
    public static String ZipCode = "";
    public static String Dob = "";

    public static String UserId = "";
    public static String Password = "";
    public static String Status = "";

    public static String Latitude = "";
    public static String Longitude = "";

    public UserDetailsModel() {

    }

    public UserDetailsModel(JSONObject userdetails)
    {
        try {
            Id = userdetails.getString("Id");
            FirstName = userdetails.getString("FirstName");
            MiddleName = userdetails.getString("MiddleName");
            LastName = userdetails.getString("LastName");
            NickName = userdetails.getString("NickName");

            ProfilePath = userdetails.getString("ProfilePath");

            LicenseNumber = userdetails.getString("LicenseNumber");
            LicenseState = userdetails.getString("LicenseState");
            LicenseExpiry = userdetails.getString("LicenseExpiry");

            Phone = userdetails.getString("Phone");
            Email = userdetails.getString("Email");
            Address1 = userdetails.getString("Address1");
            Address2 = userdetails.getString("Address2");
            City = userdetails.getString("City");
            State = userdetails.getString("State");
            ZipCode = userdetails.getString("ZipCode");
            Dob = userdetails.getString("Dob");

            UserId = userdetails.getString("UserId");
            Password = userdetails.getString("Password");
            Status = userdetails.getString("OrderStatus");

            Latitude = userdetails.getString("Latitude");
            Longitude = userdetails.getString("Longitude");
        }
        catch (Exception e)
        {
            Log.e("UserModelCatch",e+"");
        }
    }
}
