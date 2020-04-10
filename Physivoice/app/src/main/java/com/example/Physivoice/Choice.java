package com.example.Physivoice;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.Physivoice.Manual.MainActivityManual;
import com.example.Physivoice.VoiceEnabled.MainActivityVoice;

public class Choice extends AppCompatActivity {
    public static DatabaseFunctions db;
    SharedPreferences sh;
    SharedPreferences.Editor preferencesEditor;
    private Button manual, voice, devOps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        int MY_PERMISSIONS_RECORD_AUDIO = 1;
        Choice thisActivity = this;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_RECORD_AUDIO);
        }

        sh= getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        preferencesEditor = sh.edit();
        Data.setManual(sh.getInt("manual",0));
        Data.setVoice(sh.getInt("voice",0));
        db = new DatabaseFunctions(getApplicationContext());
        voice = (Button) findViewById(R.id.voice);
        manual = (Button) findViewById(R.id.manual);
        devOps = (Button) findViewById(R.id.devOps);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.addVoice();
                startActivity(new Intent(getApplicationContext(), MainActivityVoice.class));
            }
        });

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.addManual();
                startActivity(new Intent(getApplicationContext(), MainActivityManual.class));

            }
        });

        devOps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DevOption.class));
                
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        preferencesEditor.putInt("voice", Data.getVoice());
        preferencesEditor.putInt("manual", Data.getManual());
        preferencesEditor.apply();
    }
}

