package com.motolies.playmart;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by motolies on 2017-02-09.
 */

public class Item implements Serializable {
    private String barCode;
    private String name;
    private int price;

    public String getBarCode() {
        return this.barCode;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return  String.format("barcode : %s, name : %s, price : %d", getBarCode(), getName(), getPrice());
    }

}
