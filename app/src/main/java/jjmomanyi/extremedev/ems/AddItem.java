package jjmomanyi.extremedev.ems;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static jjmomanyi.extremedev.ems.URLS.url;

public class AddItem extends AppCompatActivity
{
    EditText sname,sid,rname,rID,itemID,sphone,rhome,rphone;
    Button btn;
    TextView date,show_itemIDbtn,showItemID;
//http://o3.allalla.com/EMS/EMS/cheker.php
    String registerurl=url+"register.php";
    String checker = url+"cheker.php";
    String item_id;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        sname=(EditText)findViewById(R.id.editText);
        sid=(EditText)findViewById(R.id.editText2);
        sphone=(EditText)findViewById(R.id.editText3);
        rname=(EditText)findViewById(R.id.editText4);
        rID=(EditText)findViewById(R.id.editText5);
        rphone=(EditText)findViewById(R.id.editText7);
        rhome=(EditText)findViewById(R.id.editText6);
        showItemID=(TextView)findViewById(R.id.textView8);
        btn=(Button)findViewById(R.id.button);
        item_id="EMS-KENYA"+String.valueOf(System.currentTimeMillis());
        showItemID.setText(item_id);



        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date datee= new Date();
        String dater=dateFormat.format(datee);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                   String sender_name=sname.getText().toString();
                    String sender_id=sid.getText().toString();
                    String sender_home=sphone.getText().toString();
                    String rece_name=rname.getText().toString();
                    String rece_ID=rID.getText().toString();
                    String re_home=rhome.getText().toString();

                    String reveiver_phone=rphone.getText().toString();



                if(verify()==true)
                {
                    register_items(sender_name,sender_id,sender_home,rece_name,rece_ID,re_home,reveiver_phone,"",item_id);
                }




            }
        });











    }



    public boolean verify()
    {
        boolean valid=true;
        if (sid.getText().toString().length()!=8||sid.getText().toString().isEmpty())
        {
            sid.setError("Please enter a valid ID number");
            valid=false;
        }
        else
        {
            sid.setError(null);
        }
        if(sphone.getText().toString().isEmpty()||sphone.getText().toString().length()!=10)
        {
            sphone.setError("Enter a valid phone number");
            valid=false;
        }

        else
        {
            sphone.setError(null);
        }

        if(rname.getText().toString().length()<3||rname.getText().toString().isEmpty())
        {
            rname.setError("Enter a valid name");
            valid=false;
        }
        else
        {
            rname.setError(null);
        }
        if(rphone.getText().toString().length()!=10)
        {
            rphone.setError("Enter a valid phone number");
            valid=false;
        }
        else
        {
            rphone.setError(null);
        }

        if (rID.getText().toString().length()!=8|| rID.getText().toString().isEmpty())
        {
            rID.setError("Please enter a valid ID number");
            valid=false;
        }
        else
        {
            rID.setError(null);
        }
         if (sname.getText().toString().isEmpty()||sname.getText().toString().length()<3)
        {
            sname.setError("please enter the sender name");
            valid=false;
        }
         else
         {
             sname.setError(null);

         }
           if(rhome.getText().toString().length()<2||rhome.getText().toString().isEmpty())
        {
            rhome.setError("Enter a valid destination");
            valid = false;
        }
        else {

            rhome.setError(null);
        }


        return valid;
    }

    public void register_items(final String sname,final String sID,final String sender_home, final String rname,final String rID,final String rhome,final String reveiver_phone,final String date,final String item_id)
    {

        class AddEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(AddItem.this,null,"Please wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... v)
            {
                HashMap<String,String> params = new HashMap<>();
                params.put("sname",sname);
                params.put("sID",sID);
                params.put("sphone",sender_home);
                params.put("rname",rname);
                params.put("rID",rID);
                params.put("rhome",rhome);
                params.put("date",date);
                params.put("item_id",item_id);
                params.put("reveiver_phone",reveiver_phone);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(registerurl, params);
                return res;

            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                loader(s);
            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();



    }

    public void loader(final String json)
    {
        try {
            JSONObject jsonObject= new JSONObject(json);
            JSONArray jsonArray =jsonObject.getJSONArray("result");
            JSONObject c= jsonArray.getJSONObject(0);

            int status=c.getInt("status");

            if (status==1)
            {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(sphone.getText().toString(),null,"Item ID:\n"+item_id,null,null);
                smsManager.sendTextMessage(rphone.getText().toString(),null,"Item ID:\n"+item_id,null,null);

                new SweetAlertDialog(AddItem.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Content added successfully")
                        .setContentText("Sender and receiver notified by sms")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                               finish();
                            }
                        })
                        .show();




            }
            else if(status==0)
            {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Please try again")
                        .show();

            }
            else {

                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Please try again")
                        .show();
            }




        } catch (JSONException e)
        {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Check your internet connection")
                    .show();

        }


    }

}
