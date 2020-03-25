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

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView title, menu;
    static DatabaseFunctions db;
    private Button button;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);        //set up for the UI
        menu = (TextView) findViewById(R.id.date_entry);

        title.setText("Navigation \n ");
        menu.setText(
                "Say 'record' to record a new entry. \n \n" +
                        "Say 'review' to review the entries. \n "
        );

        db = new DatabaseFunctions(this);           //database functions will handle the reading from and writing to the database
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });     //you need to press the menu navigation button to
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

        //switch (requestCode) {
        //case REQ_CODE_SPEECH_INPUT: {
        if (resultCode == RESULT_OK && null != data) {
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String check = result.get(0);
            switchCase(check);
        }
        // break;
    }

    //}

    //}

    private void switchCase(String check) {

        if (check.equals("record"))
            startActivity(new Intent(this, Record.class));
        else if (check.equals("review")) {
            startActivity(new Intent(this, Review.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please speak clearly", Toast.LENGTH_SHORT).show();
            promptSpeechInput();
        }

    }

}
