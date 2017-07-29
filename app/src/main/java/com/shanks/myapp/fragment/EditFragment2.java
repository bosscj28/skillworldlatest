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

import com.shanks.myapp.R;
import com.shanks.myapp.activities.Edit;
import com.shanks.myapp.adapter.WalletAdapter;
import com.shanks.myapp.models.ListModel;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 7/28/2017.
 */

public class EditFragment2 extends Fragment {
    View v;
    ListView list;
    Context context;
    ArrayList<ListModel> listModels = new ArrayList<>();
    Session session;

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
        init();
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onResume() {
        super.onResume();
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
        WalletAdapter adapter = new WalletAdapter(context,listModels);
        list.setAdapter(adapter);
    }

    public static EditFragment2 newInstance() {

        return new EditFragment2();
    }

}
