package com.shanks.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shanks.myapp.R;
import com.shanks.myapp.models.PaymentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by ankitpurohit on 21-02-2017.
 */

public class PaymentAdapter extends ArrayAdapter<PaymentModel> {
    private Context context;
    ArrayList<PaymentModel> PaymentList;
//
//    private int lastPosition = -1;

    public PaymentAdapter(Context context, ArrayList<PaymentModel> PaymentList) {
        super(context, R.layout.payment_list_item, PaymentList);
        this.context = context;
        this.PaymentList=PaymentList;
    }


    private static class ViewHolder {
        TextView tv_leadId,tv_offerName,customer_name,tv_leadCloseDate,tv_referrarAmount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final PaymentModel model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.payment_list_item, parent, false);

            viewHolder.tv_leadId = (TextView)convertView.findViewById(R.id.tv_leadId);
            viewHolder.tv_offerName = (TextView)convertView.findViewById(R.id.tv_offerName);
            viewHolder.customer_name = (TextView)convertView.findViewById(R.id.customer_name);
            viewHolder.tv_leadCloseDate = (TextView)convertView.findViewById(R.id.tv_leadCloseDate);
            viewHolder.tv_referrarAmount = (TextView)convertView.findViewById(R.id.tv_referrarAmount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_leadId.setText(model.getLeadId());
        viewHolder.tv_offerName.setText(model.getOfferName());
        viewHolder.customer_name.setText(model.getBuyerName());
        viewHolder.tv_leadCloseDate.setText(model.getLeadClosedDate());
        viewHolder.tv_referrarAmount.setText(model.getReferralAmount());

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