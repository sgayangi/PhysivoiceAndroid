package com.example.Physivoice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.Physivoice.Data;
import com.example.Physivoice.R;

public class DevOption extends AppCompatActivity {
    private TextView voice, manual;
    private String v,m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_option);

        voice = findViewById(R.id.voice);
        manual = findViewById(R.id.manual);

        v= Integer.toString(Data.getVoice());
        m= Integer.toString(Data.getManual());

        voice.setText("Voice mode was picked "+v+" time/s");
        manual.setText("Manual mode was picked "+m+" time/s");
    }
}
