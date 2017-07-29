package com.shanks.myapp.registration_flow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.activities.MainActivity;
import com.shanks.myapp.utils.Session;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    public static final int permsRequestCode = 200;
    //private int STORAGE_PERMISSION_CODE = 23;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        if(shouldAskPermission()){

            if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                // permission is ok do rest of the work
                init();
            } else {
                // permission is not ok show error
                String[] perms = { "android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE"
                        ,"android.permission.WRITE_EXTERNAL_STORAGE" };
                givePermissions(perms);
            }

        } else {
            init();
        }
    }


    private void init(){
        final Session session = Session.getSession(FullscreenActivity.this);

        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                if(session.getUserId().equalsIgnoreCase("")){
                    startActivity(new Intent(FullscreenActivity.this,LoginRegister.class));
                    finish();
                } else {
                    startActivity(new Intent(FullscreenActivity.this,MainActivity.class));
                    finish();
                }


            }
        }.start();
    }

    // should ask permissions
    private boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @SuppressLint("NewApi")
    private void givePermissions(String[] perms) {
        requestPermissions(perms, permsRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permsRequestCode: {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                        && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                        && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    // permission is ok do rest of the work
                    init();
                } else {
                    // permission is not ok show error
                    Toast.makeText(FullscreenActivity.this,"Sorry you do not have sufficient permissions",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}




