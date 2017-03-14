package com.dustin_domas_assignment.simon02;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PlayActivity extends AppCompatActivity implements OnClickListener {


    private int button1SoundID;
    private int button2SoundID;
    private int button3SoundID;
    private int button4SoundID;
    private int gameCount = 0;

    private SimonTask backgroundTask;
    private ArrayList<Integer> sequenceData;
    private ArrayList<Integer> playerData;

    private SoundPool sound;
    private Set<Integer> soundLoaded;

    private int randomNum = 0;
    int roundCount = 0;
    Random rand;
    ImageButton greenButton, redButton, blueButton, yellowButton;

    private boolean winLostFlag;
    private boolean playersTurn = false;
    private boolean computersTurn = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        sequenceData = new ArrayList<>();
        playerData = new ArrayList<>();


        soundLoaded = new HashSet<Integer>();

        findViewById(R.id.start_button).setOnClickListener(new StartGameListener());
        greenButton = (ImageButton) findViewById(R.id.green_button);
        redButton = (ImageButton) findViewById(R.id.red_button);
        blueButton = (ImageButton) findViewById(R.id.blue_button);
        yellowButton = (ImageButton) findViewById(R.id.yellow_button);

        //findViewById(R.id.green_button).setOnClickListener(new GreenButtonListener());
        //findViewById(R.id.red_button).setOnClickListener(new StartGameListener());
        //findViewById(R.id.yellow_button).setOnClickListener(new StartGameListener());
        //findViewById(R.id.blue_button).setOnClickListener(new StartGameListener());
        greenButton.setOnClickListener(this);
        redButton.setOnClickListener(this);
        blueButton.setOnClickListener(this);
        yellowButton.setOnClickListener(this);


        greenButton.setTag(1);
        redButton.setTag(2);
        blueButton.setTag(3);
        yellowButton.setTag(4);


        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);


        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(2);

        sound = spBuilder.build();

        sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    soundLoaded.add(sampleId);

                    Log.i("Sounds", "Loading" + sampleId);

                } else {
                    Log.i("Sounds", "Error with loading");
                }//end else
            }
        });//end LoadCompleteListener


        button1SoundID = sound.load(this, R.raw.button1_sound, 1);
        button2SoundID = sound.load(this, R.raw.button2_sound, 1);
        button3SoundID = sound.load(this, R.raw.button3_sound, 1);
        button4SoundID = sound.load(this, R.raw.button4_sound, 1);


    }


    void buttonIdentifier(int num) {

        playerData.add(num);
    }

    @Override
    public void onClick(View v) {

        int tag = (Integer) v.getTag();
        if (tag == 1) {
            if (soundLoaded.contains(button1SoundID)) {
                sound.play(button1SoundID, 1.0f, 1.0f, 0, 0, 1.0f);

            }
        } else if (tag == 2) {
            if (soundLoaded.contains(button2SoundID)) {
                sound.play(button2SoundID, 1.0f, 1.0f, 0, 0, 1.0f);

            }
        } else if (tag == 3) {
            if (soundLoaded.contains(button3SoundID)) {
                sound.play(button3SoundID, 1.0f, 1.0f, 0, 0, 1.0f);

            }
        } else if (tag == 4) {
            if (soundLoaded.contains(button4SoundID)) {
                sound.play(button4SoundID, 1.0f, 1.0f, 0, 0, 1.0f);

            }
        }
    }


    @Override
    protected void onPause(){
        super.onPause();

        if(sound != null){
            sound.release();
            sound = null;

            soundLoaded.clear();
        }
    }


    void randomSeqeunceCreator(){
        rand = new Random();

        for(int x =0; x<=roundCount; x++){
           randomNum = rand.nextInt(3);
            sequenceData.add(randomNum);
        }

    }



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






    //background task
    class SimonTask extends AsyncTask<Void, Integer, Void> {
        private TextView messageTv;
        private int messageCount;






        SimonTask() {
            messageTv = (TextView) findViewById(R.id.score_view);



        }

        protected void onPreExecute(){
             messageTv.setText("Get ready");
        }

        @Override
        protected Void doInBackground(Void... params) {


            dataCreator();
            int listSize = sequenceData.size();
            try {

                if ( listSize > 0) {
                    //synchronized (lock) {

                       // for (int i = 0; i< listSize; i++) {

                           // publishProgress();
                        //}
                        gameCount++;
                        Thread.sleep(1000);




                }

            } catch (InterruptedException e){
                e.printStackTrace();
            }


            return null;
        }

        void dataCreator(){
            rand = new Random();
            for(int x = 0; x<=gameCount; x++) {
                randomNum = rand.nextInt(3);
                Log.i("dataCreator", ""+randomNum);
                sequenceData.add(randomNum);

                randomNum = 0;

            }
        }

        /*void flipColorsBack(int y){
            final int element = y;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    ImageButton b;
                    for(int x : sequenceData){
                        Log.i("In color flip back", ""+sequenceData.indexOf(x));
                        if (sequenceData.indexOf(element) == 0) {
                            b = (ImageButton) findViewById(R.id.green_button);
                            b.setImageResource(R.drawable.greene);
                            // sound.play(button1SoundID,1.0f, 1.0f,0,0,1.0f);
                        }

                        if (sequenceData.indexOf(element) == 1) {
                            b = (ImageButton) findViewById(R.id.red_button);
                            b.setImageResource(R.drawable.lightred);
                        }

                        if (sequenceData.indexOf(element) == 2) {
                            b = (ImageButton) findViewById(R.id.blue_button);
                            b.setImageResource(R.drawable.blue);
                        }

                        if (sequenceData.indexOf(element) == 3) {
                            b = (ImageButton) findViewById(R.id.yellow_button);
                            b.setImageResource(R.drawable.yellow);
                        }


                    }

                }
            });
        }
*/


        @Override
        protected void onProgressUpdate(Integer...values){
            int value = values[0];
            ImageButton button;

           Log.i("Iteration",""+value);
           Log.i("+++In Light Up Mode",""+ sequenceData.indexOf(value));

            Log.i("in for", ""+sequenceData.indexOf(gameCount));
            for(int i = 0; i< sequenceData.size(); i++){

                if (sequenceData.get(i) == 0) {

                    button = (ImageButton) findViewById(R.id.green_button);
                    button.setImageResource(R.drawable.lightgreen);
                    // sound.play(button1SoundID,1.0f, 1.0f,0,0,1.0f);
                   // flipColorsBack(value);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    button.setImageResource(R.drawable.greene);
                }

                if (sequenceData.get(i) == 1) {
                    button = (ImageButton) findViewById(R.id.red_button);

         
                    button.setImageResource(R.drawable.lightupred);
                   // flipColorsBack(value);
                    try {
                        Thread.sleep(3000);
                        button.setImageResource(R.drawable.lightred);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (sequenceData.get(i) == 2) {
                    button = (ImageButton) findViewById(R.id.blue_button);
                    button.setImageResource(R.drawable.lightupblue);
                    //flipColorsBack(value);

                    try {
                        Thread.sleep(3000);

                        button.setImageResource(R.drawable.blue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (sequenceData.get(i) == 3) {
                    button = (ImageButton) findViewById(R.id.yellow_button);
                    button.setImageResource(R.drawable.lightupyellow);
                    //flipColorsBack(value);

                    try {
                        Thread.sleep(3000);
                        button.setImageResource(R.drawable.yellow);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }


        }//end progress update

        //post execute function
        @Override
        protected void onPostExecute(Void aVoid){
            messageTv.setText("Your Turn!");
            ImageButton button;

            for(int i = 0; i< sequenceData.size(); i++){
                Log.i("in for", ""+sequenceData.get(i));
                if (sequenceData.get(i) == 0) {

                    button = (ImageButton) findViewById(R.id.green_button);
                    button.setImageResource(R.drawable.lightgreen);
                    // sound.play(button1SoundID,1.0f, 1.0f,0,0,1.0f);
                    // flipColorsBack(value);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    button.setImageResource(R.drawable.greene);
                }

                if (sequenceData.get(i) == 1) {
                    button = (ImageButton) findViewById(R.id.red_button);
                    button.setImageResource(R.drawable.lightupred);
                    // flipColorsBack(value);
                    try {
                        Thread.sleep(3000);
                        button.setImageResource(R.drawable.lightred);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (sequenceData.get(i) == 2) {
                    button = (ImageButton) findViewById(R.id.blue_button);
                    button.setImageResource(R.drawable.lightupblue);
                    //flipColorsBack(value);

                    try {
                        Thread.sleep(3000);

                        button.setImageResource(R.drawable.blue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (sequenceData.get(i) == 3) {
                    button = (ImageButton) findViewById(R.id.yellow_button);
                    button.setImageResource(R.drawable.lightupyellow);
                    //flipColorsBack(value);

                    try {
                        Thread.sleep(3000);
                        button.setImageResource(R.drawable.yellow);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }

            backgroundTask = null;
        }

        @Override
        protected void onCancelled() {
            messageTv.setText("Cancelled");
            backgroundTask  = null;


        }

    }



}







