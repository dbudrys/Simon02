package com.dustin_domas_assignment.simon02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button play_b = (Button) findViewById(R.id.play_button);
        play_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),
                        SelectActivity.class);

                startActivity(intent);
            }
        });

        Button about_b = (Button) findViewById(R.id.about_button);
        about_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        AboutActivity.class);

                startActivity(intent);
            }
        });

    }
}
