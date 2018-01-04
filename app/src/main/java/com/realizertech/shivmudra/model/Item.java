package com.realizertech.shivmudra.model;

import java.util.List;

/**
 * Created by Win on 12/22/2017.
 */

public class Item {
    public int ItemId;
    public int ItemType;
    public String ItemName;
    public String ImageUrl;
    public double ourPrice;
    public double MallPrice;
    public double MarketPrice;
    public String MeasurementType;
    public String ItemKey;
    public String ItemDesc;
    public double ItemDefaultQty;
    public double IncrementBy;

    public String getMeasurementType() {
        return MeasurementType;
    }

    public void setMeasurementType(String measurementType) {
        MeasurementType = measurementType;
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

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public double getOurPrice() {
        return ourPrice;
    }

    public void setOurPrice(double ourPrice) {
        this.ourPrice = ourPrice;
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

    public String getMeasurmentType() {
        return MeasurementType;
    }

    public void setMeasurmentType(String measurmentType) {
        MeasurementType = measurmentType;
    }

    public String getItemKey() {
        return ItemKey;
    }

    public void setItemKey(String itemKey) {
        ItemKey = itemKey;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }
}
