package com.shanks.myapp.registration_flow;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.shanks.myapp.utils.CallService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

public class RegisterUpload extends AppCompatActivity {

    Button btn_registration_upload_page;

    private static final int ADHAR_CAMERA_REQUEST = 1;
    private static final int PAN_CAMERA_REQUEST = 2;
    private static final int MARKSHEET_CAMERA_REQUEST = 3;


    private static final int ADHAR_SELECT_PICTURE = 4;
    private static final int PAN_SELECT_PICTURE = 5;
    private static final int MARKSHEET_SELECT_PICTURE = 6;

    private static final String ADHAR_PICTURE = "adhar_picture";
    private static final String PAN_PICTURE = "pan_picture";
    private static final String MARKSHEET_PICTURE = "marksheet_picture";

    CircleImageView adhar_image,pan_image,marksheet_image;

    String adhar_serverFilePath = "";
    String pan_serverFilePath = "";
    String marksheet_serverFilePath = "";

    String selectedImagePath = "";


    RelativeLayout adhar_image_relative,pan_image_relative,marksheet_image_relative;

    Session session;
    ImageView arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_upload);
        init();
    }

    private void init(){

        session = Session.getSession(RegisterUpload.this);

        adhar_image = (CircleImageView)findViewById(R.id.adhar_image);

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setVisibility(View.GONE);

        adhar_image_relative = (RelativeLayout)findViewById(R.id.adhar_image_relative);
        adhar_image_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog(ADHAR_PICTURE);
            }
        });

        pan_image = (CircleImageView)findViewById(R.id.pan_image);

        pan_image_relative = (RelativeLayout)findViewById(R.id.pan_image_relative);
        pan_image_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog(PAN_PICTURE);
            }
        });

        marksheet_image = (CircleImageView)findViewById(R.id.marksheet_image);

        marksheet_image_relative = (RelativeLayout)findViewById(R.id.marksheet_image_relative);
        marksheet_image_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog(MARKSHEET_PICTURE);
            }
        });

        btn_registration_upload_page = (Button)findViewById(R.id.btn_registration_upload_page);
        btn_registration_upload_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adhar_serverFilePath.equalsIgnoreCase("")){
                    Utils.makeDialog(RegisterUpload.this,"Please select adhar card");
                } else {
                    String url = Utils.BASE_URL+Utils.REGISTER_DOCS
                            +"?userid=" + session.getUserId()
                            + "&pancardimage=" + pan_serverFilePath
                            + "&passportimage=" + marksheet_serverFilePath
                            + "&aadharcard=" + adhar_serverFilePath;

                    CallService service = new CallService(RegisterUpload.this, url, Utils.GET, true, new CallService.OnServicecall() {
                        @Override
                        public void onServicecall(String response) {
//                            {"result":"Success.","userid":"182","responseCode":"2001"}
                            Log.d("ankit",response);
                            try{
                                JSONObject jobj = new JSONObject(response);
                                String responseCode = jobj.getString("responseCode");
                                if(responseCode.equalsIgnoreCase("2001")){
                                    startActivity(new Intent(RegisterUpload.this,RegistrationBankDetails.class));
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

    private void makeDialog(final String image_for){
        // custom dialog
        final Dialog dialog = new Dialog(RegisterUpload.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog);

        TextView dialog_camera = (TextView)dialog.findViewById(R.id.dialog_camera);
        dialog_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                imageFromCamera(image_for);
            }
        });

        TextView dialog_card = (TextView)dialog.findViewById(R.id.dialog_card);
        dialog_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                imageFromCard(image_for);
            }
        });

        dialog.show();
    }

    private void imageFromCamera(String image_for){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if(image_for.equalsIgnoreCase(ADHAR_PICTURE)){
            startActivityForResult(cameraIntent, ADHAR_CAMERA_REQUEST);
        } else if(image_for.equalsIgnoreCase(PAN_PICTURE)){
            startActivityForResult(cameraIntent, PAN_CAMERA_REQUEST);
        } else if(image_for.equalsIgnoreCase(MARKSHEET_PICTURE)){
            startActivityForResult(cameraIntent, MARKSHEET_CAMERA_REQUEST);
        }

    }

    private void imageFromCard(String image_for){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);


        if(image_for.equalsIgnoreCase(ADHAR_PICTURE)){
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    ADHAR_SELECT_PICTURE);
        } else if(image_for.equalsIgnoreCase(PAN_PICTURE)){
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PAN_SELECT_PICTURE);
        } else if(image_for.equalsIgnoreCase(MARKSHEET_PICTURE)){
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    MARKSHEET_SELECT_PICTURE);
        }

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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADHAR_CAMERA_REQUEST && resultCode == RESULT_OK) {

            new AsyncTask<Void,Void,Void>(){
                String filePath=""+ Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis() + ".jpg";
                @Override
                protected Void doInBackground(Void... voids) {
                    adhar_serverFilePath = setAndSendFromCamera(data,adhar_image,filePath);
                    return null;
                }
            }.execute();

        } else if(requestCode == PAN_CAMERA_REQUEST && resultCode == RESULT_OK){
            new AsyncTask<Void,Void,Void>(){
                String filePath=""+ Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis() + ".jpg";
                @Override
                protected Void doInBackground(Void... voids) {
                    pan_serverFilePath = setAndSendFromCamera(data,pan_image,filePath);
                    return null;
                }
            }.execute();
        } else if(requestCode == MARKSHEET_CAMERA_REQUEST && resultCode == RESULT_OK){
            new AsyncTask<Void,Void,Void>(){
                String filePath=""+ Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis() + ".jpg";
                @Override
                protected Void doInBackground(Void... voids) {
                    marksheet_serverFilePath = setAndSendFromCamera(data,marksheet_image,filePath);
                    return null;
                }
            }.execute();
        }

        else if(requestCode == ADHAR_SELECT_PICTURE && resultCode == RESULT_OK){
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    adhar_serverFilePath = setAndSendFromSdcard(data,adhar_image);
                    return null;
                }
            }.execute();
        } else if(requestCode == PAN_SELECT_PICTURE && resultCode == RESULT_OK){
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    pan_serverFilePath = setAndSendFromSdcard(data,pan_image);
                    return null;
                }
            }.execute();
        } else if(requestCode == MARKSHEET_SELECT_PICTURE && resultCode == RESULT_OK){
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    marksheet_serverFilePath = setAndSendFromSdcard(data,marksheet_image);
                    return null;
                }
            }.execute();
        }
    }

    private String setAndSendFromCamera(final Intent data,final CircleImageView imageView,final String filePath){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

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
            }
        });

        try {
            Thread.sleep(2000);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            String ImageId = Utils.uploadFile(filePath);

            final String  ImagePath = Utils.BASE_IMAGE_URL+ ImageId;
            Log.d("ankit",ImagePath);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(RegisterUpload.this).load(ImagePath).into(imageView);
                }
            });

            return ImageId;
        } catch (Exception ex){
            ex.printStackTrace();
            return "";
        }

    }

    private String setAndSendFromSdcard(Intent data,final CircleImageView imageView){
        final Uri selectedImageUri = data.getData();



        synchronized (this){
            if (Build.VERSION.SDK_INT < 11)
                selectedImagePath = getRealPathFromURI_BelowAPI11(RegisterUpload.this, selectedImageUri);

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                selectedImagePath = getRealPathFromURI_API11to18(RegisterUpload.this, selectedImageUri);

                // SDK > 19 (Android 4.4)
            else
                selectedImagePath = getRealPathFromURI_API19(RegisterUpload.this, selectedImageUri);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageURI(selectedImageUri);
                imageView.setRotation(getCameraPhotoOrientation(RegisterUpload.this,selectedImageUri,selectedImagePath));
            }
        });



        try {

            String ImageId = Utils.uploadFile(selectedImagePath);

            final String ImagePath = Utils.BASE_IMAGE_URL+ ImageId;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(RegisterUpload.this).load(ImagePath).into(imageView);
                }
            });

            return ImageId;
        } catch (Exception ex){
            ex.printStackTrace();
            return "";
        }

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
