package com.realizertech.shivmudra.model;

/**
 * Created by Win on 12/27/2017.
 */

public class ValidationRequest {

    public String ContactNo;
    public String OTP;

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
