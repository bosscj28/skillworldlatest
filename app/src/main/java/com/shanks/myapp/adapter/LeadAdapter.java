package com.shanks.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.shanks.myapp.models.LeadModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ankitpurohit on 19-02-2017.
 */

public class LeadAdapter extends ArrayAdapter<LeadModel> {
    private Context context;
    ArrayList<LeadModel> leadList;
//
//    private int lastPosition = -1;

    public LeadAdapter(Context context, ArrayList<LeadModel> leadList) {
        super(context, R.layout.lead_list_info, leadList);
        this.context = context;
        this.leadList=leadList;
    }


    static class ViewHolder {
        TextView tv_leadId,tv_buyerName,lead_phone_number,tv_planToBuyOn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final LeadModel model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.lead_list_info, parent, false);

            viewHolder.tv_leadId = (TextView)convertView.findViewById(R.id.tv_leadId);
            viewHolder.tv_buyerName = (TextView)convertView.findViewById(R.id.tv_buyerName);
            viewHolder.lead_phone_number = (TextView)convertView.findViewById(R.id.lead_phone_number);
            viewHolder.tv_planToBuyOn = (TextView)convertView.findViewById(R.id.tv_planToBuyOn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_leadId.setText(model.getLeadid());
        viewHolder.tv_buyerName.setText(model.getBuyerName());
        viewHolder.lead_phone_number.setText(model.getBuyerPhoneNo());
        viewHolder.tv_planToBuyOn.setText(model.getPlanToBuyOn());

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