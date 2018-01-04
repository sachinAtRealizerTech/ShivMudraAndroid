package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class OrderItems {


    public int OrderItemId;
    public int OrderId;
    public int ItemId;
    public String ItemName;
    public double Quantity;
    public double PerUnitCost;
    public double CostPerItem;
    public String CreateTS;
    public String ItemKey;
    public int ItemType;
    public String MeasurementType;
    public String ItemDesc;
    public double ourPrice;
    public double MallPrice;
    public double MarketPrice;
    public double ItemDefaultQty;
    public double IncrementBy;

    public String getItemKey() {
        return ItemKey;
    }

    public void setItemKey(String itemKey) {
        ItemKey = itemKey;
    }

    public int getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        OrderItemId = orderItemId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getPerUnitCost() {
        return PerUnitCost;
    }

    public void setPerUnitCost(double perUnitCost) {
        PerUnitCost = perUnitCost;
    }

    public double getCostPerItem() {
        return CostPerItem;
    }

    public void setCostPerItem(double costPerItem) {
        CostPerItem = costPerItem;
    }

    public String getCreateTS() {
        return CreateTS;
    }

    public void setCreateTS(String createTS) {
        CreateTS = createTS;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }

    public String getMeasurementType() {
        return MeasurementType;
    }

    public void setMeasurementType(String measurementType) {
        MeasurementType = measurementType;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
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
}
