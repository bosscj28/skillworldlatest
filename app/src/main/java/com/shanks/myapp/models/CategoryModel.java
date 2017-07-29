package com.shanks.myapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by katrina on 17/01/17.
 */

public class CategoryModel implements Parcelable {

//            "name": "2 Wheeler",
//            "selectedCatImage": "automobile.jpg",
//            "id": "1",
//            "nonSelectedCatImage": "automibile-light.jpg"

    private String name = "";
    private String selectedCatImage = "";
    private String id = "";
    private String nonSelectedCatImage = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedCatImage() {
        return selectedCatImage;
    }

    public void setSelectedCatImage(String selectedCatImage) {
        this.selectedCatImage = selectedCatImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNonSelectedCatImage() {
        return nonSelectedCatImage;
    }

    public void setNonSelectedCatImage(String nonSelectedCatImage) {
        this.nonSelectedCatImage = nonSelectedCatImage;
    }

    public CategoryModel(){

    }

    protected CategoryModel(Parcel in) {
        name = in.readString();
        selectedCatImage = in.readString();
        id = in.readString();
        nonSelectedCatImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(selectedCatImage);
        dest.writeString(id);
        dest.writeString(nonSelectedCatImage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CategoryModel> CREATOR = new Parcelable.Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };
}