package com.realizertech.shivmudra.model;

/**
 * Created by Win on 1/3/2018.
 */

public class MasterData {
    public String MasterTextId;
    public String MTKey;
    public String MTText;
    public String MTCategory;

    public String getMasterTextId() {
        return MasterTextId;
    }

    public void setMasterTextId(String masterTextId) {
        MasterTextId = masterTextId;
    }

    public String getMTKey() {
        return MTKey;
    }

    public void setMTKey(String MTKey) {
        this.MTKey = MTKey;
    }

    public String getMTText() {
        return MTText;
    }

    public void setMTText(String MTText) {
        this.MTText = MTText;
    }

    public String getMTCategory() {
        return MTCategory;
    }

    public void setMTCategory(String MTCategory) {
        this.MTCategory = MTCategory;
    }
}
