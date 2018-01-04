package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class LocationDeliverySlabs {
    public int LocationDeliverySlabId;
    public int LocationId;
    public String WeekDay;
    public String SlabTiming;
    public String SlabDate;
    public String ShowDate;

    public int getLocationDeliverySlabId() {
        return LocationDeliverySlabId;
    }

    public void setLocationDeliverySlabId(int locationDeliverySlabId) {
        LocationDeliverySlabId = locationDeliverySlabId;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getWeekDay() {
        return WeekDay;
    }

    public void setWeekDay(String weekDay) {
        WeekDay = weekDay;
    }

    public String getSlabTiming() {
        return SlabTiming;
    }

    public void setSlabTiming(String slabTiming) {
        SlabTiming = slabTiming;
    }

    public String getSlabDate() {
        return SlabDate;
    }

    public void setSlabDate(String slabDate) {
        SlabDate = slabDate;
    }

    public String getShowDate() {
        return ShowDate;
    }

    public void setShowDate(String showDate) {
        ShowDate = showDate;
    }
}
