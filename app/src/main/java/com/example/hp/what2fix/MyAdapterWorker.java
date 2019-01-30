package com.example.hp.what2fix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapterWorker extends ArrayAdapter<String>
{

    public String [] workerName;
    public String [] work;
    public int orderId;
    public String [] date;
    Context context;
    public MyAdapterWorker(@NonNull Context context, int resource, String [] workerName, String [] work,int orderId, String [] date)
    {
        super(context,resource,workerName);
        this.context=context;
        this.workerName=workerName;
        this.work = work;
        this.orderId=orderId;
        this.date=date;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        convertView= LayoutInflater.from(context).inflate(R.layout.item_worker,parent,false );

        TextView tv1,tv2,tv3, tv4;
        tv1= (TextView) convertView.findViewById(R.id.worker_name);
        tv2= (TextView) convertView.findViewById(R.id.work);
        tv3= (TextView) convertView.findViewById(R.id.w_id);
        tv4= (TextView) convertView.findViewById(R.id.w_date);


        tv1.setText("Worker Name:  "+workerName[position]);
        tv2.setText("Work:  "+work[position]);
        tv3.setText("Order id:  "+orderId);
        tv4.setText("Date:  "+date[position]);

        return convertView;

    }
}