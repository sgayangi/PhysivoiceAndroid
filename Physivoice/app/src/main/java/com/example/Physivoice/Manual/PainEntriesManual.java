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

public class PainEntriesManual extends AppCompatActivity {
    private TextView dateView,transcript,time;
    private Button next, previous,textToSpeech,returnM;
    static int index;
    List<String> results;
    String[] output,columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_entries);


        index = 0;
        Bundle intentExtras = getIntent().getExtras();

        String date = intentExtras.getString("date");
        next = findViewById(R.id.nextP);
        previous = findViewById(R.id.previousP);
        textToSpeech=findViewById(R.id.audioP);
        transcript = (TextView) findViewById(R.id.painView);
        dateView = (TextView) findViewById(R.id.dateP);
        time = (TextView) findViewById(R.id.timeP);
        returnM = (Button) findViewById(R.id.returnPain);

        columns = new String[]{"TIME", "TRANSCRIPT"};

        results = getQuery(date);
        if (results.isEmpty()) {
            dateView.setText("Entries entered on " + date);
            transcript.setText("No entries.");
        } else {
            output = new String[results.size()];
            results.toArray(output);
            dateView.setText("Entries entered on " + date);
            transcript.setText(output[1]+ "\n" + "\n" );
            String t = getTime(output[index]);
            time.setText(t);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < (results.size())-2) {
                    index+=2;
                    transcript.setText(output[index+1]+ "\n" + "\n");
                    String t = getTime(output[index]);
                    time.setText(t);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index >0)
                {
                    index-=2;
                    transcript.setText(output[index+1]+ "\n" + "\n");
                    String t = getTime(output[index]);
                    time.setText(t);
                }
            }



        });

        textToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (results.size()!=0) {
                    Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                    speechIntent.putExtra("toSpeak", output[index+1]);
                    speechIntent.putExtra("from", "Entries");
                    getApplicationContext().startService(speechIntent);
                }


            }
        });

        returnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityManual.class));
                finish();
            }
        });
    }

    public void onBackPressed(){
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public List<String> getQuery(String date){
        return Choice.db.getMyItems("Pain_Entries",columns, "DATE", date);
    }

    private String getTime(String x){
        String[] temp = x.split("-");
        String answer=null;
        int hours = Integer.parseInt(temp[0]);
        String m;
        if (hours>=12){
            m=" pm";
            if (hours!=12){
                hours-=12;
            }
        }
        else{
            m=" am";
        }
        answer = "Entered at " + hours + ":" + temp[1] + m;
        return answer;
    }


}
