package com.example.hp.what2fix;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.ContextMenu;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.jar.Attributes;

public class CompletedFragment extends Fragment {
    ListView listView;
    ProgressDialog progressDialog;
    ListAdapter myAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    static int flag1 = 0;
    String[] sortItems = {"A-Z", "Z-A", "Newest first", "Oldest first", "Order Low to High", "Order High to Low"};
    int selectedItem;
    Name[] customer_name;
    Id[] customer_id;
    Date[] customer_date;
    Address[] customer_address;
    Phno[] customer_phno;
    Status[] customer_status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_completed, container, false);
        setHasOptionsMenu(true);
        listView = (ListView) v.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_completed);
        progressDialog = new ProgressDialog(getContext());
        if (flag1 == 0) {
            fetchData();
            flag1++;
        } else {
            myAdapter = new MyAdapter(getContext(), R.layout.item, JsonParseCompleted.orderId, JsonParseCompleted.customerName, JsonParseCompleted.date);
            listView.setAdapter(myAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(), DetailsCustomerCompleted.class);
                intent.putExtra("position", "" + position);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isOnline()) {
                    Toast.makeText(getContext(), "Check your internet connection !!", Toast.LENGTH_LONG).show();
                } else fetchData();
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.completed_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_sortby){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
            alertBuilder.setTitle("Sort By");
            alertBuilder.setSingleChoiceItems(sortItems, -1, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectedItem = i;
                }
            });
            alertBuilder.setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (selectedItem==0){
                                EntityName[] array = new EntityName[JsonParseCompleted.customerName.length];
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    array[j] = new EntityName(customer_name[j], customer_id[j], customer_date[j], customer_address[j],
                                            customer_phno[j], customer_status[j]);
                                }
                                Arrays.sort(array);
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    customer_name[j] = array[j].name;
                                    customer_id[j] = array[j].id;
                                    customer_date[j] = array[j].date;
                                    customer_address[j] = array[j].address;
                                    customer_phno[j] = array[j].phno;
                                    customer_status[j] = array[j].status;
                                }
                            } else if (selectedItem==1){
                                EntityName[] array = new EntityName[JsonParseCompleted.customerName.length];
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    array[j] = new EntityName(customer_name[j], customer_id[j], customer_date[j], customer_address[j],
                                            customer_phno[j], customer_status[j]);
                                }
                                Arrays.sort(array, Collections.reverseOrder());
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    customer_name[j] = array[j].name;
                                    customer_id[j] = array[j].id;
                                    customer_date[j] = array[j].date;
                                    customer_address[j] = array[j].address;
                                    customer_phno[j] = array[j].phno;
                                    customer_status[j] = array[j].status;
                                }
                            } else if (selectedItem==2){
                                EntityDate[] array = new EntityDate[JsonParseCompleted.customerName.length];
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    array[j] = new EntityDate(customer_name[j], customer_id[j], customer_date[j], customer_address[j],
                                            customer_phno[j], customer_status[j]);
                                }
                                Arrays.sort(array, Collections.reverseOrder());
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    customer_name[j] = array[j].name;
                                    customer_id[j] = array[j].id;
                                    customer_date[j] = array[j].date;
                                    customer_address[j] = array[j].address;
                                    customer_phno[j] = array[j].phno;
                                    customer_status[j] = array[j].status;
                                }
                            } else if (selectedItem==3){
                                EntityDate[] array = new EntityDate[JsonParseCompleted.customerName.length];
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    array[j] = new EntityDate(customer_name[j], customer_id[j], customer_date[j], customer_address[j],
                                            customer_phno[j], customer_status[j]);
                                }
                                Arrays.sort(array);
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    customer_name[j] = array[j].name;
                                    customer_id[j] = array[j].id;
                                    customer_date[j] = array[j].date;
                                    customer_address[j] = array[j].address;
                                    customer_phno[j] = array[j].phno;
                                    customer_status[j] = array[j].status;
                                }
                            } else if (selectedItem==4){
                                EntityId[] array = new EntityId[JsonParseCompleted.customerName.length];
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    array[j] = new EntityId(customer_name[j], customer_id[j], customer_date[j], customer_address[j],
                                            customer_phno[j], customer_status[j]);
                                }
                                Arrays.sort(array);
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    customer_name[j] = array[j].name;
                                    customer_id[j] = array[j].id;
                                    customer_date[j] = array[j].date;
                                    customer_address[j] = array[j].address;
                                    customer_phno[j] = array[j].phno;
                                    customer_status[j] = array[j].status;
                                }
                            } else if (selectedItem==5){
                                EntityId[] array = new EntityId[JsonParseCompleted.customerName.length];
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    array[j] = new EntityId(customer_name[j], customer_id[j], customer_date[j], customer_address[j],
                                            customer_phno[j], customer_status[j]);
                                }
                                Arrays.sort(array, Collections.reverseOrder());
                                for (int j = 0; j < JsonParseCompleted.customerName.length; j++ ) {
                                    customer_name[j] = array[j].name;
                                    customer_id[j] = array[j].id;
                                    customer_date[j] = array[j].date;
                                    customer_address[j] = array[j].address;
                                    customer_phno[j] = array[j].phno;
                                    customer_status[j] = array[j].status;
                                }
                            }
                            for (int j=0; j<customer_name.length; j++){
                                JsonParseCompleted.customerName[j] = customer_name[j].getName();
                                JsonParseCompleted.orderId[j] = customer_id[j].getId();
                                JsonParseCompleted.date[j] = customer_date[j].getDate();
                                JsonParseCompleted.address[j] = customer_address[j].getAddress();
                                JsonParseCompleted.phoneNumber[j] = customer_phno[j].getPhno();
                                JsonParseCompleted.status[j] = customer_status[j].getStatus();
                            }
                            /*Toast.makeText(getContext(), Arrays.toString(names)+"\n"+Arrays.toString(ids)+"\n"+Arrays.toString(dates),
                                    Toast.LENGTH_LONG).show();*/
                            myAdapter = new MyAdapter(getContext(), R.layout.item, JsonParseCompleted.orderId, JsonParseCompleted.customerName,
                                    JsonParseCompleted.date);
                            listView.setAdapter(myAdapter);
                        }
                    })
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
            Button positive = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button negative = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positive.setTextColor(Color.parseColor("#014c6f"));
            negative.setTextColor(Color.parseColor("#014c6f"));
        } else if (id == R.id.action_logout) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("Do you want to logout ?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog close = alert.create();
            close.setTitle("Logout");
            close.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://boxinall.in/kshitiz/fetch_completed_customer.php"; // change link
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void jsonParsing(String response) {
        JsonParseCompleted jsonParse = new JsonParseCompleted(response);
        jsonParse.jsonTodo();
        myAdapter = new MyAdapter(getContext(), R.layout.item, JsonParseCompleted.orderId, JsonParseCompleted.customerName,
                JsonParseCompleted.date);
        listView.setAdapter(myAdapter);
        customer_name = new Name[JsonParseCompleted.customerName.length];
        customer_id = new Id[JsonParseCompleted.customerName.length];
        customer_date = new Date[JsonParseCompleted.customerName.length];
        customer_address = new Address[JsonParseCompleted.customerName.length];
        customer_phno = new Phno[JsonParseCompleted.customerName.length];
        customer_status = new Status[JsonParseCompleted.customerName.length];
        for (int j=0; j<JsonParseCompleted.customerName.length; j++){
            customer_name[j] = new Name(JsonParseCompleted.customerName[j]);
            customer_id[j] = new Id(JsonParseCompleted.orderId[j]);
            customer_date[j] = new Date(JsonParseCompleted.date[j]);
            customer_address[j] = new Address(JsonParseCompleted.address[j]);
            customer_phno[j] = new Phno(JsonParseCompleted.phoneNumber[j]);
            customer_status[j] = new Status(JsonParseCompleted.status[j]);
        }
        swipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
    }


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }


}