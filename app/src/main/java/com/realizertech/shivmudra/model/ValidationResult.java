package com.realizertech.shivmudra.model;

/**
 * Created by Win on 1/2/2018.
 */

public class ValidationResult {
    public String Name;
    public String Result;
    public int SocietyId;
    public String FlatNo;
    public int LocationType;
    public String UserToken;
    public String SocietyName;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public int getSocietyId() {
        return SocietyId;
    }

    public void setSocietyId(int societyId) {
        SocietyId = societyId;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

    public int getLocationType() {
        return LocationType;
    }

    public void setLocationType(int locationType) {
        LocationType = locationType;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getSocietyName() {
        return SocietyName;
    }

    public void setSocietyName(String societyName) {
        SocietyName = societyName;
    }
}
