package com.realizertech.shivmudra.model;

import java.util.List;

/**
 * Created by Win on 12/27/2017.
 */

public class ConsumerStatistics {
    public int ReferralsCnt;
    public int ReferralsUsed;
    public double MarketCostSaved;
    public double MallCostSaved;
    public double CostSavedThroughReferrals;
    public int AppCurrentVersionNo;
    public List<String> ImageUrls;

    public int getReferralsCnt() {
        return ReferralsCnt;
    }

    public void setReferralsCnt(int referralsCnt) {
        ReferralsCnt = referralsCnt;
    }

    public int getReferralsUsed() {
        return ReferralsUsed;
    }

    public void setReferralsUsed(int referralsUsed) {
        ReferralsUsed = referralsUsed;
    }

    public double getMarketCostSaved() {
        return MarketCostSaved;
    }

    public void setMarketCostSaved(double marketCostSaved) {
        MarketCostSaved = marketCostSaved;
    }

    public double getMallCostSaved() {
        return MallCostSaved;
    }

    public void setMallCostSaved(double mallCostSaved) {
        MallCostSaved = mallCostSaved;
    }

    public double getCostSavedThroughReferrals() {
        return CostSavedThroughReferrals;
    }

    public void setCostSavedThroughReferrals(double costSavedThroughReferrals) {
        CostSavedThroughReferrals = costSavedThroughReferrals;
    }

    public int getAppCurrentVersionNo() {
        return AppCurrentVersionNo;
    }

    public void setAppCurrentVersionNo(int appCurrentVersionNo) {
        AppCurrentVersionNo = appCurrentVersionNo;
    }

    public List<String> getImageUrls() {
        return ImageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        ImageUrls = imageUrls;
    }
}
