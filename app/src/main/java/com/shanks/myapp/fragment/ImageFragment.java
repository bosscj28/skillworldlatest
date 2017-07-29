package com.shanks.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shanks.myapp.R;
import com.shanks.myapp.models.offerImagesListModel;
import com.shanks.myapp.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by katrina on 24/01/17.
 */

public class ImageFragment extends Fragment{

        Context context;
        offerImagesListModel model;
        ImageView full_image;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_fragment, container, false);
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
        full_image = (ImageView)v.findViewById(R.id.full_image);

        try {

            String imageUrl = Utils.IMAGE_URL+"/referralMedia/offersMedia/"+model.getImgname();
            Log.d("tanya", imageUrl);
            imageUrl = imageUrl.replace("%20","");
            Picasso.with(context)
                    .load(imageUrl)
                    .into(full_image);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static ImageFragment newInstance(offerImagesListModel model) {
        ImageFragment f = new ImageFragment();
        Bundle b = new Bundle();
        b.putParcelable("model", model);
        f.setArguments(b);
        return f;
    }

}
