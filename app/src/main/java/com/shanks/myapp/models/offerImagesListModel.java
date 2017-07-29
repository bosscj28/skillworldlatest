package com.shanks.myapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by katrina on 24/01/17.
 */

public class offerImagesListModel implements Parcelable {
    private String imgname = "";
    private String imgid = "";
    private String imgtype = "";

    public String getBannerimage() {
        return bannerimage;
    }

    public void setBannerimage(String bannerimage) {
        this.bannerimage = bannerimage;
    }

    private String bannerimage ="";

    public offerImagesListModel(){

    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getImgtype() {
        return imgtype;
    }

    public void setImgtype(String imgtype) {
        this.imgtype = imgtype;
    }

    protected offerImagesListModel(Parcel in) {
        imgname = in.readString();
        imgid = in.readString();
        imgtype = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgname);
        dest.writeString(imgid);
        dest.writeString(imgtype);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<offerImagesListModel> CREATOR = new Parcelable.Creator<offerImagesListModel>() {
        @Override
        public offerImagesListModel createFromParcel(Parcel in) {
            return new offerImagesListModel(in);
        }

        @Override
        public offerImagesListModel[] newArray(int size) {
            return new offerImagesListModel[size];
        }
    };
}
