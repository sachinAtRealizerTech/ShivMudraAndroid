package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class Location {
    public int LocationId;
    public String Name;
    public String address;
    public int LocationType;
    public String CreateTS;
    public boolean isActive;


    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLocationType() {
        return LocationType;
    }

    public void setLocationType(int locationType) {
        LocationType = locationType;
    }

    public String getCreateTS() {
        return CreateTS;
    }

    public void setCreateTS(String createTS) {
        CreateTS = createTS;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
