package com.shanks.myapp.models;

/**
 * Created by ankitpurohit on 22-01-2017.
 */

public class QualificationModel {
    private String qualificationID = "";
    private String qualificationName = "";
    private String qualificationDescription = "";

    public String getQualificationID() {
        return qualificationID;
    }

    public void setQualificationID(String qualificationID) {
        this.qualificationID = qualificationID;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getQualificationDescription() {
        return qualificationDescription;
    }

    public void setQualificationDescription(String qualificationDescription) {
        this.qualificationDescription = qualificationDescription;
    }
}
