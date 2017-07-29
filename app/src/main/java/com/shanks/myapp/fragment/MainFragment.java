package com.shanks.myapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanks.myapp.R;
import com.shanks.myapp.activities.MainActivity;
import com.shanks.myapp.models.offerListModel;
import com.shanks.myapp.registration_flow.Login;
import com.shanks.myapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fg on 6/2/2017.
 */

public class MainFragment extends Fragment {
    Context context;
    offerListModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popular_fragment, container, false);

        model = getArguments().getParcelable("model");
        if(model!=null){
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
        // set popular image
        final ImageView popular_image = (ImageView)v.findViewById(R.id.popular_image);
        popular_image.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(model!= null){
                    String url = model.getUrl();
                    if (!url.startsWith("http://") && !url.startsWith("https://")){
                        url = "http://" + url;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,  Uri.parse(url));
                    startActivity(browserIntent);

                }

            }
        });

        try {
            String imageUrl = Utils.IMAGE_URL+"/referralMedia/bannerMedia/"+model.getOfferImage();
            Log.d("tanyabanner", imageUrl);
            imageUrl = imageUrl.replace("%20","");
            Picasso.with(context)
                    .load(imageUrl)
                    .into(popular_image);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        // set offer name

    }

    public static MainFragment newInstance(offerListModel model) {
        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putParcelable("model", model);
        f.setArguments(b);
        return f;
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}


