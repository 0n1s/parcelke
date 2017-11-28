package jjmomanyi.extremedev.ems;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static jjmomanyi.extremedev.ems.URLS.url;

public class EMS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

{
    Button btn;
    EditText em1 ;
    EditText phone;
    Switch aSwitch;
    ListView listView;
    LinearLayout layout;
    SwipeRefreshLayout swipeRefreshLayout;

//http://o3.allalla.com/EMS/EMS/cheker.php

   // String checker = "http://o3.allalla.com/EMS/EMS/cheker.php";
    //String fetcher="http://o3.allalla.com/EMS/EMS/fetcher.php";

    String checker =url+"cheker.php";
    String fetcher =url+"fetcher.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ems);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EMS.this, AddItem.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.swipe);
        listView=(ListView)findViewById(R.id.listView);

        getJSON();


        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                final  String itemid =   map.get("itemID");
                Intent intent = new Intent(EMS.this, ItemDetails.class);
                intent.putExtra("itemid",itemid);
                startActivity(intent);


            }
        });










        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getJSON();
                swipeRefreshLayout.setRefreshing(false);

            }


        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.em, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
             new SweetAlertDialog(EMS.this, SweetAlertDialog.WARNING_TYPE)
                     .setTitleText("Confirm exit")
                     .setContentText("Are you sure you want to exit?")
                     .setCancelText("cancel")
                     .setConfirmText("Confirm")
                     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                         @Override
                         public void onClick(SweetAlertDialog sDialog) {

                             finish();
                             System.exit(0);

                         }
                     })
                     .show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
        }

        else if (id == R.id.nav_manage)
        {
            Intent intent = new Intent(EMS.this, AddItem.class);
                    startActivity(intent);
        }
        else if (id==R.id.picked)
        {
            Intent intent = new Intent(EMS.this, PickedItems.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void checker(final String ItemID, final String senderNUmber) {

        class AddEmployee extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("ItemID", ItemID);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(checker, params);
                return res;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                smssender(s,senderNUmber);



            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();

    }








    public void smssender(final String json, String number) {
        String sms = "";
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject c = jsonArray.getJSONObject(0);
            int status = c.getInt("status");
            String date = c.getString("pickupdate");

            if (status ==0)
            {
                sms = "Item not picked up yet";
            } else if (status == 1) {
                sms = "Item Picked up on " + date;
            } else if (status == 2) {
                sms = "Item not found";
            }

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,"EMS-KENYA\n"+sms,null,null);




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            SweetAlertDialog pDialog = new SweetAlertDialog(EMS.this, SweetAlertDialog.PROGRESS_TYPE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading items");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(fetcher);
                return s;

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
                showthem(s);

            }


        }
        GetJSON jj =new GetJSON();
        jj.execute();
    }


    private void showthem(String s) 
    {

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String itemID="";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);

                String status = "Not Picked up yet";
                String dd=jo.getString("delivery_date");
                String sname=jo.getString("sname");
                String sphone=jo.getString("sphone");
                String sID=jo.getString("sID");
                String Rname=jo.getString("Rname");
                String Rphone=jo.getString("Rphone");
                String RID=jo.getString("RID");
                 itemID=jo.getString("itemID");



                HashMap<String, String> employees = new HashMap<>();
                employees.put("itemID", itemID);
                employees.put("Sent Date", dd);
                employees.put("Rname",Rname);
                list.add(employees);
            }

            if (itemID.equals(""))
            {
               new SweetAlertDialog(this)
                       .setTitleText("Attention!!")
                       .setContentText("All items have been picked up\n Swipe down to refresh\n Consider adding more items by pressing the add FAB")
                       .show();
            }


        } catch (JSONException e) {


            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Check your network connection!")
                    .show();


        }

        ListAdapter adapter = new SimpleAdapter(EMS.this, list, R.layout.allitems,
                new String[]{"itemID", "Sent Date", "Rname"}, new int[]{R.id.textView32, R.id.textView31,R.id.textView34});
        listView.setAdapter(adapter);
    }


}




