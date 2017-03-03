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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PlayActivity extends AppCompatActivity {

    private SimonTask backgroundTask;
    private ArrayList<Integer> sequenceData;
    private ArrayList<Integer> playerData;
    private Object lock;
    private SoundPool sound;
    private Set<Integer> soundLoaded;
    private int randomNum = 0;

    private boolean winLostFlag;

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
                buttonIdentifier(0);
            }
        });

        findViewById(R.id.red_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button2SoundID)){
                    sound.play(button2SoundID,1.0f, 1.0f,0,0,1.0f);
                }
                buttonIdentifier(1);
            }
        });

        findViewById(R.id.blue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button3SoundID)){
                    sound.play(button3SoundID,1.0f, 1.0f,0,0,1.0f);
                }
                buttonIdentifier(2);
            }
        });

        findViewById(R.id.yellow_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundLoaded.contains(button4SoundID)){
                    sound.play(button4SoundID,1.0f, 1.0f,0,0,1.0f);
                }
                buttonIdentifier(3);
            }
        });

    }

    void buttonIdentifier(int num){

        playerData.add(num);
    }




// start game listener
    class StartGameListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        startGame();
    }
}//end start gamelistener


    protected void startGame(){

        //check to make sure there is not a bunch of SimonTask queued up by not setting it back to null
        //until the AsyncTask is finished
        if (backgroundTask != null && backgroundTask.getStatus() == AsyncTask.Status.FINISHED){
            backgroundTask = null;
        }


        if (backgroundTask == null) {
            backgroundTask = new SimonTask();
            backgroundTask.execute();
        }
    }


    @Override
    protected void onPause(){
        super.onPause();

        if(backgroundTask != null){
            backgroundTask.cancel(true);
            backgroundTask = null;
        }

    }




    //background task
    class SimonTask extends AsyncTask<Void, Integer, Void> {
        private TextView messageTv;
        private int messageCount;
        private int gameCount = 0;

        Random rand = new Random();



        SimonTask() {
            //messageTv = (TextView) findViewById(R.id.score_view);
            //sequenceData.clear();
           // playerData.clear();

        }

        protected void onPreExecute(){
           // messageTv.setText("Get ready");
        }

        @Override
        protected Void doInBackground(Void... params) {



            dataCreator();
            int listSize = sequenceData.size();
            try {

                if (sequenceData.size() > 0) {
                    synchronized (lock) {

                        for (int i = 0; i< listSize; i++) {
                           publishProgress(i);
                        }
                        Thread.sleep(2000);
                        gameCount++;
                    }

                }

            } catch (InterruptedException e){
                e.printStackTrace();
            }


            return null;
        }

        void dataCreator(){

            for(int x = 0; x<=gameCount; x++) {
                randomNum = rand.nextInt(3);
                sequenceData.add(randomNum);
            }
        }

        @Override
        protected void onProgressUpdate(Integer...values){
            int value = values[0];
            ImageButton button;
           Log.i("Sequence",""+value);
            switch (sequenceData.get(value)){
                case 0: button = (ImageButton) findViewById(R.id.green_button);
                    button.setImageResource(R.drawable.lightupgreen);
                    break;
                case 1:button = (ImageButton) findViewById(R.id.red_button);
                    button.setImageResource(R.drawable.lightupred);
                    break;
                case 2: button = (ImageButton) findViewById(R.id.blue_button);
                    button.setImageResource(R.drawable.lightupblue);
                    break;
                case 3:
                    button = (ImageButton) findViewById(R.id.yellow_button);
                    button.setImageResource(R.drawable.lightupyellow);
                    break;
            }

        }//hey

//post execute function
        @Override
        protected void onPostExecute(Void aVoid){
            //messageTv.setText("Your Turn!");


            backgroundTask = null;
        }

        @Override
        protected void onCancelled() {
            messageTv.setText("Cancelled");
            backgroundTask  = null;


        }

    }



}



