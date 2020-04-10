package com.example.Physivoice.Manual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Physivoice.R;

public class ReviewManual extends AppCompatActivity {
    private Button pain,med;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual__review);
        pain = (Button) findViewById(R.id.manualPainEntries);
        med = (Button) findViewById(R.id.manualMedEntries);

        pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PainReviewManual.class));
            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MedicationReviewManual.class));
            }
        });
    }
}
