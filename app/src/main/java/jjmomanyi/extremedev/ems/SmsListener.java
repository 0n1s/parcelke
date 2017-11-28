package jjmomanyi.extremedev.ems;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jjmomanyis on 8/25/16.
 */
public class SmsListener extends BroadcastReceiver {
    String smsvody;
    String registerurl="http://o3.allalla.com/EMS/EMS/register.php";
    String TAG;
    String message;
    Pattern ptn;
    Matcher matcher;
    String checker = "http://o3.allalla.com/EMS/EMS/checker.php";
   String senderNUmber;
    EMS ems = new EMS();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            Object[] pdus= (Object[]) bundle.get("pdus");
            for (int i=0;i<pdus.length;i++)
            {
                SmsMessage sms= SmsMessage.createFromPdu((byte[]) pdus[i]);
                senderNUmber=sms.getOriginatingAddress();
                message=sms.getDisplayMessageBody();
            }

            if (message.startsWith("Jisort code"))
            {
                ems.checker(message,senderNUmber);
            }

        }


    }





}


