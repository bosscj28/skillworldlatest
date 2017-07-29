package com.shanks.myapp.registration_flow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.shanks.myapp.R;
import com.shanks.myapp.activities.MainActivity;
import com.shanks.myapp.adapter.AccountTypeAdapter;
import com.shanks.myapp.adapter.BankAdapter;
import com.shanks.myapp.models.BankAccountTypeModel;
import com.shanks.myapp.models.BankModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

public class RegistrationBankDetails extends AppCompatActivity {

    Button bank_detail_submit;
    Spinner sp_accountType,sp_bankName;
    ArrayList<BankAccountTypeModel> mainModels = new ArrayList<>();
    ArrayList<BankModel> bankMainModels = new ArrayList<>();
    String accountTypeId = "";
    String bankId = "",bankNAME = "";
    Session session;

    AutoCompleteTextView acntno,branchname,ifsc;
    ImageView arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_bank_details);
        init();
    }

    private void init(){

        session = Session.getSession(RegistrationBankDetails.this);

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setVisibility(View.GONE);
        acntno = (AutoCompleteTextView)findViewById(R.id.acntno);
        branchname = (AutoCompleteTextView)findViewById(R.id.branchname);
        ifsc = (AutoCompleteTextView)findViewById(R.id.ifsc);

        sp_accountType = (Spinner)findViewById(R.id.sp_accountType);
        initAccountType();
        sp_accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accountTypeId = mainModels.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                accountTypeId = "";
            }
        });

        sp_bankName = (Spinner)findViewById(R.id.sp_bankName);
        initBankDetail();
        sp_bankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankId = bankMainModels.get(i).getId();
                bankNAME = bankMainModels.get(i).getBankname();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bankId = "";
            }
        });

        bank_detail_submit = (Button)findViewById(R.id.bank_detail_submit);
        bank_detail_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String acntnoString = acntno.getText().toString().trim();
                String branchnameString = branchname.getText().toString().trim();
                String ifscString = ifsc.getText().toString().trim();

                if(acntnoString.equalsIgnoreCase("")){
                    Utils.makeDialog(RegistrationBankDetails.this,"Please enter account number");
                } else if(branchnameString.equalsIgnoreCase("")){
                    Utils.makeDialog(RegistrationBankDetails.this,"Please enter branch name");
                } else if(ifscString.equalsIgnoreCase("")){
                    Utils.makeDialog(RegistrationBankDetails.this,"Please enter ifsc code");
                }else if(bankId.equalsIgnoreCase("")){
                    Utils.makeDialog(RegistrationBankDetails.this,"Please select bank name");
                } else if(accountTypeId.equalsIgnoreCase("")){
                    Utils.makeDialog(RegistrationBankDetails.this,"Please select account type");
                } else {
                    String url = Utils.BASE_URL+Utils.SET_BANK_DETAIL
                            + "?userid=" + session.getUserId()
                            + "&banknameid=" + bankId
                            + "&acnumber=" + acntnoString
                            + "&branchname=" + branchnameString
                            + "&ifscno=" + ifscString
                            + "&actypeId=" + accountTypeId
                            + "&bankname=" + bankNAME;

                    CallService service = new CallService(RegistrationBankDetails.this, url, Utils.GET, true, new CallService.OnServicecall() {
                        @Override
                        public void onServicecall(String response) {
                            Log.d("ankit",response);

//                            {"result":"Success.","userid":"187","userinfo":{"firstname":"Hxhhx","mobilenumber":"8383838386","userstatus":"Active","middlename":"","userid":"187","pannumber":"FUFJFU4556","lastname":"Vzhxhx","dob":"0096-01-23 00:00:00.0","userimage":"1485170233590282.jpg","authority":"BRAND_ASSOCIATE","location":"","aadharnumber":"868386838833","fullname":"Hxhhx Vzhxhx","usercode":"AAA050","email":"ufufuf@ccufu.com","username":"Xgxhxu"},"responseCode":"2001"}
                            try{
                                JSONObject jobj = new JSONObject(response);
                                String responseCode = jobj.getString("responseCode");
                                if(responseCode.equalsIgnoreCase("2001")){

                                    JSONObject userinfo = jobj.getJSONObject("userinfo");
                                    String firstname = userinfo.getString("firstname");
                                    session.setFirstname(firstname);

                                    String mobilenumber = userinfo.getString("mobilenumber");
                                    session.setMobilenumber(mobilenumber);

                                    String userstatus = userinfo.getString("userstatus");
                                    session.setUserstatus(userstatus);

                                    String middlename = userinfo.getString("middlename");
                                    session.setMiddlename(middlename);
//                                    String userid = userinfo.getString("userid");
                                    String pannumber = userinfo.getString("pannumber");
                                    session.setPannumber(pannumber);

                                    String lastname = userinfo.getString("lastname");
                                    session.setLastname(lastname);

                                    String dob = userinfo.getString("dob");
                                    session.setDob(dob);

                                    String userimage = userinfo.getString("userimage");
                                    session.setUserimage(userimage);

                                    String authority = userinfo.getString("authority");
                                    session.setAuthority(authority);

                                    String location = userinfo.getString("location");
                                    session.setLocation(location);

                                    String aadharnumber = userinfo.getString("aadharnumber");
                                    session.setAadharnumber(aadharnumber);

                                    String fullname = userinfo.getString("fullname");
                                    session.setFullname(fullname);

                                    String usercode = userinfo.getString("usercode");
                                    session.setUsercode(usercode);

                                    String email = userinfo.getString("email");
                                    session.setEmail(email);

                                    String username = userinfo.getString("username");
                                    session.setUsername(username);

                                    new AlertDialog.Builder(RegistrationBankDetails.this)
                                            .setTitle("Congrats ...")
                                            .setMessage("Congrats your registration is now complete...")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                    startActivity(new Intent(RegistrationBankDetails.this,MainActivity.class));
                                                    finish();
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();


                                }
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });

                    service.execute();


                }


            }
        });

    }

    private void initBankDetail(){

        String url = Utils.BASE_URL + Utils.BANK_DETAIL;
        CallService service = new CallService(RegistrationBankDetails.this,url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankDetailsList");

                    BankModel zero_model = new BankModel();
                    zero_model.setId("");
                    zero_model.setBankname("Select ...");
                    zero_model.setDescription("Description ...");
                    bankMainModels.add(zero_model);

                    for (int i=0;i<jarr.length();i++){
                        JSONObject innerJobj = jarr.getJSONObject(i);
                        String bankname = innerJobj.getString("bankname");
                        String description = innerJobj.getString("description");
                        String id = innerJobj.getString("id");

                        BankModel model = new BankModel();
                        model.setId(id);
                        model.setBankname(bankname);
                        model.setDescription(description);
                        bankMainModels.add(model);
                    }

                    BankAdapter adapter = new BankAdapter(RegistrationBankDetails.this,0,bankMainModels,getResources());
                    sp_bankName.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

//        service.execute();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
    }

    private void initAccountType(){

        String url = Utils.BASE_URL + Utils.BANK_TYPE;
        CallService service = new CallService(RegistrationBankDetails.this,url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankAccountTypeList");

                    BankAccountTypeModel zero_model = new BankAccountTypeModel();
                    zero_model.setId("");
                    zero_model.setAccounttype("Select ...");
                    zero_model.setDescription("Description ...");
                    mainModels.add(zero_model);

                    for (int i=0;i<jarr.length();i++){
                        JSONObject innerJobj = jarr.getJSONObject(i);
                        String accounttype = innerJobj.getString("accounttype");
                        String description = innerJobj.getString("description");
                        String id = innerJobj.getString("id");

                        BankAccountTypeModel model = new BankAccountTypeModel();
                        model.setId(id);
                        model.setAccounttype(accounttype);
                        model.setDescription(description);
                        mainModels.add(model);
                    }

                    AccountTypeAdapter adapter = new AccountTypeAdapter(RegistrationBankDetails.this,0,mainModels,getResources());
                    sp_accountType.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

//        service.execute();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
    }
}
