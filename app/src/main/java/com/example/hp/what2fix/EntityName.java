package com.example.hp.what2fix;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EntityName implements Comparable<EntityName>{
    Name name;
    Id id;
    Date date;
    Address address;
    Phno phno;
    Status status;
    EntityName(Name name, Id id, Date date, Address address, Phno phno, Status status) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.address = address;
        this.phno = phno;
        this.status = status;
    }
    @Override
    public int compareTo(EntityName entity) {
        return this.name.getName().compareToIgnoreCase(entity.name.getName());
    }
}

class EntityId implements Comparable<EntityId>{
    Name name;
    Id id;
    Date date;
    Address address;
    Phno phno;
    Status status;
    EntityId(Name name, Id id, Date date, Address address, Phno phno, Status status) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.address = address;
        this.phno = phno;
        this.status = status;
    }
    @Override
    public int compareTo(EntityId entity) {
        return ( Integer.parseInt(this.id.getId()) - Integer.parseInt(entity.id.getId()) );
    }
}

class EntityDate implements Comparable<EntityDate>{
    Name name;
    Id id;
    Date date;
    Address address;
    Phno phno;
    Status status;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    EntityDate(Name name, Id id, Date date, Address address, Phno phno, Status status) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.address = address;
        this.phno = phno;
        this.status = status;
    }
    @Override
    public int compareTo(EntityDate entity) {
        int index = -1;
        try {
            index = sdf.parse(this.date.getDate()).compareTo(sdf.parse(entity.date.getDate()));
        } catch (ParseException e) {e.printStackTrace();}
        return index;
    }
}


class Name{
    String names;
    Name(String name){
        this.names = name;
    }
    public String getName(){
        return this.names;
    }
}

class Id{
    String ids;
    Id(String id){
        this.ids = id;
    }
    public String getId(){
        return this.ids;
    }
    public void setId(String id){

    }
}

class Date{
    String dates;
    Date(String date){
        this.dates = date;
    }
    public String getDate(){
        return this.dates;
    }
}

class Address{
    String address;
    Address(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
}

class Status{
    String status;
    Status(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
}

class Phno{
    String phno;
    Phno(String phno){
        this.phno = phno;
    }
    public String getPhno(){
        return this.phno;
    }
}