package com.example.Physivoice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.HashMap;
import java.util.Locale;

public class SpeechService extends Service implements TextToSpeech.OnInitListener {

    private static TextToSpeech tts;
    private boolean isInit;
    private String toSpeak, from;
    static HashMap<String, String> map;

    @Override
    public void onCreate() {
        super.onCreate();
        map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SpeechService");
        tts = new TextToSpeech(getApplicationContext(), this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toSpeak = intent.getStringExtra("toSpeak");
        from = intent.getStringExtra("from");
        speak(toSpeak);
        return SpeechService.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            tts.setSpeechRate((float) 0.9);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                //speak("initiated");
                isInit = true;
                tts.setOnUtteranceProgressListener(new UtterLis());
                speak(toSpeak);

            }
        }

    }

    public static void resume(String toSpeak){
        if (tts != null)
        {if (!(tts.isSpeaking())){
            speak(toSpeak);
        }}

    }

    public static void pause(){
        if (tts != null)
        {if (tts.isSpeaking()){
            tts.stop();
        }}

    }

    private static void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    class UtterLis extends UtteranceProgressListener {

        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onDone(String utteranceId) {
            Intent service = new Intent(getApplicationContext(), Listener.class);
            if (from.equals("Entries")){

            }
            else {
                service.putExtra("from", from);
                startService(service);
            }
        }

        @Override
        public void onError(String utteranceId) {

        }
    }

}