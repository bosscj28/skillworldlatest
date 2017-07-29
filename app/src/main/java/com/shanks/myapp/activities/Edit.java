package com.shanks.myapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.BankAdapter;
import com.shanks.myapp.adapter.CityAdapter;
import com.shanks.myapp.adapter.StateAdapter;
import com.shanks.myapp.edit_flow.EditRegister;
import com.shanks.myapp.models.BankModel;
import com.shanks.myapp.models.CityModel;
import com.shanks.myapp.models.StateModel;
import com.shanks.myapp.registration_flow.Register;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.shanks.myapp.R.id.ac_school;
import static com.shanks.myapp.R.id.bank_detail_submit;
import static com.shanks.myapp.R.id.onlineC;
import static com.shanks.myapp.R.id.sp_Designation;
import static com.shanks.myapp.R.id.state_id;

public class Edit extends AppCompatActivity {

    Spinner sp3, sp4, sp6;
    RadioButton cb1;
    RadioButton cb2;
    RadioButton cb3;
    RadioButton cb4;
    RadioButton cb5;
    RadioButton cb6;
    RadioButton cb7;
    RadioButton cb8;
    RadioButton cb9;
    RadioButton cb10;
    RadioButton radioBusiness;
    RadioButton radioSalaried;
    Spinner city_id;
    EditText tv1;
    EditText tv2;
    EditText tv3;
    EditText tv4;
    Session session;
    private AutoCompleteTextView ac_Name, ac_Amount, ac_Email;
    View.OnClickListener checkBoxListener10;
    View.OnClickListener checkBoxListener;
    View.OnClickListener checkBoxListener5;
    View.OnClickListener checkBoxListener6;
    View.OnClickListener checkBoxListener7;
    View.OnClickListener checkBoxListener8;
    View.OnClickListener checkBoxListener9;
    Spinner sp_MainBanker1, sp_Credit, sp_house, sp_car, Age, sp_Designation, state_id, spinner1,sp_Age;
    RadioGroup radio3, radio21, radio30, creditCard, radio27, carloan, healthI, radio32, onlineC, radio37;
    String categories = "";
    String category = "";
    String bank = "";
    String bank1 = "";
    String bank2 = "";
    String age = "";
    String age1 = "";
    String cityID = "";
    ArrayList<CityModel> cityModel = new ArrayList<>();
    String designation = "";
    String receivepaid = "";
    String carsites = "";
    String wallet = "";
    String credit = "";
    String houseL = "";
    String carL = "";
    String health = "";
    String mutualF = "";
    String onlineCourse = "";
    String product = "";
    ArrayList<BankModel> bankMainModels = new ArrayList<>();
    String bankId = "";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;
    ArrayList<StateModel> stateModel = new ArrayList<>();
    String stateID = "",Userid = "", Category_name = "";
    AutoCompleteTextView tv_home, ac_homet, ac_school;
    Button edit;
    RadioButton manager;
    RadioButton teamleader;
    RelativeLayout activity_first;
    TextView city_id_txt;
    ScrollView business;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final RadioButton manager = (RadioButton) findViewById(R.id.radioBusiness);
        final RadioButton teamleader = (RadioButton) findViewById(R.id.radioSalaried);
        ac_Name = (AutoCompleteTextView) findViewById(R.id.firstname);
        ac_Name.setText(getIntent().getExtras().getString("name"));
        ac_Name.setEnabled(false);
        ac_Amount = (AutoCompleteTextView) findViewById(R.id.lastname);
        ac_Amount.setText(getIntent().getExtras().getString("mobilenumber"));
        ac_Amount.setEnabled(false);
        ac_Email = (AutoCompleteTextView) findViewById(R.id.email);
        ac_Email.setText(getIntent().getExtras().getString("emailId"));
        ac_Email.setEnabled(false);
        String cityid = getIntent().getExtras().getString("cityid");
        stateID = cityid;
        String cityname = getIntent().getExtras().getString("cityname");
        stateID = cityname;
        Userid = getIntent().getExtras().getString("userid");
        Category_name = getIntent().getExtras().getString("category");

        System.out.println("cityid>>>>>"+cityid);
        System.out.println("cityname>>>>"+cityname);
        System.out.println("Userid>>>>>"+Userid);
        System.out.println("Category_name>>>>"+Category_name);

        session = Session.getSession(Edit.this);

//        String[] category = {"Business", "Salaried",};
//      //  spinner1 = (Spinner) findViewById(R.id.Category);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner1.setAdapter(adapter);
            String[] value0 = {"Select...","20-30", "30-40", "40-50", "50-60"};
            sp_Age = (Spinner) findViewById(R.id.sp_Age);
            adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, value0);
            adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sp_Age.setAdapter(adapter3);
            String[] value5 = {"Select...", "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90",};
            sp_Designation = (Spinner) findViewById(R.id.sp_Designation);
            adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, value5);
            adapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sp_Designation.setAdapter(adapter4);
            String[] values7 = {"Select...", "SBI", "Punjab National Bank", "Yes Bank", "Axis Bank", "IDFC Bank", "ICICI Bank", "Central Bank", "HDFC", "Idbi Bank"};
            sp_house = (Spinner) findViewById(R.id.sp_house);
            adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values7);
            adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sp_house.setAdapter(adapter1);
            String[] values2 = {"Select...", "SBI", "Punjab National Bank", "Yes Bank", "Axis Bank", "IDFC Bank", "ICICI Bank", "Central Bank", "HDFC", "Idbi Bank"};
            sp_car = (Spinner) findViewById(R.id.sp_car);
            adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values2);
            adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sp_car.setAdapter(adapter2);

//        for(int i=0;i<values.length;i++){
//            if(session.getCategory().equalsIgnoreCase(values[i])){
//                spinner1.setSelection(i);
//            }
//        }

//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 == 1) {
//                    categories = "Business";
//                } else if (arg2 == 2)
//                    categories = "Salaried";
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

        city_id_txt = (TextView)  findViewById(R.id.city_id_txt);

        final String CITY_NAME = session.getCityname();

        System.out.println("CITY_NAME>>>>"+CITY_NAME);

//        if (!session.getCityname().equalsIgnoreCase("")) {
//            city_id_txt.setVisibility(View.VISIBLE);
//            city_id_txt.setText(session.getCityname());
//            stateID = city_id_txt.getText().toString();
//        }

            initState();
            state_id = (Spinner) findViewById(R.id.sp_C);
            state_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    city_id_txt.setVisibility(View.GONE);

//            for(int j=0;i<stateModel.size();j++) {

                System.out.println("CITY_NAME>>IN>>"+CITY_NAME);
                if (!CITY_NAME.equalsIgnoreCase("")) {
                    System.out.println("CITY_NAME>>INNN>>"+CITY_NAME);
                    city_id_txt.setVisibility(View.VISIBLE);
                    city_id_txt.setText(session.getCityname());
                    stateID = city_id_txt.getText().toString();
                    state_id.setVisibility(View.INVISIBLE);
                    //                spinner1.setSelection(i);
                } else {
                    state_id.setVisibility(View.VISIBLE);
                    city_id_txt.setVisibility(View.GONE);
                    stateID = stateModel.get(i).getId();
                    System.out.println("CITY_NAME>>out>>"+CITY_NAME);
                }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sp_MainBanker1 = (Spinner) findViewById(R.id.sp_MainBanker1);
            //initBankDetail1();
            sp_MainBanker1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    bankId = bankMainModels.get(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    bankId = "";
                }
            });

            cb1 = (RadioButton) findViewById(R.id.Ye28);
            cb2 = (RadioButton) findViewById(R.id.No29);
            cb3 = (RadioButton) findViewById(R.id.house1);
            cb4 = (RadioButton) findViewById(R.id.house2);
            cb5 = (RadioButton) findViewById(R.id.CreditY);
            cb6 = (RadioButton) findViewById(R.id.CreditN);
            cb7 = (RadioButton) findViewById(R.id.Yes);
            cb8 = (RadioButton) findViewById(R.id.No);
            cb9 = (RadioButton) findViewById(R.id.Yes1);
            cb10 = (RadioButton) findViewById(R.id.No1);

//        ((RadioGroup)findViewById(R.id.radio1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            }
//        });

//        sp_Credit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 == 1) {
//                    bank = "SBI";
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
//                    bank = "HDFC";
//                else if (arg2 == 9)
//                    bank = "Idbi Bank";
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
            sp_house.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    if (arg2 == 1) {
                        bank2 = "SBI";
                    } else if (arg2 == 2)
                        bank2 = "Punjab National Bank";
                    else if (arg2 == 3)
                        bank2 = "Yes Bank";
                    else if (arg2 == 4)
                        bank2 = "Axis Bank";
                    else if (arg2 == 5)
                        bank2 = "IDFC Bank";
                    else if (arg2 == 6)
                        bank2 = "ICICI Bank";
                    else if (arg2 == 7)
                        bank2 = "IDFC Bank";
                    else if (arg2 == 8)
                        bank2 = "HDFC";
                    else if (arg2 == 9)
                        bank2 = "Idbi Bank";
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            sp_car.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    if (arg2 == 1) {
                        bank1 = "SBI";
                    } else if (arg2 == 2)
                        bank1 = "Punjab National Bank";
                    else if (arg2 == 3)
                        bank1 = "Yes Bank";
                    else if (arg2 == 4)
                        bank1 = "Axis Bank";
                    else if (arg2 == 5)
                        bank1 = "IDFC Bank";
                    else if (arg2 == 6)
                        bank1 = "ICICI Bank";
                    else if (arg2 == 7)
                        bank1 = "IDFC Bank";
                    else if (arg2 == 8)
                        bank1 = "HDFC";
                    else if (arg2 == 9)
                        bank1 = "Idbi Bank";
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            sp_Age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    if (arg2 == 1) {
                        age = "20-30";
                    } else if (arg2 == 2)
                        age = "30-40";
                    else if (arg2 == 3)
                        age = "40-50";
                    else if (arg2 == 4)
                        age = "50-60";
                    else if (arg2 == 5)
                        age = "60-70";
                    else if (arg2 == 6)
                        age = "70-80";
                    else if (arg2 == 7)
                        age = "80-90";
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            sp_Designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    if (arg2 == 1) {
                        age1 = "10-20";
                    } else if (arg2 == 2)
                        age1 = "20-30";
                    else if (arg2 == 3)
                        age1 = "30-40";
                    else if (arg2 == 4)
                        age1 = "40-50";
                    else if (arg2 == 5)
                        age1 = "50-60";
                    else if (arg2 == 6)
                        age1 = "60-70";

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            checkBoxListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp3 = (Spinner) findViewById(R.id.sp_car);

                    if (cb1.isChecked()) {
                        //sp.setText(tv.getText().toString());
                        sp3.setVisibility(View.VISIBLE);
                    }
                    if (cb2.isChecked()) {
                        sp3.setVisibility(View.GONE);
                    }
                }
            };
            checkBoxListener5 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp4 = (Spinner) findViewById(R.id.sp_house);

                    if (cb3.isChecked()) {
                        //sp.setText(tv.getText().toString());
                        sp4.setVisibility(View.VISIBLE);
                    }
                    if (cb4.isChecked()) {
                        sp4.setVisibility(View.GONE);
                    }
                }
            };
            checkBoxListener6 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp6 = (Spinner) findViewById(R.id.sp_Credit);

                    if (cb5.isChecked()) {
                        //sp.setText(tv.getText().toString());
                        sp6.setVisibility(View.VISIBLE);
                    }
                    if (cb6.isChecked()) {
                        sp6.setVisibility(View.GONE);
                    }
                }
            };
//        checkBoxListener7 =new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv1=(EditText)findViewById(R.id.et_twoWheeler);
//                tv1.setHint("Brand");
//                tv1.setTextSize(12);
//                if(cb7.isChecked()) {
//                    tv1.setText(tv1.getText().toString());
//                    tv1.setVisibility(View.VISIBLE);
//                }
//                if(cb8.isChecked()) {
//                    tv1.setText(tv1.getText().toString());
//                    tv1.setVisibility(View.GONE);
//                }
//            }
//        };
            checkBoxListener7 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv1 = (EditText) findViewById(R.id.et_twoWheeler);
                    tv1.setHint("Brand");
                    tv1.setTextSize(13);
                    if (cb7.isChecked()) {
                        tv1.setText(tv1.getText().toString());
                        tv1.setVisibility(View.VISIBLE);
                    }
                    tv4 = (EditText) findViewById(R.id.et_twoWheeler2);
                    tv4.setHint("Will buy in next 1 Year Yes or No");
                    tv4.setTextSize(13);
                    if (cb8.isChecked()) {
                        tv4.setText(tv4.getText().toString());
                        tv4.setVisibility(View.VISIBLE);
                    }
                }
            };
            checkBoxListener8 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv2 = (EditText) findViewById(R.id.et_CarOwned);
                    tv2.setHint("Brand");
                    tv2.setTextSize(13);
                    if (cb9.isChecked()) {
                        tv2.setText(tv2.getText().toString());
                        tv2.setVisibility(View.VISIBLE);
                    }
                    tv3 = (EditText) findViewById(R.id.et_CarOwned1);
                    tv3.setHint("Will buy in next 1 Year Yes or No");
                    tv3.setTextSize(13);
                    if (cb10.isChecked()) {
                        tv3.setText(tv3.getText().toString());
                        tv3.setVisibility(View.VISIBLE);
                    }
                }
            };

            Log.d("Category_name", Category_name);

        if (Category_name.equalsIgnoreCase("Business")) {

            manager.setChecked(true);
            Intent createIntent = new Intent(getApplicationContext(), Busines.class);
            createIntent.putExtra("cat1", Category_name);
            startActivityForResult(createIntent, 0);
//            business.setVisibility(View.VISIBLE);

        }else if (Category_name.equalsIgnoreCase("Salaried")) {

            teamleader.setChecked(true);
            Intent typeIntent = new Intent(getApplicationContext(), Salariednew.class);
            typeIntent.putExtra("age1", age);
//                        typeIntent.putExtra("cat1",stateID);
            startActivityForResult(typeIntent, 0);
        }

            checkBoxListener10 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    String genderString = session.getCATEGORY();

//                    if (genderString.equalsIgnoreCase("Salaried")) {
//
//                        Intent typeIntent = new Intent(getApplicationContext(), Salaried.class);
//                        typeIntent.putExtra("age1",age);
////                        typeIntent.putExtra("cat1",stateID);
//                        startActivityForResult(typeIntent, 0);
//                    }else {
//
//                        Intent createIntent = new Intent(getApplicationContext(), Busines.class);
////                        createIntent.putExtra("age1",age1);
//                        createIntent.putExtra("cat1",session.getCategory());
//                        startActivityForResult(createIntent, 0);
//                    }

                        if (manager.isChecked()) {

                            if (Category_name.equalsIgnoreCase("Business")) {

                                Intent createIntent = new Intent(getApplicationContext(), Busines.class);
//                        createIntent.putExtra("age1",age1);
                                createIntent.putExtra("cat1", Category_name);
                                startActivityForResult(createIntent, 0);
                            } else {

                                Toast.makeText(Edit.this, "Your are Not Business Person", Toast.LENGTH_SHORT).show();
                            }

                            //sp.setText(tv.getText().toString());
                            //  Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();
                        } else if (teamleader.isChecked()) {

                            if (age.equalsIgnoreCase("")) {

                                Utils.makeDialog(Edit.this, "Please Select age ");

                            }else {

                                if (Category_name.equalsIgnoreCase("Salaried")) {

                                    Intent typeIntent = new Intent(getApplicationContext(), Salariednew.class);
                                    typeIntent.putExtra("age1", age);
//                        typeIntent.putExtra("cat1",stateID);
                                    startActivityForResult(typeIntent, 0);
                                } else {

                                    Toast.makeText(Edit.this, "Your are Not Salaried Person", Toast.LENGTH_SHORT).show();
                                }
                            }

                            // Toast.makeText(MainActivity.this, "hi1", Toast.LENGTH_SHORT).show();
                            //activity_first.setVisibility(View.GONE);
                        }

                }

            };

//        checkBoxListener9 =new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv3=(EditText)findViewById(R.id.et_CarOwned1);
//                tv3.setHint("Will buy in next 1Year");
//                tv3.setTextSize(12);
//                if(cb9.isChecked()) {
//                    tv3.setText(tv3.getText().toString());
//                    tv3.setVisibility(View.GONE);
//                }
//                if(cb10.isChecked()) {
//                    tv3.setText(tv3.getText().toString());
//                    tv3.setVisibility(View.VISIBLE);
//                }
//            }
//        };
            cb1.setOnClickListener(checkBoxListener);
            cb2.setOnClickListener(checkBoxListener);
            cb3.setOnClickListener(checkBoxListener5);
            cb4.setOnClickListener(checkBoxListener5);
            cb5.setOnClickListener(checkBoxListener6);
            cb6.setOnClickListener(checkBoxListener6);
            cb7.setOnClickListener(checkBoxListener7);
            cb8.setOnClickListener(checkBoxListener7);
            cb9.setOnClickListener(checkBoxListener8);
            cb10.setOnClickListener(checkBoxListener8);
            manager.setOnClickListener(checkBoxListener10);
            teamleader.setOnClickListener(checkBoxListener10);
        }

    private void initState() {
       // CallService service = new CallService(Edit.this, Utils.BASE_URL + Utils.COUNTRY_CITY_STATE + "?countryid=1", Utils.GET, false, new

        CallService service = new CallService(Edit.this, Utils.BASE_URL + Utils.GET_ALL_CITIES , Utils.GET, false, new
                CallService.OnServicecall() {
                    @Override
                    public void onServicecall(String response) {
                        try {

                            JSONObject jobj = new JSONObject(response);
                            JSONArray jarr = jobj.getJSONArray("cityList");
                            StateModel zero_model = new StateModel();
                            zero_model.setId("");
                            zero_model.setName("Select...");
                            stateModel.add(zero_model);

                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject innerJobj = jarr.getJSONObject(i);
                                String name = innerJobj.getString("name");
                                String id = innerJobj.getString("id");
                                StateModel model = new StateModel();
                                model.setId(id);
                                model.setName(name);
                                stateModel.add(model);
                            }

//                            if (!session.getCityname().equalsIgnoreCase("")) {
//                                city_id_txt.setVisibility(View.VISIBLE);
//                                city_id_txt.setText(session.getCityname());
//                                stateID = city_id_txt.getText().toString();
//                            }else {
                                StateAdapter adapter = new StateAdapter(Edit.this, 0, stateModel, getResources());
//                                city_id_txt.setVisibility(View.GONE);
                                state_id.setAdapter(adapter);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
    }
}

