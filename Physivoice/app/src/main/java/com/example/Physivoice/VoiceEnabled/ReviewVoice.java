package com.example.Physivoice.VoiceEnabled;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.Physivoice.R;
import com.example.Physivoice.SpeechService;

public class ReviewVoice extends AppCompatActivity {
    private TextView reviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewView = (TextView)findViewById(R.id.reviewView);

        Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
        speechIntent.putExtra("toSpeak", "Would you like to review your pain entries, or your medication entries?");
        reviewView.setText("If you would like to review your pain entries, please say pain." +"\n"+
                "If you would like to review your medication entries, please say medication");
        speechIntent.putExtra("from", "Review");
        getApplicationContext().startService(speechIntent);

    }

    protected void onPause() {
        super.onPause();
        SpeechService.pause();
    }
}
