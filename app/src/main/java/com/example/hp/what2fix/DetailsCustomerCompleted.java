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

public class DetailsCustomerCompleted extends AppCompatActivity {

    TextView tv_customer_name,  tv_cus_order_id;
    EditText et_customer_addr, et_customer_phno;
    AutoCompleteTextView tv_customer_status;
    Button btn_show_worker;// btn_save_changes, btn_add_worker;
    //FloatingActionButton editCustomer;
    Boolean flag = false;

    String [] status = {"Completed", "In Progress", "Pending"};
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cutomer_completed);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tv_customer_name      =  (TextView)findViewById(R.id.textView);
        et_customer_addr      =  (EditText) findViewById(R.id.editText6);
        et_customer_phno      =  (EditText) findViewById(R.id.editText11);
        tv_customer_status    =  (AutoCompleteTextView) findViewById(R.id.autoCompleteTextviewC);
        tv_cus_order_id       =  (TextView)findViewById(R.id.textView9);
        btn_show_worker       =  (Button)findViewById(R.id.button3);
       /* btn_save_changes      =  (Button)findViewById(R.id.button4);
        btn_add_worker        =  (Button)findViewById(R.id.button12);
        editCustomer          =  (FloatingActionButton)findViewById(R.id.fab4);*/

        Intent i = getIntent();
        position = Integer.parseInt(i.getStringExtra("position"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, status);
        tv_customer_status.setAdapter(adapter);

        tv_customer_name.setText(JsonParseCompleted.customerName[position]);
        et_customer_addr.setText(JsonParseCompleted.address[position]);
        et_customer_phno.setText(JsonParseCompleted.phoneNumber[position]);
        tv_customer_status.setText(JsonParseCompleted.status[position]);
        tv_cus_order_id.setText(JsonParseCompleted.orderId[position]);

        et_customer_addr.setEnabled(false);
        et_customer_phno.setEnabled(false);
        tv_customer_status.setEnabled(false);
        et_customer_addr.setFocusableInTouchMode(false);
        et_customer_phno.setFocusableInTouchMode(false);
        tv_customer_status.setFocusableInTouchMode(false);
        //btn_save_changes.setVisibility(View.INVISIBLE);

        btn_show_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsCustomerCompleted.this, wDetails.class);
                i.putExtra("OrderId",""+JsonParseCompleted.orderId[position]);
                i.putExtra("status",JsonParseCompleted.status[position] );
                startActivity(i);
            }
        });

        /*btn_add_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsCustomerCompleted.this, EditAddWorker.class);
                i.putExtra("orderId", JsonParseCompleted.orderId[position]);
                startActivity(i);
            }
        });

        editCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_customer_addr.setEnabled(true);
                et_customer_phno.setEnabled(true);
                tv_customer_status.setEnabled(true);
                et_customer_addr.setFocusableInTouchMode(true);
                et_customer_phno.setFocusableInTouchMode(true);
                tv_customer_status.setFocusableInTouchMode(true);
                btn_save_changes.setVisibility(View.VISIBLE);
                flag = true;
            }
        });

        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = et_customer_addr.getText().toString();
                String phno    = et_customer_phno.getText().toString();
                String wStatus = tv_customer_status.getText().toString();
                if (address.trim().length()==0 || phno.trim().length()==0 || wStatus.trim().length()==0) {
                    if (address.trim().length()==0)
                        et_customer_addr.setError("Enter valid Address");
                    if (phno.trim().length()==0)
                        et_customer_phno.setError("Enter valid Phone no");
                    if (wStatus.trim().length()==0)
                        tv_customer_status.setError("Enter valid Status");
                } else if (phno.trim().length()!=10) {
                    et_customer_phno.setError("Enter valid 10 digits Phone no");
                } else if (!(wStatus.equals(status[0]) || wStatus.equals(status[1]) || wStatus.equals(status[2])) ) {
                    tv_customer_status.setError("Status can be of only 3 types");
                } else {
                    et_customer_addr.setEnabled(false);
                    et_customer_phno.setEnabled(false);
                    tv_customer_status.setEnabled(false);
                    et_customer_addr.setFocusableInTouchMode(false);
                    et_customer_phno.setFocusableInTouchMode(false);
                    tv_customer_status.setFocusableInTouchMode(false);
                    btn_save_changes.setVisibility(View.INVISIBLE);
                    Toast.makeText(DetailsCustomerCompleted.this, "Updated Successfully" , Toast.LENGTH_SHORT).show();
                    flag = false;
                }
            }
        });*/

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
                    })
            ;
            AlertDialog discard = alert.create();
            discard.show();
        }else
            finish();
    }
}
