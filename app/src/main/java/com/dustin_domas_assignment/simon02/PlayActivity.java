package com.dustin_domas_assignment.simon02;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlayActivity extends AppCompatActivity {

    private SimonTask backgroundTask;
    private ArrayList<Integer> sequenceData;
    private ArrayList<Integer> playerData;
    private Object lock;
    private SoundPool sound;
    private Set<Integer> soundLoaded;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        sequenceData = new ArrayList<>();
        playerData = new ArrayList<>();
        lock = new Object();

        soundLoaded =  new HashSet<Integer>();

        findViewById(R.id.start_button).setOnClickListener(new StartGameListener());

        //findViewById(R.id.green_button).setOnClickListener(new GreenButtonListener());
        //findViewById(R.id.red_button).setOnClickListener(new StartGameListener());
        //findViewById(R.id.yellow_button).setOnClickListener(new StartGameListener());
        //findViewById(R.id.blue_button).setOnClickListener(new StartGameListener());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume(){
        super.onResume();


        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);


        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(2);

        sound = spBuilder.build();

        sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(status == 0){
                    soundLoaded.add(sampleId);

                    Log.i("Sounds", "Loading" + sampleId);

                } else{
                    Log.i("Sounds","Error with loading");
                }//end else
            }
        });//end LoadCompleteListener
        final int button1SoundID = sound.load(this, R.raw.button1_sound,1);
        final int button2SoundID = sound.load(this, R.raw.button2_sound,1);

        //final int button2SoundID = sound.load(this, R.raw.button2_sound,1);
        final int button3SoundID = sound.load(this, R.raw.button3_sound,1);
        final int button4SoundID = sound.load(this, R.raw.button4_sound,1);

        findViewById(R.id.green_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button1SoundID)){
                    sound.play(button1SoundID,1.0f, 1.0f,0,0,1.0f);
                }
            }
        });

        findViewById(R.id.red_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button2SoundID)){
                    sound.play(button2SoundID,1.0f, 1.0f,0,0,1.0f);
                }
            }
        });

        findViewById(R.id.blue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button3SoundID)){
                    sound.play(button3SoundID,1.0f, 1.0f,0,0,1.0f);
                }
            }
        });

        findViewById(R.id.yellow_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button4SoundID)){
                    sound.play(button4SoundID,1.0f, 1.0f,0,0,1.0f);
                }
            }
        });

    }
// start game listener
    class StartGameListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (backgroundTask == null) {
            backgroundTask = new SimonTask();
            backgroundTask.execute();
        }
    }
}//end start gamelistener

    /*class GreenButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(soundLoaded.contains(button1SoundID))
            sound.play(button1SoundID,1.0f, 1.0f, 0, 0, 1.0f );
        }
    }*/



    @Override
    protected void onPause(){
        super.onPause();

        if(backgroundTask != null){
            backgroundTask.cancel(true);
            backgroundTask = null;
        }

    }


/*

            ADD THE FOUR IMAGE BUTTON LISTENERS INSIDE THE THREAD FUNCTION BELOW

*/


    //background task
    class SimonTask extends AsyncTask<Void, Integer, Void> {
        private TextView messageTv;
        private int messageCount;

        SimonTask() {
            messageTv = (TextView) findViewById(R.id.score_view);
            sequenceData.clear();
            playerData.clear();

        }

        protected void onPreExecute(){
            messageTv.setText("0");
        }

        @Override
        protected Void doInBackground(Void... params) {
            /*while(!isCancelled()) {
                if (data.size() > 0) {
                    synchronized (lock) {
                        for (int i : data) {
                        }
                    }
                    if(sum > 0) {
                        publishProgress(sum);
                    }
                    }
                    }*/
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer...values){

            messageTv.setText("Sum = " + values[0]);
        }//hey

//post execute function
        @Override
        protected void onPostExecute(Void aVoid){
            messageTv.setText("Background Thread Finished");
            backgroundTask = null;
        }

        @Override
        protected void onCancelled() {
            messageTv.setText("Cancelled");
            backgroundTask  = null;


        }

    }



}



