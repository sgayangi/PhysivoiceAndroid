package com.example.Physivoice.VoiceEnabled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.Physivoice.R;

public class PainReviewVoice extends AppCompatActivity {
    private Button confirmDate;
    private CalendarView calendar;
    Intent intent;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_review);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        confirmDate = (Button) findViewById(R.id.date_entry);

        intent= new Intent(getApplicationContext(), PainEntriesVoice.class);
        extras= new Bundle();
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


        confirmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }

        });
    }
}