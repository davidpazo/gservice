package com.example.pavan.tess2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Pavan on 14-01-2016.
 */
public class MyService extends Service {
    String phrase;
    protected static SpeechRecognizer mSpeechRecognizer;
    protected Intent mSpeechRecognizerIntent;
    Context c;
    protected AudioManager audioManager;
    protected AudioManager mAudioManager;
    String sphrase;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sphrase  = intent.getExtras().getString("okescriba");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        c= getApplicationContext();
        Log.d("tag", "service" + phrase);
        //Shared preferences retreival

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            sphrase = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Intent sri = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        SpeechRecognitionListener srl = new SpeechRecognitionListener();
        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        sr.setRecognitionListener(listener);
        sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
        sri.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        sri.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di la palabra magica");
        sri.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**protected CountDownTimer mNoSpeechCountDown = new CountDownTimer(90000, 90000){

        @Override
        public void onTick(long millisUntilFinished){
            // TODO Auto-generated method stub
        }
        @Override
        public void onFinish(){
            mSpeechRecognizer.stopListening();
            mSpeechRecognizer.destroy();
        }
    };*/

    class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {

            //mNoSpeechCountDown.start();
            Log.d("onReady", "service");

        }

        @Override
        public void onBeginningOfSpeech() {

            //mNoSpeechCountDown.cancel();
        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {
            Log.d("ERROR","ERROR");
            mSpeechRecognizer.stopListening();
            mSpeechRecognizer.destroy();
            onCreate();
        }

        @Override
        public void onResults(Bundle resultsBundle) {
            Boolean check = false;
            Log.d("Results", "onResults"); //$NON-NLS-1$
            ArrayList<String> matches = resultsBundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String s = "";
            for (String result : matches) {
                if (result.equals(sphrase)) {
                    Intent googleNowIntent = new Intent("android.intent.action.VOICE_ASSIST");
                    startActivity(googleNowIntent);
                }
                s += result + "\n";
            }
            if(!check){
                mSpeechRecognizer.stopListening();
                mSpeechRecognizer.destroy();
                onCreate();

            }


            Log.d("Results", s);

            // stopSelf();

        }

        @Override
        public void onPartialResults(Bundle bundle) {

            //mSpeechRecognizer.stopListening();
            //mSpeechRecognizer.destroy();
            onCreate();

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    }

}