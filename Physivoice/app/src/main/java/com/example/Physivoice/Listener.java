package com.example.Physivoice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.example.Physivoice.Manual.MedicationRecordManual;
import com.example.Physivoice.Manual.PainRecordManual;
import com.example.Physivoice.VoiceEnabled.MedicationRecordVoice;
import com.example.Physivoice.VoiceEnabled.MedicationReviewVoice;
import com.example.Physivoice.VoiceEnabled.PainRecordVoice;
import com.example.Physivoice.VoiceEnabled.PainReviewVoice;
import com.example.Physivoice.VoiceEnabled.ReviewVoice;

import java.util.ArrayList;
import java.util.HashMap;

public class Listener extends Service {
    public static SpeechRecognizer sr;
    HashMap<String, Class> activities, reviewClasses;
    private String from;
    Intent recorded;

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        from = intent.getStringExtra("from");
        activities = new HashMap<String, Class>();
        activities.put("pain record", PainRecordVoice.class);
        activities.put("Penn record", PainRecordVoice.class);
        activities.put("medication record", MedicationRecordVoice.class);
        activities.put("something", PainRecordVoice.class); //TODO: replace for actual pain record
        activities.put("review", ReviewVoice.class);
        activities.put("tribute", ReviewVoice.class);
        activities.put("dvo", ReviewVoice.class);

        activities.put("manual pain record", PainRecordManual.class);
        activities.put("manual medication record", MedicationRecordManual.class);

        reviewClasses = new HashMap<String, Class>();
        reviewClasses.put("pain", PainReviewVoice.class);
        reviewClasses.put("medication", MedicationReviewVoice.class);

        recorded = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recorded.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        String pkg = getApplication().getPackageName();
        recorded.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, pkg);
        recorded.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 600000);
        recorded.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        recorded.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);
        recorded.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 100000);
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false); //TODO: make true to mute the beep

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(getApplicationContext(), "Recording...",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {


            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> result = new ArrayList(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));

                if (from.equals("Main Activity")) {
                    switchCase(result.get(0));

                } else if (from.equals("Review")) {
                    switchCaseForReview(result.get(0));
                } else {
                    String transcript = result.get(0);
                    Intent dialogIntent = new Intent(getApplicationContext(), activities.get(from));
                    if ((from.equals("something")) || (from.equals("medication record")) || (from.equals("manual pain record")) || (from.equals("manual medication record"))) {
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    }
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    dialogIntent.putExtra("transcript", transcript);
                    startActivity(dialogIntent);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {


            }
        });
        sr.startListening(recorded);

        return START_STICKY;
    }

    public static void stopListening() {
        sr.stopListening();
    }


    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void switchCase(String aClass) {
        if (activities.containsKey(aClass)) {
            Intent dialogIntent = new Intent(getApplicationContext(), activities.get(aClass));
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        } else {
            Toast.makeText(getApplicationContext(), aClass,
                    Toast.LENGTH_SHORT).show();
            sr.startListening(recorded);
        }
    }

    public void switchCaseForReview(String aClass) {
        if (reviewClasses.containsKey(aClass)) {
            Intent dialogIntent = new Intent(getApplicationContext(), reviewClasses.get(aClass));
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        } else {
            Toast.makeText(getApplicationContext(), aClass,
                    Toast.LENGTH_SHORT).show();
            sr.startListening(recorded);
        }
    }

}






