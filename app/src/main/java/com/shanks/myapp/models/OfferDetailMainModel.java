package com.shanks.myapp.models;

import java.util.ArrayList;

/**
 * Created by katrina on 24/01/17.
 */

public class OfferDetailMainModel {
    private ArrayList<userInfoModel> userInfoModel = new ArrayList<>();
    private ArrayList<offerListModel> offerList = new ArrayList<>();
    private ArrayList<offerdetailModel>  offerdetail = new ArrayList<>();

    public ArrayList<com.shanks.myapp.models.userInfoModel> getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(ArrayList<com.shanks.myapp.models.userInfoModel> userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public ArrayList<offerListModel> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<offerListModel> offerList) {
        this.offerList = offerList;
    }

    public ArrayList<offerdetailModel> getOfferdetail() {
        return offerdetail;
    }

    public void setOfferdetail(ArrayList<offerdetailModel> offerdetail) {
        this.offerdetail = offerdetail;
    }
}
