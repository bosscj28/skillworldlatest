package com.shanks.myapp.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shanks.myapp.R;
import com.shanks.myapp.adapter.BankAdapter;
import com.shanks.myapp.edit_flow.EditRegister;
import com.shanks.myapp.models.BankAccountTypeModel;
import com.shanks.myapp.models.BankModel;
import com.shanks.myapp.models.Business_model_class;
import com.shanks.myapp.models.ListModel;
import com.shanks.myapp.registration_flow.Login;
import com.shanks.myapp.registration_flow.Register;
import com.shanks.myapp.registration_flow.RegistrationBankDetails;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Busines extends AppCompatActivity implements View.OnClickListener {
    EditText tv;
    Spinner sp, sp1;
    RadioButton cbS;
    RadioButton cbW;
    RadioButton cbS1;
    RadioButton cbW1;
    RadioButton cbS2;
    RadioButton cbW2;
    Session session;
    Context context;
    Button btn_S;
    RelativeLayout bill_relative;
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 2;
    private String selectedImagePath;
    String serverFilePath = "none";
    ImageView image;
    AutoCompleteTextView ProductDealtIn,Addreespincode,Numberofstaffemployed;
    Spinner spinner1, sp_mainbanker, sp_bankloan,sp_creditcard;
    String category = "";
    String bank = "";
    String shop = "";
    String loan = "";
    String creditcard = "";
    String loannB= "",cat_gret;
    RadioGroup radioG,radio2,radio3,radio4;
    ArrayList<BankModel> bankMainModels = new ArrayList<>();
    ArrayList<BankModel> BankArray = new ArrayList<>();
    ArrayList<BankModel> LoanBankArray = new ArrayList<>();
    ArrayList<BankModel> CCBankArray = new ArrayList<>();
    String bankId = "";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    View.OnClickListener checkBoxListener;
    View.OnClickListener checkBoxListener1;
    View.OnClickListener checkBoxListener2;
    private GoogleApiClient client;
    RadioButton AirC1,AirC;
    TextView city_id_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busines);

        session = Session.getSession(Busines.this);
        cat_gret = getIntent().getExtras().getString("cat1");

        Log.d("Cat>>>", cat_gret);

        image = (ImageView)findViewById(R.id.image);
        btn_S=(Button)findViewById(R.id.btn_S);
        btn_S.setOnClickListener(this);
        ProductDealtIn= (AutoCompleteTextView) findViewById(R.id.ProductDealtIn);
        Addreespincode = (AutoCompleteTextView) findViewById(R.id.Addreespincode);
        Numberofstaffemployed= (AutoCompleteTextView) findViewById(R.id.Numberofstaffemployed);
        bill_relative = (RelativeLayout) findViewById(R.id.bill_relative);
        bill_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog();
            }
        });
        /*String[] values = {cat_gret};//{"Select...", "Retail", "Service", "Wholesale", "Manufacturing", "Exporter"};
        spinner1 = (Spinner) findViewById(R.id.sp_Category);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);*/

 //       String[] values1 = {"Select...", "StateBankIndia", "Punjab National Bank", "Yes Bank", "Axis Bank", "IDFC Bank", "ICICI Bank", "Central Bank", "Bank of Baroda", "Idbi Bank"};
//        sp1 = (Spinner) findViewById(R.id.sp_creditcard);
//        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values1);
//        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        sp1.setAdapter(adapter1);
        AirC1 = (RadioButton) findViewById(R.id.AirC1);
        AirC = (RadioButton) findViewById(R.id.AirC);
        cbS = (RadioButton) findViewById(R.id.Y4);
        cbW = (RadioButton) findViewById(R.id.N4);
        cbS1 = (RadioButton) findViewById(R.id.Y3);
        cbW1 = (RadioButton) findViewById(R.id.N3);
        cbS2 = (RadioButton) findViewById(R.id.Y1);
        cbW2 = (RadioButton) findViewById(R.id.N1);

         city_id_txt = (TextView)  findViewById(R.id.city_id_txt);

        final String CITY_NAME = session.getBank_name();
        /*
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                category = cat_gret;

                if (arg2 == 1) {
                    category = "Retail";
                } else if (arg2 == 2) {
                    category = "Service";
                }
                else if (arg2 == 3) {
                    category = "Wholesale";
                }
                else if (arg2 == 4) {
                    category = "Manufacturing";
                }
                else if (arg2 == 5) {
                    category = "Exporter";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });*/
//        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 == 1) {
//                    bank = "StateBankIndia";
//                } else if (arg2 == 2)
//                    bank = "Punjab National Bank";
//                else if (arg2 == 3)
//                    bank = "Yes Bank";
//                else if (arg2 == 4)
//                    bank = "Axis Bank";
//                else if (arg2 == 5)
//                    bank = "IDFC Bank";
//                else if (arg2 == 6)
//                    bank = "ICICI Bank";
//                else if (arg2 == 7)
//                    bank = "IDFC Bank";
//                else if (arg2 == 8)
//                    bank = "Bank of Baroda";
//                else if (arg2 == 9)
//                    bank = "Idbi Bank";
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
        sp_mainbanker = (Spinner) findViewById(R.id.sp_mainbanker);
        initBankDetail1();
        sp_mainbanker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!CITY_NAME.equalsIgnoreCase("")) {
                    System.out.println("CITY_NAME>>INNN>>"+CITY_NAME);
                    city_id_txt.setVisibility(View.VISIBLE);
                    city_id_txt.setText(session.getBank_name());
                    bankId = session.getBank_detail_id();
                    sp_mainbanker.setVisibility(View.INVISIBLE);
                    //                spinner1.setSelection(i);
                } else {
                    sp_mainbanker.setVisibility(View.VISIBLE);
                    city_id_txt.setVisibility(View.GONE);
                    bankId = BankArray.get(i).getId();
                    System.out.println("CITY_NAME>>out>>"+CITY_NAME);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bankId = "";
            }
        });

        sp_creditcard = (Spinner) findViewById(R.id.sp_creditcard);
        initBankDetail3();
        sp_creditcard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankId = CCBankArray.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bankId = "";
            }
        });
        sp_bankloan = (Spinner) findViewById(R.id.sp_bankloan);
        initBankDetail2();
        sp_bankloan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankId = LoanBankArray.get(i).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                bankId = "";
            }
        });
        checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv = (EditText) findViewById(R.id.et_value);
                tv.setHint("When and how Much");
                tv.setTextSize(12);
                if (cbS.isChecked()) {
                    tv.setText(tv.getText().toString());
                    tv.setVisibility(View.VISIBLE);
                }
                if (cbW.isChecked()) {
                    tv.setVisibility(View.GONE);
                }
            }
        };
        checkBoxListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = (Spinner) findViewById(R.id.sp_creditcard);

                if (cbS1.isChecked()) {
                    //sp.setText(tv.getText().toString());
                    sp.setVisibility(View.VISIBLE);
                }
                if (cbW1.isChecked()) {
                    sp.setVisibility(View.GONE);
                }
            }
        };
        checkBoxListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp1 = (Spinner) findViewById(R.id.sp_bankloan);

                if (cbS2.isChecked()) {
                    //sp.setText(tv.getText().toString());
                    sp1.setVisibility(View.VISIBLE);
                }
                if (cbW2.isChecked()) {
                    sp1.setVisibility(View.GONE);
                }
                radio2 = (RadioGroup) findViewById(R.id.radio2);
                radio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Y1:
                                // do operations specific to this selection
                                loan= "Yes";
                                break;
                            case R.id.N1:
                                // do operations specific to this selection
                                loan= "No";
                                break;
                        }
                    }
                });
                radio4 = (RadioGroup) findViewById(R.id.radio4);
                radio4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Y3:
                                // do operations specific to this selection
                                creditcard = "Yes";
                                break;
                            case R.id.N3:
                                // do operations specific to this selection
                                creditcard = "No";
                                break;
                        }
                    }
                });
                radio3 = (RadioGroup) findViewById(R.id.radio3);
                radio3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Y4:
                                // do operations specific to this selection
                                loannB = "Yes";
                                break;
                            case R.id.N4:
                                // do operations specific to this selection
                                loannB = "No";
                                break;
                        }
                    }
                });
                radioG = (RadioGroup) findViewById(R.id.radioG);
                radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.AirC1:
                                // do operations specific to this selection
                                shop = "Yes";
                                break;
                            case R.id.AirC:
                                // do operations specific to this selection
                                shop = "No";
                                break;
                        }
                    }
                });
            }
        };

        cbS.setOnClickListener(checkBoxListener);
        cbW.setOnClickListener(checkBoxListener);
        cbS1.setOnClickListener(checkBoxListener1);
        cbW1.setOnClickListener(checkBoxListener1);
        cbS2.setOnClickListener(checkBoxListener2);
        cbW2.setOnClickListener(checkBoxListener2);

//        Business_model_class business = new Business_model_class();

        if (!session.getCreditCards().equalsIgnoreCase("")) {
            if (session.getCreditCards().equalsIgnoreCase("Yes")) {
                cbS1.setChecked(true);
                creditcard = cbS1.getText().toString();
            } else {
                cbW1.setChecked(true);
                creditcard = cbW1.getText().toString();
            }
        }

        if (!session.getPlan_For_Bank_Loan().equalsIgnoreCase("")) {
            if (session.getPlan_For_Bank_Loan().equalsIgnoreCase("Yes")) {
                cbS2.setChecked(true);
                loan = cbS2.getText().toString();
            } else {
                cbW2.setChecked(true);
                loan = cbW2.getText().toString();
            }
        }

//        if (!session.getCityname().equalsIgnoreCase("")) {
//            city_id_txt.setVisibility(View.VISIBLE);
//            city_id_txt.setText(session.getBank_name());
//            bankId = city_id_txt.getText().toString();
//        }

//        if (!session.getBank_name().equalsIgnoreCase("")) {
//            for (int i = 0; i < bankMainModels.size(); i++) {
//                if (session.getBank_name().equalsIgnoreCase(bankMainModels.get(i).getId())) {
//                    sp_mainbanker.setSelection(i);
//                }
//            }
//        }

        if (!session.getHomePinCode().equalsIgnoreCase("")) {
            Addreespincode.setText(session.getHomePinCode());
        }

        if (!session.getShopFront_Image().equalsIgnoreCase("")) {
            System.out.println("session.getShopFront_Image()"+session.getShopFront_Image());
            serverFilePath = session.getShopFront_Image();
            Picasso.with(Busines.this).load(Utils.BASE_IMAGE_URL + serverFilePath).into(image);
        }

        if (!session.getProductDetails().equalsIgnoreCase("")) {
            ProductDealtIn.setText(session.getProductDetails());
        }

        if (!session.getOfficeAirconditioned().equalsIgnoreCase("")) {
            if (session.getOfficeAirconditioned().equalsIgnoreCase("Yes")) {
                AirC1.setChecked(true);
                shop = AirC1.getText().toString();
            } else {
                AirC.setChecked(true);
                shop = AirC.getText().toString();
            }
        }

        if (!session.getNoOfStaffEmployed().equalsIgnoreCase("")) {
            Numberofstaffemployed.setText(session.getNoOfStaffEmployed());
        }
    }
        private void userLogin1() {

            Log.d("shop---->", shop);

            if (loannB.equalsIgnoreCase("")) {

                loannB = String.valueOf(0);

            }else {
                loannB = tv.getText().toString();
            }

            String productString = ProductDealtIn.getText().toString().trim();
            String addressPin = Addreespincode.getText().toString().trim();
            String noofE = Numberofstaffemployed.getText().toString().trim();
            if(category.equalsIgnoreCase("")){
                //Utils.makeDialog(Busines.this,"Please select Category");
                category="";
            }
            //else
                if (productString.equalsIgnoreCase("")) {
                //Utils.makeDialog(Busines.this, "Please enter Product Dealts In");
                    productString="";
            }
            //else
                if (addressPin.equalsIgnoreCase("")) {
                //Utils.makeDialog(Busines.this, "Please enter Address Pin code");
                    addressPin="";
            }
            //else
                if (noofE.equalsIgnoreCase("")) {
                //Utils.makeDialog(Busines.this, "Please enter No of Staff");
                    noofE="";
            }
            //else
                if(shop.length() == 0){
                //Utils.makeDialog(Busines.this,"Office/Shop is Air Conditioned or Not");
                shop="";
                }
            //else
                if(bankId.length() == 0){
                //Utils.makeDialog(Busines.this,"Please select Bank");
                    bankId="";
            }
            //else
                if(loan.length() == 0){
                //Utils.makeDialog(Busines.this,"Please select Bank loan");
                    loan="";
            }
//            else if(loannB.length() == 0){
//                Utils.makeDialog(Busines.this,"Please select if intrested in Bank Loan");
//            }
            //else
                if(creditcard.length() == 0){
                //Utils.makeDialog(Busines.this,"Please select CreditCard Machine");
                    creditcard="";
            }
            //else
                if(serverFilePath.length() == 0) {
                //Utils.makeDialog(Busines.this, "Please enter Image");
                    serverFilePath="none";

           }
           //else {
                String url = Utils.BASE_URL + Utils.BUSINESS
                        + "?customerid=1"
                        + "&category_detail=" + category
                        + "&productdetails=" + productString
                        + "&homepincode=" + addressPin
                        + "&NoOfStaffEmployed=" + noofE
                        + "&bankdetailsid=" + bankId
                        + "&officeAirconditioned=" + shop
                        + "&plan_For_Bank_Loan=" + loan
                        + "&amount_Of_Loan=" + loannB
                        + "&creditcards=" + creditcard
                        + "&shopFront_Image=" + serverFilePath;

                Log.d("tanyaBhanu", url);

                CallService service = new CallService(Busines.this, url, Utils.GET, true, new CallService.OnServicecall() {
                    @Override
                    public void onServicecall(String response) {
                        Log.d("tanyaBhanuResponse", response);

//                        http://139.59.18.198:6060/SkillWorldAPI/getBusinessListAPI?customerid=1&category_detail=Retail&productdetails=Goods&homepincode=632536&NoOfStaffEmployed=50&bankdetailsid=6&officeAirconditioned=&plan_For_Bank_Loan=Yes&amount_Of_Loan=Yes&creditcards=Yes&shopFront_Image=none

//                            {"result":"Success.","userid":"187","userinfo":{"firstname":"Hxhhx","mobilenumber":"8383838386","userstatus":"Active","middlename":"","userid":"187","pannumber":"FUFJFU4556","lastname":"Vzhxhx","dob":"0096-01-23 00:00:00.0","userimage":"1485170233590282.jpg","authority":"BRAND_ASSOCIATE","location":"","aadharnumber":"868386838833","fullname":"Hxhhx Vzhxhx","usercode":"AAA050","email":"ufufuf@ccufu.com","username":"Xgxhxu"},"responseCode":"2001"}
                        try {
//                            JSONObject jobj = new JSONObject(response);

                            JSONObject jsonObject = new JSONObject(response);
                            String Success = jsonObject.getString("result");

                            String userinfo = jsonObject.getString("categoryinfo");

                            JSONObject userinfo1 = new JSONObject(userinfo);
//                            JSONObject categoryinfo = jobj.getJSONObject("categoryinfo");
//                            JSONObject innerJob = categoryinfo.getJSONObject(0);

                            System.out.println("Results>userinfo1>>"+userinfo1);

                            Business_model_class business = new Business_model_class();

                            String creditCards = userinfo1.getString("creditCards");
                            session.setCreditCards(creditCards);
                            business.setCreditCards(creditCards);
                            String card = business.getCreditCards();
                            if(card.equalsIgnoreCase("Yes")){
                                cbS1.setChecked(true);
                            } else {
                                cbW1.setChecked(true);
                            }

                            String category_detail = userinfo1.getString("category_detail");

                            String plan_For_Bank_Loan = userinfo1.getString("plan_For_Bank_Loan");
                            session.setPlan_For_Bank_Loan(plan_For_Bank_Loan);
                            business.setPlan_For_Bank_Loan(plan_For_Bank_Loan);
                            String loan = business.getPlan_For_Bank_Loan();
                            if(loan.equalsIgnoreCase("Yes")){
                                cbS2.setChecked(true);
                            } else {
                                cbW2.setChecked(true);
                            }

                            System.out.println("Results>>>"+business.getCreditCards()+"\n"+business.getPlan_For_Bank_Loan());

                            String bankDetailsId = userinfo1.getString("bankDetailsId");

                            String customerid = userinfo1.getString("customerid");

                            String bank_name = userinfo1.getString("bank_name");
                            session.setBank_name(bank_name);
                            business.setBank_name(bankDetailsId);
                            session.setBank_detail_id(bankDetailsId);
                            String bank = business.getBank_name();
                            for(int i=0;i<bankMainModels.size();i++){
                                if(bank.equalsIgnoreCase(bankMainModels.get(i).getId())){
                                    sp_mainbanker.setSelection(i);
                                }
                            }

                            String amount_Of_Loan = userinfo1.getString("amount_Of_Loan");

                            String homePinCode = userinfo1.getString("homePinCode");
                            session.setHomePinCode(homePinCode);
                            business.setHomePinCode(homePinCode);
                            Addreespincode.setText(business.getHomePinCode());

                            String shopFront_Image = userinfo1.getString("shopFront_Image");
                            session.setShopFront_Image(shopFront_Image);
                            business.setShopFront_Image(shopFront_Image);
                            serverFilePath = business.getShopFront_Image();
                            Picasso.with(Busines.this).load(Utils.BASE_IMAGE_URL+serverFilePath).into(image);

                            String productDetails = userinfo1.getString("productDetails");
                            session.setProductDetails(productDetails);
                            business.setProductDetails(productDetails);
                            ProductDealtIn.setText(business.getProductDetails());

                            String officeAirconditioned = userinfo1.getString("officeAirconditioned");
                            session.setOfficeAirconditioned(officeAirconditioned);
                            business.setOfficeAirconditioned(officeAirconditioned);
                            String Air = business.getOfficeAirconditioned();
                            if(Air.equalsIgnoreCase("Yes")){
                                AirC1.setChecked(true);
                            } else {
                                AirC.setChecked(true);
                            }

                            String NoOfStaffEmployed = userinfo1.getString("NoOfStaffEmployed");
                            session.setNoOfStaffEmployed(NoOfStaffEmployed);
                            business.setNoOfStaffEmployed(NoOfStaffEmployed);
                            Numberofstaffemployed.setText(business.getNoOfStaffEmployed());

//                            String responseCode = jobj.getString("responseCode");
//                            if (responseCode.equalsIgnoreCase("2001")){

                                new AlertDialog.Builder(Busines.this)
                                        .setTitle("Congrats ...")
                                        .setMessage("Congrats your CustomerDetails is now complete....")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                startActivity(new Intent(Busines.this, MainActivity.class));

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                service.execute();

           // }
        }
            public void onClick(View v) {
                userLogin1();
            }

    public static JSONArray sortJsonArray(JSONArray array) {
        final List<JSONObject> jsons = new ArrayList<JSONObject>();
        JSONObject END = null;
        for (int i = 0; i < array.length(); i++) {
            try {
                jsons.add(array.getJSONObject(i));
                if(jsons.get(i).getString("bankname").equals("Others"))
                {
                    END = jsons.get(i);
                    jsons.remove(i);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                String lid="";
                String rid="";

                try {
                    lid = lhs.getString("bankname");
                    rid = rhs.getString("bankname");
                }catch (Exception e){
                    e.printStackTrace();
                }
                // Here you could parse string id to integer and then compare.
                return lid.compareTo(rid);
            }

        });

        if(END!=null){
            jsons.add(END);
        }
        return new JSONArray(jsons);
    }

    private void initBankDetail1() {

        String url = Utils.BASE_URL + Utils.BANK_DETAIL;
        CallService service = new CallService(Busines.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankDetailsList");
                    BankArray.clear();

                    BankModel zero_model = new BankModel();
                    zero_model.setId("");
                    zero_model.setBankname("Select ...");
                    zero_model.setDescription("Description ...");
                    BankArray.add(zero_model);
                    JSONArray jarr2 = sortJsonArray(jarr);

                    for (int i = 0; i < jarr2.length(); i++) {
                        BankModel model = new BankModel();
                        model.setId(jarr2.getJSONObject(i).getString("id"));
                        model.setBankname(jarr2.getJSONObject(i).getString("bankname"));
                        model.setDescription(jarr2.getJSONObject(i).getString("description"));
                        BankArray.add(model);
                    }

                    BankAdapter adapter = new BankAdapter(Busines.this, 0, BankArray, getResources());
                    sp_mainbanker.setAdapter(adapter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

//        service.execute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
    }

    private void initBankDetail2() {

        String url = Utils.BASE_URL + Utils.BANK_DETAIL;
        CallService service = new CallService(Busines.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankDetailsList");
                    LoanBankArray.clear();
                    BankModel zero_model = new BankModel();
                    zero_model.setId("");
                    zero_model.setBankname("Select ...");
                    zero_model.setDescription("Description ...");
                    LoanBankArray.add(zero_model);
                    JSONArray jarr2 = sortJsonArray(jarr);
                    for (int i = 0; i < jarr2.length(); i++) {
                        BankModel model = new BankModel();
                        model.setId(jarr2.getJSONObject(i).getString("id"));
                        model.setBankname(jarr2.getJSONObject(i).getString("bankname"));
                        model.setDescription(jarr2.getJSONObject(i).getString("description"));
                        LoanBankArray.add(model);
                    }

                    BankAdapter adapter = new BankAdapter(Busines.this, 0, LoanBankArray, getResources());
                    sp_bankloan.setAdapter(adapter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

//        service.execute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
    }
    private void initBankDetail3() {

        String url = Utils.BASE_URL + Utils.BANK_DETAIL;
        CallService service = new CallService(Busines.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankDetailsList");
                    CCBankArray.clear();
                    BankModel zero_model = new BankModel();
                    zero_model.setId("");
                    zero_model.setBankname("Select ...");
                    zero_model.setDescription("Description ...");
                    CCBankArray.add(zero_model);
                    JSONArray jarr2 = sortJsonArray(jarr);
                    for (int i = 0; i < jarr2.length(); i++) {
                        BankModel model = new BankModel();
                        model.setId(jarr2.getJSONObject(i).getString("id"));
                        model.setBankname(jarr2.getJSONObject(i).getString("bankname"));
                        model.setDescription(jarr2.getJSONObject(i).getString("description"));
                        CCBankArray.add(model);
                    }
                    BankAdapter adapter = new BankAdapter(Busines.this, 0, CCBankArray, getResources());
                    sp_creditcard.setAdapter(adapter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

//        service.execute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
    }
    private void makeDialog(){
        // custom dialog
        final Dialog dialog = new Dialog(Busines.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog);

        TextView dialog_camera = (TextView)dialog.findViewById(R.id.dialog_camera);
        dialog_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                imageFromCamera();
            }
        });

        TextView dialog_card = (TextView)dialog.findViewById(R.id.dialog_card);
        dialog_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                imageFromCard();
            }
        });

        dialog.show();
    }

    private void imageFromCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void imageFromCard(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);
    }

    @SuppressLint("NewApi")
    public  String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal tol
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @SuppressLint("NewApi")
    public  String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public  String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            final String filePath = Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis() + ".jpg";
            File destination = new File(filePath);

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    try{
                        serverFilePath = Utils.uploadFile(filePath);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                    Log.d("ankit",serverFilePath);
                    return null;
                }
            }.execute();



        } else if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();

            sendToServer(selectedImageUri);

            image.setImageURI(selectedImageUri);
            image.setRotation(getCameraPhotoOrientation(Busines.this,selectedImageUri,selectedImagePath));
        }
    }

    private void sendToServer(Uri selectedImageUri){
        synchronized (this){
            if (Build.VERSION.SDK_INT < 11)
                selectedImagePath = getRealPathFromURI_BelowAPI11(Busines.this, selectedImageUri);

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                selectedImagePath = getRealPathFromURI_API11to18(Busines.this, selectedImageUri);

                // SDK > 19 (Android 4.4)
            else
                selectedImagePath = getRealPathFromURI_API19(Busines.this, selectedImageUri);
        }

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Log.d("ankit","local path :::"+selectedImagePath);
                try{
                    serverFilePath = Utils.uploadFile(selectedImagePath);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("ankit",serverFilePath);
            }
        }.execute();
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
}
