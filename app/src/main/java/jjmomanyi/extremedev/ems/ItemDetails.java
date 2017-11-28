package jjmomanyi.extremedev.ems;

import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.*;
import static jjmomanyi.extremedev.ems.URLS.url;

public class ItemDetails extends AppCompatActivity {
    String itemid;
    TextView itemidv;
    Switch aSwitch;
    TextView textdate, textsname, textsid, textsphone, textrname, textrid, textrphone, textdestination;
   
    //String fetcher = "http://o3.allalla.com/EMS/EMS/getone.php";
    //String picked = "http://o3.allalla.com/EMS/EMS/picked.php";
    String checker = url+"cheker.php";
    String fetcher = url+"getone.php";
    String picked = url+"picked.php";
    Button btn1, bnt2;

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
        setContentView(R.layout.activity_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        itemid = intent.getStringExtra("itemid");
        aSwitch = (Switch) findViewById(R.id.switch1);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aSwitch.isChecked()) {
                    markasPicked(itemid);

                }
            }
        });


        btn1 = (Button) findViewById(R.id.button2);
        bnt2 = (Button) findViewById(R.id.button3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SweetAlertDialog(ItemDetails.this)
                        .setTitleText("Do you want to call or text?")
                        .setConfirmText("Call")
                        .setConfirmClickListener(new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent t = new Intent(Intent.ACTION_CALL);
                                t.setData(Uri.parse("tel:" + textsphone.getText().toString()));
                                if (ActivityCompat.checkSelfPermission(ItemDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(ItemDetails.this, "Call permissions not granted", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                startActivity(t);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Text")
                        .setCancelClickListener(new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(textsphone.getText().toString(),null,"EMS-KENYA\n"+"Item ID:\n"+itemid+" has never been picked up yet. Please follow it up",null,null);
                                new SweetAlertDialog(ItemDetails.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Message sent")
                                        .show();



                            }
                        })
                        .show();








            }
        });
        bnt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SweetAlertDialog(ItemDetails.this)
                        .setTitleText("Do you want to call or text?")
                        .setConfirmText("Call")
                        .setConfirmClickListener(new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent t = new Intent(Intent.ACTION_CALL);
                                t.setData(Uri.parse("tel:" + textrphone.getText().toString()));
                                if (ActivityCompat.checkSelfPermission(ItemDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(ItemDetails.this, "Call permissions not granted", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                startActivity(t);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("Text")
                        .setCancelClickListener(new OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(textsphone.getText().toString(),null,"EMS-KENYA\n"+"Please pick up your item\n"+"Item ID:\n"+itemid,null,null);
                                new SweetAlertDialog(ItemDetails.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Message sent")
                                        .show();



                            }
                        })
                        .show();


            }
        });


        itemidv=(TextView)findViewById(R.id.textView4);
        itemidv.setText(itemid);
        textdate=(TextView)findViewById(R.id.textView10) ;
        textsname=(TextView)findViewById(R.id.textView13) ;
        textsid=(TextView)findViewById(R.id.textView17);
        textsphone=(TextView)findViewById(R.id.textView15);
        textrname=(TextView)findViewById(R.id.textView20);
        textrid=(TextView)findViewById(R.id.textView22);
        textrphone=(TextView)findViewById(R.id.textView24);
        textdestination=(TextView)findViewById(R.id.textView26);


        getJSON();
    }

   public void  markasPicked(String itemId)
    {



            class GetJSON extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(ItemDetails.this, null, "Please wait...", false, false);
                }
                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String,String> parwams = new HashMap<>();
                    parwams.put("itemid",itemid);
                    String res = rh.sendPostRequest(picked, parwams);
                    return res;

                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();


                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray result = jsonObject.getJSONArray("result");
                        JSONObject jo =result.getJSONObject(0);
                        int succes=jo.getInt("succes");

                        if (succes ==1)
                        {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(2000);

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            Toast.makeText(ItemDetails.this, "Content marked as picked", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(ItemDetails.this, EMS.class);
                            startActivity(intent);





                        }
                        else
                        {

                            new SweetAlertDialog(ItemDetails.this,SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error!")
                                    .setContentText("Failed to mark as picked. Please try again")
                                    .show();


                        }






                    } catch (JSONException e) {

                        new SweetAlertDialog(ItemDetails.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Check your network connection!")
                                .show();

                    }


                }


            }
            GetJSON jj =new GetJSON();
            jj.execute();
        }



    public void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ItemDetails.this, null, "Please wait...", false, false);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> parwams = new HashMap<>();
                parwams.put("itemid",itemid);
                String res = rh.sendPostRequest(fetcher, parwams);
                return res;

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(ItemDetails.this, s, Toast.LENGTH_SHORT).show();
                showthem(s);

            }


        }
        GetJSON jj =new GetJSON();
        jj.execute();
    }

    private void showthem(String s) {
        String dd="",sphone="",sID="",sname="",Rphone="",RID="",Rname="";
        String destination="";

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");


            for (int i = 0; i < result.length(); i++)

            {  JSONObject jo = result.getJSONObject(i);

                dd=jo.getString("delivery_date");
                sname=jo.getString("sname");
                sphone=jo.getString("sphone");
                sID=jo.getString("sID");
                Rname=jo.getString("Rname");
                Rphone=jo.getString("Rphone");
                RID=jo.getString("RID");
                destination=jo.getString("destination");





            }

            textdate.setText(dd);
            textsphone.setText(sphone);
            textsid.setText(sID);
            textsname.setText(sname);
            textrphone.setText(Rphone);
            textrid.setText(RID);
            textrname.setText(Rname);
            textdestination.setText(destination);


        } catch (JSONException e) {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Check your network connection!")
                    .setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });

            


        }


    }

}
