package com.shanks.myapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by katrina on 18/01/17.
 */

public class OfferModel implements Parcelable {


    private String referralAmount = "";
    private String offerName = "";
    private String locationName = "";
    private String referralPercentage = "";
    private String detailedPageUrl = "";
    private String offerStatus = "";
    private String corporateId = "";
    private String offerShortDescription = "";
    private String fromdate = "";
    private String createdDate = "";
    private String todate = "";
    private String createdby = "";
    private String offerDescription = "";
    private String offerImage = "";
    private String offerId = "";

    public OfferModel(){

    }

    public String getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(String referralAmount) {
        this.referralAmount = referralAmount;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getReferralPercentage() {
        return referralPercentage;
    }

    public void setReferralPercentage(String referralPercentage) {
        this.referralPercentage = referralPercentage;
    }

    public String getDetailedPageUrl() {
        return detailedPageUrl;
    }

    public void setDetailedPageUrl(String detailedPageUrl) {
        this.detailedPageUrl = detailedPageUrl;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    public String getOfferShortDescription() {
        return offerShortDescription;
    }

    public void setOfferShortDescription(String offerShortDescription) {
        this.offerShortDescription = offerShortDescription;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    protected OfferModel(Parcel in) {
        referralAmount = in.readString();
        offerName = in.readString();
        locationName = in.readString();
        referralPercentage = in.readString();
        detailedPageUrl = in.readString();
        offerStatus = in.readString();
        corporateId = in.readString();
        offerShortDescription = in.readString();
        fromdate = in.readString();
        createdDate = in.readString();
        todate = in.readString();
        createdby = in.readString();
        offerDescription = in.readString();
        offerImage = in.readString();
        offerId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(referralAmount);
        dest.writeString(offerName);
        dest.writeString(locationName);
        dest.writeString(referralPercentage);
        dest.writeString(detailedPageUrl);
        dest.writeString(offerStatus);
        dest.writeString(corporateId);
        dest.writeString(offerShortDescription);
        dest.writeString(fromdate);
        dest.writeString(createdDate);
        dest.writeString(todate);
        dest.writeString(createdby);
        dest.writeString(offerDescription);
        dest.writeString(offerImage);
        dest.writeString(offerId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OfferModel> CREATOR = new Parcelable.Creator<OfferModel>() {
        @Override
        public OfferModel createFromParcel(Parcel in) {
            return new OfferModel(in);
        }

        @Override
        public OfferModel[] newArray(int size) {
            return new OfferModel[size];
        }
    };
}