package com.example.mylife.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataWallet{
    String id,id_wallet,date,activity,money,type;

    public DataWallet (){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_wallet() {
        return id_wallet;
    }

    public void setId_wallet(String id_wallet) {
        this.id_wallet = id_wallet;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
