package com.example.hp.what2fix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent i = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    Toast.makeText(StartActivity.this, "nhi", Toast.LENGTH_LONG).show();
                }
            }
        };
        thread.start();

    }
}
