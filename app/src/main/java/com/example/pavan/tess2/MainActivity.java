package com.example.pavan.tess2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText e = (EditText) findViewById(R.id.phrase);
        Button b = (Button) findViewById(R.id.bService);
        Button b2 = (Button) findViewById(R.id.bService1);
        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        if (prefs.getString("phrase", null) != "") {
            e.setText(prefs.getString("phrase", null));

        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phrase = e.getText().toString();
                editor.putString("phrase", phrase);
                editor.commit();
                Intent i = new Intent(getApplicationContext(), MyService.class);
                i.putExtra("phrase", phrase);
                getApplicationContext().startService(i);
            }
        });


    }

}



