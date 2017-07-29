package com.shanks.myapp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.activities.Edit;
import com.shanks.myapp.activities.MainActivity;
import com.shanks.myapp.activities.MyCustomer;
import com.shanks.myapp.activities.SetOnContactAdded;
import com.shanks.myapp.adapter.AccountTypeAdapter;
import com.shanks.myapp.adapter.BankAdapter;
import com.shanks.myapp.adapter.StateAdapter;
import com.shanks.myapp.models.BankAccountTypeModel;
import com.shanks.myapp.models.BankModel;
import com.shanks.myapp.models.ListModel;
import com.shanks.myapp.models.StateModel;
import com.shanks.myapp.registration_flow.Register;
import com.shanks.myapp.registration_flow.RegistrationBankDetails;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.getIntent;
import static com.shanks.myapp.R.id.list;
import static com.shanks.myapp.R.id.sp_accountType;
import static com.shanks.myapp.R.id.view;

public class FragmentAdd extends Fragment {

    Button bank_detail_submit;
    ArrayList<BankAccountTypeModel> mainModels = new ArrayList<>();
    ArrayList<BankModel> bankMainModels = new ArrayList<>();
    String accountTypeId = "";
    String bankId = "";
    Spinner spinner1;
    ArrayAdapter<String> adapter;
    Spinner state_id;
    ArrayList<StateModel> stateModel = new ArrayList<>();
    String stateID = "";
    Session session;
    String categories = "";
    AutoCompleteTextView acntno, branchname, ifsc;
    AutoCompleteTextView ac_name, ac_phonenumber, ac_email;
    ImageView arrow_left;
    View view;
    SetOnContactAdded setOnContactAdded;
    String acntnoString,branchnameString,ifscString;
    public Communicator Comm;
    Context context;

    public void setContactAdded(SetOnContactAdded setOnContactAdded){
        this.setOnContactAdded = setOnContactAdded;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Comm = (Communicator)context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_add, container, false);
        init();
        return view;
    }



    private void init() {
        session = Session.getSession(getActivity());
        ac_name = (AutoCompleteTextView) view.findViewById(R.id.ac_name);
        ac_phonenumber = (AutoCompleteTextView) view.findViewById(R.id.ac_phonenumber);
        ac_email = (AutoCompleteTextView) view.findViewById(R.id.ac_email);
        String[] values1 = {"Select...", "Business", "Salaried",};
        spinner1 = (Spinner) view.findViewById(R.id.sp_categor);
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);
        state_id = (Spinner) view.findViewById(R.id.sp_citi);
        initCity();
        state_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateID = stateModel.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 == 1) {
                    categories = "Business";
                } else if (arg2 == 2)
                    categories = "Salaried";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        bank_detail_submit = (Button) view.findViewById(R.id.submit1);
        bank_detail_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 acntnoString = ac_name.getText().toString().trim();
                 branchnameString = ac_phonenumber.getText().toString().trim();
                 ifscString = ac_email.getText().toString().trim();
                if (acntnoString.equalsIgnoreCase("")) {
                    Utils.makeDialog(getActivity(), "Please enter name ");
                } else if (branchnameString.equalsIgnoreCase("")) {
                    Utils.makeDialog(getActivity(), "Please enter mobilenumber");
                } else if (ifscString.equalsIgnoreCase("")) {
                    Utils.makeDialog(getActivity(), "Please enter email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(ifscString).matches()){
                    Utils.makeDialog(getActivity(),"Please enter valid email id");
                }
                else if(stateID.equalsIgnoreCase("")){
                    Utils.makeDialog(getActivity(),"Please select city");
                }
                  else if(categories.equalsIgnoreCase("")){
                    Utils.makeDialog(getActivity(),"Please select category");
                }
                else {

//                    aaddinfo();

                    String url = Utils.BASE_URL + Utils.TRANSACTION
                            + "?userid=" + session.getUserId()
                            + "&categories=" + categories
                            + "&name=" + acntnoString
                            + "&mobilenumber=" + branchnameString
                            + "&emailid=" + ifscString
                            + "&cityid=" + stateID;
                    Log.wtf("ankit", url);

                    CallService service = new CallService(getActivity(), url, Utils.GET, true, new CallService.OnServicecall() {
                        @Override
                        public void onServicecall(String response) {
                            Log.wtf("ankit", response);

                            try {
                                JSONObject jobj = new JSONObject(response);
                                String responseCode = jobj.getString("responseCode");
                                if (responseCode.equalsIgnoreCase("2001")) {
                                    Log.d("listResponse", response);
                                    Toast.makeText(getActivity(),"Contact Added Successfully",Toast.LENGTH_LONG).show();
                                    ac_email.setText("");
                                    ac_name.setText("");
                                    ac_phonenumber.setText("");
                                    spinner1.setSelection(0);
                                    state_id.setSelection(0);
                                    Comm.updateData("data");


                                    /*
                                    if(EditFragment.mHandler != null){
                                        EditFragment.mHandler.sendEmptyMessage(1);
                                    }

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Congrats ...")
                                            .setMessage("Record Added Successfully...")

                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //   setOnContactAdded.OnContactAdded();
                                                    ((MyCustomer) getActivity()).dashborad_pager.setCurrentItem(0);

                                                    // continue with delete
//                                                     startActivity(new Intent(getActivity(), EditFragment.class));

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                            */
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }


                    });

                    service.execute();

                }
            }
        });
    }
    private void PARSE_JSON1(String result) throws Exception{
        System.out.print("nik"+result);
        JSONObject jobj = new JSONObject(result);
        JSONArray honey = jobj.getJSONArray("customerList");
        if(( (MyCustomer) getActivity()).listModels == null){
            ( (MyCustomer) getActivity()).listModels = new ArrayList<>();
        }
        ( (MyCustomer) getActivity()).listModels.clear();
        for (int i=0;i<honey.length();i++){
            JSONObject innerJob = honey.getJSONObject(i);
            ListModel model = new ListModel();
            String name = innerJob.getString("name");
            model.setTv_name(name);
            String date = innerJob.getString("customerid");
            model.setTv_Date(date);
            String amount = innerJob.getString("mobileNumber");
            model.setTv_Amount(amount);
            String emailid = innerJob.getString("emailId");
            model.setModeofpay(emailid);


            // listModels.add(model);
            ( (MyCustomer) getActivity()).listModels.add(model);
        }
    }

    private void aaddinfo() {

        String url = Utils.BASE_URL + Utils.EDITCUSTOMER
                + "?customerid=1"
                + "&userid=" + session.getUserId()
//                + "&categories=" + categories
                + "&name=" + acntnoString
                + "&mobilenumber=" + branchnameString
                + "&emailid=" + ifscString
                + "&cityid=" + stateID
                + "&categories=" + categories;
        Log.d("CJ PRINT","ankita"+url);

        CallService service = new CallService(getActivity(), url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit EDITCUSTOMER", response);

                try {
                    JSONObject jobj = new JSONObject(response);
                    String responseCode = jobj.getString("responseCode");
                    if (responseCode.equalsIgnoreCase("2001")) {
                        Log.d("listResponse", response);

                        PARSE_JSON2(response);


                        /*
                        if(EditFragment.mHandler != null){
                            EditFragment.mHandler.sendEmptyMessage(1);
                        }

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Congrats ...")
                                .setMessage("Record Added Successfully...")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //   setOnContactAdded.OnContactAdded();
                                        ((MyCustomer) getActivity()).dashborad_pager.setCurrentItem(0);
//                                        startActivity(new Intent(getActivity(), MainActivity.class));

                                        // continue with delete
//                                         startActivity(new Intent(getActivity(), EditFragment.class));

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                           */
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


        });

        service.execute();

    }

    private void PARSE_JSON2(String result) throws Exception{
        System.out.print("nik"+result);
        JSONObject jobj = new JSONObject(result);
        JSONArray honey = jobj.getJSONArray("customerList");
        if(( (MyCustomer) getActivity()).listModels == null){
            ( (MyCustomer) getActivity()).listModels = new ArrayList<>();
        }
        ( (MyCustomer) getActivity()).listModels.clear();
        for (int i=0;i<honey.length();i++){
            JSONObject innerJob = honey.getJSONObject(i);
            ListModel model = new ListModel();
            String name = innerJob.getString("name");
            model.setTv_name(name);
            String date = innerJob.getString("customerid");
            model.setTv_Date(date);
            String amount = innerJob.getString("mobileNumber");
            model.setTv_Amount(amount);
            String emailid = innerJob.getString("emailId");
            model.setModeofpay(emailid);
            String categories = innerJob.getString("categories");
            model.setCategories(categories);

            Utils.CUSTOMER_ID = categories;
//            session.setCATEGORY(categories);


            // listModels.add(model);
            ( (MyCustomer) getActivity()).listModels.add(model);
        }
    }

    private void initCity() {
        //CallService service = new CallService(getActivity(), Utils.BASE_URL + Utils.COUNTRY_CITY_STATE + "?countryid=1", Utils.GET, false, new CallService.OnServicecall() {
//            @Override
//            public void onServicecall(String response) {
//
                CallService service = new CallService(getActivity(), Utils.BASE_URL + Utils.GET_ALL_CITIES , Utils.GET, false, new
                CallService.OnServicecall() {
                    @Override
                    public void onServicecall(String response) {
                        try {

                                JSONObject jobj = new JSONObject(response);
                                JSONArray jarr = jobj.getJSONArray("cityList");
                                StateModel zero_model = new StateModel();
                                zero_model.setId("");
                                zero_model.setName("Select ...");
                                stateModel.add(zero_model);

                                ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
                                String[] topList = {"Mumbai", "Delhi", "Chennai", "Kolkata", "Bangalore", "Hyderabad", "Pune", "Ahmedabad", "Jaipur", "Lucknow", "Kochi", "Indore"};

                                //listdata has all cities
                                for (int i = 0; i < jarr.length(); i++) {
                                    listdata.add(jarr.getJSONObject(i));
                                    //Log.d("CJ PRINT","LIST DATA - "+ listdata.get(i));
                                }

                                ArrayList<JSONObject> listdatafinal = new ArrayList<JSONObject>();
                                //removing top cities from listdata and adding those in listdatafinal
                                for (int i=0;i < topList.length; i++)
                                {
                                    for (int j = 0; j < listdata.size(); j++)
                                    {
                                        JSONObject innerJobj = listdata.get(j);
                                        String name = innerJobj.getString("name");
                                        if(name.equals(topList[i])){
                                            listdata.remove(j);
                                            listdatafinal.add(innerJobj);
                                            break;
                                        }
                                    }
                                }
                                //adding other cities apart from top cities in listdatafinal
                                for(int i=0;i<listdata.size();i++)
                                {
                                    JSONObject innerJobj = listdata.get(i);
                                    listdatafinal.add(innerJobj);
                                }

                                for(int i=0;i<listdatafinal.size();i++)
                                {
                                    JSONObject innerJobj = listdatafinal.get(i);
                                    String name = innerJobj.getString("name");
                                    String id = innerJobj.getString("id");
                                    StateModel model = new StateModel();
                                    model.setId(id);
                                    model.setName(name);
                                    stateModel.add(model);
                                }

                               /* for (int i = 0; i < jarr.length(); i++) {

                                    JSONObject innerJobj = jarr.getJSONObject(i);
                                    String name = innerJobj.getString("name");
                                    String id = innerJobj.getString("id");
                                    StateModel model = new StateModel();
                                    model.setId(id);
                                    model.setName(name);
                                    stateModel.add(model);
                                }*/
                                StateAdapter adapter = new StateAdapter(getActivity(), 0, stateModel, getResources());
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

    public static FragmentAdd newInstance() {
        return new FragmentAdd();
    }

    public interface Communicator {
        public void updateData(String data);
    }

}



