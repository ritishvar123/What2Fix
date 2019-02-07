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

public class EditAddWorker extends AppCompatActivity {
    EditText et_add_worker_name, et_add_worker_work, et_add_worker_phno, et_add_worker_cost, et_add_worker_profit_per;
    TextView tv_add_worker_profit;
    Button btn_profit, btn_save, btn_cancel;
    String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_worker);
        et_add_worker_name        = (EditText)findViewById(R.id.editText25);
        et_add_worker_work        = (EditText)findViewById(R.id.editText26);
        et_add_worker_phno        = (EditText)findViewById(R.id.editText27);
        et_add_worker_cost        = (EditText)findViewById(R.id.editText28);
        et_add_worker_profit_per  = (EditText)findViewById(R.id.editText29);
        tv_add_worker_profit      = (TextView)findViewById(R.id.textView16);
        btn_profit = (Button)findViewById(R.id.button_edit_add_profit);
        btn_save = (Button)findViewById(R.id.button_edit_add_save);
        btn_cancel = (Button)findViewById(R.id.button_edit_add_cancel);
        Intent i = getIntent();
        order = i.getStringExtra("orderId");
        btn_profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String worker_cost = et_add_worker_cost.getText().toString();
                String worker_profit_per = et_add_worker_profit_per.getText().toString();
                if (worker_cost.trim().length()==0 || worker_profit_per.trim().length()==0) {
                    tv_add_worker_profit.setText("0");
                } else {
                    double profit_per = Double.parseDouble(worker_profit_per);
                    double cost       = Double.parseDouble(worker_cost);
                    double profit     = (cost / 100.0) * profit_per;
                    tv_add_worker_profit.setText("" + String.format("%.2f", profit));
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String worker_name       = et_add_worker_name.getText().toString();
                final String worker_work       = et_add_worker_work.getText().toString();
                final String worker_phone      = et_add_worker_phno.getText().toString();
                final String worker_cost       = et_add_worker_cost.getText().toString();
                final String worker_profit_per = et_add_worker_profit_per.getText().toString();
                final String worker_profit     = tv_add_worker_profit.getText().toString();
                if ( worker_name.trim().length()==0 || worker_work.trim().length()==0 || worker_phone.trim().length()==0 ||
                        worker_cost.trim().length()==0 || worker_profit_per.trim().length()==0 ) {
                    if ( worker_name.trim().length()==0 )
                        et_add_worker_name.setError("Enter valid Name");
                    if ( worker_work.trim().length()==0 )
                        et_add_worker_work.setError("Enter valid Work");
                    if ( worker_phone.trim().length()==0 )
                        et_add_worker_phno.setError("Enter valid Phone No");
                    if ( worker_cost.trim().length()==0 )
                        et_add_worker_cost.setError("Enter valid Cost");
                    if ( worker_profit_per.trim().length()==0 )
                        et_add_worker_profit_per.setError("Enter valid Profit %");
                } else if( worker_phone.length()!=10 ) {
                    et_add_worker_phno.setError("Enter valid 10 digits Phone No");
                } else if(worker_profit.equals("  --  ")){
                    Toast.makeText(EditAddWorker.this, "Profit is not set", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EditAddWorker.this);
                    alert.setMessage("Save your changes ?").setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="https://boxinall.in/kshitiz/insertworker.php";
                                    StringRequest stringRequest= new StringRequest(1, url, new Response.Listener<String>() {
                                        @Override public void onResponse(String response) {}
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {}
                                    })
                                    {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> map=new HashMap<>();
                                            map.put("orderId",order);
                                            map.put("workerName",worker_name);
                                            map.put("workerWork",worker_work);
                                            map.put("workerPhone",worker_phone);
                                            map.put("cost",worker_cost);
                                            map.put("percent",worker_profit_per);
                                            map.put("profit",worker_profit);
                                            return map;
                                        }
                                    };
                                    RequestQueue requestQueue= Volley.newRequestQueue(EditAddWorker.this);
                                    requestQueue.add(stringRequest);
                                    Toast.makeText(EditAddWorker.this, "Saved Successfully !!",Toast.LENGTH_LONG).show();
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
                    close.show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EditAddWorker.this);
                alert.setMessage("Discard your changes ?").setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                ;
                AlertDialog close = alert.create();
                close.show();
            }
        });
    }


}

