package com.dustin_domas_assignment.simon02;

import android.media.SoundPool;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private SimonTask backgroundTask;
    private ArrayList<Integer> sequenceData;
    private ArrayList<Integer> playerData;
    private Object lock;
    private SoundPool sound;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        sequenceData = new ArrayList<>();
        playerData = new ArrayList<>();
        lock = new Object();

        findViewById(R.id.start_button).setOnClickListener(new StartGameListener());
    }


// start game listener
    class StartGameListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(backgroundTask==null){
                backgroundTask = new SimonTask();
                backgroundTask.execute();
            }
        }




    }











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



        }

    }



}



