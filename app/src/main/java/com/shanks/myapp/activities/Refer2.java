package com.shanks.myapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.DealerNameAdapter;
import com.shanks.myapp.adapter.OfferCityAdapter;
import com.shanks.myapp.adapter.PlanToBuyAdapter;
import com.shanks.myapp.mailer.GMailSender;
import com.shanks.myapp.models.CityModel;
import com.shanks.myapp.models.DealerNameModel;
import com.shanks.myapp.models.PlanToBuyModel;
import com.shanks.myapp.models.SmsEmailModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fg on 4/29/2017.
 */

public class Refer2 extends AppCompatActivity {
    Button submit;
    AutoCompleteTextView name, phonenumber, email, buy_location;
    Spinner dealername, planbuy;
    String nameSting = "", phonenumberString = "", emailString = "", planbuyString = "", buy_locationString = "";
    ArrayList<CityModel> cityArray = new ArrayList<>();
    ArrayList<DealerNameModel> DealerNameModels = new ArrayList<>();
    String cityId = "";
    String dealerId = "";
    Session session;
    String offerid = "";
    String yes = "YES";
    String no = "NO";
    String approvalprocess ;
    SmsEmailModel smsEmailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        synchronized (this) {
            init();
        }
        getSmsAndEmailContent(offerid);
        getAllCities();
    }

//    SEND_EMAIL_SMS

    private void getSmsAndEmailContent(String offerid){
        String url = Utils.BASE_URL + Utils.SEND_EMAIL_SMS
                + "?offerid="+offerid
                + "&communicateuser=y";
        CallService service = new CallService(Refer2.this, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{
                    PARSE_SMS_DATA(response);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }

    private void PARSE_SMS_DATA(String response) throws Exception{
        Log.d("ankit",response);
        JSONObject jobj = new JSONObject(response);
        String responseCode = jobj.getString("responseCode");
        if(responseCode.equalsIgnoreCase("2001")){
            JSONObject userinfo = jobj.getJSONObject("userinfo");
            smsEmailModel = new SmsEmailModel();

            String subject = userinfo.getString("subject");
            smsEmailModel.setEmail_subject(subject);

            String offerid = userinfo.getString("offerid");

            String smsContent = userinfo.getString("smsContent");
            smsEmailModel.setSmsContent(smsContent);

            String content = userinfo.getString("content");
            smsEmailModel.setEmail_content(content);
        }
    }

    private void init() {

        offerid = getIntent().getExtras().getString("offerid");
        session = Session.getSession(Refer2.this);
        name = (AutoCompleteTextView) findViewById(R.id.name);
        phonenumber = (AutoCompleteTextView) findViewById(R.id.phonenumber);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        planbuy = (Spinner) findViewById(R.id.planbuy);
        initPlanToBuy();
        buy_location = (AutoCompleteTextView) findViewById(R.id.buy_location);
        dealername = (Spinner) findViewById(R.id.dealername);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    nameSting = name.getText().toString().trim();
                    phonenumberString = phonenumber.getText().toString().trim();
                    emailString = email.getText().toString().trim();
                    //planbuyString = planbuy.getText().toString().trim();
                    buy_locationString = buy_location.getText().toString().trim();
//                dealernameString = dealername.getText().toString().trim();
                    if (emailString.equalsIgnoreCase("")) {
                        showToast("Please enter email");
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                        showToast("Please enter valid email id");
                    } else if (nameSting.equalsIgnoreCase("")) {
                        showToast("Please enter name");
                    } else if (phonenumberString.equalsIgnoreCase("")) {
                        showToast("Please enter phone number");
                    } else if (phonenumberString.length() < 10) {
                        showToast("Phone number should be more then 10 digits");
                    } else if (planbuyString.equalsIgnoreCase("")) {
                        showToast("Please enter plan to buy");
                    } else if (cityId.equalsIgnoreCase("")) {
                        showToast("Please select city");
                    } else if (dealerId.equalsIgnoreCase("")) {
                        showToast("Please select dealer");
                    } else {
                        saveLeads(nameSting, phonenumberString, emailString, planbuyString, cityId, dealerId);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showToast("Something went wrong please try again later");
                }


//                if (mDealId.containsKey(dealername.getText().toString())) {
//                    str = "http://" + Utils.HOSTNAME_MAIN_URL + "saveLead?userid=" + userid
//                            + "&offerid=" + OfferId
//                            + "&buyername=" + name.getText().toString().replaceAll(" ", "")
//                            + "&email=" + mEmailView.getText().toString().replaceAll(" ", "")
//                            + "&phonenumber=" + phonenumber.getText().toString().replaceAll(" ", "")
//                            + "&plantobuyon=" + item1.replaceAll(" ", "%20")
//                            + "&dealerprefer=" + dealerpref
//                            + "&dealerid=" + mDealId.get(dealername.getText().toString()) + "&buyerlocation=" + item2;
//                    Log.d(TAG, "doInBackground: "+str);
//                } else {
//                    str = "http://" + Utils.HOSTNAME_MAIN_URL + "saveLead?userid=" + userid + "&offerid=" + OfferId + "&buyername=" + name.getText().toString().replaceAll(" ", "") + "&email=" + mEmailView.getText().toString().replaceAll(" ", "") + "&phonenumber=" + phonenumber.getText().toString().replaceAll(" ", "") + "&plantobuyon=" + item1.replaceAll(" ", "%20") + "&dealerprefer=" + dealerpref + "&dealername=" + et_dealerName.getText().toString().replaceAll(" ", "") + "&buyerlocation=" + item2;
//                    Log.d(TAG, "doInBackground: "+str);
//                }


            }
        });
    }
    private void saveLeads(String nameSting, String phonenumberString, final String emailString, String planbuyString,
                           String buy_locationString, String dealerId) {
        String str = Utils.BASE_URL + Utils.SAVE_LEADS
                + "?userid=" + session.getUserId()
                + "&offerid=" + offerid
                + "&buyername=" + nameSting
                + "&email=" + emailString
                + "&phonenumber=" + phonenumberString
                + "&plantobuyon=" + planbuyString
                + "&dealerprefer=yes"
                + "&approvalprocess=No"
                + "&dealername=Honda"
                + "&dealerid=" + dealerId
                + "&buyerlocation=" + buy_locationString;
        Log.d("ankit", str);

        CallService service = new CallService(Refer2.this, str, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                if (approvalprocess.equals("No"))

                    Log.d("ankit", "::::" + response);
                //{"result":"Success.","leadid":"50","responseCode":"2001"}

                try {
                    JSONObject jobj = new JSONObject(response);
                    String responseCode = jobj.getString("responseCode");
                    if (responseCode.equalsIgnoreCase("2001")) {
                        new AlertDialog.Builder(Refer2.this)
                                .setTitle("Congrats ...")
                                .setMessage("Congrats your lead is now generated...")
                                .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(smsEmailModel!=null && !smsEmailModel.getSmsContent().equalsIgnoreCase("")){
                                            try{
                                                sendSms(smsEmailModel.getSmsContent());

                                            }catch (Exception ex){
                                                ex.printStackTrace();
                                            }
                                        } else {
                                            sendToHome();
                                        }
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        Toast.makeText(Refer2.this, "Lead not generated please try again", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        service.execute();

//        sendToHome();

    }

    private void sendToHome() {
        Intent intent = new Intent(Refer2.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(Refer2.this, message, Toast.LENGTH_LONG).show();
    }

    private void getAllCities() {
        String url = Utils.BASE_URL + Utils.GET_ALL_CITIES;
        CallService service = new CallService(Refer2.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    PARSE_JSON(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }

    private void PARSE_JSON(String response) throws Exception {
        JSONObject jobj = new JSONObject(response);
        JSONArray cityList = jobj.getJSONArray("cityList");

        for (int i = 0; i < cityList.length(); i++) {
            JSONObject innerJob = cityList.getJSONObject(i);
            CityModel model = new CityModel();
            String name = innerJob.getString("name");
            model.setName(name);
            String id = innerJob.getString("id");
            model.setId(id);
            cityArray.add(model);
        }
        //spinner_rows
        OfferCityAdapter adapter = new OfferCityAdapter(Refer2.this, R.layout.spinner_rows, cityArray);
        buy_location.setAdapter(adapter);
        buy_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cityId = cityArray.get(i).getId();
                getDealerList(cityId);
            }
        });
    }

    private void getDealerList(String city_id) {
//        GET_DEALER_LIST
//        String url = Utils.BASE_URL+Utils.GET_DEALER_LIST+"?preferredLocation="+city_id;
        String url = Utils.BASE_URL + Utils.GET_DEALER_LIST;
        CallService service = new CallService(Refer2.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit", "::::" + response);
                try {
                    PARSE_DEALER_JSON(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }

    private void PARSE_DEALER_JSON(String response) throws Exception {
        JSONObject jobj = new JSONObject(response);
        JSONArray dealerList = jobj.getJSONArray("dealerList");
        try {
            DealerNameModels.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        DealerNameModel zero_model = new DealerNameModel();
        zero_model.setId("0");
        zero_model.setDealername("Select ...");
        DealerNameModels.add(zero_model);

        for (int i = 0; i < dealerList.length(); i++) {
            DealerNameModel model = new DealerNameModel();
            JSONObject innerobj = dealerList.getJSONObject(i);

            String dealername = innerobj.getString("dealername");
            String id = innerobj.getString("id");
            String username = innerobj.getString("username");

            model.setDealername(dealername);
            model.setId(id);
            model.setUsername(username);

            DealerNameModels.add(model);
        }
        DealerNameAdapter adapter = new DealerNameAdapter(Refer2.this, 0, DealerNameModels, getResources());
        dealername.setAdapter(adapter);
        dealername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dealerId = DealerNameModels.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dealerId = "";
            }
        });
    }

    private void initPlanToBuy() {
//        planbuy
        final ArrayList<PlanToBuyModel> planToBuyModels = new ArrayList<>();
        {
            PlanToBuyModel model = new PlanToBuyModel();
            model.setName("Select ...");
            planToBuyModels.add(model);
        }
        {
            PlanToBuyModel model = new PlanToBuyModel();
            model.setName("Immediately");
            planToBuyModels.add(model);
        }
        {
            PlanToBuyModel model = new PlanToBuyModel();
            model.setName("Within a Week");
            planToBuyModels.add(model);
        }
        {
            PlanToBuyModel model = new PlanToBuyModel();
            model.setName("Within a Month");
            planToBuyModels.add(model);
        }
        {
            PlanToBuyModel model = new PlanToBuyModel();
            model.setName("More than a Month");
            planToBuyModels.add(model);
        }
        PlanToBuyAdapter adapter = new PlanToBuyAdapter(Refer2.this, 0, planToBuyModels, getResources());
        planbuy.setAdapter(adapter);
        planbuy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    planbuyString = planToBuyModels.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                planbuyString = "";
            }
        });
    }
    private void sendSms(final String smsContent) {
        String url1 = "http://trans.msgadvert.com/api/pushsms.php?" +
                "usr=921147" +
                "&key=010fesQ50oRlZqug2ZpIU2hoVqrLsu" +
                "&sndr=SKILWD" +
                "&ph=91" + phonenumber.getText().toString() +
                "&text="+smsContent+
                "&rpt=1";
        //Log.d("ankit", url1);
        CallService service = new CallService(Refer2.this, url1, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {

                if(smsEmailModel!=null && !smsEmailModel.getEmail_content().equalsIgnoreCase("") && !smsEmailModel.getEmail_subject().equalsIgnoreCase("")){
                    try{
                        sendEmail(emailString,smsEmailModel.getEmail_subject(),smsEmailModel.getEmail_content());

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                } else {
                    sendToHome();
                }

//                try {
//                    String no = phonenumber.getText().toString();
//                    //String message = message.getText().toString();
//                    //String message=.getText().toString();
//                    Intent intent = new Intent(getApplicationContext(), Refer.class);
//                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//                    SmsManager sms = SmsManager.getDefault();
//                    sms.sendTextMessage(no, null, String.valueOf(message), pi, null);
//                    Toast.makeText(getApplicationContext(), "Message Sent successfully!",
//                            Toast.LENGTH_LONG).show();
//                } catch (Exception ex) {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS failed, please try again later!",
//                            Toast.LENGTH_LONG).show();
//                    ex.printStackTrace();
//                }
            }
        });
        service.execute();
    }

    private void sendEmail(final String emailString,final String emailSubject,final String emailContent){
        new AsyncTask<Void,Void,Void>(){
            ProgressDialog pd;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ProgressDialog.show(Refer2.this,"Loading...","Please wait");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                pd.dismiss();
                sendToHome();
                Log.d("ankit","Mail send");

            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    GMailSender sender = new GMailSender("welcometoskillworld@gmail.com", "skillworld12345");
                    sender.sendMail(emailSubject,
                            emailContent,
                            "welcometoskillworld@gmail.com",
                            emailString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

}

