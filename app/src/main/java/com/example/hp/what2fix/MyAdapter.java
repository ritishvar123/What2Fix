package com.example.hp.what2fix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyAdapter extends ArrayAdapter<String>
{

    public String [] orderId;
    public String [] customerName;
    public String [] date;
    Context context;

    public MyAdapter(Context context, int resource, String [] orderId, String [] customerName, String [] date) {
        super(context,resource,customerName);
        this.context=context;
        this.orderId = new String[orderId.length];
        this.customerName = new String[customerName.length];
        this.date = new String[date.length];
        this.orderId= Arrays.copyOf(orderId,orderId.length);
        this.customerName = Arrays.copyOf(customerName, customerName.length);
        this.date = Arrays.copyOf(date,date.length);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = LayoutInflater.from(context).inflate(R.layout.item,parent,false );

        TextView tv1,tv2,tv3;
        tv1= (TextView) customView.findViewById(R.id.name);
        tv2= (TextView) customView.findViewById(R.id.id1);
        tv3= (TextView) customView.findViewById(R.id.date);

        tv1.setText("Customer Name:  "+customerName[position]);
        tv2.setText("\tOrder id:  "+orderId[position]);
        tv3.setText("\tDate:  "+date[position]);

        return customView;

    }
}