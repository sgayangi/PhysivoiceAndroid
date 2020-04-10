package com.example.Physivoice.Manual;

import android.content.Intent;
import android.widget.Toast;

import com.example.Physivoice.Choice;
import com.example.Physivoice.SpeechService;

public class MedicationRecordManual extends RecordManual {
    @Override
    public void speak(String toSpeak) {
        Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
        speechIntent.putExtra("toSpeak", toSpeak);
        speechIntent.putExtra("from", "manual medication record");
        getApplicationContext().startService(speechIntent);

    }

    @Override
    void confirmRecording(String transcript) {
        columns = new String[]{"DATE", "MEDICATION"};
        String[] temp = getDateandTime();
        boolean isInserted = Choice.db.insertData("Medication_Entries",new String[]{temp[0], transcript}, columns);
        if (isInserted) {

            Toast.makeText(getApplicationContext(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_SHORT).show();
        }

    }
}
