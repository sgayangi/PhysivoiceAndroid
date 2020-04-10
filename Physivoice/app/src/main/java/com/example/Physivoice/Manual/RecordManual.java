
package com.example.Physivoice.Manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Physivoice.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class RecordManual extends AppCompatActivity {
    String[] columns;
    private Button edit, confirm, record, menu;
    private TextView textView;
    private EditText editText;
    private String transcript;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_record);
        textView = (TextView) findViewById(R.id.manualMedRecorder);
        confirm = (Button) findViewById(R.id.confirmManual);
        edit = (Button) findViewById(R.id.manualRecEdit);
        record = (Button) findViewById(R.id.manualRecord);
        editText = (EditText) findViewById(R.id.recEdit);
        menu = (Button) findViewById(R.id.menuManual);
        editText.setVisibility(View.GONE);

        Intent received = getIntent();
        if (received.hasExtra("transcript")){
            transcript = received.getStringExtra("transcript");
            textView.setText(transcript);
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityManual.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                editText.setText(transcript);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals(""))
                    {transcript = editText.getText().toString();}
                confirmRecording(transcript);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("What would you like to record?");
            }
        });


    }

    public abstract void speak(String toSpeak);

    public static String[] getDateandTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String[] temp = currentDateandTime.split(" ");

        return temp;
    }

    abstract void confirmRecording(String transcript);
}

