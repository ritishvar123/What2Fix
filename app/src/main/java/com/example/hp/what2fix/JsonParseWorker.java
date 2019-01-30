package com.example.hp.what2fix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParseWorker
{

    public static String [] workerName;
    public static String [] work;
    public static String [] wphone;
    public static String [] cost;
    public static String [] profitPercent;
    public static String [] profit;
    public static String [] date;
    String json;
    public JsonParseWorker( String response)
    {
        this.json=response;
    }

    public void jsonTodoWorker()
    {
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            workerName= new String[jsonArray.length()];
            work=new String[jsonArray.length()];
            wphone=new String[jsonArray.length()];
            cost=new String[jsonArray.length()];
            profitPercent=new String[jsonArray.length()];
            profit=new String[jsonArray.length()];
            date=new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                workerName[i]=jsonObject1.getString("Worker Name");
                work[i]=jsonObject1.getString("Work");
                wphone[i]=jsonObject1.getString("Worker Phone");
                cost[i]=jsonObject1.getString("Cost");
                profitPercent[i]=jsonObject1.getString("Profit Percent");
                profit[i]=jsonObject1.getString("Profit");
                date[i]=jsonObject1.getString("Date");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}