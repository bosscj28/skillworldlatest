package com.shanks.myapp.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.shanks.myapp.R;
import com.shanks.myapp.adapter.StateAdapter;
import com.shanks.myapp.models.StateModel;
import com.shanks.myapp.registration_flow.Register;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class spinnercity extends AppCompatActivity {
    Spinner state_id;
    String stateID = "";
    String cityID = "";
    ArrayList<StateModel> stateModel = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinnercity);
        state_id = (Spinner) findViewById(R.id.state_id);
        initState();
        state_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateID = stateModel.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void initState(){

        CallService service = new CallService(spinnercity.this, Utils.BASE_URL + Utils.GET_ALL_CITIES, Utils.GET, false, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try{

                    JSONObject jobj = new JSONObject(response);
                    JSONArray jarr = jobj.getJSONArray("cityList");
                    StateModel zero_model = new StateModel();
                    zero_model.setId("");
                    zero_model.setName("Select ...");
                    stateModel.add(zero_model);

                    for (int i=0;i<jarr.length();i++){
                        JSONObject innerJobj = jarr.getJSONObject(i);
                        String name = innerJobj.getString("name");
                        String id = innerJobj.getString("id");
                        StateModel model = new StateModel();
                        model.setId(id);
                        model.setName(name);
                        stateModel.add(model);
                    }
                    StateAdapter adapter = new StateAdapter(spinnercity.this,0,stateModel,getResources());
                    state_id.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            service.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            service.execute();
        }
//        service.execute();
    }

    }

