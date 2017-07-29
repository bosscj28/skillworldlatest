package com.shanks.myapp.models;

/**
 * Created by katrina on 23/01/17.
 */

public class BankAccountTypeModel {
    private String accounttype = "";
    private String description = "";
    private String id = "";

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
