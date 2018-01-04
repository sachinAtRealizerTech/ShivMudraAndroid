package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/28/2017.
 */

public class ReferralReminderInput {
    public String UserToken;
    public String ReferralId;

    public String getReferralId() {
        return ReferralId;
    }

    public void setReferralId(String referralId) {
        ReferralId = referralId;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }
}
