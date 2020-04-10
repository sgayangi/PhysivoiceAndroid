package com.example.Physivoice.VoiceEnabled;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Physivoice.Manual.MainActivityManual;
import com.example.Physivoice.R;
import com.example.Physivoice.SpeechService;

public class SecondaryActivity extends AppCompatActivity {
    private TextView secondView;
    private Button toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        toggle = (Button) findViewById(R.id.vtm);
        secondView = (TextView)findViewById(R.id.secondaryView);
        secondView.setText("If you would like to make a pain entry, please say pain record."+"\n"+
                "If you would like to make a medication entry, please say medication record."+"\n"+
                "If you would like to review existing entries, please say review.");
        Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
        speechIntent.putExtra("toSpeak", "What would you like to do now?");
        speechIntent.putExtra("from", "Main Activity");
        getApplicationContext().startService(speechIntent);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivityManual.class));
            }
        });
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
}
