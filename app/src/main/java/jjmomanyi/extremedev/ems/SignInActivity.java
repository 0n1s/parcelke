package jjmomanyi.extremedev.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static jjmomanyi.extremedev.ems.URLS.url;

public class SignInActivity extends AppCompatActivity
{
    Button btn;
    EditText username;
    EditText password;
    CheckBox remme;
    EditText _usernameText,_passwordText;
    Button _loginButton;
    CheckBox checkBox;
    public static final String MyPREFERENCES = "MyPrefs";
    String checker = url+"admi.php";
    TextView _signupLink;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
         checkBox= (CheckBox)findViewById(R.id.checkBox);
        _usernameText=(EditText)findViewById(R.id.editText8);
        _passwordText=(EditText)findViewById(R.id.editText9);
        _loginButton=(Button)findViewById(R.id.button4);
       // _signupLink=(TextView)findViewById(R.id.link_signup);

        String username= sharedpreferences.getString("username", "null");
        String password=sharedpreferences.getString("password","null");

        if(username.equals("null")||password.equals("null"))
        {
            // Toast.makeText(Signin.this, "I Just Logged out", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(SignInActivity.this, "Welcome back "+ username, Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(SignInActivity.this, EMS.class);
            startActivity(intent);
            finish();

        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                login();
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }


        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        loginnow(username,password);


    }
    public void rememberme(final String username,final String password)
    {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

    }
    public void loginnow (final String username,final String password)

    {
        Toast.makeText(SignInActivity.this, username +"\n"+ password, Toast.LENGTH_SHORT).show();
        class AddEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(SignInActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(checker, params);
                return res;


            }

            @Override
            protected void onPostExecute(final String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                login_verify(s,username,password);
               Toast.makeText(SignInActivity.this, s, Toast.LENGTH_SHORT).show();

            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();


    }

    public void login_verify(String s,String username,String password)
    {
        try {
            JSONObject json = new JSONObject(s);
            JSONArray array = json.getJSONArray("result");
            JSONObject c = array.getJSONObject(0);
            String succes =c.getString("succes");
            if (succes.equals("1"))
            {

                if (checkBox.isChecked())
                {
                    rememberme(username, password);
                }
                else
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("username", "null");
                    editor.putString("password", "null");
                    editor.commit();

                }

                Intent intent=new Intent(SignInActivity.this,EMS
                        .class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();


            }
            else if (succes.equals(0))
            {
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void onLoginFailed() {


        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("enter a valid username username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Between 4 and ten characters");
           valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
