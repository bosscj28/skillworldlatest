package com.shanks.myapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.BankAdapter;
import com.shanks.myapp.adapter.SalAdapter;
import com.shanks.myapp.models.BankModel;
import com.shanks.myapp.models.Designation_model;
import com.shanks.myapp.models.Salaryed_model;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.shanks.myapp.R.id.view;

public class Salariednew extends AppCompatActivity implements View.OnClickListener {
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
    RadioButton cb10,Ye29,No32,Ye30,No33,Yes36,No37;
    EditText tv1;
    EditText tv2;
    EditText tv3;
    EditText tv4;
    Session session;
    private AutoCompleteTextView ac_Name, ac_Amount;
    View.OnClickListener checkBoxListener;
    View.OnClickListener checkBoxListener5;
    View.OnClickListener checkBoxListener6;
    View.OnClickListener checkBoxListener7;
    View.OnClickListener checkBoxListener8;
    View.OnClickListener checkBoxListener9;
    Spinner sp_MainBanker1, sp_Credit, sp_house, sp_car, Age, sp_Designation;
    RadioGroup radio1, radio3, radio2, radio21, radio30, creditCard, radio27, carloan, healthI, radio32, onlineC, radio37;
    String category = "";
    String bank = "";
    String bank1 = "";
    String bank2 = "";
    String age = "";
    String age1 = "";
    String designation = "";
    String receivepaid = "";
    String carsites = "";
    String wallet = "";
    String credit = "";
    String houseL = "";
    String twowheller = "";
    String carOwned = "";
    String carL = "";
    String health = "";
    String mutualF = "";
    String onlineCourse = "";
    String product = "";
    String HomeTution = "",age_get,cat_get;
    ArrayList<BankModel> bankMainModels = new ArrayList<>();
    ArrayList<Designation_model> Designation_MainModels = new ArrayList<>();
    String bankId = "";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;
    ArrayAdapter<String> adapter5;
    AutoCompleteTextView tv_home, ac_homet, ac_school;
    Button btn_E;
    Spinner spinner1,sp_hometution;
    ArrayList<String> list ;
    CheckBox Yes2,No4,No5,No6,No7,No8,No9,No10,Ye9,No0,Ye10,Ye11,
            No11,No12,No24,No25,No26,Ye25,No3_0,No3_1,No3_2,No3_3,Yes37,Yes38,Yes39,Yes40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaried);

        session = Session.getSession(Salariednew.this);

        age_get = getIntent().getExtras().getString("age1");

        Log.d("Age>>>", age_get);

        age = age_get;

        list = new ArrayList<String>();

        btn_E = (Button) findViewById(R.id.btn_E);
        btn_E.setOnClickListener(this);
        tv_home = (AutoCompleteTextView) findViewById(R.id.tv_home);
        ac_homet = (AutoCompleteTextView) findViewById(R.id.ac_homet);
        ac_school = (AutoCompleteTextView) findViewById(R.id.ac_school);
//        String[] catvalues = {"Select...", "Retail", "Service", "Wholesale", "Manufacturing", "Exporter"};
//        spinner1 = (Spinner) findViewById(R.id.sp_Category);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catvalues);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner1.setAdapter(adapter);
        String[] value = {age_get};//{"Select...", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90"};
        Age = (Spinner) findViewById(R.id.Age);
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, value);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Age.setAdapter(adapter3);
//        String[] value5 = {"Select...", "Manager", "AssistantManager", "ProjectManager"};
        sp_Designation = (Spinner) findViewById(R.id.sp_Designation);
//        adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, value5);
//        adapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        sp_Designation.setAdapter(adapter4);
        String[] values1 = {"Select...", "HDFC Bank", "ICICI Bank", "Citi Bank", "Standard Chartered Bank", "Kotak Bank", "Yes Bank", "SBI", "Amex", "Axis Bank","HSBC Bank","IDFC Bank","Bank of Baroda",
                "TATA Capital","Central Bank","IndusInd Bank","RBL Bank","Bank of India","Vijata Bank","J&K Bank","IDBI Bank"};
        sp_Credit = (Spinner) findViewById(R.id.sp_Credit);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_Credit.setAdapter(adapter);
        String[] values = {"Select...", "HDFC Ltd", "Indiabulls", "LIC Housing Finance", "PNB Housing", "DHFL", "Reliance", "Birla Finance", "Bajaj", "Finance","Edelweiss","Tata Capital","PNB","Bank of Baroda","Capital First","Others"};
        sp_house = (Spinner) findViewById(R.id.sp_house);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_house.setAdapter(adapter1);
        String[] values2 = {"Select...", "Reliance", "Birla Finance", "Bajaj", "Finance", "Edelweiss", "Tata Capital", "PNB", "Bank of Baroda", "Capital First","Others"};
        sp_car = (Spinner) findViewById(R.id.sp_car);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values2);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_car.setAdapter(adapter2);
        sp_MainBanker1 = (Spinner) findViewById(R.id.sp_MainBanker1);
        initBankDetail1();
        initBankDetail11();
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
        Ye29 = (RadioButton) findViewById(R.id.No1);
        No32 = (RadioButton) findViewById(R.id.No1);
        Ye30 = (RadioButton) findViewById(R.id.No1);
        No33 = (RadioButton) findViewById(R.id.No1);
        Yes36 = (RadioButton) findViewById(R.id.No1);
        No37 = (RadioButton) findViewById(R.id.No1);

        Yes2 = (CheckBox) findViewById(R.id.Yes2);
        No4 = (CheckBox) findViewById(R.id.No4);
        No5 = (CheckBox) findViewById(R.id.No5);
        No6 = (CheckBox) findViewById(R.id.No6);
        No7 = (CheckBox) findViewById(R.id.No7);
        No8 = (CheckBox) findViewById(R.id.No8);
        No9 = (CheckBox) findViewById(R.id.No9);
        No10 = (CheckBox) findViewById(R.id.No10);

//        Yes2 = (CheckBox) findViewById(R.id.Yes2);
        No4 = (CheckBox) findViewById(R.id.No4);
        No5 = (CheckBox) findViewById(R.id.No5);
        No6 = (CheckBox) findViewById(R.id.No6);
        No7 = (CheckBox) findViewById(R.id.No7);
        No8 = (CheckBox) findViewById(R.id.No8);
        No9 = (CheckBox) findViewById(R.id.No9);
        No10 = (CheckBox) findViewById(R.id.No10);
//
        Ye9 = (CheckBox) findViewById(R.id.Ye9);
        No0 = (CheckBox) findViewById(R.id.No0);
        Ye10 = (CheckBox) findViewById(R.id.Ye10);
        Ye11 = (CheckBox) findViewById(R.id.Ye11);
        No11 = (CheckBox) findViewById(R.id.No11);
        No12 = (CheckBox) findViewById(R.id.No12);
        No24 = (CheckBox) findViewById(R.id.Ye24);
        No25 = (CheckBox) findViewById(R.id.No25);
        No26 = (CheckBox) findViewById(R.id.No26);
        Ye25 = (CheckBox) findViewById(R.id.Ye25);

        No3_0 = (CheckBox) findViewById(R.id.Yes3_0);
        No3_1 = (CheckBox) findViewById(R.id.No3_1);
        No3_2 = (CheckBox) findViewById(R.id.Yes3_2);
        No3_3 = (CheckBox) findViewById(R.id.No3_3);

        Yes37 = (CheckBox) findViewById(R.id.Yes37);
        Yes38 = (CheckBox) findViewById(R.id.No38);
        Yes39 = (CheckBox) findViewById(R.id.No39);
        Yes40 = (CheckBox) findViewById(R.id.No40);

        Yes2.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No4.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No5.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No6.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No7.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No8.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No9.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        No10.setOnCheckedChangeListener(new myCheckBoxChnageClicker());

        Ye9.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        No0.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        Ye10.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        Ye11.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        No11.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        No12.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        No24.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        No25.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        No26.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());
        Ye25.setOnCheckedChangeListener(new myCheckBoxChnageClicker1());

        No3_0.setOnCheckedChangeListener(new myCheckBoxChnageClicker2());
        No3_1.setOnCheckedChangeListener(new myCheckBoxChnageClicker2());
        No3_2.setOnCheckedChangeListener(new myCheckBoxChnageClicker2());
        No3_3.setOnCheckedChangeListener(new myCheckBoxChnageClicker2());

        Yes37.setOnCheckedChangeListener(new myCheckBoxChnageClicker3());
        Yes38.setOnCheckedChangeListener(new myCheckBoxChnageClicker3());
        Yes39.setOnCheckedChangeListener(new myCheckBoxChnageClicker3());
        Yes40.setOnCheckedChangeListener(new myCheckBoxChnageClicker3());

//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 == 1) {
//                    category = "Retail";
//                } else if (arg2 == 2)
//                    category = "Service";
//                else if (arg2 == 3)
//                    category = "Wholesale";
//                else if (arg2 == 4)
//                    category = "Manufacturing";
//                else if (arg2 == 5)
//                    category = "Exporter";
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

        String[] value6 = {"Yes", "No"};
        sp_hometution = (Spinner) findViewById(R.id.sp_hometution);

        adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, value6);
        adapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_hometution.setAdapter(adapter5);

        sp_hometution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HomeTution = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        ((RadioGroup)findViewById(R.id.radio1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            }
//        });

        sp_Credit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 == 1) {
                    bank = "SBI";
                } else if (arg2 == 2)
                    bank = "Punjab National Bank";
                else if (arg2 == 3)
                    bank = "Yes Bank";
                else if (arg2 == 4)
                    bank = "Axis Bank";
                else if (arg2 == 5)
                    bank = "IDFC Bank";
                else if (arg2 == 6)
                    bank = "ICICI Bank";
                else if (arg2 == 7)
                    bank = "IDFC Bank";
                else if (arg2 == 8)
                    bank = "HDFC";
                else if (arg2 == 9)
                    bank = "Idbi Bank";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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

        Age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age1 = Designation_MainModels.get(i).getDesignationName_id();
                Log.d("Deginationid", age1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                age1 = "";
            }
        });

//        sp_Designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 == 1) {
//                    age1 = "Manager";
//                } else if (arg2 == 2)
//                    age1 = "AssistantManager";
//                else if (arg2 == 3)
//                    age1 = "ProjectManager";
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
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
                } else {
                    tv1.setVisibility(View.GONE);

                }

                tv4 = (EditText) findViewById(R.id.et_twoWheeler2);
                tv4.setHint("Will buy in next 1 Year Yes or No");
                tv4.setTextSize(13);
                if (cb8.isChecked()) {
                    tv4.setText(tv4.getText().toString());
                    tv4.setVisibility(View.VISIBLE);
                } else {
                    tv4.setVisibility(View.GONE);

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
                } else {
                    tv2.setVisibility(View.GONE);

                }
                tv3 = (EditText) findViewById(R.id.et_CarOwned1);
                tv3.setHint("Will buy in next 1 Year Yes or No");
                tv3.setTextSize(13);
                if (cb10.isChecked()) {
                    tv3.setText(tv3.getText().toString());
                    tv3.setVisibility(View.VISIBLE);
                } else {
                    tv3.setVisibility(View.GONE);

                }
            }
        };

//        radio3 = (RadioGroup) findViewById(R.id.radio3);
//        radio3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                              @Override
//                                              public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                                  switch (checkedId) {
//                                                      case R.id.Yes2:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Shopping";
//                                                          break;
//                                                      case R.id.No4:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Travel Booking";
//                                                          break;
//                                                      case R.id.No5:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Hotel Booking";
//                                                          break;
//                                                      case R.id.No6:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Food Ordering";
//                                                          break;
//                                                      case R.id.No7:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Movie Ticketing";
//                                                          break;
//                                                      case R.id.No8:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Bill Payement";
//                                                          break;
//                                                      case R.id.No9:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Taxi Booking";
//                                                          break;
//                                                      case R.id.No10:
//                                                          // do operations specific to this selection
//                                                          receivepaid = "Groceries";
//                                                          break;
//
//                                                  }
//                                              }
//                                          }
//        );
//        radio21 = (RadioGroup) findViewById(R.id.radio21);
//        radio21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                               @Override
//                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                                   switch (checkedId) {
//                                                       case R.id.Ye9:
//                                                           // do operations specific to this selection
//                                                           carsites = "Amazon";
//                                                           break;
//                                                       case R.id.No0:
//                                                           // do operations specific to this selection
//                                                           carsites = "Flipkart";
//                                                           break;
//                                                       case R.id.Ye10:
//                                                           // do operations specific to this selection
//                                                           carsites = "Yatra";
//                                                           break;
//                                                       case R.id.No11:
//                                                           // do operations specific to this selection
//                                                           carsites = "MakeMyTrip";
//                                                           break;
//                                                       case R.id.Ye11:
//                                                           // do operations specific to this selection
//                                                           carsites = "Book My Show";
//                                                           break;
//                                                       case R.id.No12:
//                                                           // do operations specific to this selection
//                                                           carsites = "Zomato";
//                                                           break;
//                                                       case R.id.Ye24:
//                                                           // do operations specific to this selection
//                                                           carsites = "Swiggy";
//                                                           break;
//                                                       case R.id.No25:
//                                                           // do operations specific to this selection
//                                                           carsites = "Paytm";
//                                                           break;
//                                                       case R.id.Ye25:
//                                                           // do operations specific to this selection
//                                                           carsites = "Ola";
//                                                           break;
//                                                       case R.id.No26:
//                                                           // do operations specific to this selection
//                                                           carsites = "Uber";
//                                                           break;
//
//                                                   }
//                                               }
//                                           }
//
//        );

//        radio30 = (RadioGroup) findViewById(R.id.radio30);
//        radio30.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                               @Override
//                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                                   switch (checkedId) {
//                                                       case R.id.Ye30:
//                                                           // do operations specific to this selection
//                                                           wallet = "PayU";
//                                                           break;
//                                                       case R.id.No30:
//                                                           // do operations specific to this selection
//                                                           wallet = "PayTM";
//                                                           break;
//                                                       case R.id.Yes31:
//                                                           // do operations specific to this selection
//                                                           wallet = "Mobikwik";
//                                                           break;
//                                                       case R.id.No31:
//                                                           // do operations specific to this selection
//                                                           wallet = "Any Other";
//                                                           break;
//
//
//                                                   }
//                                               }
//                                           }
//
//        );
        creditCard = (RadioGroup) findViewById(R.id.creditCard);
        creditCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                      switch (checkedId) {
                                                          case R.id.CreditY:
                                                              // do operations specific to this selection
                                                              credit = "Yes";
                                                              break;
                                                          case R.id.CreditN:
                                                              // do operations specific to this selection
                                                              credit = "No";
                                                              break;
                                                      }
                                                  }
                                              }
        );
        radio1 = (RadioGroup) findViewById(R.id.radio1);
        radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                  switch (checkedId) {
                                                      case R.id.Yes:
                                                          // do operations specific to this selection
                                                          twowheller = "Yes";
                                                          break;
                                                      case R.id.No:
                                                          // do operations specific to this selection
                                                          twowheller = "No";
                                                          break;
                                                  }
                                              }
                                          }
        );
        radio2 = (RadioGroup) findViewById(R.id.radio2);
        radio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                  switch (checkedId) {
                                                      case R.id.Yes1:
                                                          // do operations specific to this selection
                                                          carOwned = "Yes";
                                                          break;
                                                      case R.id.No1:
                                                          // do operations specific to this selection
                                                          carOwned = "No";
                                                          break;
                                                  }
                                              }
                                          }
        );

        radio27 = (RadioGroup) findViewById(R.id.radio27);
        radio27.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                   switch (checkedId) {
                                                       case R.id.house1:
                                                           // do operations specific to this selection
                                                           houseL = "Yes";
                                                           break;
                                                       case R.id.house2:
                                                           // do operations specific to this selection
                                                           houseL = "No";
                                                           break;
                                                   }
                                               }
                                           }
        );
        carloan = (RadioGroup) findViewById(R.id.carloan);
        carloan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                   switch (checkedId) {
                                                       case R.id.Ye28:
                                                           // do operations specific to this selection
                                                           carL = "Yes";
                                                           break;
                                                       case R.id.No29:
                                                           // do operations specific to this selection
                                                           carL = "No";
                                                           break;
                                                   }
                                               }
                                           }
        );
        healthI = (RadioGroup) findViewById(R.id.healthI);
        healthI.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                   switch (checkedId) {
                                                       case R.id.Ye29:
                                                           // do operations specific to this selection
                                                           health = "Yes";
                                                           break;
                                                       case R.id.No32:
                                                           // do operations specific to this selection
                                                           health = "No";
                                                           break;
                                                   }
                                               }
                                           }
        );
        onlineC = (RadioGroup) findViewById(R.id.onlineC);
        onlineC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                   switch (checkedId) {
                                                       case R.id.Yes36:
                                                           // do operations specific to this selection
                                                           onlineCourse = "Yes";
                                                           break;
                                                       case R.id.No37:
                                                           // do operations specific to this selection
                                                           onlineCourse = "No";
                                                           break;
                                                   }
                                               }
                                           }
        );
        radio32 = (RadioGroup) findViewById(R.id.radio32);
        radio32.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                   switch (checkedId) {
                                                       case R.id.Ye30:
                                                           // do operations specific to this selection
                                                           mutualF = "Yes";
                                                           break;
                                                       case R.id.No33:
                                                           // do operations specific to this selection
                                                           mutualF = "No";
                                                           break;
                                                   }
                                               }
                                           }
        );

//        radio37 = (RadioGroup) findViewById(R.id.radio37);
//        radio37.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                               @Override
//                                               public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                                   switch (checkedId) {
//                                                       case R.id.Yes37:
//                                                           // do operations specific to this selection
//                                                           product = "2Wheeler";
//                                                           break;
//                                                       case R.id.No38:
//                                                           // do operations specific to this selection
//                                                           product = "Car";
//                                                           break;
//                                                       case R.id.No39:
//                                                           // do operations specific to this selection
//                                                           product = "House";
//                                                           break;
//                                                       case R.id.No40:
//                                                           // do operations specific to this selection
//                                                           product = "Household Appliance(like TV/Fridge/AC)";
//                                                           break;
//                                                   }
//                                               }
//                                           }
//        );

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


//        session.setHealthInsurance(healthInsurance);
//        session.setHomeTution(homeTution);
//        session.setTranscations(transcations);
//        session.setDescription(description);
//        session.setBank_name(bank_name);
//        session.setTwoWheelerOwned(twoWheelerOwned);
//        session.setWallets(wallets);
//        session.setHomePinCode(homePinCode);
//        session.setHousingLoan(housingLoan);
//        session.setProductDetails(productDetails);
//        session.setEcommerceSites(ecommerceSites);
//        session.setNoOfStaffEmployed(noOfSchoolGoingChildren);
//        session.setCreditCards(creditCards);
//        session.setOnlineCourse(onlineCourse);
//        session.setCarOwned(carOwned);
//        session.setCarLoan(carLoan);
//        session.setMutualFund(mutualFund);
//        session.setBankDetailsIdC(bankDetailsId);

//        D/Age: 40-50
//        D/Pin Code: 235689
//        D/Two wheeler: Yes
//        D/Car Owned: Yes
//        D/Transcations: Shopping
//        D/Ecommerece Sites: Amazon
//        D/credit card: Yes
//        D/House loan: Yes
//        D/Car Loan: Yes
//        D/Priducts: 2Wheeler
//        D/Home Tution: Yes
//        D/Health Insurance: Yes
//        D/Mutual Found: Yes
//        D/Online Course: Yes
//        D/No of going to school: Yes
//        D/Bank id: 4
//        D/Designation: 4

        Log.d("Age", String.valueOf(session.getDescriptionSalery()));

        if (!session.getDescriptionSalery().equalsIgnoreCase("")) {
            age = session.getDescriptionSalery();
        }

        Log.d("Pin Code", String.valueOf(session.getHomePinCodeSalery()));
        if (!session.getHomePinCodeSalery().equalsIgnoreCase("")) {
            tv_home.setText(session.getHomePinCodeSalery());
        }

        Log.d("Two wheeler", String.valueOf(session.getTwoWheelerOwnedSalery()));
        if (!session.getTwoWheelerOwnedSalery().equalsIgnoreCase("")) {
            if (session.getTwoWheelerOwnedSalery().equalsIgnoreCase("Yes")) {
                cb7.setChecked(true);
            } else {
                cb8.setChecked(true);
            }
        }

        Log.d("Car Owned", String.valueOf(session.getCarOwnedSalery()));
        if (!session.getCarOwnedSalery().equalsIgnoreCase("")) {
            if (session.getCarOwnedSalery().equalsIgnoreCase("Yes")) {
                cb9.setChecked(true);
            } else {
                cb10.setChecked(true);
            }
        }

        Log.d("Transcations", String.valueOf(session.getTranscationsSalery()));
        receivepaid = session.getTranscationsSalery();

        if (!session.getTranscationsSalery().equalsIgnoreCase("")) {
            if (receivepaid.equalsIgnoreCase("Shopping")) {

                Yes2.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Travel Booking")) {

                No4.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Hotel Booking")) {

                No5.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Food Ordering")) {

                No6.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Movie Ticketing")) {

                No7.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Bill Payement")) {

                No8.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Taxi Booking")) {

                No9.setChecked(true);

            } else if (receivepaid.equalsIgnoreCase("Groceries")) {

                No10.setChecked(true);

            }
        }

        Log.d("Ecommerece Sites", String.valueOf(session.getEcommerceSitesSalery()));
        carsites = session.getEcommerceSitesSalery();

        if (!session.getEcommerceSitesSalery().equalsIgnoreCase("")) {
            if (carsites.equalsIgnoreCase("Amazon")) {

                Ye9.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Flipkart")) {

                No0.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Yatra")) {

                Ye10.setChecked(true);

            } else if (carsites.equalsIgnoreCase("MakeMyTrip")) {

                Ye11.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Book My Show")) {

                No11.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Zomato")) {

                No12.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Swiggy")) {

                No24.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Paytm")) {

                No25.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Ola")) {

                No26.setChecked(true);

            } else if (carsites.equalsIgnoreCase("Uber")) {

                Ye25.setChecked(true);

            }
        }

        Log.d("Wallets", String.valueOf(session.getWalletsSalery()));
        wallet = session.getWalletsSalery();

        if (!session.getWalletsSalery().equalsIgnoreCase("")) {
            if (wallet.equalsIgnoreCase("PayU")) {

                No3_0.setChecked(true);

            }
            if (wallet.equalsIgnoreCase("PayTM")) {

                No3_1.setChecked(true);

            }
            if (wallet.equalsIgnoreCase("Mobikwik")) {

                No3_2.setChecked(true);

            }
            if (wallet.equalsIgnoreCase("Any Other")) {

                No3_3.setChecked(true);

            }
        }

        Log.d("credit card", String.valueOf(session.getCreditCardsSalery()));
        if (!session.getCreditCardsSalery().equalsIgnoreCase("")) {
            if (session.getCreditCardsSalery().equalsIgnoreCase("Yes")) {
                cb5.setChecked(true);
            } else {
                cb6.setChecked(true);
            }
        }

        Log.d("House loan", String.valueOf(session.getHousingLoanSalery()));
        if (!session.getHousingLoanSalery().equalsIgnoreCase("")) {
            if (session.getHousingLoanSalery().equalsIgnoreCase("Yes")) {
                cb3.setChecked(true);
            } else {
                cb4.setChecked(true);
            }
        }

        Log.d("Car Loan", String.valueOf(session.getCarLoanSalery()));
        if (!session.getCarLoanSalery().equalsIgnoreCase("")) {
            if (session.getCarLoanSalery().equalsIgnoreCase("Yes")) {
                cb1.setChecked(true);
            } else {
                cb2.setChecked(true);
            }
        }

        Log.d("Priducts", String.valueOf(session.getProductDetailsSalery()));
        product = session.getProductDetailsSalery();

        if (!session.getProductDetailsSalery().equalsIgnoreCase("")) {
            if (product.equalsIgnoreCase("2Wheeler")) {

                Yes37.setChecked(true);

            } else if (product.equalsIgnoreCase("Car")) {

                Yes38.setChecked(true);

            } else if (product.equalsIgnoreCase("House")) {

                Yes39.setChecked(true);

            } else if (product.equalsIgnoreCase("Household Appliance(like TV/Fridge/AC)")) {

                Yes40.setChecked(true);

            }
        }

        Log.d("Home Tution", String.valueOf(session.getHomeTutionSalery()));
        String[] value66 = {session.getHomeTutionSalery()};
        if (!session.getHomeTutionSalery().equalsIgnoreCase("")) {
            adapter5 = new ArrayAdapter<String>(Salariednew.this, android.R.layout.simple_spinner_item, value66);
            adapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            sp_hometution.setAdapter(adapter5);
        }

        Log.d("Health Insurance", String.valueOf(session.getHealthInsuranceSalery()));
        if (!session.getHealthInsuranceSalery().equalsIgnoreCase("")) {
            if (session.getHealthInsuranceSalery().equalsIgnoreCase("Yes")) {
                Ye29.setChecked(true);
            } else {
                No32.setChecked(true);
            }
        }

        Log.d("Mutual Found", String.valueOf(session.getMutualFundSalery()));
        if (!session.getMutualFundSalery().equalsIgnoreCase("")) {
            if (session.getMutualFundSalery().equalsIgnoreCase("Yes")) {
                Ye30.setChecked(true);
            } else {
                No33.setChecked(true);
            }
        }

        Log.d("Online Course", String.valueOf(session.getOnlineCourseSalery()));
        if (!session.getOnlineCourseSalery().equalsIgnoreCase("")) {
            if (session.getOnlineCourseSalery().equalsIgnoreCase("Yes")) {
                Yes36.setChecked(true);
            } else {
                No37.setChecked(true);
            }
        }

        Log.d("No of going to school", String.valueOf(session.getNoOfSchoolGoingChildrenSalery()));
        if (!session.getNoOfSchoolGoingChildrenSalery().equalsIgnoreCase("")) {
            ac_school.setText(session.getNoOfSchoolGoingChildrenSalery());
        }


        Log.d("Bank id", String.valueOf(session.getBankDetailsIdSalery()));
        if (!session.getBankDetailsIdSalery().equalsIgnoreCase("")) {
            for (int i = 0; i < bankMainModels.size(); i++) {
                if (session.getBankDetailsIdSalery().equalsIgnoreCase(bankMainModels.get(i).getId())) {
                    sp_MainBanker1.setSelection(i);
                }
            }
        }

        Log.d("Designation", String.valueOf(session.getBankDetailsIdSalery()));
        if (!session.getBankDetailsIdSalery().equalsIgnoreCase("")) {
            for (int i = 0; i < Designation_MainModels.size(); i++) {
                if (session.getBankDetailsIdSalery().equalsIgnoreCase(Designation_MainModels.get(i).getDesignationName_id())) {
                    sp_Designation.setSelection(i);
                }
            }
        }


    }

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(isChecked) {
                if(buttonView==Yes2) {
                    showTextNotification("Shopping");
                    receivepaid = "Shopping";//String.valueOf(Yes2);
                }

                if(buttonView==No4) {
                    showTextNotification("Travel Booking");
                    receivepaid = "Travel Booking";//String.valueOf(No4);;
                }

                if(buttonView==No5) {
                    showTextNotification("Hotel Booking");
                    receivepaid = "Hotel Booking";//String.valueOf(No5);
                }

                if(buttonView==No6) {
                    showTextNotification("Food Ordering");
                    receivepaid = "Food Ordering";//String.valueOf(No6);
                }

                if(buttonView==No7) {
                    showTextNotification("Movie Ticketing");
                    receivepaid = "Movie Ticketing";//String.valueOf(No7);
                }

                if(buttonView==No8) {
                    showTextNotification("Bill Payement");
                    receivepaid = "Bill Payement";//String.valueOf(No8);
                }

                if(buttonView==No9) {
                    showTextNotification("Taxi Booking");
                    receivepaid = "Taxi Booking";//String.valueOf(No9);
                }

                if(buttonView==No10) {
                    showTextNotification("Groceries");
                    receivepaid = "Groceries";//String.valueOf(No10);
                }
            }
        }
    }

    public void showTextNotification(String msgToDisplay) {
//        Toast.makeText(this, msgToDisplay, Toast.LENGTH_SHORT).show();
    }

    class myCheckBoxChnageClicker1 implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(isChecked) {
                if(buttonView==Ye9) {
                    showTextNotification("Amazon");
                    carsites = "Amazon";//String.valueOf(Ye9);
                }

                if(buttonView==No0) {
                    showTextNotification("Flipkart");
                    carsites = "Flipkart";//String.valueOf(No0);
                }

                if(buttonView==Ye10) {
                    showTextNotification("Yatra");
                    carsites = "Yatra";//String.valueOf(Ye10);
                }

                if(buttonView==Ye11) {
                    showTextNotification("MakeMyTrip");
                    carsites = "MakeMyTrip";//String.valueOf(Ye11);
                }

                if(buttonView==No11) {
                    showTextNotification("Book My Show");
                    carsites = "Book My Show";//String.valueOf(No11);
                }

                if(buttonView==No12) {
                    showTextNotification("Zomato");
                    carsites = "Zomato";//String.valueOf(No12);
                }

                if(buttonView==No24) {
                    showTextNotification("Swiggy");
                    carsites = "Swiggy";//String.valueOf(No24);
                }

                if(buttonView==No25) {
                    showTextNotification("Paytm");
                    carsites = "Paytm";//String.valueOf(No25);
                }

                if(buttonView==No26) {
                    showTextNotification("Ola");
                    carsites = "Ola";//String.valueOf(No26);
                }

                if(buttonView==Ye25) {
                    showTextNotification("Uber");
                    carsites = "Uber";//String.valueOf(Ye25);
                }
            }
        }
    }

    class myCheckBoxChnageClicker2 implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(isChecked) {
                if(buttonView==No3_0) {
                    showTextNotification("PayU");
                    wallet = "PayU";//String.valueOf(No3_0);
                }

                if(buttonView==No3_1) {
                    showTextNotification("PayTM");
                    wallet = "PayTM";//String.valueOf(No3_1);
                }

                if(buttonView==No3_2) {
                    showTextNotification("Mobikwik");
                    wallet = "Mobikwik";//String.valueOf(No3_2);
                }

                if(buttonView==No3_3) {
                    showTextNotification("Any Other");
                    wallet = "Any Other";//String.valueOf(No3_3);
                }

            }
        }
    }

    class myCheckBoxChnageClicker3 implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(isChecked) {
                if(buttonView==Yes37) {
                    showTextNotification("2Wheeler");
                    product = "2Wheeler";//String.valueOf(Yes37);
                }

                if(buttonView==Yes38) {
                    showTextNotification("Car");
                    product = "Car";//String.valueOf(Yes38);
                }

                if(buttonView==Yes39) {
                    showTextNotification("House");
                    product = "House";//String.valueOf(Yes39);
                }

                if(buttonView==Yes40) {
                    showTextNotification("Household Appliance(like TV/Fridge/AC)");
                    product = "Household Appliance(like TV/Fridge/AC)";//String.valueOf(Yes40);
                }

            }
        }
    }

//    public void onCheckboxClicked(View view) {
//
//        boolean checked = ((CheckBox) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.Yes2:
//                // do operations specific to this selection
////                receivepaid = "Shopping";
//                list.add(String.valueOf(Yes2));
//                break;
//            case R.id.No4:
//                // do operations specific to this selection
////                receivepaid = "Travel Booking";
//                list.add(String.valueOf(No4));
//                break;
//            case R.id.No5:
//                // do operations specific to this selection
////                receivepaid = "Hotel Booking";
//                list.add(String.valueOf(No5));
//                break;
//            case R.id.No6:
//                // do operations specific to this selection
////                receivepaid = "Food Ordering";
//                list.add(String.valueOf(No6));
//                break;
//            case R.id.No7:
//                // do operations specific to this selection
////                receivepaid = "Movie Ticketing";
//                list.add(String.valueOf(No7));
//                break;
//            case R.id.No8:
//                // do operations specific to this selection
////                receivepaid = "Bill Payement";
//                list.add(String.valueOf(No8));
//                break;
//            case R.id.No9:
//                // do operations specific to this selection
////                receivepaid = "Taxi Booking";
//                list.add(String.valueOf(No9));
//                break;
//            case R.id.No10:
//                // do operations specific to this selection
////                receivepaid = "Groceries";
//                list.add(String.valueOf(No10));
//                break;
//
//        }
//    }

    private void userLogin() {

        String homeString = tv_home.getText().toString().trim();
//      String homeTution = ac_homet.getText().toString().trim();
        String school = ac_school.getText().toString().trim();
        if (homeString.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Home Pin Code");
            homeString="";
        } //else
         if (HomeTution.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please select Home Tution");
        } //else
         if (school.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter School Going Children");
             school="";
        }
        //else
        if (age1.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Designation");
        }
        //else
        if (twowheller.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Two Wheeler Owned");
        }
        //else
        if (age.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Age");
        }
        //else
        if (carOwned.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Car Owned or Not");
        }
        //else
        if (bankId.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Bank");
        }
        //else
        if (receivepaid.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Online Tranaction");
        }
        //else
        if (carsites.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Car Sites");
        }
        //else
        if (wallet.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Wallet");
        }
        //else
        if (credit.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Credit Car");
        }
        //else
        if (houseL.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter House Loan");
        }
        //else
        if (carL.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Car Loan");
        }
        //else
        if (health.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Health Insurance");
        }
        //else
        if (mutualF.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Mutual Fund");
        }
        //else
        if (onlineCourse.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Online Course");
        }
        //else
        if (product.equalsIgnoreCase("")) {
            Utils.makeDialog(Salariednew.this, "Please enter Product Detail");
        }
        //else{
            //http://139.59.18.198:6060/SkillWorldAPI/getSalaryListAPI?customerid=1&designationid=2
            // &homepincode=110334&twoWheelerOwned=Yes&carOwned=No&description=Brand&bankdetailsid=5
            // &transcations=Travel Booking&ecommercesites=Amazon&wallets=paytm&creditcards=Yes
            // &housingLoan=Yes&carLoan=Yes&healthinsurance=Yes&mutualfund=Yes&noOfSchoolGoingChildren=2
            // &hometution=Yes&onlinecourse=Yes&productdetails=Car

            String url = Utils.BASE_URL + Utils.EDIT
                    + "?customerid=1"
//                    + "&category_detail=" + category
                    + "&designationid=" + age1
                    + "&homepincode=" + homeString
                    + "&twoWheelerOwned=" + twowheller
                    + "&description=" + age
                    + "&carOwned=" + carOwned
                    + "&bankdetailsid=" + bankId
                    + "&transcations=" + receivepaid
                    + "&ecommercesites=" + carsites
                    + "&wallets=" + wallet
                    + "&creditcards=" + credit
                    + "&housingLoan=" + houseL
                    + "&carLoan=" + carL
                    + "&healthinsurance=" + health
                    + "&mutualfund=" + mutualF
                    + "&noOfSchoolGoingChildren=" + school
                    + "&hometution=" + HomeTution
                    + "&onlinecourse=" + onlineCourse
                    + "&productdetails=" + product;
            Log.wtf("ankit", url);

            CallService service = new CallService(Salariednew.this, url, Utils.GET, true, new CallService.OnServicecall() {
                @Override
                public void onServicecall(String response) {
                    Log.wtf("tanyaBhanuResponse", response);

//                    http://139.59.18.198:6060/SkillWorldAPI/getSalaryListAPI?customerid=1&designationid=Manager&homepincode=342010&twoWheelerOwned=No&description=20-30&carOwned=No&bankdetailsid=4&transcations=Shoppingecommercesites=Amazonwallets=creditcards=NohousingLoan=NocarLoan=Nohealthinsurance=Nomutualfund=NonoOfSchoolGoingChildren=nohometution=Noonlinecourse=Noproductdetails=Car
//                            {"result":"Success.","userid":"187","userinfo":{"firstname":"Hxhhx","mobilenumber":"8383838386","userstatus":"Active","middlename":"","userid":"187","pannumber":"FUFJFU4556","lastname":"Vzhxhx","dob":"0096-01-23 00:00:00.0","userimage":"1485170233590282.jpg","authority":"BRAND_ASSOCIATE","location":"","aadharnumber":"868386838833","fullname":"Hxhhx Vzhxhx","usercode":"AAA050","email":"ufufuf@ccufu.com","username":"Xgxhxu"},"responseCode":"2001"}

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String Success = jsonObject.getString("result");

                                String userinfo = jsonObject.getString("categoryinfo");

                                JSONObject categoryinfo = new JSONObject(userinfo);


//                                JSONObject jobj = new JSONObject(response);
//                        String responseCode = jobj.getString("responseCode");
//                        if (responseCode.equalsIgnoreCase("3001"))

//                                JSONObject categoryinfo = jobj.getJSONObject("categoryinfo");

                                Salaryed_model sal_model = new Salaryed_model();
                                {

//           {"result":"CustomerDetails saved successfully whose category is salaried.",
//                   "categoryinfo":{"healthInsurance":"No","homeTution":"Yes","designation_name":"Asst\/ Dy Mgr",
//                   "transcations":"Movie Ticketing","designationId":"1","bankDetailsId":"3","description":"20-30",
//                   "twoWheelerOwned":"No","wallets":"PayTM","homePinCode":"342006","housingLoan":"No",
//                   "housingLoan":"Car","ecommerceSites":"Zomato","noOfSchoolGoingChildren":"Yes","creditCards":"No",
//                   "customerid":"1","bank_name":"Yes Bank","onlineCourse":"No","carOwned":"No","carLoan":"No",
//                   "mutualFund":"No"},"responseCode":"2001"}

                                    String healthInsurance = categoryinfo.getString("healthInsurance");
                                    String homeTution = categoryinfo.getString("homeTution");
                                    String designation_name = categoryinfo.getString("designation_name");
                                    String transcations = categoryinfo.getString("transcations");
                                    String designationId = categoryinfo.getString("designationId");
                                    String bankDetailsId = categoryinfo.getString("bankDetailsId");
                                    String description = categoryinfo.getString("description");
                                    String twoWheelerOwned = categoryinfo.getString("twoWheelerOwned");
                                    String wallets = categoryinfo.getString("wallets");
                                    String homePinCode = categoryinfo.getString("homePinCode");
                                    String housingLoan = categoryinfo.getString("housingLoan");
                                    String productDetails = categoryinfo.getString("productDetails");
                                    String ecommerceSites = categoryinfo.getString("ecommerceSites");
                                    String noOfSchoolGoingChildren = categoryinfo.getString("noOfSchoolGoingChildren");
                                    String creditCards = categoryinfo.getString("creditCards");
                                    String customerid = categoryinfo.getString("customerid");
                                    String bank_name = categoryinfo.getString("bank_name");
                                    String onlineCourse = categoryinfo.getString("onlineCourse");
                                    String carOwned = categoryinfo.getString("carOwned");
                                    String carLoan = categoryinfo.getString("carLoan");
                                    String mutualFund = categoryinfo.getString("mutualFund");


                                    sal_model.setHealthInsurance(healthInsurance);
                                    sal_model.setHomeTution(homeTution);
                                    sal_model.setTranscations(transcations);
                                    sal_model.setDescription(description);
                                    sal_model.setBankDetailsId(bankDetailsId);
                                    sal_model.setTwoWheelerOwned(twoWheelerOwned);
                                    sal_model.setWallets(wallets);
                                    sal_model.setHomePinCode(homePinCode);
                                    sal_model.setHousingLoan(housingLoan);
                                    sal_model.setProductDetails(productDetails);
                                    sal_model.setEcommerceSites(ecommerceSites);
                                    sal_model.setNoOfSchoolGoingChildren(noOfSchoolGoingChildren);
                                    sal_model.setCreditCards(creditCards);
                                    sal_model.setOnlineCourse(onlineCourse);
                                    sal_model.setCarOwned(carOwned);
                                    sal_model.setCarLoan(carLoan);
                                    sal_model.setMutualFund(mutualFund);

                                    session.setHealthInsuranceSalery(healthInsurance);
                                    session.setHomeTutionSalery(homeTution);
                                    session.setTranscationsSalery(transcations);
                                    session.setDescriptionSalery(description);
                                    session.setBank_nameSalery(bank_name);
                                    session.setTwoWheelerOwnedSalery(twoWheelerOwned);
                                    session.setWalletsSalery(wallets);
                                    session.setHomePinCodeSalery(homePinCode);
                                    session.setHousingLoanSalery(housingLoan);
                                    session.setProductDetailsSalery(productDetails);
                                    session.setEcommerceSitesSalery(ecommerceSites);
                                    session.setNoOfSchoolGoingChildrenSalery(noOfSchoolGoingChildren);
                                    session.setCreditCardsSalery(creditCards);
                                    session.setOnlineCourseSalery(onlineCourse);
                                    session.setCarOwnedSalery(carOwned);
                                    session.setCarLoanSalery(carLoan);
                                    session.setMutualFundSalery(mutualFund);
                                    session.setBankDetailsIdSalery(bankDetailsId);


                                    tv_home.setText(sal_model.getHomePinCode());

                                    if(sal_model.getTwoWheelerOwned().equalsIgnoreCase("Yes")){
                                        cb7.setChecked(true);
                                    } else {
                                        cb8.setChecked(true);
                                    }

                                    if(sal_model.getCarOwned().equalsIgnoreCase("Yes")){
                                        cb9.setChecked(true);
                                    } else {
                                        cb10.setChecked(true);
                                    }

                                    receivepaid = sal_model.getTranscations();
                                    carsites = sal_model.getEcommerceSites();
                                    wallet = sal_model.getWallets();


                                    if(sal_model.getCreditCards().equalsIgnoreCase("Yes")){
                                        cb5.setChecked(true);
                                    } else {
                                        cb6.setChecked(true);
                                    }

                                    if(sal_model.getHousingLoan().equalsIgnoreCase("Yes")){
                                        cb3.setChecked(true);
                                    } else {
                                        cb4.setChecked(true);
                                    }

                                    if(sal_model.getCarLoan().equalsIgnoreCase("Yes")){
                                        cb1.setChecked(true);
                                    } else {
                                        cb2.setChecked(true);
                                    }

                                    product = sal_model.getProductDetails();

                                    String[] value6 = {sal_model.getHomeTution()};
                                    adapter5 = new ArrayAdapter<String>(Salariednew.this, android.R.layout.simple_spinner_item, value6);
                                    adapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                    sp_hometution.setAdapter(adapter5);


                                    if(sal_model.getHealthInsurance().equalsIgnoreCase("Yes")){
                                        Ye29.setChecked(true);
                                    } else {
                                        No32.setChecked(true);
                                    }

                                    if(sal_model.getMutualFund().equalsIgnoreCase("Yes")){
                                        Ye30.setChecked(true);
                                    } else {
                                        No33.setChecked(true);
                                    }

                                    if(sal_model.getOnlineCourse().equalsIgnoreCase("Yes")){
                                        Yes36.setChecked(true);
                                    } else {
                                        No37.setChecked(true);
                                    }

                                    ac_school.setText(sal_model.getNoOfSchoolGoingChildren());



                                    for(int i=0;i<bankMainModels.size();i++){
                                        if(sal_model.getBankDetailsId().equalsIgnoreCase(bankMainModels.get(i).getId())){
                                            sp_MainBanker1.setSelection(i);
                                        }
                                    }


                                    for(int i=0;i<Designation_MainModels.size();i++){
                                        if(sal_model.getDesignationId().equalsIgnoreCase(Designation_MainModels.get(i).getDesignationName_id())){
                                            sp_Designation.setSelection(i);
                                        }
                                    }

                            new AlertDialog.Builder(Salariednew.this)
                                    .setTitle("Congrats ...")
                                    .setMessage("Congrats your CustomerDetails is now complete...")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            startActivity(new Intent(Salariednew.this, MainActivity.class));

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            service.execute();
        //}
    }
    public void onClick(View v) {

        userLogin();
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
        CallService service = new CallService(Salariednew.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("bankDetailsList");

                    BankModel zero_model = new BankModel();
                    zero_model.setId("");
                    zero_model.setBankname("Select ...");
                    zero_model.setDescription("Description ...");
                    bankMainModels.add(zero_model);
                    JSONArray jarr2 = sortJsonArray(jarr);
                    for (int i = 0; i < jarr2.length(); i++) {
                        JSONObject innerJobj = jarr2.getJSONObject(i);
                        String bankname = innerJobj.getString("bankname");
                        String description = innerJobj.getString("description");
                        String id = innerJobj.getString("id");

                        BankModel model = new BankModel();
                        model.setId(id);
                        model.setBankname(bankname);
                        model.setDescription(description);
                        bankMainModels.add(model);
                    }

                    BankAdapter adapter = new BankAdapter(Salariednew.this, 0, bankMainModels, getResources());
                    sp_MainBanker1.setAdapter(adapter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }

    private void initBankDetail11() {

        String url = Utils.BASE_URL + "getDesignationLIstAPI";
        CallService service = new CallService(Salariednew.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("designationList");

                    Designation_model zero_model = new Designation_model();
                    zero_model.setDesignationName_id("");
                    zero_model.setDesignationName("Select ...");
                    Designation_MainModels.add(zero_model);

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject innerJobj = jarr.getJSONObject(i);
                        String designationName = innerJobj.getString("designationName");
                        String id = innerJobj.getString("id");

                        Designation_model model = new Designation_model();
                        model.setDesignationName_id(id);
                        model.setDesignationName(designationName);
                        Designation_MainModels.add(model);
                    }

                    SalAdapter adapter = new SalAdapter(Salariednew.this, 0, Designation_MainModels, getResources());
                    sp_Designation.setAdapter(adapter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }
}
