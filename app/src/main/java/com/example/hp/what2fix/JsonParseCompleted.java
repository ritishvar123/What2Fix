package com.example.hp.what2fix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonParseCompleted
{
    public static String [] orderId;
    public static String [] customerName;
    public static String [] address;
    public static String [] phoneNumber;
    public static String [] status;
    public static String [] workerName;
    public static String [] work;
    public static String [] wphone;
    public static String [] cost;
    public static String [] profitPercent;
    public static String [] profit;
    public static String [] date;
    public static int size;
    String json;


    public JsonParseCompleted(String response)
    {
        this.json=response;
    }

    public void jsonTodo()
    {
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            size=jsonArray.length();
            orderId=new String[jsonArray.length()];
            customerName=new String[jsonArray.length()];
            address=new String[jsonArray.length()];
            phoneNumber=new String[jsonArray.length()];
            status=new String[jsonArray.length()];
//            workerName= new String[jsonArray.length()];
//            work=new String[jsonArray.length()];
//            wphone=new String[jsonArray.length()];
//            cost=new String[jsonArray.length()];
//            profitPercent=new String[jsonArray.length()];
//            profit=new String[jsonArray.length()];
            date=new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                orderId[i]=jsonObject1.getString("OrderId");
                customerName[i]=jsonObject1.getString("Customer Name");
                address[i]=jsonObject1.getString("Address");
                phoneNumber[i]=jsonObject1.getString("PhoneNo");
                status[i]=jsonObject1.getString("Status");
//                workerName[i]=jsonObject1.getString("Worker Name");
//                work[i]=jsonObject1.getString("Work");
//                wphone[i]=jsonObject1.getString("Phone Number");
//                cost[i]=jsonObject1.getString("Cost");
//                profitPercent[i]=jsonObject1.getString("Profit Percent");
//                profit[i]=jsonObject1.getString("Profit");//
                date[i]=jsonObject1.getString("Date");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}