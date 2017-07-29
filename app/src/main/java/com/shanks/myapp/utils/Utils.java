package com.shanks.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by katrina on 17/01/17.
 */

public class Utils {


    public static String userId = "userId";
    public static String userId1 = "offerId";
    public static String mobileNumber = "mobileNumber";
    public static String userStatus = "userStatus";
    public static String userId2 = "communicateUser";
    public static String firstName = "firstName";
    public static String middleName = "middleName";
    public static String panNumber = "panNumber";
    public static String lastName = "lastName";
    public static String dob = "dob";
    public static String userimage = "userimage";
    public static String authority = "authority";
    public static String location = "location";
    public static String aadharnumber = "aadharnumber";
    public static String fullname = "fullname";
    public static String usercode = "usercode";
    public static String email = "email";
    public static String username = "username";
    public static String cityid = "cityid";
    public static String cityname = "cityname";
    public static String addressid = "addressid";
    public static String category = "category";
    public static String main = "initial";
    public static String POST = "POST";
    public static String GET = "GET";
    public static String PUT = "PUT";
    public static String CATEGORY = "CATEGORY";
    public static String creditCards = "creditCards";
    public static String plan_For_Bank_Loan = "plan_For_Bank_Loan";
    public static String bankDetailsId = "bankDetailsId";
    public static String customerid = "customerid";
    public static String bank_name = "bank_name";
    public static String homePinCode = "homePinCode";
    public static String shopFront_Image = "shopFront_Image";
    public static String productDetails = "productDetails";
    public static String officeAirconditioned = "officeAirconditioned";
    public static String noOfStaffEmployed = "noOfStaffEmployed";
    public static String passowrd = "passowrd";

    public static String healthInsuranceSalery = "healthInsurance";
    public static String homeTutionSalery = "homeTution";
    public static String transcationsSalery = "transcations";
    public static String descriptionSalery = "description";
    public static String bankDetailsIdSalery = "bankDetailsId";
    public static String twoWheelerOwnedSalery = "twoWheelerOwned";
    public static String walletsSalery = "wallets";
    public static String homePinCodeSalery = "homePinCode";
    public static String housingLoanSalery = "housingLoan";
    public static String productDetailsSalery = "productDetails";
    public static String ecommerceSitesSalery = "ecommerceSites";
    public static String noOfSchoolGoingChildrenSalery = "noOfSchoolGoingChildren";
    public static String creditCardsSalery = "creditCards";
    public static String onlineCourseSalery = "onlineCourse";
    public static String carOwnedSalery = "carOwned";
    public static String carLoanSalery = "carLoan";
    public static String mutualFundSalery ="mutualFund";
    public static String bank_nameSalery = "bank_nameSalery";
    public static String bank_detail_id = "bank_detail_id";

    // base url
    public static final String BASE_URL = "http://139.59.18.198:6060/SkillWorldAPI/";
//    public static final String BASE_URL1 ="http://192.168.1.20:5050/SkillBaazarAPI/";

    public static final String BASE_IMAGE_URL = "http://139.59.18.198/referralMedia/profileImages/";

    public static final String IMAGE_URL = "http://139.59.18.198";


    // get all cities
    public static final String GET_ALL_CITIES = "getAllCities";

    public static final String TRANSACTION = "addCustomerAPI";

    public static final String EDITCUSTOMER = "editCustomerAPI";

    // sign up api
    public static final String REGISTER = "signUpPersonal";//

    // edit REGISTER data
    public static final String EDIT_REGISTER = "editSignUpPersonal";

    // get upload details
    public static final String UPLOAD_DETAILS = "getUserDocumentDetail";

    // get bank detais
    public static final String GET_BANK_DETAILS = "getUserBankDetail";

    // get customer list
    public static final String GET_CUSTOMER_LIST = "getCustomerListAPI";

    // get user details
    public static final String USER_DETAILS = "getUserPersonalDetail";

    // login api
    public static final String LOGIN = "signin";

    // sign up docs signUpDocs
    public static final String REGISTER_DOCS = "signUpDocs";

    // get categories
    public static final String CATEGORIES = "getCategory";

    public static final String EDIT = "getSalaryListAPI";

    // upload profile
    public static final String UPLOADIMAGE = "uploadprofilepic";

    // get qualification
    public static final String GET_QUALIFICATION = "qualification";//

    // get qualificationz
    public static final String COUNTRY_CITY_STATE = "cityStateCountry";

    // get account type
    public static final String BANK_TYPE = "bankAccountType";

    // get account detail
    public static final String BANK_DETAIL = "bankDetails";

    // set account detail
    public static final String SET_BANK_DETAIL = "signUpBankDetails"; //

    // get account detail
    public static final String OFFER_LIST = "getCategoryWiseOfferListAPI";//

    // get offer detail
    public static final String OFFER_DETAIL = "getOfferDetails";

    // category id
    public static String CATEGORY_ID = "";

    public static String CUSTOMER_ID = "";

    public static final String OFFER_DETAIL1 = "getCategoryWiseOfferListAPI";


    public static final String USER_STATUS_INITIAL = "Initial";

    public static final String GET_LEADS = "getStatusWiseMyLead";
    public static final String GET_CITY = "getAllCities";

    public static final String GET_PAYMENTS = "getStatusWisePaymentList";//
    public static final String GET_DEALER_LIST = "getDealerList";
    public static final String BUSINESS = "getBusinessListAPI";
    public static final String SAVE_LEADS = "saveLead";//

    public static final String CLOSE_LEADS = "closedLeadData";
    public static final String IMAGE = "getBannerListAPI";
    public static final String SEND_EMAIL_SMS = "getSmsAndEmalDetailAPI";

    // shared methods
    public static void setShared(Context context, String name, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String getShared(Context context, String name, String defaultValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString(name, defaultValue);

    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();

        return (int) d.getHeight();
    }
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();

        return (int) d.getWidth();
    }

    public static String uploadFile(String imagePath) throws Exception{
        // String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        String uploadImageFilename = "";
        String uploadFileName = System.currentTimeMillis() + ".jpg";

            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
                URL url = new URL(BASE_URL+UPLOADIMAGE);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("id_proof", uploadFileName);
                // Toast.makeText(getActivity(),uploadFileName_id,Toast.LENGTH_SHORT).show();
                //conn.setRequestProperty("user_id", user_id);


                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"id_proof\";filename=\"" + uploadFileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                int serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                // id_proof_value="1";

                //Log.i("uploadFile", "HTTP Response is : "
                //     + serverResponseMessage + ": " + serverResponseCode);


                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//                dialog.dismiss();
                ex.printStackTrace();

                //Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//                dialog.dismiss();
                e.printStackTrace();

                /*runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        *//*Toast.makeText(getBaseContext()
                                , "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();*//*
                    }
                });*/
                //   //Log.e("Upload file to server Exception", "Exception : "
                //         + e.getMessage(), e);
            }

            InputStream responseStream = null;
            try {
                responseStream = new
                        BufferedInputStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            try {
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                responseStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String response = stringBuilder.toString();
            try {
                JSONObject jres = new JSONObject(response);
                String filepath = jres.getString("filepath");
                uploadImageFilename = filepath;


            } catch (Exception e) {

            }

            Log.d("response", response);
            try {
                responseStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();

            return uploadImageFilename;

    }

    public static void makeDialog(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
