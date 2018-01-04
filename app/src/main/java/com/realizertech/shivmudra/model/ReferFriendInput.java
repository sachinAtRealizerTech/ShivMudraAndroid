package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class ReferFriendInput {
    public String UserToken;
    public String ReferralName;
    public String SocietyName;
    public int SocietyID;
    public String ReferralContactNo;
    public String ReferralToken;

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getReferralName() {
        return ReferralName;
    }

    public void setReferralName(String referralName) {
        ReferralName = referralName;
    }

    public String getSocietyName() {
        return SocietyName;
    }

    public void setSocietyName(String societyName) {
        SocietyName = societyName;
    }

    public int getSocietyID() {
        return SocietyID;
    }

    public void setSocietyID(int societyID) {
        SocietyID = societyID;
    }

    public String getReferralContactNo() {
        return ReferralContactNo;
    }

    public void setReferralContactNo(String referralContactNo) {
        ReferralContactNo = referralContactNo;
    }

    public String getReferralToken() {
        return ReferralToken;
    }

    public void setReferralToken(String referralToken) {
        ReferralToken = referralToken;
    }
}
