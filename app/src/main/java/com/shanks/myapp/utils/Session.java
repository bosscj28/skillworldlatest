package com.shanks.myapp.utils;

import android.content.Context;

/**
 * Created by katrina on 18/01/17.
 */

public class Session  {
//{"result":"Success.","userid":"187","userinfo"
// :{"firstname":"Hxhhx", ...........
// "mobilenumber":"8383838386", ...........
// "userstatus":"Active", ..................
// "middlename":"", .............
// "userid":"187", .............
// "pannumber":"FUFJFU4556", ............
// "lastname":"Vzhxhx", ............
// "dob":"0096-01-23 00:00:00.0", ............
// "userimage":"1485170233590282.jpg", ...........
// "authority":"BRAND_ASSOCIATE", .........
// "location":"", ...................
// "aadharnumber":"868386838833", .............
// "fullname":"Hxhhx Vzhxhx", ..............
// "usercode":"AAA050", ............
// "email":"ufufuf@ccufu.com", .............
// "username":"Xgxhxu"},"responseCode":"2001"}..............

    private String firstname = "";
    private String mobilenumber = "";
    private String userstatus = "";
    private String middlename = "";
    private String pannumber = "";
    private String lastname = "";
    private String dob = "";
    private String userimage = "";
    private String authority = "";
    private String location = "";
    private String aadharnumber = "";
    private String fullname = "";
    private String usercode = "";
    private String email = "";
    private String username = "";
    private String cityid = "";
    private String cityname = "";
    private String addressid = "";
    private String category = "";

    public String getHealthInsuranceSalery() {
        return Utils.getShared(context,Utils.healthInsuranceSalery,"");
    }

    public void setHealthInsuranceSalery(String healthInsuranceSalery) {
        Utils.setShared(context,Utils.healthInsuranceSalery,healthInsuranceSalery);
    }

    public String getHomeTutionSalery() {
        return Utils.getShared(context,Utils.homeTutionSalery,"");
    }

    public void setHomeTutionSalery(String homeTutionSalery) {
        Utils.setShared(context,Utils.homeTutionSalery,homeTutionSalery);
    }

    public String getTranscationsSalery() {
        return Utils.getShared(context,Utils.transcationsSalery,"");
    }

    public void setTranscationsSalery(String transcationsSalery) {
        Utils.setShared(context,Utils.transcationsSalery,transcationsSalery);
    }

    public String getDescriptionSalery() {
        return Utils.getShared(context,Utils.descriptionSalery,"");
    }

    public void setDescriptionSalery(String descriptionSalery) {
        Utils.setShared(context,Utils.descriptionSalery,descriptionSalery);
    }

    public String getBankDetailsIdSalery() {
        return Utils.getShared(context,Utils.bankDetailsIdSalery,"");
    }

    public void setBankDetailsIdSalery(String bankDetailsIdSalery) {
        Utils.setShared(context,Utils.bankDetailsIdSalery,bankDetailsIdSalery);
    }

    public String getTwoWheelerOwnedSalery() {
        return Utils.getShared(context,Utils.twoWheelerOwnedSalery,"");
    }

    public void setTwoWheelerOwnedSalery(String twoWheelerOwnedSalery) {
        Utils.setShared(context,Utils.twoWheelerOwnedSalery,twoWheelerOwnedSalery);
    }

    public String getWalletsSalery() {
        return Utils.getShared(context,Utils.walletsSalery,"");
    }

    public void setWalletsSalery(String wallets) {
        Utils.setShared(context,Utils.walletsSalery,walletsSalery);
    }

    public String getHomePinCodeSalery() {
        return Utils.getShared(context,Utils.homePinCodeSalery,"");
    }

    public void setHomePinCodeSalery(String homePinCodeSalery) {
        Utils.setShared(context,Utils.homePinCodeSalery,homePinCodeSalery);
    }

    public String getHousingLoanSalery() {
        return Utils.getShared(context,Utils.housingLoanSalery,"");
    }

    public void setHousingLoanSalery(String housingLoanSalery) {
        Utils.setShared(context,Utils.housingLoanSalery,housingLoanSalery);
    }

    public String getProductDetailsSalery() {
        return Utils.getShared(context,Utils.productDetailsSalery,"");
    }

    public void setProductDetailsSalery(String productDetailsSalery) {
        Utils.setShared(context,Utils.productDetailsSalery,productDetailsSalery);
    }

    public String getEcommerceSitesSalery() {
        return Utils.getShared(context,Utils.ecommerceSitesSalery,"");
    }

    public void setEcommerceSitesSalery(String ecommerceSitesSalery) {
        Utils.setShared(context,Utils.ecommerceSitesSalery,ecommerceSitesSalery);
    }

    public String getNoOfSchoolGoingChildrenSalery() {
        return Utils.getShared(context,Utils.noOfSchoolGoingChildrenSalery,"");
    }

    public void setNoOfSchoolGoingChildrenSalery(String noOfSchoolGoingChildrenSalery) {
        Utils.setShared(context,Utils.noOfSchoolGoingChildrenSalery,noOfSchoolGoingChildrenSalery);
    }

    public String getCreditCardsSalery() {
        return Utils.getShared(context,Utils.creditCardsSalery,"");
    }

    public void setCreditCardsSalery(String creditCardsSalery) {
        Utils.setShared(context,Utils.creditCardsSalery,creditCardsSalery);
    }

    public String getOnlineCourseSalery() {
        return Utils.getShared(context,Utils.onlineCourseSalery,"");
    }

    public void setOnlineCourseSalery(String onlineCourseSalery) {
        Utils.setShared(context,Utils.onlineCourseSalery,onlineCourseSalery);
    }

    public String getCarOwnedSalery() {
        return Utils.getShared(context,Utils.carOwnedSalery,"");
    }

    public void setCarOwnedSalery(String carOwnedSalery) {
        Utils.setShared(context,Utils.carOwnedSalery,carOwnedSalery);
    }

    public String getCarLoanSalery() {
        return Utils.getShared(context,Utils.carLoanSalery,"");
    }

    public void setCarLoanSalery(String carLoan) {
        Utils.setShared(context,Utils.carLoanSalery,carLoan);
    }

    public String getMutualFundSalery() {
        return Utils.getShared(context,Utils.mutualFundSalery,"");
    }

    public void setMutualFundSalery(String mutualFundSalery) {
        Utils.setShared(context,Utils.mutualFundSalery,mutualFundSalery);
    }

    public static Session getInstance() {
        return instance;
    }

    public static void setInstance(Session instance) {
        Session.instance = instance;
    }

    private String healthInsuranceSalery = "";
    private String homeTutionSalery = "";
    private String transcationsSalery = "";
    private String descriptionSalery = "";
    private String bankDetailsIdSalery = "";
    private String twoWheelerOwnedSalery = "";
    private String walletsSalery = "";
    private String homePinCodeSalery = "";
    private String housingLoanSalery = "";
    private String productDetailsSalery = "";
    private String ecommerceSitesSalery = "";
    private String noOfSchoolGoingChildrenSalery = "";
    private String creditCardsSalery = "";
    private String onlineCourseSalery = "";
    private String carOwnedSalery = "";
    private String carLoanSalery = "";
    private String mutualFundSalery ="";

    public String getBank_nameSalery() {
        return Utils.getShared(context,Utils.bank_nameSalery,"");
    }

    public void setBank_nameSalery(String bank_nameSalery) {
        Utils.setShared(context,Utils.bank_nameSalery,bank_nameSalery);
    }

    private String bank_nameSalery = "";

    public String getPassowrd() {
        return Utils.getShared(context,Utils.passowrd,"");
    }

    public void setPassowrd(String passowrd) {
        Utils.setShared(context,Utils.passowrd,passowrd);
    }

    public String getCreditCards() {
        return Utils.getShared(context,Utils.creditCards,"");
    }

    public void setCreditCards(String creditCards) {
        Utils.setShared(context,Utils.creditCards,creditCards);
    }

    public String getPlan_For_Bank_Loan() {
        return Utils.getShared(context,Utils.plan_For_Bank_Loan,"");
    }

    public void setPlan_For_Bank_Loan(String plan_For_Bank_Loan) {
        Utils.setShared(context,Utils.plan_For_Bank_Loan,plan_For_Bank_Loan);
    }

    public String getBankDetailsId() {
        return Utils.getShared(context,Utils.bankDetailsId,"");
    }

    public void setBankDetailsId(String bankDetailsId) {
        Utils.setShared(context,Utils.bankDetailsId,bankDetailsId);
    }

    public String getCustomerid() {
        return Utils.getShared(context,Utils.customerid,"");
    }

    public void setCustomerid(String customerid) {
        Utils.setShared(context,Utils.customerid,customerid);
    }

    public String getBank_name() {
        return Utils.getShared(context,Utils.bank_name,"");
    }

    public void setBank_name(String bank_name) {
        Utils.setShared(context,Utils.bank_name,bank_name);
    }

    public String getHomePinCode() {
        return Utils.getShared(context,Utils.homePinCode,"");
    }

    public void setHomePinCode(String homePinCode) {
        Utils.setShared(context,Utils.homePinCode,homePinCode);
    }

    public String getShopFront_Image() {
        return Utils.getShared(context,Utils.shopFront_Image,"");
    }

    public void setShopFront_Image(String shopFront_Image) {
        Utils.setShared(context,Utils.shopFront_Image,shopFront_Image);
    }

    public String getProductDetails() {
        return Utils.getShared(context,Utils.productDetails,"");
    }

    public void setProductDetails(String productDetails) {
        Utils.setShared(context,Utils.productDetails,productDetails);
    }

    public String getOfficeAirconditioned() {
        return Utils.getShared(context,Utils.officeAirconditioned,"");
    }

    public void setOfficeAirconditioned(String officeAirconditioned) {
        Utils.setShared(context,Utils.officeAirconditioned,officeAirconditioned);
    }

    public String getNoOfStaffEmployed() {
        return Utils.getShared(context,Utils.noOfStaffEmployed,"");
    }

    public void setNoOfStaffEmployed(String noOfStaffEmployed) {
        Utils.setShared(context,Utils.noOfStaffEmployed,noOfStaffEmployed);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    String creditCards = "";
    String plan_For_Bank_Loan = "";
    String bankDetailsId = "";
    String customerid = "";
    String bank_name = "";
    String homePinCode = "";
    String shopFront_Image = "";
    String productDetails = "";
    String officeAirconditioned = "";
    String NoOfStaffEmployed = "";

    public String getBank_detail_id() {
        return Utils.getShared(context,Utils.bank_detail_id,"");
    }

    public void setBank_detail_id(String bank_detail_id) {
        Utils.setShared(context,Utils.bank_detail_id,bank_detail_id);
    }

    String bank_detail_id = "";

    public String getCATEGORY() {
        return Utils.getShared(context,Utils.CATEGORY,"");
    }

    public void setCATEGORY(String CATEGORY) {
        Utils.setShared(context,Utils.CATEGORY,CATEGORY);
    }

    private String CATEGORY = "";

    public String getCityid() {
        return Utils.getShared(context,Utils.cityid,"");
    }

    public void setCityid(String cityid) {
        Utils.setShared(context,Utils.cityid,cityid);
    }

    public String getCityname() {
        return Utils.getShared(context,Utils.cityname,"");
    }

    public void setCityname(String cityname) {
        Utils.setShared(context,Utils.cityname,cityname);
    }

    public String getAddressid() {
        return Utils.getShared(context,Utils.addressid,"");
    }

    public void setAddressid(String addressid) {
        Utils.setShared(context,Utils.addressid,addressid);
    }

    public String getCategory() {
        return Utils.getShared(context,Utils.category,"");
    }

    public void setCategory(String category) {
        Utils.setShared(context,Utils.category,category);
    }



    public String getFirstname() {
        return Utils.getShared(context,Utils.firstName,"");
    }

    public void setFirstname(String firstname) {
        Utils.setShared(context,Utils.firstName,firstname);
    }

    public String getMobilenumber() {
        return Utils.getShared(context,Utils.mobileNumber,"");
    }

    public void setMobilenumber(String mobilenumber) {
        Utils.setShared(context,Utils.mobileNumber,mobilenumber);
    }

    public String getUserstatus() {
        return Utils.getShared(context,Utils.userStatus,"");
    }

    public void setUserstatus(String userstatus) {
        Utils.setShared(context,Utils.userStatus,userstatus);
    }

    public String getMiddlename() {
        return Utils.getShared(context,Utils.middleName,"");
    }

    public void setMiddlename(String middlename) {
        Utils.setShared(context,Utils.middleName,middlename);
    }

    public String getPannumber() {
        return Utils.getShared(context,Utils.panNumber,"");
    }

    public void setPannumber(String pannumber) {
        Utils.setShared(context,Utils.panNumber,pannumber);
    }

    public String getLastname() {
        return Utils.getShared(context,Utils.lastName,"");
    }

    public void setLastname(String lastname) {
        Utils.setShared(context,Utils.lastName,lastname);
    }

    public String getDob() {
        return Utils.getShared(context,Utils.dob,"");
    }

    public void setDob(String dob) {
        Utils.setShared(context,Utils.dob,dob);
    }

    public String getUserimage() {
        return Utils.getShared(context,Utils.userimage,"");
    }

    public void setUserimage(String userimage) {
        Utils.setShared(context,Utils.userimage,userimage);
    }

    public String getAuthority() {
        return Utils.getShared(context,Utils.authority,"");
    }

    public void setAuthority(String authority) {
        Utils.setShared(context,Utils.authority,authority);
    }

    public String getLocation() {
        return Utils.getShared(context,Utils.location,"");
    }

    public void setLocation(String location) {
        Utils.setShared(context,Utils.location,location);
    }

    public String getAadharnumber() {
        return Utils.getShared(context,Utils.aadharnumber,"");
    }

    public void setAadharnumber(String aadharnumber) {
        Utils.setShared(context,Utils.aadharnumber,aadharnumber);
    }

    public String getFullname() {
        return Utils.getShared(context,Utils.fullname,"");
    }

    public void setFullname(String fullname) {
        Utils.setShared(context,Utils.fullname,fullname);
    }

    public String getUsercode() {
        return Utils.getShared(context,Utils.usercode,"");
    }

    public void setUsercode(String usercode) {
        Utils.setShared(context,Utils.usercode,usercode);
    }

    public String getEmail() {
        return Utils.getShared(context,Utils.email,"");
    }

    public void setEmail(String email) {
        Utils.setShared(context,Utils.email,email);
    }

    public String getUsername() {
        return Utils.getShared(context,Utils.username,"");
    }

    public void setUsername(String username) {
        Utils.setShared(context,Utils.username,username);
    }

    private static Session instance = null;
    Context context;

    public static Session getSession(Context context) {
        if(instance == null) {
            instance = new Session(context);
        }
        return instance;
    }

    protected Session(Context context) {
        this.context = context;
    }

    public String getUserId() {
        return Utils.getShared(context,Utils.userId,"");
    }

    public void setUserId(String userId) {
        Utils.setShared(context,Utils.userId,userId);
    }


}
