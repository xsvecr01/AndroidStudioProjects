package com.example.radek.appbluetooth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class controls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent IntentBack = new Intent(getApplicationContext(), MainActivity.class);
                //startIntent.putExtra("com.example.radek.app2.SOMETHING", "Hello World");
                startActivity(IntentBack);

            }
        });
    }
}
