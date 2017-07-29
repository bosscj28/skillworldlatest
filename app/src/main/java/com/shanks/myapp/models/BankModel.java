package com.shanks.myapp.models;

/**
 * Created by katrina on 23/01/17.
 */

public class BankModel {
    private String id = "";
    private String bankname = "";
    private String description = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
