package com.realizertech.shivmudra.model;

import java.util.Date;

/**
 * Created by Win on 12/27/2017.
 */

public class OrderInput {
    public String UserToken;
    public String ItemCSV;
    public String OrderDate;
    public double TotatlCost;
    public String DeliveryDate;
    public String DeliverySlot;
    public double MarketCostSaved;
    public double MallCostSaved;
    public int ReferralCountApplied;
    public double ReferralCostSaved;
    public int ReferralDiscountPercentage;
    public double FirstOrderDiscount;
    public int FirstOrderDiscountPercentage;
    public double deliveryCost;
    public int OrderId;

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getItemCSV() {
        return ItemCSV;
    }

    public void setItemCSV(String itemCSV) {
        ItemCSV = itemCSV;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public double getTotatlCost() {
        return TotatlCost;
    }

    public void setTotatlCost(double totatlCost) {
        TotatlCost = totatlCost;
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

    public int getReferralDiscountPercentage() {
        return ReferralDiscountPercentage;
    }

    public void setReferralDiscountPercentage(int referralDiscountPercentage) {
        ReferralDiscountPercentage = referralDiscountPercentage;
    }

    public double getFirstOrderDiscount() {
        return FirstOrderDiscount;
    }

    public void setFirstOrderDiscount(double firstOrderDiscount) {
        FirstOrderDiscount = firstOrderDiscount;
    }

    public int getFirstOrderDiscountPercentage() {
        return FirstOrderDiscountPercentage;
    }

    public void setFirstOrderDiscountPercentage(int firstOrderDiscountPercentage) {
        FirstOrderDiscountPercentage = firstOrderDiscountPercentage;
    }
}
