package com.shanks.myapp.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.OfferCityAdapter;
import com.shanks.myapp.models.CityModel;
import com.shanks.myapp.models.LeadModel;
import com.shanks.myapp.models.offerListModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
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
import java.util.Locale;

import static android.R.string.no;
import static android.R.string.yes;

public class LeadCloseForm extends AppCompatActivity{
    LeadModel model;
    RelativeLayout bill_relative;
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 2;
    private String selectedImagePath;
    String serverFilePath="none";
    ImageView image;
    Button Submit;
    String offerid = "";
    AutoCompleteTextView purchasedate,dealername,Contact,locbuy,amount,bill;
    ArrayList<CityModel> cityArray = new ArrayList<>();
    Session session;
    String cityId = "";
    String closureprocess;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_close_form);
        model = getIntent().getParcelableExtra("model");
        if(model!=null) init();
    }

    private void init(){

        image = (ImageView)findViewById(R.id.image);
        offerid = getIntent().getExtras().getString("offerid");
        session = Session.getSession(LeadCloseForm.this);

//        if (mDealId.containsKey(dealername.getText().toString())) {
//            str = "http://" + Utils.HOSTNAME_MAIN_URL + "closedLeadData?
// userid=" +userid +
// "&leadid=" + closeID +
// "&billnumber=" + bill.getText().toString().replaceAll(" ", "") +
// "&amountpaid=" + amount.getText().toString().replaceAll(" ", "") +
// "&dealerid=" + mDealId.get(dealername.getText().toString()) +
// "&purchasedate=" + purchasedate.getText().toString().replaceAll(" ", "") +
// "&dealerphonenumber=" + Contact.getText().toString().replaceAll(" ", "") +
// "&dealerlocation=" + mCityListWithID.get(locbuy.getText().toString()) +
// "&billimage=" + uploadFileName;
//        } else {
//            str = "http://" + Utils.HOSTNAME_MAIN_URL + "closedLeadData?userid=" + userid + "&leadid=" + closeID + "&billnumber=" + bill.getText().toString().replaceAll(" ", "") + "&amountpaid=" + amount.getText().toString().replaceAll(" ", "") + "&dealername=" + dealername.getText().toString().replaceAll(" ", "") + "&purchasedate=" + purchasedate.getText().toString().replaceAll(" ", "") + "&dealerphonenumber=" + Contact.getText().toString().replaceAll(" ", "") + "&dealerlocation=" + mCityListWithID.get(locbuy.getText().toString()) + "&billimage=" + uploadFileName;
//        }

        bill = (AutoCompleteTextView)findViewById(R.id.bill);

        purchasedate = (AutoCompleteTextView)findViewById(R.id.purchasedate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                purchasedate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        purchasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(LeadCloseForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dealername = (AutoCompleteTextView)findViewById(R.id.dealername);
        dealername.setText(model.getDealerName());

        Contact = (AutoCompleteTextView)findViewById(R.id.Contact);

        locbuy = (AutoCompleteTextView)findViewById(R.id.locbuy);
        getAllCities();

        bill_relative = (RelativeLayout)findViewById(R.id.bill_relative);
        bill_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog();
            }
        });

        amount = (AutoCompleteTextView)findViewById(R.id.amount);

        Submit = (Button)findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String billNumber = bill.getText().toString().trim();
                String amountSting = amount.getText().toString().trim();
                String purchasedateString = purchasedate.getText().toString().trim();
                String ContactString = Contact.getText().toString().trim();
                if(billNumber.equalsIgnoreCase("")){
                    Toast.makeText(LeadCloseForm.this,"Please enter the bill number",Toast.LENGTH_LONG).show();
                } else if(ContactString.equalsIgnoreCase("")){
                    Toast.makeText(LeadCloseForm.this,"Please enter the contact number",Toast.LENGTH_LONG).show();
                } else if(cityId.equalsIgnoreCase("")){
                    Toast.makeText(LeadCloseForm.this,"Please chose the location",Toast.LENGTH_LONG).show();
                } else if(amountSting.equalsIgnoreCase("")){
                    Toast.makeText(LeadCloseForm.this,"Please fill amount",Toast.LENGTH_LONG).show();
                } else if(purchasedateString.equalsIgnoreCase("")){
                    Toast.makeText(LeadCloseForm.this,"Please fill purchase date",Toast.LENGTH_LONG).show();
                }else {
                    closeLead(billNumber,amountSting,ContactString,cityId,purchasedateString,offerid);
                }
            }
        });
    }
    private void closeLead(String billNumber,String amountPaid,String ContactString,String dealerlocation,String purchasedate,String offerid ){
        String url = Utils.BASE_URL+Utils.CLOSE_LEADS
                +"?userid="+session.getUserId()
                +"&leadid="+model.getId()
                +"&billnumber="+billNumber
                +"&amountpaid="+amountPaid
                +"&purchasedate="+purchasedate
                +"&dealerphonenumber="+ContactString
                +"&dealerlocation="+dealerlocation
                + "&offerid=147"
                +"&communicateuser=y"
                +"&dealername=Honda"
                +"&billimage="+serverFilePath;
         Log.d("ankit",url);

        CallService service = new CallService(LeadCloseForm.this, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit",response);
                    //{"result":"Success.","responseCode":"2001"}
                    try{
                        PARSE_FORM_JSON(response);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
            }
        });
        service.execute();
    }
    private void PARSE_FORM_JSON(String response) throws Exception{
        JSONObject jobj = new JSONObject(response);
        String responseCode = jobj.getString("responseCode");
        Log.d("tanya", response);
        if(responseCode.equalsIgnoreCase("2001")){
            new AlertDialog.Builder(LeadCloseForm.this)
                    .setTitle("Congrats ...")
                    .setMessage("Congrats your lead is now closed...")
                    .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            sendToHome();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            Toast.makeText(LeadCloseForm.this,"Something went wrong please try again later",Toast.LENGTH_LONG).show();
        }
    }
    private void getAllCities(){
        String url = Utils.BASE_URL+Utils.GET_ALL_CITIES;
        Log.d("get", "response ::" + url);
        CallService service = new CallService(LeadCloseForm.this, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{
                    PARSE_JSON(response);
                    Log.d( "response ::",response);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }

    private void PARSE_JSON(String response) throws Exception{
        JSONObject jobj = new JSONObject(response);
        JSONArray cityList = jobj.getJSONArray("cityList");

        for (int i=0;i<cityList.length();i++){
            JSONObject innerJob = cityList.getJSONObject(i);
            CityModel model = new CityModel();
            String name = innerJob.getString("name");
            model.setName(name);
            String id = innerJob.getString("id");
            model.setId(id);
            cityArray.add(model);
        }
        //spinner_rows
        OfferCityAdapter adapter = new OfferCityAdapter(LeadCloseForm.this,R.layout.spinner_rows,cityArray);
        locbuy.setAdapter(adapter);
        locbuy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cityId = cityArray.get(i).getId();
            }
        });
    }
    private void sendToHome(){
        Intent intent = new Intent(LeadCloseForm.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void makeDialog(){
        // custom dialog
        final Dialog dialog = new Dialog(LeadCloseForm.this);
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
            image.setRotation(getCameraPhotoOrientation(LeadCloseForm.this,selectedImageUri,selectedImagePath));
        }
    }

    private void sendToServer(Uri selectedImageUri){
        synchronized (this){
            if (Build.VERSION.SDK_INT < 11)
                selectedImagePath = getRealPathFromURI_BelowAPI11(LeadCloseForm.this, selectedImageUri);

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                selectedImagePath = getRealPathFromURI_API11to18(LeadCloseForm.this, selectedImageUri);

                // SDK > 19 (Android 4.4)
            else
                selectedImagePath = getRealPathFromURI_API19(LeadCloseForm.this, selectedImageUri);
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


