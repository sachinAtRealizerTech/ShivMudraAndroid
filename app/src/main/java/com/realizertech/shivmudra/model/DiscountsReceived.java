package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class DiscountsReceived {
    public int DRId;
    public String DiscountType;
    public double DiscountPercentage;
    public double DiscountAmount;
    public String CreateTS;


    public int getDRId() {
        return DRId;
    }

    public void setDRId(int DRId) {
        this.DRId = DRId;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getCreateTS() {
        return CreateTS;
    }

    public void setCreateTS(String createTS) {
        CreateTS = createTS;
    }
}
