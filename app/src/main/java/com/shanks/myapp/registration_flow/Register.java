package com.shanks.myapp.registration_flow;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.CityAdapter;
import com.shanks.myapp.adapter.QualificationAdapter;
import com.shanks.myapp.adapter.StateAdapter;
import com.shanks.myapp.models.QualificationModel;
import com.shanks.myapp.models.Registeration_info_detail;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import com.shanks.myapp.models.CityModel;
import com.shanks.myapp.models.StateModel;
import com.shanks.myapp.utils.Session;

public class Register extends AppCompatActivity {

    Session session;

    Button register;
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 2;
    private String selectedImagePath;
    CircleImageView profile_image;
    // registration feilds
    AutoCompleteTextView firstname,lastname,dob,email,phonenumber,username,password,pan,aadhar,et_address ,et_pincode;
    Spinner education_qualifition,state_id,city_id;
    Spinner spinner1;
    String category = "";
    String stateID = "";
    String cityID = "";
    ArrayList<StateModel> stateModel = new ArrayList<>();
    RadioButton male,female;
    CheckBox accept_checkbox;
    String serverFilePath="none";
    String qualificationID = "0";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    ArrayList<QualificationModel> mainQualificationModel = new ArrayList<>();
    ArrayList<CityModel> cityModel = new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();
    TextView terms_conditions,privacy_policy;

    ImageView arrow_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register.this,LoginRegister.class));
        finish();
    }

    private void init(){

        session = Session.getSession(Register.this);

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profile_image = (CircleImageView)findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog();
            }
        });

        // registration init
        firstname = (AutoCompleteTextView)findViewById(R.id.firstname);
        lastname = (AutoCompleteTextView)findViewById(R.id.lastname);
        dob = (AutoCompleteTextView)findViewById(R.id.dob);

        // terms and privacy poliy
        terms_conditions = (TextView)findViewById(R.id.terms_conditions);
        terms_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://139.59.18.198/agreement.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        privacy_policy = (TextView)findViewById(R.id.privacy_policy);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://139.59.18.198/privacy.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);

                Calendar userAge = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(userAge)) {
                    Toast.makeText(Register.this,"Age should be more than 18 years",Toast.LENGTH_LONG).show();
                } else {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "yyyy/MM/dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    dob.setText(sdf.format(myCalendar.getTime()));
                }
            }

        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // populate data from server
        education_qualifition = (Spinner)findViewById(R.id.education_qualifition);
        initEducationQualification();
        education_qualifition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                qualificationID = mainQualificationModel.get(i).getQualificationID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                qualificationID = "";
            }
        });

        email = (AutoCompleteTextView)findViewById(R.id.email);
        phonenumber = (AutoCompleteTextView)findViewById(R.id.phonenumber);
        username = (AutoCompleteTextView)findViewById(R.id.username);
        password = (AutoCompleteTextView)findViewById(R.id.password);
        pan = (AutoCompleteTextView)findViewById(R.id.pan);
        aadhar = (AutoCompleteTextView)findViewById(R.id.aadhar);
        et_address = (AutoCompleteTextView)findViewById(R.id.et_address);
        et_pincode= (AutoCompleteTextView)findViewById(R.id.et_pincode);
        String[] values = {"Select...", "Student", "Working", "Housewife", "Freelancer", "Others"};
        spinner1 = (Spinner) findViewById(R.id.sp_Category);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 == 1) {
                    category = "Student";
                } else if (arg2 == 2)
                    category = "Working";
                else if (arg2 == 3)
                    category = "Housewife";
                else if (arg2 == 4)
                    category = "Freelancer";
                else if (arg2 == 5)
                    category = "Others";

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        state_id = (Spinner)findViewById(R.id.state_id);
//        initState();
        city_id = (Spinner)findViewById(R.id.city_id);
        city_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityID = cityModel.get(i).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        state_id = (Spinner)findViewById(R.id.sp_citi);
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
//        initState();
//        state_id = (Spinner) findViewById(R.id.sp_C);
//        state_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                stateID = stateModel.get(i).getId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });



        male = (RadioButton)findViewById(R.id.male);
        male.setChecked(true);
        female = (RadioButton)findViewById(R.id.female);

        accept_checkbox = (CheckBox)findViewById(R.id.checkbox);

        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Register.this,RegisterUpload.class));
//                finish();

                String firstnameString = firstname.getText().toString().trim();
                String lastnameString = lastname.getText().toString().trim();
                String dobString = dob.getText().toString().trim();
                String emailString = email.getText().toString().trim();
                String phonenumberString = phonenumber.getText().toString().trim();
                String usernameString = username.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
//                String panString = pan.getText().toString().trim();
//                String aadharString = aadhar.getText().toString().trim();
//                String et_addressString = et_address.getText().toString().trim();
//                String et_pincodeString = et_pincode.getText().toString().trim();
//                String genderString = "";
//                if(male.isChecked()){
//                    genderString = "male";
//                } else if(female.isChecked()){
//                    genderString = "female";
//                }
                if(serverFilePath.equalsIgnoreCase("none")){
                    Utils.makeDialog(Register.this,"Please enter image");
                } else if(firstnameString.equalsIgnoreCase("")){
                    Utils.makeDialog(Register.this,"Please enter first name");
                } else if(lastnameString.equalsIgnoreCase("")){
                    Utils.makeDialog(Register.this,"Please enter last name");
//                }
//                else if(dobString.equalsIgnoreCase("")) {
//                    Utils.makeDialog(Register.this, "Please enter date of birth");

//                } else if(qualificationID.equalsIgnoreCase("")){
//                    Utils.makeDialog(Register.this,"Please select qualification");
                } else if(emailString.equalsIgnoreCase("")) {
                    Utils.makeDialog(Register.this, "Please enter email id");

                } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
                    Utils.makeDialog(Register.this,"Please enter valid id");
                } else if(phonenumberString.equalsIgnoreCase("")){
                    Utils.makeDialog(Register.this,"Please enter phone number");
                } else if(phonenumberString.length()<10){
                    Utils.makeDialog(Register.this,"Phone number should be of 10 digits");
                }
////                else if(usernameString.equalsIgnoreCase("")){
////                    Utils.makeDialog(Register.this,"Please enter user name");
////                }
                else if(passwordString.equalsIgnoreCase("")){
                    Utils.makeDialog(Register.this,"Please enter password");

////                else if(panString.equalsIgnoreCase("")){
////                    Utils.makeDialog(Register.this,"Please enter pan card number");
////                } else if(panString.length()<10){
////                    Utils.makeDialog(Register.this,"Pan card number should be of 10 digits");
////                }
//                else if(aadharString.equalsIgnoreCase("")){
//                    Utils.makeDialog(Register.this,"Please enter adhar card number");
//                } else if(aadharString.length()<12){
//                    Utils.makeDialog(Register.this,"Adhar card number should be of 12 digits");
//                } else if(et_addressString.equalsIgnoreCase("")){
//                    Utils.makeDialog(Register.this,"Please enter address");
//                } else if(et_pincodeString.equalsIgnoreCase("")){
//                    Utils.makeDialog(Register.this,"Please enter address");
                } else if(category.equalsIgnoreCase("")){
                    Utils.makeDialog(Register.this,"Please select Category");
                } else if(stateID.equalsIgnoreCase("")){
                    Utils.makeDialog(Register.this,"Please select city");
                } else if(!accept_checkbox.isChecked()){
                    Utils.makeDialog(Register.this,"Please accept the terms and conditions");
                }
else {
                    String registerUrl = Utils.BASE_URL+Utils.REGISTER+
                            "?username=" + phonenumberString
                            + "&firstname=" + firstnameString
                            + "&lastname=" + lastnameString
                            + "&cityid=" + stateID
                            + "&category=" + category
                            + "&password=" + passwordString
                            + "&email=" + emailString
                            + "&mobilenumber=" + phonenumberString
                            + "&userimage=" + serverFilePath
                            + "&termaccept=1";

                    Log.d("ankit",registerUrl);

                    CallService service = new CallService(Register.this, registerUrl, Utils.GET, true, new CallService.OnServicecall() {
                        @Override
                        public void onServicecall(String response) {
                            try{

                                Log.d("ankitregisteration",response);
                                //{"result":"User already registered.","responseCode":"11004"}

                                JSONObject jobj = new JSONObject(response);

                                String success = jobj.getString("result");
                                if(success.equalsIgnoreCase("Success")){


                                Registeration_info_detail StudentDetailModel = new Registeration_info_detail();
                                JSONArray Studetail = jobj.getJSONArray("userinfo");
                                {
                                    JSONObject studentJson = Studetail.getJSONObject(0);


                                    String firstname = studentJson.getString("firstname");
                                    StudentDetailModel.setFirstname(firstname);

                                    String mobilenumber = studentJson.getString("mobilenumber");
                                    StudentDetailModel.setFirstname(mobilenumber);

                                    String userstatus = studentJson.getString("userstatus");
                                    StudentDetailModel.setUserstatus(userstatus);

                                    String authority = studentJson.getString("authority");
                                    StudentDetailModel.setAuthority(authority);

                                    String location = studentJson.getString("location");
                                    StudentDetailModel.setLocation(location);

                                    String fullname = studentJson.getString("fullname");
                                    StudentDetailModel.setFullname(fullname);

                                    String category = studentJson.getString("category");
                                    StudentDetailModel.setCategory(category);

                                    String userid = studentJson.getString("userid");
                                    StudentDetailModel.setUserid(userid);

                                    String email = studentJson.getString("email");
                                    StudentDetailModel.setEmail(email);

                                    String username = studentJson.getString("username");
                                    StudentDetailModel.setUsername(username);

                                    String lastname = studentJson.getString("lastname");
                                    StudentDetailModel.setLastname(lastname);

                                    Log.d("ankit","user name :"+username);
                                    Log.d("ankit","user mobilenumber :"+mobilenumber);

                                    String addressid = jobj.getString("addressid");
                                    StudentDetailModel.setAddressid(addressid);

                                    String responseCode = jobj.getString("responseCode");
                                    if(responseCode.equalsIgnoreCase("2001")) {
//                                        String userid = jobj.getString("userid");
                                        session.setUserId(userid);
                                        startActivity(new Intent(Register.this, Login.class));
                                        finish();
                                    }
                                }

                                String responseCode = jobj.getString("responseCode");
                                if(responseCode.equalsIgnoreCase("2001")) {
                                    String userid = jobj.getString("userid");
                                    session.setUserId(userid);
                                    startActivity(new Intent(Register.this, Login.class));
                                    finish();

                                    Log.d("ankit", "user id :" + userid);

//          {"result":"Success.","userinfo":{"firstname":"Dbbb","mobilenumber":"9696969699",
//                  "userstatus":"Active","authority":"BRAND_ASSOCIATE","location":"Gujarat"
//                  ,"fullname":"Dbbb Dbbb","usercode":"AAA027","category":"Exporter","userid":"407",
//                  "email":"dbbb@gmail.com","username":"9696969699","lastname":"Dbbb"},"addressid":"313","responseCode":"2001"}

                                }
                                } else {
                                    String result = jobj.getString("result");
                                    Toast.makeText(Register.this,"Thank You for registering ! Now you can login with your mobile no.",Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(Register.this, Login.class));
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


    private void makeDialog(){
        // custom dialog
        final Dialog dialog = new Dialog(Register.this);
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

        // where id is equal to
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
            profile_image.setImageBitmap(photo);

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

            profile_image.setImageURI(selectedImageUri);
            profile_image.setRotation(getCameraPhotoOrientation(Register.this,selectedImageUri,selectedImagePath));
        }
    }

    private void sendToServer(Uri selectedImageUri){
        synchronized (this){
            if (Build.VERSION.SDK_INT < 11)
                selectedImagePath = getRealPathFromURI_BelowAPI11(Register.this, selectedImageUri);

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                selectedImagePath = getRealPathFromURI_API11to18(Register.this, selectedImageUri);

                // SDK > 19 (Android 4.4)
            else
                selectedImagePath = getRealPathFromURI_API19(Register.this, selectedImageUri);
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

    private void initEducationQualification(){
        Log.d("ankit","qualification details:::"+Utils.BASE_URL + Utils.GET_QUALIFICATION);
        CallService service = new CallService(Register.this, Utils.BASE_URL + Utils.GET_QUALIFICATION, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{


                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("qualificationList");

                    QualificationModel zero_model = new QualificationModel();
                    zero_model.setQualificationID("");
                    zero_model.setQualificationDescription("");
                    zero_model.setQualificationName("Select ... ");

                    mainQualificationModel.add(zero_model);

                    for (int i=0;i<jarr.length();i++){
                        JSONObject innerJob = jarr.getJSONObject(i);
                        String qualificationname = innerJob.getString("qualificationname");
                        String description = innerJob.getString("description");
                        String id = innerJob.getString("id");

                        QualificationModel model = new QualificationModel();
                        model.setQualificationID(id);
                        model.setQualificationDescription(description);
                        model.setQualificationName(qualificationname);

                        mainQualificationModel.add(model);
                    }

                    QualificationAdapter adapter = new QualificationAdapter(Register.this,0,mainQualificationModel,getResources());
                    education_qualifition.setAdapter(adapter);
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

    private void initState(){

        CallService service = new CallService(Register.this, Utils.BASE_URL + Utils.COUNTRY_CITY_STATE
                + "?countryid=1", Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{

                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("stateList");
                    StateModel zero_model = new StateModel();
                    zero_model.setId("");
                    zero_model.setName("Select ...");
                    stateModel.add(zero_model);

                    for (int i=0;i<jarr.length();i++){
                        JSONObject innerJobj = jarr.getJSONObject(i);
                        String name = innerJobj.getString("name");
                        String id = innerJobj.getString("id");
                        StateModel model = new StateModel();
                        model.setId(id);
                        model.setName(name);
                        stateModel.add(model);
                    }
                    StateAdapter adapter = new StateAdapter(Register.this,0,stateModel,getResources());
                    state_id.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
//        service.execute();
    }
    private void initCity() {
        //CallService service = new CallService(getActivity(), Utils.BASE_URL + Utils.COUNTRY_CITY_STATE + "?countryid=1", Utils.GET, false, new CallService.OnServicecall() {
//            @Override
//            public void onServicecall(String response) {
//
        Log.wtf("ankit",Utils.BASE_URL + Utils.GET_ALL_CITIES);
        CallService service = new CallService(Register.this, Utils.BASE_URL + Utils.GET_ALL_CITIES , Utils.GET, false, new
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
                                Log.d("CJ PRINT","LIST DATA - "+ listdata.get(i));
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
                            StateAdapter adapter = new StateAdapter(Register.this, 0, stateModel, getResources());
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

//    private void initCity(String stateId){
//        Log.d("ankit","state id "+stateId);
//        try{
//            cityModel.clear();
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        CallService service = new CallService(Register.this, Utils.BASE_URL + Utils.COUNTRY_CITY_STATE + "?stateid=" + stateId, Utils.GET, false, new CallService.OnServicecall() {
//            @Override
//            public void onServicecall(String response) {
//                try{
//                    Log.d("ankit",response);
//                    JSONObject jobj = new JSONObject(response);
//                    JSONArray jarr = jobj.getJSONArray("cityList");
//                    CityModel zero_model = new CityModel();
//                    zero_model.setId("");
//                    zero_model.setName("Select ...");
//                    cityModel.add(zero_model);
//
//                    for (int i=0;i<jarr.length();i++){
//                        JSONObject inner_job = jarr.getJSONObject(i);
//                        String name = inner_job.getString("name");
//                        String id = inner_job.getString("id");
//
//                        CityModel model = new CityModel();
//                        model.setName(name);
//                        model.setId(id);
//                        cityModel.add(model);
//                    }
//                    CityAdapter adapter = new CityAdapter(Register.this,0,cityModel,getResources());
//                    city_id.setAdapter(adapter);
//                } catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
//            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        } else {
//            service.execute();
//       }

//        service.execute();
    }

