package com.shanks.myapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by katrina on 24/01/17.
 */

public class offerdetailModel implements Parcelable {

    public offerdetailModel(){

    }

    private String referralAmount = "";
    private String salesPitch = "";
    private String testsRequired = "";
    private String locationName = "";
    private String shortdesc = "";
    private String productVideos = "";

    private static ArrayList<offerImagesListModel> offerImagesList = new ArrayList<>();

    public static ArrayList<offerImagesListModel> getOfferImagesList() {
        return offerImagesList;
    }

    public void setOfferImagesList(ArrayList<offerImagesListModel> offerImagesList) {
        this.offerImagesList = offerImagesList;
    }

    private String offerStatus = "";
    private String fromdate = "";
    private String longdesc = "";
    private String referralBonusType = "";
    private String offerimage = "";
    private String offername = "";
    private String todate = "";
    private String terms = "";
    private String offerid = "";
    private String categoryname = "";
    private String corporatename = "";


    public String getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(String referralAmount) {
        this.referralAmount = referralAmount;
    }

    public String getSalesPitch() {
        return salesPitch;
    }

    public void setSalesPitch(String salesPitch) {
        this.salesPitch = salesPitch;
    }

    public String getTestsRequired() {
        return testsRequired;
    }

    public void setTestsRequired(String testsRequired) {
        this.testsRequired = testsRequired;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getProductVideos() {
        return productVideos;
    }

    public void setProductVideos(String productVideos) {
        this.productVideos = productVideos;
    }



    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getReferralBonusType() {
        return referralBonusType;
    }

    public void setReferralBonusType(String referralBonusType) {
        this.referralBonusType = referralBonusType;
    }

    public String getOfferimage() {
        return offerimage;
    }

    public void setOfferimage(String offerimage) {
        this.offerimage = offerimage;
    }

    public String getOffername() {
        return offername;
    }

    public void setOffername(String offername) {
        this.offername = offername;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCorporatename() {
        return corporatename;
    }

    public void setCorporatename(String corporatename) {
        this.corporatename = corporatename;
    }

    protected offerdetailModel(Parcel in) {
        referralAmount = in.readString();
        salesPitch = in.readString();
        testsRequired = in.readString();
        locationName = in.readString();
        shortdesc = in.readString();
        productVideos = in.readString();
        if (in.readByte() == 0x01) {
            offerImagesList = new ArrayList<offerImagesListModel>();
            in.readList(offerImagesList, offerImagesListModel.class.getClassLoader());
        } else {
            offerImagesList = null;
        }
        offerStatus = in.readString();
        fromdate = in.readString();
        longdesc = in.readString();
        referralBonusType = in.readString();
        offerimage = in.readString();
        offername = in.readString();
        todate = in.readString();
        terms = in.readString();
        offerid = in.readString();
        categoryname = in.readString();
        corporatename = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(referralAmount);
        dest.writeString(salesPitch);
        dest.writeString(testsRequired);
        dest.writeString(locationName);
        dest.writeString(shortdesc);
        dest.writeString(productVideos);
        if (offerImagesList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(offerImagesList);
        }
        dest.writeString(offerStatus);
        dest.writeString(fromdate);
        dest.writeString(longdesc);
        dest.writeString(referralBonusType);
        dest.writeString(offerimage);
        dest.writeString(offername);
        dest.writeString(todate);
        dest.writeString(terms);
        dest.writeString(offerid);
        dest.writeString(categoryname);
        dest.writeString(corporatename);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<offerdetailModel> CREATOR = new Parcelable.Creator<offerdetailModel>() {
        @Override
        public offerdetailModel createFromParcel(Parcel in) {
            return new offerdetailModel(in);
        }

        @Override
        public offerdetailModel[] newArray(int size) {
            return new offerdetailModel[size];
        }
    };
}