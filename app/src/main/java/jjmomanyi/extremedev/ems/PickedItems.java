package jjmomanyi.extremedev.ems;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static jjmomanyi.extremedev.ems.URLS.url;

public class PickedItems extends AppCompatActivity {
    String search = "http://192.168.43.184/EMS/search.php";
    String pickedup=url+"pickedup.php";
ListView listView;
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
        setContentView(R.layout.activity_picked_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        listView=(ListView)findViewById(R.id.listView2);

        getJSON();

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                final  String itemid =   map.get("itemID");
                Intent intent = new Intent(PickedItems.this, PIcked.class);
                intent.putExtra("itemid",itemid);
                startActivity(intent);


            }
        });

    }


    public void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {


            SweetAlertDialog pDialog = new SweetAlertDialog(PickedItems.this, SweetAlertDialog.PROGRESS_TYPE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Fetching from database...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(pickedup);
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


    private void showthem(String s) {

       // Toast.makeText(PickedItems.this, s, Toast.LENGTH_SHORT).show();

        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");

            String itemID="";
            for (int i = 0; i < result.length(); i++)
            {  JSONObject jo = result.getJSONObject(i);

            //    String status = "Not Picked up yet";
                String dd=jo.getString("delivery_date");
             //   String sname=jo.getString("sname");
               // String sphone=jo.getString("sphone");
              //  String sID=jo.getString("sID");
                String Rname=jo.getString("Rname");
               // String Rphone=jo.getString("Rphone");
              //  String RID=jo.getString("RID");
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
                        .setContentText("NO records found")
                        .show();
            }


        } catch (JSONException e) {


            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(String.valueOf(e))
                    .show();


        }

        ListAdapter adapter = new SimpleAdapter(PickedItems.this, list, R.layout.allitems,
                new String[]{"itemID", "Sent Date", "Rname"}, new int[]{R.id.textView32, R.id.textView31,R.id.textView34});
        listView.setAdapter(adapter);
    }

}
