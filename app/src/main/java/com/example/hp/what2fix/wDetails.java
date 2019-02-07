package com.example.hp.what2fix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class wDetails extends AppCompatActivity {
    ListView listView4;
    ProgressDialog progressDialog;
    int orderId;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_details);
        listView4 = (ListView)findViewById(R.id.listView4);
        progressDialog = new ProgressDialog(wDetails.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Intent i = getIntent();
        orderId = Integer.parseInt(i.getStringExtra("OrderId"));
        status  = i.getStringExtra("status");
        fetchWorkerData(orderId);
        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent=new Intent(wDetails.this,editWorkerDetails.class);
                intent.putExtra("wPosition",""+position);
                intent.putExtra("OrderId",""+orderId);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });
    }

    private void fetchWorkerData(final int orderId) {
        String url="https://boxinall.in/kshitiz/fetchworker.php";
        StringRequest stringRequest= new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonParsingWorker(response);
            }
        }, new Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) {}
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map= new HashMap<>();
                map.put("OrderId",""+orderId);
                return  map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(wDetails.this);
        requestQueue.add(stringRequest);
    }

    private void jsonParsingWorker(String response) {
        JsonParseWorker jsonParseWorker=new JsonParseWorker(response);
        jsonParseWorker.jsonTodoWorker();
        MyAdapterWorker myAdapterWorker=new MyAdapterWorker(wDetails.this,R.layout.item_worker,JsonParseWorker.workerName,
                JsonParseWorker.work,orderId,JsonParseWorker.date);
        listView4.setAdapter(myAdapterWorker);
        progressDialog.hide();
    }


}
