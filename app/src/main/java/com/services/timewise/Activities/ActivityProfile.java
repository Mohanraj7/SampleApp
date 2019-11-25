package com.services.timewise.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityProfile extends AppCompatActivity {

    @BindView(R.id.txt_full_name)
    TextView txt_full_name;
    @BindView(R.id.txt_nick_name)
    TextView txt_nick_name;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_phone)
    TextView txt_phone;
    @BindView(R.id.txt_dob)
    TextView txt_dob;

    @BindView(R.id.txt_lic_no)
    TextView txt_lic_no;
    @BindView(R.id.txt_lic_state)
    TextView txt_lic_state;
    @BindView(R.id.txt_lic_exp)
    TextView txt_lic_exp;

    @BindView(R.id.txt_street)
    TextView txt_street;
    @BindView(R.id.txt_city)
    TextView txt_city;
    @BindView(R.id.txt_state)
    TextView txt_state;
    @BindView(R.id.txt_country)
    TextView txt_country;
    @BindView(R.id.txt_zip)
    TextView txt_zip;

    ImageView img_editpersonaldetails;
    ImageView img_editlicensedetails;
    ImageView img_editaddressdetails;
    ImageView img_back;
    TextView txtvw_changepassword;


    ImageView img_propic;
    ImageView imgbtn_uploadpropic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        img_propic = findViewById(R.id.img_propic);
        imgbtn_uploadpropic = findViewById(R.id.imgbtn_uploadpropic);
        img_back = findViewById(R.id.img_back);
        img_editpersonaldetails = findViewById(R.id.img_editpersonaldetails);
        img_editlicensedetails = findViewById(R.id.img_editlicensedetails);
        img_editaddressdetails = findViewById(R.id.img_editaddressdetails);
        txtvw_changepassword = findViewById(R.id.txtvw_changepassword);

        getProfilePhotoTask();

        imgbtn_uploadpropic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("111", "start1");
                selectImage();
            }
        });
        img_editpersonaldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOfEditPersonalDetailsButton();
            }
        });
        img_editlicensedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOfEditLicenseDetails();
            }
        });
        img_editaddressdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOfEditAddressDetails();
            }
        });
        txtvw_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOfChangePasswordText();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDats();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void setDats()
    {
        txt_full_name.setText(UserDetailsModel.FirstName+" "+UserDetailsModel.MiddleName+" "+UserDetailsModel.LastName);
        txt_nick_name.setText(UserDetailsModel.NickName);
        txt_email.setText(UserDetailsModel.Email);
        txt_phone.setText(UserDetailsModel.Phone);
        txt_dob.setText(UserDetailsModel.Dob);
        txt_lic_no.setText(UserDetailsModel.LicenseNumber);
        txt_lic_state.setText(UserDetailsModel.LicenseState);
        txt_lic_exp.setText(UserDetailsModel.LicenseExpiry);
        txt_street.setText(UserDetailsModel.Address1);
        txt_city.setText(UserDetailsModel.Address2);
        txt_state.setText(UserDetailsModel.State);
        txt_state.setText("Country");
        txt_zip.setText(UserDetailsModel.ZipCode);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onClickOfEditPersonalDetailsButton() {
        Intent intent = new Intent(ActivityProfile.this, ActivityEditPersonalDetails.class);
        startActivity(intent);
    }

    public void onClickOfEditLicenseDetails() {
        Intent intent = new Intent(ActivityProfile.this, ActivityEditLicenseDetails.class);
        startActivity(intent);
    }

    public void onClickOfEditAddressDetails() {
        Intent intent = new Intent(ActivityProfile.this, ActivityEditAddressDetails.class);
        startActivity(intent);
    }

    public void onClickOfChangePasswordText() {
        Intent intent = new Intent(ActivityProfile.this, ActivityChangePassword.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == 1) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    File imgFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

                    try {
                        imgFile.createNewFile();
                        FileOutputStream fo = new FileOutputStream(imgFile);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //img_propic.setImageBitmap(thumbnail);
                    bitmapTObase64Convertor(thumbnail);
                } else if (requestCode == 2) {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //img_propic.setImageBitmap(selectedImage);
                    bitmapTObase64Convertor(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void bitmapTObase64Convertor(Bitmap selectedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        profilePhotoUploadTask(ConvertImage);
    }

    private void profilePhotoUploadTask(final String ConvertImage) {
        Log.e("pput", "1.1");
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        final String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/UpdateProfilePhoto";

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    Log.e("UploadPhotoResponse", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String ResponseCode = jsonObject.getString("ResponseCode");
                    String ResponseMessage = jsonObject.getString("ResponseMessage");
                    if(ResponseCode.equals("1"))
                    {
                        String imageurl = jsonObject.getString("ResponseData");

                        Picasso.with(ActivityProfile.this).load(imageurl).into(img_propic);

                    }
                }
                catch (Exception e)
                {
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
                params.put("Photo", ConvertImage);

                Log.e("UploadPhotoRequest", url);
                Log.e("UploadPhotoParams",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //Setup precondition to execute some task
        }

        @Override
        protected String doInBackground(String... params) {
            //Do some task
            getProfilePhotoTask();

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            //Show the result obtained from doInBackground
        }
    }

    private void getProfileDetailsTask () {
        final ProgressDialog pDialog = new ProgressDialog(ActivityProfile.this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(ActivityProfile.this);

        final String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/GetProfilePhoto";

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                pDialog.dismiss();


                try {
                    Log.e("GetPhotoResponse", response);
                    JSONObject jsobj = new JSONObject(response);
                    String imageurl = jsobj.getString("ResponseData");

                    Picasso.with(ActivityProfile.this).load(imageurl).into(img_propic);

                }
                catch (Exception e)
                {
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

                Log.e("GetPhotoRequest", url);
                Log.e("GetPhotoParams",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    private void getProfilePhotoTask () {
        final ProgressDialog pDialog = new ProgressDialog(ActivityProfile.this);
        pDialog.setMessage("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(ActivityProfile.this);

        final String url = "http://64.15.141.244/TimewiseDriverApi/api/Web/GetProfilePhoto";

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                pDialog.dismiss();


                try {
                    Log.e("GetPhotoResponse", response);
                    JSONObject jsobj = new JSONObject(response);
                    String imageurl = jsobj.getString("ResponseData");

                    Picasso.with(ActivityProfile.this).load(imageurl).into(img_propic);

                }
                catch (Exception e)
                {
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

                Log.e("GetPhotoRequest", url);
                Log.e("GetPhotoParams",params+"");
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
