package com.example.Physivoice.VoiceEnabled;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.Physivoice.DatabaseFunctions;
import com.example.Physivoice.R;
import com.example.Physivoice.SpeechService;

public class MainActivityVoice extends AppCompatActivity {
    public static TextView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (TextView) findViewById(R.id.mainView) ;

        Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
        speechIntent.putExtra("toSpeak", "Hello,    and welcome to Physivoice");
        mainView.setText("Hello, and welcome to Physivoice." +"\n"+"\n"+
                "If you would like to make a pain entry, please say pain record."+"\n"+
                "If you would like to make a medication entry, please say medication record."+"\n"+
                "If you would like to review existing entries, please say review.");
        speechIntent.putExtra("from", "Main Activity");
        getApplicationContext().startService(speechIntent);


    }

    public void onBackPressed() {
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechService.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SpeechService.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SpeechService.resume("Hello, and welcome to Physivoice.");
    }
}
/*



*/