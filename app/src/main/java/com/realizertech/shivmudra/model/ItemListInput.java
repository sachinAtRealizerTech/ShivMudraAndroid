package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class ItemListInput {

    public String UserToken;
    public int SocietyType;
    public int ItemType;

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public int getSocietyType() {
        return SocietyType;
    }

    public void setSocietyType(int societyType) {
        SocietyType = societyType;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }
}
