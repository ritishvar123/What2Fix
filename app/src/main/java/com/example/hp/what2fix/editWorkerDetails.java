package com.example.hp.what2fix;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
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

public class editWorkerDetails extends AppCompatActivity {
    TextView tv_worker_name;
    EditText et_worker_work, et_worker_phno, et_worker_cost, et_worker_profit_per, et_order_id;
    FloatingActionButton btn_edit_worker;
    AutoCompleteTextView autoCompleteTextView;
    int position;
    String orderId;
    Boolean flag = false, temp = false;
    String status="", z = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_worker_details);
        tv_worker_name = (TextView)findViewById(R.id.textView10);
        et_worker_work = (EditText)findViewById(R.id.editText19);
        et_worker_phno = (EditText)findViewById(R.id.editText20);
        et_worker_cost = (EditText)findViewById(R.id.editText21);
        et_worker_profit_per = (EditText)findViewById(R.id.editText22);
        et_order_id = (EditText)findViewById(R.id.editText24);
        btn_edit_worker = (FloatingActionButton)findViewById(R.id.fab3);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextview);
        Intent i = getIntent();
        position = Integer.parseInt(i.getStringExtra("wPosition"));
        orderId  = i.getStringExtra("OrderId");
        status   = i.getStringExtra("status");
        tv_worker_name.setText(JsonParseWorker.workerName[position]);
        et_worker_work.setText(JsonParseWorker.work[position]);
        et_worker_phno.setText(JsonParseWorker.wphone[position]);
        et_worker_cost.setText(JsonParseWorker.cost[position]);
        et_worker_profit_per.setText(JsonParseWorker.profitPercent[position]);
        et_order_id.setText(orderId);
        et_worker_work.setEnabled(false);
        et_worker_phno.setEnabled(false);
        et_worker_cost.setEnabled(false);
        et_worker_profit_per.setEnabled(false);
        et_order_id.setEnabled(false);
        et_worker_work.setFocusableInTouchMode(false);
        et_worker_phno.setFocusableInTouchMode(false);
        et_worker_cost.setFocusableInTouchMode(false);
        et_worker_profit_per.setFocusableInTouchMode(false);
        et_order_id.setFocusableInTouchMode(false);
        if(status.equals("Completed")) {
            btn_edit_worker.setVisibility(View.INVISIBLE);
            temp = true;
        }
        btn_edit_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_worker_phno.setEnabled(true);
                et_worker_cost.setEnabled(true);
                et_worker_profit_per.setEnabled(true);
                et_worker_phno.setFocusableInTouchMode(true);
                et_worker_cost.setFocusableInTouchMode(true);
                et_worker_profit_per.setFocusableInTouchMode(true);
                z = "";
                flag = true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!temp) {
            getMenuInflater().inflate(R.menu.edit_worker_menu, menu);
            return true;
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save){
            String work = et_worker_work.getText().toString();
            String phno = et_worker_phno.getText().toString();
            String cost = et_worker_cost.getText().toString();
            String profit_per = et_worker_profit_per.getText().toString();
            String profit = String.valueOf((Double.parseDouble(cost) / 100) * (Double.parseDouble(profit_per)));
            if(phno.trim().length()==0 || cost.trim().length()==0 ||
                    profit_per.trim().length()==0 || profit.trim().length()==0){
                if (phno.trim().length()==0)
                    et_worker_phno.setError("Field can't empty");
                if (cost.trim().length()==0)
                    et_worker_cost.setError("Field can't empty");
                if (profit_per.trim().length()==0)
                    et_worker_profit_per.setError("Field can't empty");
            } else if (phno.trim().length()!=10) {
                et_worker_phno.setError("Enter valid 10 digits Phone no");
            } else {
                et_worker_phno.setEnabled(false);
                et_worker_cost.setEnabled(false);
                et_worker_profit_per.setEnabled(false);
                et_order_id.setEnabled(false);
                et_worker_phno.setFocusableInTouchMode(false);
                et_worker_cost.setFocusableInTouchMode(false);
                et_worker_profit_per.setFocusableInTouchMode(false);
                et_order_id.setFocusableInTouchMode(false);
                z = "Update";
                change(orderId, work, phno, cost, profit, profit_per, z);
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_LONG).show();
                flag = false;
            }
        } else if(id == R.id.delete){
            et_worker_phno.setEnabled(false);
            et_worker_cost.setEnabled(false);
            et_worker_profit_per.setEnabled(false);
            et_order_id.setEnabled(false);
            et_worker_phno.setFocusableInTouchMode(false);
            et_worker_cost.setFocusableInTouchMode(false);
            et_worker_profit_per.setFocusableInTouchMode(false);
            et_order_id.setFocusableInTouchMode(false);
            final String work = et_worker_work.getText().toString();
            final String phno = et_worker_phno.getText().toString();
            final String cost = et_worker_cost.getText().toString();
            final String profit_per = et_worker_profit_per.getText().toString();
            final String profit = String.valueOf((Double.parseDouble(cost) / 100) * (Double.parseDouble(profit_per)));
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Delete this worker?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            z = "Delete";
                            change(orderId,work, phno, cost, profit, profit_per,z);
                            Toast.makeText(editWorkerDetails.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
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
            AlertDialog discard = alert.create();
            discard.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if(flag){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                    });
            AlertDialog discard = alert.create();
            discard.show();
        }else
            finish();
    }

    private void change(final String orderid, final String work, final String phoneno, final String cost,
                        final String profit, final String profitpercent, final String result) {
        String url="https://boxinall.in/kshitiz/updateAndDeleteWorker.php";
        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override public void onResponse(String response) {}
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {}}
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("orderid",orderid);
                map.put("work",work);
                map.put("phoneno",phoneno);
                map.put("cost",cost);
                map.put("profit",profit);
                map.put("profitpercent",profitpercent);
                map.put("result",result);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
