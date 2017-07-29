package com.shanks.myapp.adapter;

/**
 * Created by fg on 3/16/2017.
 */
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.shanks.myapp.R;
//import com.shanks.myapp.models.ListModel;
//
//import java.util.ArrayList;
//import static android.R.id.text1;
//
///********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
//public class WalletAdapter extends BaseAdapter implements DialogInterface.OnClickListener {
//
//    /*********** Declare Used Variables *********/
//    private Activity activity;
//    private ArrayList data;
//    private static LayoutInflater inflater=null;
//    public Resources res;
//    ListModel tempValues=null;
//    Context context;
//    int received,paid;
//    int i=0;
//
//    /*************  CustomAdapter Constructor *****************/
//    public WalletAdapter(Activity a,ArrayList d,Resources resLocal) {
//
//        /********** Take passed values **********/
//        activity = a;
//        data=d;
//        res = resLocal;
//
//        /***********  Layout inflator to call external xml layout () ***********/
//        inflater = (LayoutInflater)activity.
//                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//
//    /******** What is the size of Passed Arraylist Size ************/
//    public int getCount() {
//
//        return data.size();
//    }
//
//    public Object getItem(int position) {
//        return position;
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//
//    }
//
//
//
//    /********* Create a holder Class to contain inflated xml file elements *********/
//    public static class ViewHolder{
//
//        public TextView text;
//        public TextView text1;
//
//    }
//
//    /****** Depends upon data size called for each row , Create each ListView row *****/
//    public View getView(final int position, View convertView, final ViewGroup parent) {
//
//        View vi = convertView;
//        final ViewHolder holder;
//        if(convertView==null) {
//
//            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
//            vi = inflater.inflate(R.layout.fragmentoneitem, null);
//
//            /****** View Holder Object to contain tabitem.xml file elements ******/
//            holder = new ViewHolder();
//            holder.text = (TextView) vi.findViewById(R.id.tv_Name);
//            holder.text1 = (TextView) vi.findViewById(R.id.tv_Amount);
//
//
////            vi.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////
////                    // TODO Auto-generated method stub
////
////               }
////           });
//
//            /************  Set holder with LayoutInflater ************/
//            vi.setTag( holder );
//        }
//        else
//            holder=(ViewHolder)vi.getTag();
//
//        if(data.size()<=0)
//        {
//            // holder.text.setText("No Data");
//
//        }
//        else
//        {
//            /***** Get each Model object from Arraylist ********/
//            tempValues=null;
//            tempValues = ( ListModel ) data.get( position );
//
//            Log.d("ankit",tempValues.getReceivepaid());
//            if(tempValues.getReceivepaid().trim().equalsIgnoreCase("recieved")){
//                holder.text1.setTextColor(Color.GREEN);
//            } else {
//                holder.text1.setTextColor(Color.RED);
//            }
//
////            if (text1 == received) {
////                holder.text1.setTextColor(Color.RED);
////            }
////            else if (text1 == paid)
////            {
////                holder.text1.setTextColor(Color.BLACK);
////            }
//            /************  Set Model values in Holder elements ***********/
//
//            holder.text.setText( tempValues.getTv_name() );
//            holder.text1.setText( tempValues.getTv_Amount() );
//
//            /******** Set Item Click Listner for LayoutInflater for each row *******/
//
//            // vi.setOnClickListener(new OnItemClickListener( position ));
//        }
//        return vi;
//    }



    /********* Called when Item click in ListView ************/
//    private class OnItemClickListener  implements View.OnClickListener {
//        private int mPosition;
//
//        OnItemClickListener(int position){
//            mPosition = position;
//        }
//
//        @Override
//        public void onClick(View arg0) {
//
//
//            DashBoard  sct = (DashBoard)activity;
//
//            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
//
//            sct.onItemClick(mPosition);
//        }
//............................................................    //
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shanks.myapp.R;
import com.shanks.myapp.models.ListModel;
import java.util.ArrayList;
import java.util.Comparator;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/

public class WalletAdapter extends ArrayAdapter<ListModel> {
    private Context context;
    ArrayList<ListModel> leadList;
//
//    private int lastPosition = -1;

    public WalletAdapter(Context context, ArrayList<ListModel> leadList) {
        super(context, R.layout.fragmentoneitem, leadList);
        this.context = context;
        this.leadList = leadList;
        Log.d("CheckList","Size Check : "+leadList.size());
    }

    static class ViewHolder {
        public TextView text;
        public TextView text1;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ListModel model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        com.shanks.myapp.adapter.WalletAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new com.shanks.myapp.adapter.WalletAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.fragmentoneitem, parent, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tv_Name);
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.tv_Amount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (com.shanks.myapp.adapter.WalletAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(model.getTv_name());
        viewHolder.text1.setText(model.getTv_Amount());
        return convertView;
    }

}
