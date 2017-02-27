package com.dustin_domas_assignment.simon02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        Button activity1 = (Button) findViewById(R.id.activity1_button);
        activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(),
                PlayActivity.class);


                startActivity(intent);
            }
        });
    }



    /*
    Will have to add activities and intents for
    activity2 (Version II)
    activity3 (Version II)
     */


}
