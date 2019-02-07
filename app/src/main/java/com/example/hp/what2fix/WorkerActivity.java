package com.example.hp.what2fix;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WorkerActivity extends AppCompatActivity {
    Button save, cancel, profit;
    EditText et_worker_name, et_worker_work, et_worker_phone, et_worker_cost, et_worker_profit_per;
    TextView tv_profit;
    String  worker_name, worker_work, worker_phone, worker_cost, worker_profit_per, worker_profit;
    int worker_count;
    double current_cost = 0.0, current_profit = 0.0;
    String order = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// for back button
        et_worker_name = (EditText)findViewById(R.id.editText7);
        et_worker_work = (EditText)findViewById(R.id.editText8);
        et_worker_phone = (EditText)findViewById(R.id.editText9);
        et_worker_cost = (EditText)findViewById(R.id.editText10);
        et_worker_profit_per = (EditText)findViewById(R.id.editText50);
        tv_profit = (TextView)findViewById(R.id.textView30);
        save = (Button)findViewById(R.id.button_save);
        cancel = (Button)findViewById(R.id.button_cancel);
        profit = (Button)findViewById(R.id.button_profit);
        Intent i = getIntent();
        worker_count = Integer.parseInt(i.getStringExtra("count"));
        order = i.getStringExtra("orderId");
        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                worker_profit_per = et_worker_profit_per.getText().toString();
                worker_cost = et_worker_cost.getText().toString();
                if (worker_profit_per.trim().length()==0 || worker_cost.trim().length()==0) {
                    tv_profit.setText("0");
                } else {
                    double profit_per = Double.parseDouble(et_worker_profit_per.getText().toString());
                    double cost       = Double.parseDouble(et_worker_cost.getText().toString());
                    double profit     = (cost / 100.0) * profit_per;
                    tv_profit.setText("" + String.format("%.2f", profit));
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                worker_name = et_worker_name.getText().toString();
                worker_work = et_worker_work.getText().toString();
                worker_phone = et_worker_phone.getText().toString();
                worker_cost = et_worker_cost.getText().toString();
                worker_profit_per = et_worker_profit_per.getText().toString();
                worker_profit = tv_profit.getText().toString();
                if ( worker_name.trim().length()==0 || worker_work.trim().length()==0 || worker_phone.trim().length()==0 ||
                         worker_cost.trim().length()==0 || worker_profit_per.trim().length()==0 ) {
                    if ( worker_name.trim().length()==0 )
                        et_worker_name.setError("Enter valid Name");
                    if ( worker_work.trim().length()==0 )
                        et_worker_work.setError("Enter valid Work");
                    if ( worker_phone.trim().length()==0 )
                        et_worker_phone.setError("Enter valid Phone No");
                    if ( worker_cost.trim().length()==0 )
                        et_worker_cost.setError("Enter valid Cost");
                    if ( worker_profit_per.trim().length()==0 )
                        et_worker_profit_per.setError("Enter valid Profit %");
                } else if( worker_phone.length()!=10 ) {
                    et_worker_phone.setError("Enter valid 10 digits Phone No");
                } else if(worker_profit.equals("  --  ")){
                    Toast.makeText(WorkerActivity.this, "Profit is not set", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(WorkerActivity.this);
                    alert.setMessage("Do you want to Save ?").setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="https://boxinall.in/kshitiz/insertworker.php";
                                    StringRequest stringRequest= new StringRequest(1, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {}
                                    }, new Response.ErrorListener() {
                                        @Override public void onErrorResponse(VolleyError error) {}
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> map=new HashMap<>();
                                            map.put("orderId",order);
                                            map.put("workerName",worker_name);
                                            map.put("workerPhone",worker_phone);
                                            map.put("workerWork",worker_work);
                                            map.put("cost",worker_cost);
                                            map.put("percent",worker_profit_per);
                                            map.put("profit",worker_profit);
                                            return map;
                                        }
                                    };
                                    RequestQueue requestQueue= Volley.newRequestQueue(WorkerActivity.this);
                                    requestQueue.add(stringRequest);
                                    Toast.makeText(WorkerActivity.this, "Saved Successfully !!",Toast.LENGTH_LONG).show();
                                    worker_count++;
                                    current_cost = Double.parseDouble(et_worker_cost.getText().toString());
                                    current_profit = Double.parseDouble(tv_profit.getText().toString());
                                    Intent i = new Intent();
                                    i.putExtra("count",""+worker_count);
                                    i.putExtra("total_pay",""+current_cost);
                                    i.putExtra("total_profit",""+current_profit);
                                    setResult(RESULT_OK, i);
                                    finish();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog close = alert.create();
                    close.setTitle("Save");
                    close.show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WorkerActivity.this);
                alert.setMessage("Do you want to Cancel ?").setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                i.putExtra("count",""+worker_count);
                                i.putExtra("total_pay",""+current_cost);
                                i.putExtra("total_profit",""+current_profit);
                                setResult(RESULT_OK, i);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog close = alert.create();
                close.setTitle("Cancel");
                close.show();
            }
        });
    }


}
