package com.example.Physivoice.VoiceEnabled;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Physivoice.Choice;
import com.example.Physivoice.R;
import com.example.Physivoice.SpeechService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicationRecordVoice extends AppCompatActivity {
    private String[] columns;
    private TextView medView;
    private Button confirm,edit,reRecordMed;
    private String transcript;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_record);
        medView = (TextView) findViewById(R.id.medView);
        confirm = (Button) findViewById( R.id.confirmMed);
        edit = (Button) findViewById( R.id.editMed);
        reRecordMed = (Button) findViewById( R.id.reRecordMed);
        editText = (EditText) findViewById(R.id.editTextMed);
        editText.setVisibility(View.GONE);

        columns = new String[]{"DATE", "MEDICATION"};

        Intent received = getIntent();
        if (received.hasExtra("transcript")){
            transcript = received.getStringExtra("transcript");
            medView.setText(transcript);
        }
        else{
            speak("What would you like to record?");
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medView.setVisibility(View.GONE);
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

        reRecordMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("What would you like to record?");
            }
        });
    }


    public void speak(String toSpeak){
        Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
        speechIntent.putExtra("toSpeak", toSpeak);
        speechIntent.putExtra("from", "medication record");
        getApplicationContext().startService(speechIntent);
    }

    private String[] getDateandTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String[] temp = currentDateandTime.split(" ");

        return temp;
    }

    private void confirmRecording(String transcript){

        String[] temp = getDateandTime();
        boolean isInserted = Choice.db.insertData("Medication_Entries",new String[]{temp[0], transcript}, columns);
        if (isInserted) {

            Toast.makeText(getApplicationContext(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(getApplicationContext(), SecondaryActivity.class));
    }

    protected void onDestroy() {
        super.onDestroy();
        SpeechService.pause();
    }

}

