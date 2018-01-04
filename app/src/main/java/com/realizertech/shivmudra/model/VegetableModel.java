package com.realizertech.shivmudra.model;

import java.io.Serializable;

/**
 * Created by Win on 12/20/2017.
 */

public class VegetableModel implements Serializable{
    public int id;
    public String name;
    public String Image;
    public double OurPrice;
    public double MallPrice;
    public double MarketPrice;
    public double totalPrice=0;
    public double totalMarketSave=0;
    public double totalMallSave=0;
    public int itemType;
    public double quantity;
    public String itemKey;
    public String measurementType;
    public String itemDesc;
    public String unitType;
    public double ItemDefaultQty;
    public double IncrementBy;
    public String displayQuantity;
    public String displayWt;

    public String getDisplayWt() {
        return displayWt;
    }

    public void setDisplayWt(String displayWt) {
        this.displayWt = displayWt;
    }

    public String getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(String displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getOurPrice() {
        return OurPrice;
    }

    public void setOurPrice(double ourPrice) {
        OurPrice = ourPrice;
    }

    public double getMallPrice() {
        return MallPrice;
    }

    public void setMallPrice(double mallPrice) {
        MallPrice = mallPrice;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        MarketPrice = marketPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalMarketSave() {
        return totalMarketSave;
    }

    public void setTotalMarketSave(double totalMarketSave) {
        this.totalMarketSave = totalMarketSave;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public double getTotalMallSave() {
        return totalMallSave;
    }

    public void setTotalMallSave(double totalMallSave) {
        this.totalMallSave = totalMallSave;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public double getItemDefaultQty() {
        return ItemDefaultQty;
    }

    public void setItemDefaultQty(double itemDefaultQty) {
        ItemDefaultQty = itemDefaultQty;
    }

    public double getIncrementBy() {
        return IncrementBy;
    }

    public void setIncrementBy(double incrementBy) {
        IncrementBy = incrementBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true; //if both pointing towards same object on heap

        VegetableModel veg = (VegetableModel) obj;
        return (this.getId() == veg.getId());
    }

}
