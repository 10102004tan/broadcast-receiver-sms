package com.example.broadcastreceiverotp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_USER_CONSENT = 200;
    private SmsBroadcastReceiver broadcastReceiver;
    private EditText otp;
    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otp = findViewById(R.id.opt);

        //show dialog mess opt
        startSmartUserConsent();

    }
    private void registerBroadcastReceiver(){

        broadcastReceiver = new SmsBroadcastReceiver();
        broadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent,REQ_USER_CONSENT);
            }
            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        this.registerReceiver(broadcastReceiver,intentFilter,RECEIVER_EXPORTED);
    }
    private void startSmartUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT){
            if (resultCode == RESULT_OK && data != null){
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);
            }
        }
    }


    private void getOtpFromMessage(String message) {
        Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()){
            otp.setText(matcher.group(0));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}