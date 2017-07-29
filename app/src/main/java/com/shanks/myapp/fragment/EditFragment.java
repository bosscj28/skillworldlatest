package com.shanks.myapp.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Comparator;

import com.shanks.myapp.R;
import com.shanks.myapp.activities.Busines;
import com.shanks.myapp.activities.Edit;
import com.shanks.myapp.activities.LeadDetail;
import com.shanks.myapp.models.LeadModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.activities.SetOnContactAdded;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;
import com.shanks.myapp.adapter.WalletAdapter;
import com.shanks.myapp.models.ListModel;

import static android.R.attr.data;
import static com.shanks.myapp.adapter.WalletAdapter.*;

//Our class extending fragment
public class EditFragment extends Fragment {
    Context context;
    Session session;
    ListView list;
    ArrayList<ListModel> listModels = new ArrayList<>();
    WalletAdapter adapter;
    String categories = "";
    Context myContext,appContext;
    View view,v;
    Bundle arguments;
    public static Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_edit, container, false);
        list = (ListView) v.findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                ListModel model = listModels.get(i);
                Intent intent = new Intent(context, Edit.class);
                intent.putExtra("name",model.getTv_name());
                intent.putExtra("mobilenumber",model.getTv_Amount());
                intent.putExtra("emailId", model.getModeofpay());
                intent.putExtra("cityid", model.getCityId());
                intent.putExtra("cityname", model.getCityname());
                intent.putExtra("category", model.getCategories());
                intent.putExtra("userid", model.getId());

                startActivity(intent);
            }
        });
        setHandler();
        //getAllContacts(session.getUserId());
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public void getAllContacts(String userid) {
        try {
            //listModels.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void init() {
        session = Session.getSession(context);
        {
            String url = Utils.BASE_URL + Utils.GET_CUSTOMER_LIST +
            "?userid=" + session.getUserId();
//            String url = "http://139.59.18.198:6060/SkillWorldAPI/getCustomerListAPI?userid=232&categories=Salaried";
            Log.wtf("ankit",url);
            CallService service = new CallService(context, url, Utils.GET, true, new CallService.OnServicecall() {
                @Override
                public void onServicecall(String response) {
                    try {
                        Log.wtf("ankit", response);
                        PARSE_JSON(response);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            service.execute();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        synchronized (this) {
            init();
        }
    }

    public void setHandler() {
        mHandler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);

                if (msg.what == 1) {
                    if (adapter == null) {
                        adapter = new WalletAdapter(context, listModels);
                        list.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                        list.invalidate();
                    }
                    //            Application.setListViewHeightBasedOnChildren(beneficiaryLists);
                }

                if (msg.what == 2) {
                    init();
                }

            }
        };
    }

    private void PARSE_JSON(String result) throws Exception {
        System.out.print("nik" + result);
        JSONObject jobj = new JSONObject(result);
        JSONArray honey = jobj.getJSONArray("customerList");
        listModels.clear();
        for (int i = 0; i < honey.length(); i++) {
            JSONObject innerJob = honey.getJSONObject(i);
            ListModel model = new ListModel();
            String name = innerJob.getString("name");
            model.setTv_name(name);
            String amount = innerJob.getString("mobileNumber");
            model.setTv_Amount(amount);
            String email = innerJob.getString("emailId");
            model.setModeofpay(email);
            String cityname = innerJob.getString("cityname");
            model.setCityname(cityname);
            String cityId = innerJob.getString("cityId ");
            model.setCityId(cityId);
            String id = innerJob.getString("id");
            model.setId(id);
            String categories = innerJob.getString("categories");
            model.setCategories(categories);


//     {"result":"Success.","customerList":[{"mobileNumber":"773763480","cityname":"Bhandara","name":"Bhanudave",
//             "emailId":"bhanudave13@gmail.co","cityId ":"440","id":"98","userId":"397"}],"responseCode":"2001"}

            listModels.add(model);
        }
        mHandler.sendEmptyMessage(1);
//        adapter.notifyDataSetChanged();
//        list.invalidate();
//        list.setAdapter(adapter);
    }

    public static EditFragment newInstance() {

        return new EditFragment();
    }
}