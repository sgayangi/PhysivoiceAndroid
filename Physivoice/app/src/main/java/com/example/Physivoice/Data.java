package com.example.Physivoice;

public class Data {
    public static int voice=0;
    public static int manual=0;

    public static void addVoice(){
        voice++;
    }

    public static void addManual(){
        manual++;
    }

    public static int getVoice(){
        return voice;
    }

    public static int getManual(){
        return manual;
    }

    public static void setVoice(int v){
        voice=v;
    }

    public static void setManual(int m){
        manual = m;
    }

}
