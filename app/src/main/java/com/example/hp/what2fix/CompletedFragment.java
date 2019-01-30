package com.example.hp.what2fix;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class CompletedFragment extends Fragment {

    ListView listView;
    ProgressDialog progressDialog;
    ListAdapter myAdapter;
   /* Boolean isLoaded = false;

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
        View v = inflater.inflate(R.layout.fragment_completed, container, false);

            listView = (ListView) v.findViewById(R.id.listView);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            fetchData();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity().getBaseContext(), DetailsCustomerCompleted.class);
                    intent.putExtra("position", "" + position);
                    startActivity(intent);
                }
            });
        return v;
    }

    private void fetchData()
    {
        String url="https://boxinall.in/kshitiz/fetch_completed_customer.php"; // change link
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
        JsonParseCompleted jsonParse=new JsonParseCompleted(response);
        jsonParse.jsonTodo();
        myAdapter=new MyAdapter(getContext(),R.layout.item,JsonParseCompleted.orderId,JsonParseCompleted.customerName,JsonParseCompleted.date);
        listView.setAdapter(myAdapter);
        progressDialog.hide();
        //createExampleList(JsonParse.customerName, JsonParse.orderId,jsonParse.date, JsonParse.customerName.length);
        /*Collections.sort(mExampleList, new Comparator<ExampleItem>() {
            @Override
            public int compare(ExampleItem obj1, ExampleItem obj2) {
                return obj1.getCustomerName().compareTo(obj2.getCustomerName());
            }
        });*/

        /*customers = new String[JsonParse.customerName.length];
        customers = Arrays.copyOf(JsonParse.customerName, JsonParse.customerName.length);
        customers = sort(customers, customers.length);*/
        //Toast.makeText(getContext(), Arrays.toString(mExampleList.toArray()), Toast.LENGTH_LONG).show();
    }

    /*private String[] sort(String[] customerName, int length) {
        String temp;
        for (int i = 0; i < length; i++)
            for (int j = i + 1; j < length; j++)
                if (customerName[i].compareTo(customerName[j])>0) {
                    temp = customerName[i];
                    customerName[i] = customerName[j];
                    customerName[j] = temp;
                }
        return customerName;
    }*/

   /* public void createExampleList(String[] customers, String[] orderId, String[] date, int length){
        mExampleList = new ArrayList<>();
        for (int i=0; i<length; i++){
            mExampleList.add(new ExampleItem(customers[i], orderId[i], date[i]));
        }

    }*/

    /*public void abc(){
        //new MyAdapter(getContext(), R.layout.item, JsonParse.orderId, customers, JsonParse.date);
    }*/

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sort_by, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.sort){
            //Arrays.sort(customers);
            Toast.makeText(getContext(),"jbvdbskvbksd"*//* Arrays.toString(customers)*//*, Toast.LENGTH_LONG).show();
            *//*myAdapter=new MyAdapter(getContext(),R.layout.item,JsonParse.orderId,Arrays.toString(),JsonParse.date);
            myAdapter.notifyDataSetChanged();*//*
            return true;

        }
        return super.onOptionsItemSelected(item);
    }*/
}