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
import com.shanks.myapp.adapter.LeadAdapter;
import com.shanks.myapp.models.LeadModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by katrina on 18/01/17.
 */

public class FragmentCloseLead extends Fragment{

    Context context;
    Session session;
    ListView close_list;
    ArrayList<LeadModel> leadModels = new ArrayList<>();
    LeadAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_close_leads, container, false);
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

        close_list = (ListView)v.findViewById(R.id.close_list);
        adapter = new LeadAdapter(context,leadModels);

        String url = Utils.BASE_URL + Utils.GET_LEADS + "?userid="+session.getUserId()+"&status=Approved";
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
        JSONArray leadList = jobj.getJSONArray("leadList");
        for (int i=0;i<leadList.length();i++){
            JSONObject innerJob = leadList.getJSONObject(i);
            LeadModel model = new LeadModel();

            String buyerPhoneNo = innerJob.getString("buyerPhoneNo");
            model.setBuyerPhoneNo(buyerPhoneNo);

            String dealerName = innerJob.getString("dealerName");
            model.setDealerName(dealerName);

            String buyerLocation = innerJob.getString("buyerLocation");
            model.setBuyerLocation(buyerLocation);

            String buyerEmail = innerJob.getString("buyerEmail");
            model.setBuyerEmail(buyerEmail);

            String planToBuyOn = innerJob.getString("planToBuyOn");
            model.setPlanToBuyOn(planToBuyOn);

            String dealerId = innerJob.getString("dealerId");
            model.setDealerId(dealerId);

            String leadCreateDate = innerJob.getString("leadCreateDate");
            model.setLeadCreateDate(leadCreateDate);

            String buyerName = innerJob.getString("buyerName");
            model.setBuyerName(buyerName);

            String dealerPreference = innerJob.getString("dealerPreference");
            model.setDealerPreference(dealerPreference);

            String newDealerName = innerJob.getString("newDealerName");
            model.setNewDealerName(newDealerName);

            String otherDealerName = innerJob.getString("otherDealerName");
            model.setOtherDealerName(otherDealerName);

            String dealerLocation = innerJob.getString("dealerLocation");
            model.setDealerLocation(dealerLocation);

            String offername = innerJob.getString("offername");
            model.setOffername(offername);

            String amountPaid = innerJob.getString("amountPaid");
            model.setAmountPaid(amountPaid);

            String prefererredDealerid = innerJob.getString("prefererredDealerid");
            model.setPrefererredDealerid(prefererredDealerid);

            String prefererredDealerName = innerJob.getString("prefererredDealerName");
            model.setPrefererredDealerName(prefererredDealerName);

            String id = innerJob.getString("id");
            model.setId(id);

            String billNumber = innerJob.getString("billNumber");
            model.setBillNumber(billNumber);

            String leadid = innerJob.getString("leadid");
            model.setLeadid(leadid);

            String leadClosedDate = innerJob.getString("leadClosedDate");
            model.setLeadClosedDate(leadClosedDate);

            //String offerId = innerJob.getString("offerId");
            //model.setOfferId(leadClosedDate);

            //String communicateUser = innerJob.getString("communicateUser");
            //model.setCommunicateUser(leadClosedDate);

            leadModels.add(model);
        }
        adapter.notifyDataSetChanged();
    }
    public static FragmentCloseLead newInstance() {
        return new FragmentCloseLead();
    }
}
