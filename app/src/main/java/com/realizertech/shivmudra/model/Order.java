package com.realizertech.shivmudra.model;

import java.util.List;

/**
 * Created by Win on 12/27/2017.
 */

public class Order {
    public int OrderId;
    public String OrderDate;
    public double TotalCost;
    public String DeliveryDate;
    public String DeliverySlot;
    public String OrderStatus;
    public String CreateTS;
    public int DeliverySlabId;
    public double MarketCostSaved;
    public double MallCostSaved;
    public int ReferralCountApplied;
    public double ReferralCostSaved;
    public double FirstOrderDiscount;
    public double FirstOrderDiscountPercentage;
    public double ReferralDiscountPercentage;
    public List<OrderItems>Items;
    public double FreeDeliveryThreshold;
    public double DeliveryCharges;

    public double getFreeDeliveryThreshold() {
        return FreeDeliveryThreshold;
    }

    public void setFreeDeliveryThreshold(double freeDeliveryThreshold) {
        FreeDeliveryThreshold = freeDeliveryThreshold;
    }

    public double getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(double totalCost) {
        TotalCost = totalCost;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getDeliverySlot() {
        return DeliverySlot;
    }

    public void setDeliverySlot(String deliverySlot) {
        DeliverySlot = deliverySlot;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getCreateTS() {
        return CreateTS;
    }

    public void setCreateTS(String createTS) {
        CreateTS = createTS;
    }

    public int getDeliverySlabId() {
        return DeliverySlabId;
    }

    public void setDeliverySlabId(int deliverySlabId) {
        DeliverySlabId = deliverySlabId;
    }

    public double getMarketCostSaved() {
        return MarketCostSaved;
    }

    public void setMarketCostSaved(double marketCostSaved) {
        MarketCostSaved = marketCostSaved;
    }

    public double getMallCostSaved() {
        return MallCostSaved;
    }

    public void setMallCostSaved(double mallCostSaved) {
        MallCostSaved = mallCostSaved;
    }

    public int getReferralCountApplied() {
        return ReferralCountApplied;
    }

    public void setReferralCountApplied(int referralCountApplied) {
        ReferralCountApplied = referralCountApplied;
    }

    public double getReferralCostSaved() {
        return ReferralCostSaved;
    }

    public void setReferralCostSaved(double referralCostSaved) {
        ReferralCostSaved = referralCostSaved;
    }

    public double getFirstOrderDiscount() {
        return FirstOrderDiscount;
    }

    public void setFirstOrderDiscount(double firstOrderDiscount) {
        FirstOrderDiscount = firstOrderDiscount;
    }

    public double getFirstOrderDiscountPercentage() {
        return FirstOrderDiscountPercentage;
    }

    public void setFirstOrderDiscountPercentage(double firstOrderDiscountPercentage) {
        FirstOrderDiscountPercentage = firstOrderDiscountPercentage;
    }

    public double getReferralDiscountPercentage() {
        return ReferralDiscountPercentage;
    }

    public void setReferralDiscountPercentage(double referralDiscountPercentage) {
        ReferralDiscountPercentage = referralDiscountPercentage;
    }

    public List<OrderItems> getItems() {
        return Items;
    }

    public void setItems(List<OrderItems> items) {
        Items = items;
    }
}
