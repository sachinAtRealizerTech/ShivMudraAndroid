package com.realizertech.shivmudra.model;

import java.util.List;

/**
 * Created by Win on 1/3/2018.
 */

public class ItemsList {
    public double FreeDeliveryThreshold;
    public double DeliveryCharges;
    public List<Item>items;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
