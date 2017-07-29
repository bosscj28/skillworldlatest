package com.shanks.myapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import com.shanks.myapp.R;
import com.shanks.myapp.activities.LeadCloseForm;
import com.shanks.myapp.activities.LeadDetail;
import com.shanks.myapp.adapter.LeadAdapter;
import com.shanks.myapp.models.LeadModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;


public class FragmentOpenLead extends Fragment {
    Context context;
    Session session;
    ListView open_list;
    String status = "";

    ArrayList<LeadModel> leadModels = new ArrayList<>();
    LeadAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_open_lead, container, false);

        synchronized (this) {
            init(v);
        }
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void init(View v) {

        session = Session.getSession(context);

        adapter = new LeadAdapter(context, leadModels);
        open_list = (ListView) v.findViewById(R.id.open_list);
        open_list.setAdapter(adapter);

        open_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LeadModel model = leadModels.get(i);
                Intent intent = new Intent(context, LeadDetail.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });
         {
            String url = Utils.BASE_URL + Utils.GET_LEADS + "?userid=" + session.getUserId() + "&status=Open";
            Log.d("tany", url);
            CallService service = new CallService(context, url, Utils.GET, true, new CallService.OnServicecall()
            {
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
        {
            String url = Utils.BASE_URL + Utils.GET_LEADS + "?userid=" + session.getUserId() + "&status=Pending";
            Log.d("ankit", url);
            CallService service = new CallService(context, url, Utils.GET, true, new CallService.OnServicecall() {
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
        {
            String url = Utils.BASE_URL + Utils.GET_LEADS + "?userid=" + session.getUserId() + "&status=Approved";
            Log.d("tanya", url);
            CallService service = new CallService(context, url, Utils.GET, true, new CallService.OnServicecall() {
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
    }

    private void PARSE_JSON(String result) throws Exception{
        JSONObject jobj = new JSONObject(result);
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

            leadModels.add(model);
        }
        adapter.notifyDataSetChanged();
    }
    public static FragmentOpenLead newInstance() {
        return new FragmentOpenLead();
    }
}


