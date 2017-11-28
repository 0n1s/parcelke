package jjmomanyi.extremedev.ems;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static jjmomanyi.extremedev.ems.URLS.url;

public class PIcked extends AppCompatActivity {

    String itemid;
    TextView itemidv;
    Switch aSwitch;
    TextView textdate, textsname, textsid, textsphone, textrname, textrid, textrphone, textdestination;
    String pickedup="http://192.168.43.184/EMS/pickedup.php";
    //String fetcher = "http://o3.allalla.com/EMS/EMS/getone.php";
    //String picked = "http://o3.allalla.com/EMS/EMS/picked.php";
    String checker = "http://192.168.43.184/EMS/cheker.php";
    String fetcher = url+"pickeddetails.php";
    String picked = "http://192.168.43.184/EMS/picked.php";
    Button btn1, bnt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        itemid = intent.getStringExtra("itemid");
        aSwitch = (Switch) findViewById(R.id.switch1);

        itemidv=(TextView)findViewById(R.id.textView4);
        itemidv.setText(itemid);
        textdate=(TextView)findViewById(R.id.textView10) ;
        textsname=(TextView)findViewById(R.id.textView13) ;
        textsid=(TextView)findViewById(R.id.textView17);
        textsphone=(TextView)findViewById(R.id.textView15);
        textrname=(TextView)findViewById(R.id.textView20);
        textrid=(TextView)findViewById(R.id.textView22);
        textrphone=(TextView)findViewById(R.id.textView24);
        textdestination=(TextView)findViewById(R.id.textView39);

        getJSON();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void getJSON()
    {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            SweetAlertDialog pDialog = new SweetAlertDialog(PIcked.this, SweetAlertDialog.PROGRESS_TYPE);
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
                HashMap<String,String> parwams = new HashMap<>();
                parwams.put("itemid",itemid);
                String res = rh.sendPostRequest(fetcher, parwams);
                return res;

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismissWithAnimation();
              //  Toast.makeText(PIcked.this, s, Toast.LENGTH_SHORT).show();
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
/*
                        "delivery_date"=>$row['delivery_date'],
						"sname"=>$row['SenderName'],
                        "sID"=>$row['senderID'],
                        "Rname"=>$row['ReceiverName'],
						"RID"=>$row['ReceiverID'],
                        "Pickupdate"=>$row['pickupdate'],
                        "itemID"=>$row['itemID']
 */

            for (int i = 0; i < result.length(); i++)

            {  JSONObject jo = result.getJSONObject(i);

                dd=jo.getString("delivery_date");
                sname=jo.getString("sname");
              //  sphone=jo.getString("sphone");
                sID=jo.getString("sID");
                Rname=jo.getString("Rname");
              //  Rphone=jo.getString("Rphone");
                RID=jo.getString("RID");
                destination=jo.getString("Pickupdate");





            }

            textdate.setText(dd);
           // textsphone.setText(sphone);
            textsid.setText(sID);
            textsname.setText(sname);
           // textrphone.setText(Rphone);
            textrid.setText(RID);
            textrname.setText(Rname);
            textdestination.setText(destination);


        } catch (JSONException e) {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Check your network connection!")
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });




        }


    }

}
