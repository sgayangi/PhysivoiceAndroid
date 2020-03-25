package com.example.physivoice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import android.speech.tts.TextToSpeech;

import com.example.physivoice.R;

public class Entries extends AppCompatActivity {
    private TextView dateView,transcript;
    private Button next, previous;
    static int index;
    private Button textToSpeech;
    TextToSpeech t1;
    List<String> results;
    String[] output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);
        index = 0;
        Bundle intentExtras = getIntent().getExtras();
        String date = intentExtras.getString("date");
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        textToSpeech=findViewById(R.id.audio);
        transcript = (TextView) findViewById(R.id.transcript);
        dateView = (TextView) findViewById(R.id.date);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {            //to initialize the text to speech function
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        results = getQuery(date);
        if (results.isEmpty()) {
            dateView.setText("Entries entered on " + date);
            transcript.setText("No entries.");
        } else {
            output = new String[results.size()];
            results.toArray(output);
            dateView.setText("Entries entered on " + date);
            transcript.setText(output[0]);
        }

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index < results.size()-1) {
                        index++;
                        transcript.setText(output[index]);
                    }
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(index >0)
                    {
                        index--;
                        transcript.setText(output[index]);
                    }
                }



            });

            textToSpeech.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (results.size()!=0)
                        t1.speak(output[index], TextToSpeech.QUEUE_FLUSH, null);

                }
            });



    }


    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public List<String> getQuery(String date){
        return MainActivity.db.getMyItems(date);
    }



}
