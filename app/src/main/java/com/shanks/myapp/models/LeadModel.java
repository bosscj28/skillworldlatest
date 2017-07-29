package com.shanks.myapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ankitpurohit on 14-02-2017.
 */

public class LeadModel implements Parcelable {
    private String buyerPhoneNo = "";
    private String dealerName = "";
    private String buyerLocation = "";
    private String buyerEmail = "";
    private String planToBuyOn = "";
    private String dealerId = "";
    private String leadCreateDate = "";
    private String buyerName = "";
    private String dealerPreference = "";
    private String newDealerName = "";
    private String otherDealerName = "";
    private String dealerLocation = "";
    private String offername = "";
    private String amountPaid = "";
    private String prefererredDealerid = "";
    private String prefererredDealerName = "";
    private String id = "";
    private String billNumber = "";
    private String leadid = "";
    private String leadClosedDate = "";

    public LeadModel(){

    }

    public String getBuyerPhoneNo() {
        return buyerPhoneNo;
    }

    public void setBuyerPhoneNo(String buyerPhoneNo) {
        this.buyerPhoneNo = buyerPhoneNo;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getBuyerLocation() {
        return buyerLocation;
    }

    public void setBuyerLocation(String buyerLocation) {
        this.buyerLocation = buyerLocation;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getPlanToBuyOn() {
        return planToBuyOn;
    }

    public void setPlanToBuyOn(String planToBuyOn) {
        this.planToBuyOn = planToBuyOn;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getLeadCreateDate() {
        return leadCreateDate;
    }

    public void setLeadCreateDate(String leadCreateDate) {
        this.leadCreateDate = leadCreateDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getDealerPreference() {
        return dealerPreference;
    }

    public void setDealerPreference(String dealerPreference) {
        this.dealerPreference = dealerPreference;
    }

    public String getNewDealerName() {
        return newDealerName;
    }

    public void setNewDealerName(String newDealerName) {
        this.newDealerName = newDealerName;
    }

    public String getOtherDealerName() {
        return otherDealerName;
    }

    public void setOtherDealerName(String otherDealerName) {
        this.otherDealerName = otherDealerName;
    }

    public String getDealerLocation() {
        return dealerLocation;
    }

    public void setDealerLocation(String dealerLocation) {
        this.dealerLocation = dealerLocation;
    }

    public String getOffername() {
        return offername;
    }

    public void setOffername(String offername) {
        this.offername = offername;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPrefererredDealerid() {
        return prefererredDealerid;
    }

    public void setPrefererredDealerid(String prefererredDealerid) {
        this.prefererredDealerid = prefererredDealerid;
    }

    public String getPrefererredDealerName() {
        return prefererredDealerName;
    }

    public void setPrefererredDealerName(String prefererredDealerName) {
        this.prefererredDealerName = prefererredDealerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getLeadid() {
        return leadid;
    }

    public void setLeadid(String leadid) {
        this.leadid = leadid;
    }

    public String getLeadClosedDate() {
        return leadClosedDate;
    }

    public void setLeadClosedDate(String leadClosedDate) {
        this.leadClosedDate = leadClosedDate;
    }

    protected LeadModel(Parcel in) {
        buyerPhoneNo = in.readString();
        dealerName = in.readString();
        buyerLocation = in.readString();
        buyerEmail = in.readString();
        planToBuyOn = in.readString();
        dealerId = in.readString();
        leadCreateDate = in.readString();
        buyerName = in.readString();
        dealerPreference = in.readString();
        newDealerName = in.readString();
        otherDealerName = in.readString();
        dealerLocation = in.readString();
        offername = in.readString();
        amountPaid = in.readString();
        prefererredDealerid = in.readString();
        prefererredDealerName = in.readString();
        id = in.readString();
        billNumber = in.readString();
        leadid = in.readString();
        leadClosedDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(buyerPhoneNo);
        dest.writeString(dealerName);
        dest.writeString(buyerLocation);
        dest.writeString(buyerEmail);
        dest.writeString(planToBuyOn);
        dest.writeString(dealerId);
        dest.writeString(leadCreateDate);
        dest.writeString(buyerName);
        dest.writeString(dealerPreference);
        dest.writeString(newDealerName);
        dest.writeString(otherDealerName);
        dest.writeString(dealerLocation);
        dest.writeString(offername);
        dest.writeString(amountPaid);
        dest.writeString(prefererredDealerid);
        dest.writeString(prefererredDealerName);
        dest.writeString(id);
        dest.writeString(billNumber);
        dest.writeString(leadid);
        dest.writeString(leadClosedDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LeadModel> CREATOR = new Parcelable.Creator<LeadModel>() {
        @Override
        public LeadModel createFromParcel(Parcel in) {
            return new LeadModel(in);
        }

        @Override
        public LeadModel[] newArray(int size) {
            return new LeadModel[size];
        }
    };
}