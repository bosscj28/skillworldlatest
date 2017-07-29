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

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.OfferAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import com.shanks.myapp.activities.Offer;
import com.shanks.myapp.edit_flow.EditRegister;
import com.shanks.myapp.models.CategoryModel;
import com.shanks.myapp.models.OfferModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

/**
 * Created by katrina on 17/01/17.
 */

public class CategoryFragment extends Fragment {

    Context context;
    CategoryModel model;
    ListView offer_list;
    ArrayList<OfferModel> mainmodel;
    Session session;
    String userStatus = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_fragment, container, false);

        model = getArguments().getParcelable("model");
        if (model != null) {
            synchronized (this) {
                init(v);
            }
            getUserDetails();
        }
        return v;
    }
    private void getUserDetails() {
        String url = Utils.BASE_URL + Utils.USER_DETAILS + "?userid=" + session.getUserId();
        Log.d("tulika", "get user details::" + url);
        CallService service = new CallService(context, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit", "get user details response::" + response);
                try {
                    JSON_PARSE1(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        service.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void init(View v) {
        session = Session.getSession(context);
        mainmodel = new ArrayList<>();
        offer_list = (ListView) v.findViewById(R.id.offer_list);
        offer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(userStatus.equalsIgnoreCase(Utils.USER_STATUS_INITIAL)) {
//                    Intent intent = new Intent(context, Offer.class);
//                    intent.putExtra("model", mainmodel.get(i));
//                    startActivity(intent);
                    Toast.makeText(context,"Please clear the Learning First", Toast.LENGTH_SHORT).show();
                }
                    else {
                        //.makeText(context,"Please clear the Learning First", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, Offer.class);
                    intent.putExtra("model", mainmodel.get(i));
                    startActivity(intent);
                }

            }
        });
        String url = Utils.BASE_URL + Utils.OFFER_LIST
                + "?userid=" + session.getUserId()
                + "&categoryid=" + model.getId();
        Log.d("ankit", url);
        CallService service = new CallService(context, url, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit", response);
                try {
                    JSON_PARSE(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
    }
    private void JSON_PARSE(String response) throws Exception {
        JSONObject jobj = new JSONObject(response);
        JSONArray jarr = jobj.getJSONArray("offerList");
        for (int i = 0; i < jarr.length(); i++) {
            JSONObject inner_job = jarr.getJSONObject(i);

            String referralAmount = inner_job.getString("referralAmount");
            String offerName = inner_job.getString("offerName");
            String locationName = inner_job.getString("locationName");
            String referralPercentage = inner_job.getString("referralPercentage");
            String detailedPageUrl = inner_job.getString("detailedPageUrl");
            String offerStatus = inner_job.getString("offerStatus");
            String corporateId = inner_job.getString("corporateId");
            String offerShortDescription = inner_job.getString("offerShortDescription");
            String fromdate = inner_job.getString("fromdate");
            String createdDate = inner_job.getString("createdDate");
            String todate = inner_job.getString("todate");
            String createdby = inner_job.getString("createdby");
            String offerDescription = inner_job.getString("offerDescription");
            String offerImage = inner_job.getString("offerImage");
            String offerId = inner_job.getString("offerId");


            OfferModel model = new OfferModel();
            model.setReferralAmount(referralAmount + "");
            model.setOfferName(offerName + "");
            model.setLocationName(locationName + "");
            model.setReferralPercentage(referralPercentage + "");
            model.setDetailedPageUrl(detailedPageUrl + "");
            model.setOfferStatus(offerStatus + "");
            model.setCorporateId(corporateId + "");
            model.setOfferShortDescription(offerShortDescription + "");
            model.setFromdate(fromdate + "");
            model.setCreatedDate(createdDate + "");
            model.setTodate(todate + "");
            model.setCreatedby(createdby + "");
            model.setOfferDescription(offerDescription);
            model.setOfferImage(offerImage);
            model.setOfferId(offerId);
            mainmodel.add(model);
        }

        OfferAdapter adapter = new OfferAdapter(context, mainmodel);
        offer_list.setAdapter(adapter);
    }

    public static CategoryFragment newInstance(CategoryModel model) {

        CategoryFragment f = new CategoryFragment();
        Bundle b = new Bundle();
        b.putParcelable("model", model);

        f.setArguments(b);

        return f;
    }

    private void JSON_PARSE1(String response) throws Exception {
        JSONObject jobj = new JSONObject(response);
        JSONArray jarr = jobj.getJSONArray("personaldetailList");

            JSONObject inner_job = jarr.getJSONObject(0);

            userStatus = inner_job.getString("userStatus")+"";



    }
}

