package com.example.hp.what2fix;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity {
    RelativeLayout relativeLayout;
    Button btn_get_order_id, btn_add_worker;
    FloatingActionButton btn_save_customer;
    EditText et_customer_name, et_customer_add, et_customer_phone;
    TextView tv_worker_count, tv_order_id;
    Spinner sp;
    String select_type[] = {"--Select Work Type--", "Completed", "In Progress", "Pending"};
    String record = null;
    String customer_name, customer_add, customer_phone, worker_count, o_id;
    Double total_pay = 0.0, total_profit = 0.0;
    String order_id[], order;
    Boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // hide keyboard
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for back button

        relativeLayout    = (RelativeLayout)findViewById(R.id.relative_layout_2);
        et_customer_name  = (EditText)findViewById(R.id.editText3);
        et_customer_add   = (EditText)findViewById(R.id.editText4);
        et_customer_phone = (EditText)findViewById(R.id.editText5);
        tv_worker_count   = (TextView)findViewById(R.id.textView24);
        tv_order_id       = (TextView)findViewById(R.id.textView26);

        customer_name     = et_customer_name.getText().toString();
        customer_add      = et_customer_add.getText().toString();
        customer_phone    = et_customer_phone.getText().toString();

        btn_get_order_id = (Button)findViewById(R.id.button5);
        btn_add_worker    = (Button)findViewById(R.id.button2);
        btn_save_customer     = (FloatingActionButton) findViewById(R.id.fab2);

        sp = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, select_type
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        record = "Completed";
                        break;
                    case 2:
                        record = "In Progress";
                        break;
                    case 3:
                        record = "Pending";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_get_order_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer_name = et_customer_name.getText().toString();
                customer_add = et_customer_add.getText().toString();
                customer_phone = et_customer_phone.getText().toString();

                if ( customer_name.trim().length()==0 || customer_add.trim().length()==0 || customer_phone.trim().length()==0 ) {
                    if( customer_name.trim().length()==0 )
                        et_customer_name.setError("Enter valid Name");
                    if( customer_add.trim().length()==0 )
                        et_customer_add.setError("Enter valid Address");
                    if( customer_phone.trim().length()==0 )
                        et_customer_phone.setError("Enter valid Phone no");
                } else if( customer_phone.length()!=10 ) {
                    et_customer_phone.setError("Enter valid 10 digits Phone number");
                } else if (record == null) {
                    Toast.makeText(Create.this, "Select status type", Toast.LENGTH_LONG).show();
                } else {
                    if (flag) {
                        String url = "https://boxinall.in/kshitiz/insertcustomer.php";
                        StringRequest stringRequest = new StringRequest(1, url, new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("customerName", customer_name);
                                map.put("customerAddress", customer_add);
                                map.put("customerPhone", customer_phone);
                                map.put("record", record);
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Create.this);
                        requestQueue.add(stringRequest);

                        android.os.Handler handler = new android.os.Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String url3 = "https://boxinall.in/kshitiz/orderIdFetch.php";
                                StringRequest stringRequest1 = new StringRequest(1, url3, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                            order_id = new String[jsonArray.length()];
                                            //for(int i=0;i<jsonArray.length();i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                            order_id[0] = jsonObject1.getString("OrderId");
                                            tv_order_id.setText(order_id[0]);
                                            order = order_id[0];
                                            //}
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                                RequestQueue requestQueue1 = Volley.newRequestQueue(Create.this);
                                requestQueue1.add(stringRequest1);
                            }
                        }, 4000);
                        Toast.makeText(Create.this, "Please Wait...", Toast.LENGTH_LONG).show();
                        et_customer_name.setEnabled(false);
                        et_customer_add.setEnabled(false);
                        et_customer_phone.setEnabled(false);
                        et_customer_name.setFocusableInTouchMode(false);
                        et_customer_add.setFocusableInTouchMode(false);
                        et_customer_phone.setFocusableInTouchMode(false);
                        flag = false;
                    }
                }
            }
        });

        btn_add_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = tv_order_id.getText().toString();
                if(!id.equals("0")) {
                    worker_count = tv_worker_count.getText().toString();
                    Intent i2 = new Intent(Create.this, WorkerActivity.class);
                    i2.putExtra("count", worker_count);
                    i2.putExtra("orderId", order);
                    startActivityForResult(i2, 999);
                }
            }
        });

        btn_save_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customer_name  = et_customer_name.getText().toString();
                customer_add   = et_customer_add.getText().toString();
                customer_phone = et_customer_phone.getText().toString();
                if ( customer_name.trim().length()==0 || customer_add.trim().length()==0 || customer_phone.trim().length()==0 ) {
                    if( customer_name.trim().length()==0 )
                        et_customer_name.setError("Enter valid Name");
                    if( customer_add.trim().length()==0 )
                        et_customer_add.setError("Enter valid Address");
                    if( customer_phone.trim().length()==0 )
                        et_customer_phone.setError("Enter valid Phone No");
                } else if( customer_phone.length()!=10 ) {
                    et_customer_phone.setError("Enter valid 10 digits Phone number");
                } else if (record == null) {
                    Toast.makeText(Create.this, "Select status type", Toast.LENGTH_LONG).show();
                } else if ( (tv_worker_count.getText().toString()).equals("0") ) {
                    Snackbar.make(relativeLayout, "Add Atleast 1 worker", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Intent i3 = new Intent(Create.this, StatusActivity.class);
                    i3.putExtra("total_pay", ""+total_pay);
                    i3.putExtra("total_profit", ""+total_profit);
                    i3.putExtra("orderId", order);
                    startActivity(i3);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 999 && resultCode == RESULT_OK ){
            String count = data.getStringExtra("count");
            total_pay += Double.parseDouble(data.getStringExtra("total_pay"));
            total_profit += Double.parseDouble(data.getStringExtra("total_profit"));

            tv_worker_count.setText(count);
            /*if ( (tv_worker_count.getText().toString()).equals("0") ) {
                et_customer_name.setEnabled(true);
                et_customer_add.setEnabled(true);
                et_customer_phone.setEnabled(true);
            }
            else {
                et_customer_name.setEnabled(false);
                et_customer_add.setEnabled(false);
                et_customer_phone.setEnabled(false);

            }*/
        }
    }

    @Override
    public void onBackPressed() {
        customer_name = et_customer_name.getText().toString();
        customer_add = et_customer_add.getText().toString();
        customer_phone = et_customer_phone.getText().toString();

        if ( !(customer_name.trim().length()==0 && customer_add.trim().length()==0 && customer_phone.trim().length()==0 ) ) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Discard your changes ?").setCancelable(true)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteExistingRecord();
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
        else
            finish();
    }

    public void deleteExistingRecord()
    {
        String url="https://boxinall.in/kshitiz/delete.php";
        StringRequest stringRequest= new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map= new HashMap<>();
                map.put("orderId",order);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Create.this);
        requestQueue.add(stringRequest);
    }

}
