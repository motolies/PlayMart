package com.motolies.playmart;

/**
 * Created by motolies on 2017-02-06.
 */


//이름, 단가, 수량, 총액
//http://recipes4dev.tistory.com/43
public class ListViewItem {

    private String itemName;
    private int unitPrice;
    private int unit;

    public void setName(String itemName) {
        this.itemName = itemName;
    }

    public String getName() {
        return this.itemName;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitPrice() {
        return this.unitPrice;
    }

    public int getTotalPrice() {
        return this.unitPrice * this.unit;
    }


}