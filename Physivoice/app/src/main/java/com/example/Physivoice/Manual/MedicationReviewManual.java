package com.example.Physivoice.Manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Physivoice.R;

public class MedicationReviewManual extends AppCompatActivity {
    private CalendarView calendar;
    private Button medDateBtn;
    Intent intent;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_review);
        intent= new Intent(getApplicationContext(), MedicationEntriesManual.class);
        extras= new Bundle();

        calendar = (CalendarView) findViewById(R.id.calendarViewMed);
        medDateBtn = (Button) findViewById(R.id.medDateBtn);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String m;
                month++;
                if(month<10){
                    m="0"+month;
                }
                else{
                    m=""+month;
                }
                if (dayOfMonth>9) {
                    extras.putString("date", year + "-" + m + "-" + dayOfMonth);
                }
                else{
                    extras.putString("date", year + "-" + m + "-0" + dayOfMonth);
                }
                intent.putExtras(extras);

            }
        });

        medDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}