package com.shanks.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.shanks.myapp.models.OfferModel;
import com.shanks.myapp.utils.Utils;

/**
 * Created by katrina on 18/01/17.
 */


    public class OfferAdapter extends ArrayAdapter<OfferModel> {
        private Context context;
        ArrayList<OfferModel> schoolList;
//
//    private int lastPosition = -1;

        public OfferAdapter(Context context, ArrayList<OfferModel> schoolList) {
            super(context, R.layout.offer_list_item, schoolList);
            this.context = context;
            this.schoolList=schoolList;
        }


        private static class ViewHolder {
            TextView ofer_name,valid_date,description,location;
            ImageView image;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final OfferModel model = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());

                convertView = inflater.inflate(R.layout.offer_list_item, parent, false);
//            convertView.requestLayout();
//
//            convertView.getLayoutParams().height = Utils.getScreenHeight(context)/4;

//                viewHolder.school_image = (ImageView) convertView.findViewById(R.id.school_image);
//                viewHolder.school_image.requestLayout();
//
//                viewHolder.school_image.getLayoutParams().width = 2* Utils.getScreenWidth(context)/7;
//                viewHolder.school_image.getLayoutParams().height = 2*Utils.getScreenWidth(context)/7;
//
                viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.ofer_name= (TextView) convertView.findViewById(R.id.ofer_name);
                viewHolder.valid_date = (TextView) convertView.findViewById(R.id.valid_date);
                viewHolder.description = (TextView) convertView.findViewById(R.id.description);
                viewHolder.location = (TextView)convertView.findViewById(R.id.location);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

//
            try {

                String imageUrl = Utils.IMAGE_URL+"/referralMedia/offersMedia/"+model.getOfferImage();

                imageUrl = imageUrl.replace("%20","");
                Picasso.with(context)
                        .load(imageUrl)
                        .into(viewHolder.image);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

//
            viewHolder.ofer_name.setText(model.getOfferName());
            viewHolder.valid_date.setText(parseDateToddMMyyyy(model.getTodate()));// dd mm yy
            viewHolder.description.setText(model.getOfferShortDescription());
            viewHolder.location.setText(model.getLocationName());

            return convertView;
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




