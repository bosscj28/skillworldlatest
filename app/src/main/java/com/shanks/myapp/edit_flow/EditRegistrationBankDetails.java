package com.shanks.myapp.edit_flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.AccountTypeAdapter;
import com.shanks.myapp.adapter.BankAdapter;
import com.shanks.myapp.models.BankAccountTypeModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import com.shanks.myapp.activities.MainActivity;
import com.shanks.myapp.models.BankModel;
import com.shanks.myapp.utils.Session;

/**
 * Created by katrina on 25/01/17.
 */

public class EditRegistrationBankDetails extends AppCompatActivity{

    Button bank_detail_submit;
    Spinner sp_accountType,sp_bankName;
    ArrayList<BankAccountTypeModel> mainModels = new ArrayList<>();
    ArrayList<BankModel> bankMainModels = new ArrayList<>();
    String accountTypeId = "";
    String bankId = "";
    Session session;

    AutoCompleteTextView acntno,branchname,ifsc;
    TextView header;
    ImageView arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_bank_details);

        synchronized (this){
            init();
        }

        getBankDetails();
    }

    private void init(){

        session = Session.getSession(EditRegistrationBankDetails.this);

        header = (TextView)findViewById(R.id.header);
        header.setText("EDIT BANK DETAILS");

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

            }
        });

        sp_bankName = (Spinner)findViewById(R.id.sp_bankName);
        initBankDetail();
        sp_bankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankId = bankMainModels.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bank_detail_submit = (Button)findViewById(R.id.bank_detail_submit);
        bank_detail_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                sendToHome();
//                startActivity(new Intent(EditRegistrationBankDetails.this,MainActivity.class));
//                finish();
                String acntnoString = acntno.getText().toString().trim();
                String branchnameString = branchname.getText().toString().trim();
                String ifscString = ifsc.getText().toString().trim();

                if(acntnoString.equalsIgnoreCase("")){
                    Utils.makeDialog(EditRegistrationBankDetails.this,"Please enter account number");
                } else if(branchnameString.equalsIgnoreCase("")){
                    Utils.makeDialog(EditRegistrationBankDetails.this,"Please enter branch name");
                } else if(ifscString.equalsIgnoreCase("")){
                    Utils.makeDialog(EditRegistrationBankDetails.this,"Please enter ifsc code");
                } else {

                    String url = Utils.BASE_URL+Utils.SET_BANK_DETAIL
                            + "?userid=" + session.getUserId()
                            + "&banknameid=" + bankId
                            + "&acnumber=" + acntnoString
                            + "&branchname=" + branchnameString
                            + "&ifscno=" + ifscString
                            + "&actypeId=" + accountTypeId;

                    CallService service = new CallService(EditRegistrationBankDetails.this, url, Utils.GET, true, new CallService.OnServicecall() {
                        @Override
                        public void onServicecall(String response) {
                            Log.d("ankit",response);

//                            {"result":"Success.","userid":"187","userinfo":{"firstname":"Hxhhx","mobilenumber":"8383838386","userstatus":"Active","middlename":"","userid":"187","pannumber":"FUFJFU4556","lastname":"Vzhxhx","dob":"0096-01-23 00:00:00.0","userimage":"1485170233590282.jpg","authority":"BRAND_ASSOCIATE","location":"","aadharnumber":"868386838833","fullname":"Hxhhx Vzhxhx","usercode":"AAA050","email":"ufufuf@ccufu.com","username":"Xgxhxu"},"responseCode":"2001"}
                            try{
                                JSONObject jobj = new JSONObject(response);
                                String responseCode = jobj.getString("responseCode");
                                if(responseCode.equalsIgnoreCase("2001")){
                                    startActivity(new Intent(EditRegistrationBankDetails.this,MainActivity.class));
                                    finish();
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


    private void sendToHome(){
        Intent intent = new Intent(EditRegistrationBankDetails.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void getBankDetails(){
        String url = Utils.BASE_URL+Utils.GET_BANK_DETAILS + "?userid="+session.getUserId();
        CallService service = new CallService(EditRegistrationBankDetails.this, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit","bank details data "+response);
                try{
                    PARSE_DATA(response);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }

    private void PARSE_DATA(String response) throws Exception{
        JSONObject jsonObject = new JSONObject(response);
        JSONArray bankdetailList = jsonObject.getJSONArray("bankdetailList");
        JSONObject innerJob = bankdetailList.getJSONObject(0);

        String bankid = innerJob.getString("bankid");
        for(int i=0;i<bankMainModels.size();i++){
            if(bankid.equalsIgnoreCase(bankMainModels.get(i).getId())){
                sp_bankName.setSelection(i);
            }
        }

        String accountnumber = innerJob.getString("accountnumber");
        acntno.setText(accountnumber);

        String branchName = innerJob.getString("branchName");
        branchname.setText(branchName);

        String id = innerJob.getString("id");
        String bankname = innerJob.getString("bankname");

        String ifsccode = innerJob.getString("ifsccode");
        ifsc.setText(ifsccode);

        String accounttypename = innerJob.getString("accounttypename");

        String accounttypeid = innerJob.getString("accounttypeid");
        for(int i=0;i<mainModels.size();i++){
            if(accounttypeid.equalsIgnoreCase(mainModels.get(i).getId())){
                sp_accountType.setSelection(i);
            }
        }

    }

    private void initBankDetail(){

        String url = Utils.BASE_URL + Utils.BANK_DETAIL;
        CallService service = new CallService(EditRegistrationBankDetails.this,url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankDetailsList");

                    BankModel zero_model = new BankModel();
                    zero_model.setId("0");
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

                    BankAdapter adapter = new BankAdapter(EditRegistrationBankDetails.this,0,bankMainModels,getResources());
                    sp_bankName.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }

    private void initAccountType(){

        String url = Utils.BASE_URL + Utils.BANK_TYPE;
        CallService service = new CallService(EditRegistrationBankDetails.this,url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankAccountTypeList");

                    BankAccountTypeModel zero_model = new BankAccountTypeModel();
                    zero_model.setId("0");
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

                    AccountTypeAdapter adapter = new AccountTypeAdapter(EditRegistrationBankDetails.this,0,mainModels,getResources());
                    sp_accountType.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }
}
