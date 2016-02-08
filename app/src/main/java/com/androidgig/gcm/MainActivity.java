package com.androidgig.gcm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(checkPlayServices())
            new GenerateToken().start();
    }

    class GenerateToken extends Thread {
        public void run() {
            try {
                InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                final String token = instanceID.getToken("<project number>",
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.e("device_token", token);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        9000).show();
            } else {
                Log.i("play service", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
