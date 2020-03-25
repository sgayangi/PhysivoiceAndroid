package com.example.physivoice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physivoice.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Record extends AppCompatActivity {

    private Button confirm, rerecord;
    private TextView textView;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        textView = (TextView) findViewById(R.id.textView);
        intent = new Intent();
        confirm = (Button) findViewById(R.id.confirm);
        rerecord = (Button) findViewById(R.id.rerecord);
        promptSpeechInput();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] temp = getDateandTime();

                String transcript = intent.getStringExtra("recording");
                boolean isInserted = MainActivity.db.insertData(temp[0], temp[1], transcript);
                if (isInserted) {
                    Toast.makeText(getApplicationContext(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });

        rerecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText("");
                promptSpeechInput();

            }
        });
    }

    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String transcript = result.get(0);
                    textView.setText(transcript);
                    intent.putExtra("recording", transcript);
                }
                break;
            }

        }
    }

    private String[] getDateandTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String[] temp = currentDateandTime.split(" ");

        return temp;

    }
}
