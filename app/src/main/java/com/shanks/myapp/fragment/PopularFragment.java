package com.shanks.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.shanks.myapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.shanks.myapp.models.offerListModel;

/**
 * Created by katrina on 25/01/17.
 */

public class PopularFragment extends Fragment{

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
        ImageView popular_image = (ImageView)v.findViewById(R.id.popular_image);
        try {

            String imageUrl = Utils.IMAGE_URL+"/referralMedia/offersMedia/"+model.getOfferImage();

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
        TextView offer_name = (TextView)v.findViewById(R.id.offer_name);
        offer_name.setText(model.getOfferName());

        // set offer date
        TextView valid_date = (TextView)v.findViewById(R.id.valid_date);
        valid_date.setText(parseDateToddMMyyyy(model.getTodate()));
    }

    public static PopularFragment newInstance(offerListModel model) {
        PopularFragment f = new PopularFragment();
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
