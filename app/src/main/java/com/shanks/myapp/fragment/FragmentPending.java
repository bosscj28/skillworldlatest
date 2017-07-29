package com.shanks.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.PaymentAdapter;
import com.shanks.myapp.models.PaymentModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by katrina on 18/01/17.
 */

public class FragmentPending extends Fragment {

    Context context;
    Session session;
    ListView open_list;
    ArrayList<PaymentModel> paymentModels;
    PaymentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending, container, false);

        synchronized (this){
            init(v);
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void init(View v){
        session = Session.getSession(context);

        paymentModels = new ArrayList<>();
        open_list = (ListView)v.findViewById(R.id.open_list);

        adapter = new PaymentAdapter(context,paymentModels);
        open_list.setAdapter(adapter);
        String url = Utils.BASE_URL + Utils.GET_PAYMENTS + "?userid="+session.getUserId()+"&status=Pending";
        Log.d("ankit",url);
        CallService service = new CallService(context, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit",response);
                try{
                    PARSE_JSON(response);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }

    private void PARSE_JSON(String response) throws Exception{
        JSONObject jobj = new JSONObject(response);
        JSONArray paymentList = jobj.getJSONArray("paymentList");
        for (int i =0;i<paymentList.length();i++){
            JSONObject innerJob = paymentList.getJSONObject(i);
            PaymentModel model = new PaymentModel();

            String buyerPhoneNo = innerJob.getString("buyerPhoneNo");
            model.setBuyerPhoneNo(buyerPhoneNo);

            String referralAmount = innerJob.getString("referralAmount");
            model.setReferralAmount(referralAmount);

            String offerName = innerJob.getString("offerName");
            model.setOfferName(offerName);

            String dealerName = innerJob.getString("dealerName");
            model.setDealerName(dealerName);

            String buyerLocation = innerJob.getString("buyerLocation");
            model.setBuyerLocation(buyerLocation);

            String buyerEmail = innerJob.getString("buyerEmail");
            model.setBuyerName(buyerEmail);

            String planToBuyOn = innerJob.getString("planToBuyOn");
            model.setPlanToBuyOn(planToBuyOn);

            String dealerId = innerJob.getString("dealerId");
            model.setDealerId(dealerId);

            String leadCreateDate = innerJob.getString("leadCreateDate");
            model.setLeadClosedDate(leadCreateDate);

            String buyerName = innerJob.getString("buyerName");
            model.setBuyerName(buyerName);

            String dealerPreference = innerJob.getString("dealerPreference");
            model.setDealerPreference(dealerPreference);

            String closeDate = innerJob.getString("closeDate");
            model.setCloseDate(closeDate);

            String dealerLocation = innerJob.getString("dealerLocation");
            model.setDealerLocation(dealerLocation);

            String offername = innerJob.getString("offername");
            model.setOfferName(offername);

            String amountPaid = innerJob.getString("amountPaid");
            model.setAmountPaid(amountPaid);

            String paymentId = innerJob.getString("paymentId");
            model.setPaymentId(paymentId);

            String prefererredDealerid = innerJob.getString("prefererredDealerid");
            model.setPrefererredDealerid(prefererredDealerid);

            String prefererredDealerName = innerJob.getString("prefererredDealerName");
            model.setPrefererredDealerName(prefererredDealerName);

            String billNumber = innerJob.getString("billNumber");
            model.setBillNumber(billNumber);

            String leadId = innerJob.getString("leadId");
            model.setLeadId(leadId);

            String actualAmountPaid = innerJob.getString("actualAmountPaid");
            model.setActualAmountPaid(actualAmountPaid);

            String leadClosedDate = innerJob.getString("leadClosedDate");
            model.setLeadClosedDate(leadClosedDate);

            paymentModels.add(model);
        }

        adapter.notifyDataSetChanged();
    }

    public static FragmentPending newInstance() {
        return new FragmentPending();
    }


}
