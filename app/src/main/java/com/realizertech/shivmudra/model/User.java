package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/22/2017.
 */

public class User {
    public int Id;
    public String Name;
    public int SocietyId;
    public String SocietyName;
    public String FlatNo;
    public String ContactNo;
    public boolean IsRegistered;
    public String UserToken;
    public String ReferralToken;
    public int Otp;

    public int getId() {
        return Id;
    }

    public void setId(int userId) {
        Id = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSocietyId() {
        return SocietyId;
    }

    public void setSocietyId(int societyId) {
        SocietyId = societyId;
    }

    public String getSocietyName() {
        return SocietyName;
    }

    public void setSocietyName(String societyName) {
        SocietyName = societyName;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String phone) {
        ContactNo = phone;
    }

    public boolean isRegistered() {
        return IsRegistered;
    }

    public void setRegistered(boolean registered) {
        IsRegistered = registered;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public int getOtp() {
        return Otp;
    }

    public void setOtp(int otp) {
        Otp = otp;
    }

    public String getReferralToken() {
        return ReferralToken;
    }

    public void setReferralToken(String referralToken) {
        ReferralToken = referralToken;
    }
}
