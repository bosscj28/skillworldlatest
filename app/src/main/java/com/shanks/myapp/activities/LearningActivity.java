package com.shanks.myapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.registration_flow.Register;
import com.shanks.myapp.utils.Utils;

/**
 * Created by lenovo on 30-06-2017.
 */

public class LearningActivity extends AppCompatActivity {

    private EditText edtEmail, edtName, edtUsername, edtPassword;
    private EditText edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_activity);

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
    }

    private void validate() {
        if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
            Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show();
            return;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                Utils.makeDialog(LearningActivity.this, "Please enter valid id");

        } else if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(edtUsername.getText().toString().trim())) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        } else {
//            callingDeeplinkIntent();
            Toast.makeText(LearningActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void callingDeeplinkIntent() {
        Uri uri = Uri.parse("skillworld.com://data?email=" + edtEmail.getText().toString() +
                "&name=" + edtName.getText().toString() +
                "&phone=" + edtPhone.getText().toString() +
                "&username=" + edtUsername.getText().toString() +
                "&password=" + edtPassword.getText().toString());
        System.out.println("URL>>>"+uri);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(uri);
        startActivity(i);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_click) {
            validate();
        }
    }
}
