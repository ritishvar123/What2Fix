package com.example.hp.what2fix;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class DetailsCustomerPending extends AppCompatActivity {
    TextView tv_customer_name,  tv_cus_order_id;
    EditText tv_customer_addr, tv_customer_phno;
    AutoCompleteTextView tv_customer_status;
    Button btn_show_worker, btn_save_changes, btn_add_workers;
    FloatingActionButton editCustomer;
    Boolean flag = false;
    String [] status = {"Completed", "In Progress", "Pending"};
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cutomer_pending);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_customer_name      =  (TextView)findViewById(R.id.textView2);
        tv_customer_addr      =  (EditText) findViewById(R.id.editText12);
        tv_customer_phno      =  (EditText) findViewById(R.id.editText16);
        tv_customer_status    =  (AutoCompleteTextView) findViewById(R.id.autoCompleteTextviewPen);
        tv_cus_order_id       =  (TextView)findViewById(R.id.textView5);
        btn_show_worker       =  (Button)findViewById(R.id.button8);
        btn_save_changes      =  (Button)findViewById(R.id.button9);
        btn_add_workers       =  (Button)findViewById(R.id.button13);
        editCustomer          =  (FloatingActionButton)findViewById(R.id.fab5);
        Intent i = getIntent();
        position = Integer.parseInt(i.getStringExtra("position"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, status);
        tv_customer_status.setAdapter(adapter);
        tv_customer_name.setText(JsonParsePending.customerName[position]);
        tv_customer_addr.setText(JsonParsePending.address[position]);
        tv_customer_phno.setText(JsonParsePending.phoneNumber[position]);
        tv_customer_status.setText(JsonParsePending.status[position]);
        tv_cus_order_id.setText(JsonParsePending.orderId[position]);
        tv_customer_addr.setEnabled(false);
        tv_customer_phno.setEnabled(false);
        tv_customer_status.setEnabled(false);
        tv_customer_addr.setFocusableInTouchMode(false);
        tv_customer_phno.setFocusableInTouchMode(false);
        tv_customer_status.setFocusableInTouchMode(false);
        btn_save_changes.setVisibility(View.INVISIBLE);
        btn_show_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsCustomerPending.this, wDetails.class);
                i.putExtra("OrderId",""+JsonParsePending.orderId[position]);
                i.putExtra("status",JsonParsePending.status[position] );
                startActivity(i);
            }
        });
        editCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_customer_addr.setEnabled(true);
                tv_customer_phno.setEnabled(true);
                tv_customer_status.setEnabled(true);
                tv_customer_addr.setFocusableInTouchMode(true);
                tv_customer_phno.setFocusableInTouchMode(true);
                tv_customer_status.setFocusableInTouchMode(true);
                btn_save_changes.setVisibility(View.VISIBLE);
                flag = true;
            }
        });
        btn_add_workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsCustomerPending.this, EditAddWorker.class);
                i.putExtra("orderId", JsonParsePending.orderId[position]);
                startActivity(i);
            }
        });
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = tv_customer_addr.getText().toString();
                String phno    = tv_customer_phno.getText().toString();
                String wStatus  = tv_customer_status.getText().toString();
                if (address.trim().length()==0 || phno.trim().length()==0 || wStatus.trim().length()==0) {
                    if (address.trim().length()==0)
                        tv_customer_addr.setError("Enter valid Address");
                    if (phno.trim().length()==0)
                        tv_customer_phno.setError("Enter valid Phone no");
                    if (wStatus.trim().length()==0)
                        tv_customer_status.setError("Enter valid Status");
                } else if (phno.trim().length()!=10) {
                    tv_customer_phno.setError("Enter valid 10 digits Phone no");
                } else if (!(wStatus.equals(status[0]) || wStatus.equals(status[1]) || wStatus.equals(status[2])) ) {
                    tv_customer_status.setError("Status can be of only 3 types");
                } else {
                    tv_customer_addr.setEnabled(false);
                    tv_customer_phno.setEnabled(false);
                    tv_customer_status.setEnabled(false);
                    tv_customer_addr.setFocusableInTouchMode(false);
                    tv_customer_phno.setFocusableInTouchMode(false);
                    tv_customer_status.setFocusableInTouchMode(false);
                    btn_save_changes.setVisibility(View.INVISIBLE);
                    Toast.makeText(DetailsCustomerPending.this,JsonParsePending.orderId[position], Toast.LENGTH_LONG ).show();
                    updateCustomer(JsonParsePending.orderId[position], address, phno, wStatus);
                    Toast.makeText(DetailsCustomerPending.this, "Updated Successfully" , Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            }
        });
    }


    public void updateCustomer(final String orderid, final String address, final String phno, final String status) {
        String url="https://boxinall.in/kshitiz/updateCustomer.php";
        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {}
        }, new Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) {}}
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("orderid",orderid);
                map.put("address",address);
                map.put("phoneno",phno);
                map.put("status",status);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if(flag){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Discard the saved changes ?").setCancelable(false)
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
            AlertDialog discard = alert.create();
            discard.show();
        }else
            finish();
    }


}
