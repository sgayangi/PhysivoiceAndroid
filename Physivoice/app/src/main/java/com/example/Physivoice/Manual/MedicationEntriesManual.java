package com.example.Physivoice.Manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Physivoice.Choice;
import com.example.Physivoice.R;
import com.example.Physivoice.SpeechService;
import com.example.Physivoice.VoiceEnabled.SecondaryActivity;

import java.util.List;

public class MedicationEntriesManual extends AppCompatActivity {
    private TextView dateView, transcript;
    private Button next, previous, textToSpeech, returnM;
    static int index;
    List<String> results;
    String[] output, columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_entries);

        columns = new String[]{"MEDICATION"};

        index = 0;
        Bundle intentExtras = getIntent().getExtras();

        String date = intentExtras.getString("date");
        next = findViewById(R.id.nextMed);
        previous = findViewById(R.id.previousMed);
        textToSpeech = findViewById(R.id.audioMed);
        transcript = (TextView) findViewById(R.id.medView);
        dateView = (TextView) findViewById(R.id.dateMed);
        returnM = (Button) findViewById(R.id.returnMed);

        results = getQuery(date);
        if (results.isEmpty()) {
            dateView.setText("Medication prescribed on " + date);
            transcript.setText("No entries.");
        } else {
            output = new String[results.size()];
            results.toArray(output);
            dateView.setText("Entries entered on " + date);
            transcript.setText(output[0] + "\n" + "\n");
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < (results.size()) - 1) {
                    index += 1;
                    transcript.setText(output[index ] + "\n" + "\n");
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    index -= 1;
                    transcript.setText(output[index ] + "\n" + "\n");
                }
            }


        });

        textToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (results.size() != 0) {
                    Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                    speechIntent.putExtra("toSpeak", output[index]);
                    speechIntent.putExtra("from", "Entries");
                    getApplicationContext().startService(speechIntent);
                }


            }
        });

        returnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecondaryActivity.class));
                finish();
            }
        });
    }

    public void onBackPressed(){
        finish();
    }


    public List<String> getQuery(String date) {
        return Choice.db.getMyItems("Medication_Entries", columns, "DATE", date);
    }
}
