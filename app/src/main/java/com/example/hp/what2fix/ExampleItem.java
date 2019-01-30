package com.example.hp.what2fix;

public class ExampleItem {
    private String customerName;
    private String orderId;
    private String date;

    public ExampleItem(String customerName, String orderId, String date){
        this.customerName = customerName;
        this.orderId = orderId;
        this.date = date;
    }

    public String getCustomerName() { return customerName; }
    public String getOrderId() { return orderId; }
    public String getDate() { return date; }
}