package com.iboy.smsboombing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText enumber,ecount,etext;
    Button btn;
    TextView smslog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        enumber = findViewById(R.id.enumber);
        ecount = findViewById(R.id.ecount);
        etext = findViewById(R.id.etext);
        smslog = findViewById(R.id.smslog);


        enumber.setHighlightColor(Color.parseColor("#2DB7BFCD"));
        ecount.setHighlightColor(Color.parseColor("#2DB7BFCD"));
        etext.setHighlightColor(Color.parseColor("#2DB7BFCD"));

        btn = findViewById(R.id.btn);
        btn.setOnClickListener( v -> {

            String count = ecount.getText().toString();
            int icount=0;

            try {
                icount= Integer.parseInt(count);
            }catch (NumberFormatException n){

            }

            Toast.makeText(MainActivity.this,""+icount,Toast.LENGTH_SHORT).show();

            if(ecount.length()>0  &&  icount>0 && icount < 501 ){
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)==
                        PackageManager.PERMISSION_GRANTED){

                    sentSms();
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},
                            100);
                }
                /////////
                ////////////////////////////////////
            } else if (icount>= 500) {
                ecount.setError("500+ SMS");
            } else if(ecount.length()<=1){
                ecount.setError("0 SMS");
            } else if (icount <1) {
                ecount.setError("0 SMS");

            }


        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode ==100 && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sentSms();

        }else {
            smslog.setText("SMS Log : Permission Denied!");

        }
    }

    private void sentSms() {
        String number = enumber.getText().toString();
        String massage = etext.getText().toString();
        String count = ecount.getText().toString();
        int icount = Integer.parseInt(count);




        if (count.length() <1 && icount < 1 && icount >500){
            ecount.setError("");
        }else {
            for (int i = 1; i<=icount; i++){
                /////////////////////////////////
                if (!number.isEmpty() && !massage.isEmpty()){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number,null,massage,null,null);


                    smslog.setText("SMS Log : Sent SMS "+i);
                    if(icount == i){
                        smslog.setText("SMS Log : Sent SMS "+icount +" ....Completed");
                        ecount.setText("");
                        enumber.setText("");
                        etext.setText("");

                    }


                }else {
                    smslog.setText("SMS Log : Enter Sender number and massege");

                }
                ////==============================
            }
        }




    }


}