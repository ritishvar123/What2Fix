package com.example.hp.what2fix;

import android.content.Intent;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {
    Button done;
    TextView tv_total_payment, tv_profit_gain;
    Double total_payment, profit_gain;
    String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        tv_total_payment = (TextView) findViewById(R.id.textView33);
        tv_profit_gain = (TextView) findViewById(R.id.textView34);
        done = (Button)findViewById(R.id.button5);
        Intent i = getIntent();
        total_payment = Double.parseDouble(i.getStringExtra("total_pay"));
        profit_gain = Double.parseDouble(i.getStringExtra("total_profit"));
        order = i.getStringExtra("orderId");
        tv_total_payment.setText(""+total_payment);
        tv_profit_gain.setText(""+profit_gain);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
