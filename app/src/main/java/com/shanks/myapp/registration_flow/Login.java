package com.shanks.myapp.registration_flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import com.shanks.myapp.R;
import com.shanks.myapp.activities.MainActivity;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import static android.support.v7.appcompat.R.styleable.CompoundButton;
import static com.shanks.myapp.R.id.view;

public class Login extends AppCompatActivity {

    Button email_sign_in_button;
    AutoCompleteTextView email;
    EditText password;
    Session session;
    CheckBox checkbox;
    ImageView arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init() {
        session = Session.getSession(Login.this);
        arrow_left = (ImageView) findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        email = (AutoCompleteTextView) findViewById(R.id.email);
        checkbox = (CheckBox)  findViewById(R.id.checkbox);
        password = (EditText) findViewById(R.id.password);

        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                    // checkbox status is changed from uncheck to checked.
                                                    if (!isChecked) {
                                                        // show password
                                                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                    } else {
                                                        // hide password
                                                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                    }
                                                }
                                            });
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailString = email.getText().toString().trim();
                final String passwordString = password.getText().toString().trim();

                if (emailString.equalsIgnoreCase("")) {
                    Utils.makeDialog(Login.this, "Please fill mobilenumber");
                } else if (passwordString.equalsIgnoreCase("")) {
                    Utils.makeDialog(Login.this, "Please fill password");
                } else {
                    String url = Utils.BASE_URL + Utils.LOGIN + "?username=" + emailString + "&password=" + passwordString;

                    Log.wtf("ankit", "url ::" + url);
                    CallService service = new CallService(Login.this, url, Utils.GET, true, new CallService.OnServicecall() {
                        @Override
                        public void onServicecall(String response) {
                            Log.wtf("ankit", "login response ::" + response);

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("JSON", jsonObject.toString());
                                String Success = jsonObject.getString("result");

                                String cityid = jsonObject.getString("cityid");
                                session.setCityid(cityid);
                                String cityname = jsonObject.getString("cityname");
                                session.setCityname(cityname);
                                String addressid = jsonObject.getString("addressid");
                                session.setAddressid(addressid);

                                String userinfo = jsonObject.getString("userinfo");
                                Log.d("userinfo", userinfo);
                                JSONObject userinfo1 = new JSONObject(userinfo);
                                String email = userinfo1.getString("email");
                                session.setEmail(email);
                                String userid = userinfo1.getString("userid");
                                session.setUserId(userid);
                                String userimage = userinfo1.getString("userimage");
                                session.setUserimage(userimage);
                                String fullname = userinfo1.getString("fullname");
                                session.setFullname(fullname);
                                String username = userinfo1.getString("username");
                                session.setUsername(username);
                                String mobileNumber = userinfo1.getString("mobilenumber");
                                session.setMobilenumber(mobileNumber);
                                String firstname = userinfo1.getString("firstname");
                                session.setFirstname(firstname);
                                String lastname = userinfo1.getString("lastname");
                                session.setLastname(lastname);

                                String location = userinfo1.getString("location");
                                session.setLocation(location);
                                String category = userinfo1.getString("category");
                                session.setCategory(category);


                                session.setPassowrd(passwordString);

// login response ::{"result":"Login successful.!","userinfo":{"firstname":"Bh","mobilenumber":"9369369369",
//                                        "dob":"","userimage":"","fullname":"Bh Bh","userid":"410","email":"bh@gmail.com",
//                                        "lastname":"Bh","username":"9369369369"},"responseCode":"2001"}

//   {"result":"Login successful.!","userinfo":{"firstname":"Priya","mobilenumber":"9898989898","dob":"",
//           "userimage":"","fullname":"Priya Rathi","userid":"403","email":"priya@gmail.com","lastname":"Rathi",
//           "username":"9898989898"},"responseCode":"2001"}

                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                showToast("Invalid mobilenumber and password");
                            }
                        }
                    });
                    service.execute();
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this,LoginRegister.class));
        finish();
    }

}
