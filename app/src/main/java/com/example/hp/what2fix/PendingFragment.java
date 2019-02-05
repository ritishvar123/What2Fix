package com.example.hp.what2fix;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PendingFragment extends Fragment {

    ListView listView2;
    ProgressDialog progressDialog;
    ListAdapter myAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    static int flag3 = 0;
  /*  Boolean isLoaded = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isLoaded = true;
        }
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending, container, false);
        listView2 = (ListView) v.findViewById(R.id.listView2);
        progressDialog = new ProgressDialog(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_pending);
        if (flag3==0) {
            fetchData();
            flag3++;
        } else {
            myAdapter=new MyAdapter(getContext(),R.layout.item,JsonParsePending.orderId,JsonParsePending.customerName,JsonParsePending.date);
            listView2.setAdapter(myAdapter);
        }
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Toast.makeText(getContext(), JsonParse.phoneNumber[position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getBaseContext(), DetailsCustomerPending.class);
                    intent.putExtra("position", "" + position);
                    startActivity(intent);
                }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*if (!isOnline()){
                    Toast.makeText(MainActivity3_Edit_Members_List.this, "Check your internet connection !!", Toast.LENGTH_LONG).show();
                } else*/
                fetchData();
            }
        });
        return v;
    }

    private void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="https://boxinall.in/kshitiz/fetch_pending_customer.php"; // change link
        StringRequest stringRequest= new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonParsing(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error in response", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void jsonParsing(String response) {
        JsonParsePending jsonParse=new JsonParsePending(response);
        jsonParse.jsonTodo();
        myAdapter=new MyAdapter(getContext(),R.layout.item,JsonParsePending.orderId,JsonParsePending.customerName,JsonParsePending.date);
        listView2.setAdapter(myAdapter);
        swipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
    }
}
