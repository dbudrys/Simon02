package com.dustin_domas_assignment.simon02;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        // set more_button to Wiki
        Button more = (Button) findViewById(R.id.more_button);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);

                builder.setTitle("Wiki link");

                builder.setMessage("https://en.wikipedia.org/wiki/Simon_%28game%29");


                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();


                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Linkify.addLinks((TextView) alertDialog.findViewById(android.R.id.message), Linkify.ALL);


                }
            });


        //set dev_button about developers
        Button dev = (Button) findViewById(R.id.dev_button);
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                builder.setTitle("Developers SIMON game");
                builder.setMessage(Html.fromHtml("<h2>Dustin Lobato</h2> <br>"
                                                  + "<h2>Domas Budrys</h2>"));

                builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = builder.create();

                // show it
                alertDialog.show();

            }// end of button onClickListener

        }); // end of button

        }
}
