package com.example.Physivoice.Manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.Physivoice.R;
import com.example.Physivoice.VoiceEnabled.SecondaryActivity;

public class MainActivityManual extends AppCompatActivity {
    private Button painRecord, review, medRecord, toggle;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_main);
        painRecord = (Button) findViewById(R.id.painRecordManual);
        review = (Button) findViewById(R.id.manualReview);
        medRecord = (Button) findViewById(R.id.manualMedRecorder);
        toggle = (Button)findViewById(R.id.mtv);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecondaryActivity.class));
            }
        });

        painRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PainRecordManual.class));

            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReviewManual.class));

            }
        });

        medRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MedicationRecordManual.class));

            }
        });
    }


}
