package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/29/2017.
 */

public class Discount {
    public String DiscountType;
    public String AvailableDiscountCnt;
    public double DiscountPercentage;
    public double DiscountValue;
    public boolean IsApplicable;

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public String getAvailableDiscountCnt() {
        return AvailableDiscountCnt;
    }

    public void setAvailableDiscountCnt(String availableDiscountCnt) {
        AvailableDiscountCnt = availableDiscountCnt;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public boolean isApplicable() {
        return IsApplicable;
    }

    public void setApplicable(boolean applicable) {
        IsApplicable = applicable;
    }

    public double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(double discountValue) {
        DiscountValue = discountValue;
    }
}
