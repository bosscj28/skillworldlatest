package com.shanks.myapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.shanks.myapp.models.LeadModel;

public class LeadDetail extends AppCompatActivity {

    LeadModel model;
    TextView tv_leadID,tv_buyerName,tv_buyerMobileNumber,tv_buyerEmailID,tv_dealerName,tv_offerName
            ,tv_planToBuyOn,tv_leadcreatedDate;
    Button btn_closeLead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_detail);
        model = getIntent().getParcelableExtra("model");
        if(model!=null)
        init();
    }
    private void init(){
        tv_leadID = (TextView)findViewById(R.id.tv_leadID);
        tv_leadID.setText(model.getLeadid());

        tv_buyerName = (TextView)findViewById(R.id.tv_buyerName);
        tv_buyerName.setText(model.getBuyerName());

        tv_buyerMobileNumber = (TextView)findViewById(R.id.tv_buyerMobileNumber);
        tv_buyerMobileNumber.setText(model.getBuyerPhoneNo());

        tv_buyerEmailID = (TextView)findViewById(R.id.tv_buyerEmailID);
        tv_buyerEmailID.setText(model.getBuyerEmail());

        tv_dealerName = (TextView)findViewById(R.id.tv_dealerName);
        tv_dealerName.setText(model.getDealerName());

        tv_offerName = (TextView)findViewById(R.id.tv_offerName);
        tv_offerName.setText(model.getOffername());

        tv_planToBuyOn = (TextView)findViewById(R.id.tv_planToBuyOn);
        tv_planToBuyOn.setText(model.getPlanToBuyOn());

        tv_leadcreatedDate = (TextView)findViewById(R.id.tv_leadcreatedDate);
        tv_leadcreatedDate.setText(model.getLeadCreateDate());

        btn_closeLead = (Button)findViewById(R.id.btn_closeLead);
        btn_closeLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeadDetail.this,LeadCloseForm.class);
                intent.putExtra("model",model);
                startActivity(intent);
            }
        });
    }

}
