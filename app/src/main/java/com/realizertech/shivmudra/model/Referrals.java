package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class Referrals {
    public String ReferralId;
    public String Name;
    public String SocietyName;
    public String ReferralRegisteredDate;
    public String ReferralRegisteredOrderDate;
    public String ReferralContactNo;

    public String getReferralId() {
        return ReferralId;
    }

    public void setReferralId(String referralId) {
        ReferralId = referralId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSocietyName() {
        return SocietyName;
    }

    public void setSocietyName(String societyName) {
        SocietyName = societyName;
    }

    public String getReferralRegisteredDate() {
        return ReferralRegisteredDate;
    }

    public void setReferralRegisteredDate(String referralRegisteredDate) {
        ReferralRegisteredDate = referralRegisteredDate;
    }

    public String getReferralRegisteredOrderDate() {
        return ReferralRegisteredOrderDate;
    }

    public void setReferralRegisteredOrderDate(String referralRegisteredOrderDate) {
        ReferralRegisteredOrderDate = referralRegisteredOrderDate;
    }

    public String getReferralContactNo() {
        return ReferralContactNo;
    }

    public void setReferralContactNo(String referralContactNo) {
        ReferralContactNo = referralContactNo;
    }
}
