package com.example.charging_check_karlo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.Channels;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
public TextView t1;
   public TextToSpeech b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView)findViewById(R.id.t1);


        b1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    b1.setLanguage(Locale.UK);
                    b1.speak(t1.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

                }
            }
        });

    }
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 30 * 1000; //Delay for 15 seconds.  One second = 1000 milliseconds.
    protected void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something

                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryIntent = registerReceiver(null, ifilter);
                int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);


                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    t1.setText("BATTERY_STATUS_CHARGING\n");

                }
                if (status == BatteryManager.BATTERY_STATUS_FULL) {
                    t1.setText("BATTERY_STATUS_FULL\n");
                }
                if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                    t1.setText("BATTERY not chaarging adjust the charger\n");
                    b1.speak(t1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(getApplicationContext(),"Not charging", Toast.LENGTH_SHORT).show();


                }

                handler.postDelayed(runnable, delay);

            }
        }, delay);

        super.onResume();
    }




}