package com.shanks.myapp.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.shanks.myapp.R;
import com.shanks.myapp.models.OfferModel;
import com.shanks.myapp.models.userInfoModel;
import com.shanks.myapp.utils.CallService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.shanks.myapp.fragment.ImageFragment;
import com.shanks.myapp.fragment.PopularFragment;
import com.shanks.myapp.models.OfferDetailMainModel;
import com.shanks.myapp.models.offerImagesListModel;
import com.shanks.myapp.models.offerListModel;
import com.shanks.myapp.models.offerdetailModel;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;

public class Offer extends AppCompatActivity {

    Button refer_button,accept_button;
    OfferModel model;
    Session session;

    // views
    TextView displayoffername,date,description,amount,displaylocation,descsalespitch;

    // poppup text views
    TextView viewmore,viewmoreLocation,view_sales,productvideo,testrequired,tnc;

    // view pager
    ViewPager image_viewPager,popular_offers_view_pager;
    private String userStatus = "";
    CheckBox selecttnc;
    String offerId = "";
    RelativeLayout loading_relative;
    ScrollView scroll;
    ImageView arrow_left;
    ImagePagerAdapter ImagePagerAdapter;
    CountDownTimer timer;
    int count;
    PopularPagerAdapter PopularPagerAdapter;
    CountDownTimer bottomtimer;
    int bottomcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        model = getIntent().getParcelableExtra("model");

        if(model!=null)
        init();
    }

    private void init(){

        session = Session.getSession(Offer.this);

        loading_relative = (RelativeLayout)findViewById(R.id.loading_relative);
        scroll = (ScrollView)findViewById(R.id.scroll);

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String url =  Utils.BASE_URL+ Utils.OFFER_DETAIL
                + "?userid=" + session.getUserId()
                + "&offerid=" + model.getOfferId()
                + "&categoryid=" + Utils.CATEGORY_ID;

        CallService service = new CallService(Offer.this, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                Log.d("ankit","offer detail::::"+response);
                try{

                    synchronized (this){
                        populateViews(PARSE_JSON(response));
                    }

                    loading_relative.setVisibility(View.GONE);
                    scroll.setVisibility(View.VISIBLE);

                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        service.execute();
        refer_button = (Button)findViewById(R.id.refer_button);
        refer_button.setEnabled(false);
        refer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(userStatus.equalsIgnoreCase("")){
                  Log.d("Bhanu" , "First");
                  // Toast.makeText(Offer.this, "Please clear the Learning First", Toast.LENGTH_SHORT).show();
                } else if(userStatus.equalsIgnoreCase(Utils.USER_STATUS_INITIAL)){
                  Log.d("Bhanu" , "Second");
                    Toast.makeText(Offer.this, "Please clear the Learning First", Toast.LENGTH_SHORT).show();
                } else {
                  Log.d("Bhanu" , "Refer");
                    Intent intent = new Intent(Offer.this, Refer.class);
                    intent.putExtra("offerid",offerId);
                    startActivity(intent);
                    finish();
                }
            }
        });

        selecttnc = (CheckBox)findViewById(R.id.selecttnc);
        selecttnc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    accept_button.setBackgroundColor(getResources().getColor(R.color.terms_blue));
                }
            }
        });

        accept_button = (Button)findViewById(R.id.accept_button);
        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selecttnc.isChecked()){
                    refer_button.setBackgroundColor(getResources().getColor(R.color.terms_blue));
                    refer_button.setEnabled(true);
                }

                else
                    Toast.makeText(Offer.this,"Please accept the terms and conditions",Toast.LENGTH_LONG).show();
            }
        });

        viewmore = (TextView)findViewById(R.id.viewmore);

        viewmoreLocation = (TextView)findViewById(R.id.viewmoreLocation);
        view_sales = (TextView)findViewById(R.id.view_sales);

        productvideo = (TextView)findViewById(R.id.productvideo);

        testrequired = (TextView)findViewById(R.id.testrequired);

        tnc = (TextView)findViewById(R.id.tnc);
    }

    private void populateViews(OfferDetailMainModel OfferDetailMainModel){

        ArrayList<userInfoModel> userInfoModel = OfferDetailMainModel.getUserInfoModel();
        ArrayList<offerListModel> offerList = OfferDetailMainModel.getOfferList();
        ArrayList<offerdetailModel> offerdetailModelList = OfferDetailMainModel.getOfferdetail();

        offerdetailModel offerdetailModel = offerdetailModelList.get(0);

        // set offer name
        displayoffername = (TextView)findViewById(R.id.displayoffername);
        displayoffername.setText(offerdetailModel.getOffername());

        // set date
        date = (TextView)findViewById(R.id.date);
        date.setText(parseDateToddMMyyyy(offerdetailModel.getTodate()));

        // set description
        description = (TextView)findViewById(R.id.description);
        description.setText(offerdetailModel.getLongdesc());

        final String longDec = offerdetailModel.getLongdesc();
        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Offer.this).setTitle("Offer Description")
                        .setMessage(longDec)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        }).show();
            }
        });

        final String locations = offerdetailModel.getLocationName();
        viewmoreLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Offer.this).setTitle("Locations")
                        .setMessage(locations)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        }).show();
            }
        });

        // set amount
        amount = (TextView)findViewById(R.id.amount);
        if(offerdetailModel.getReferralBonusType().equalsIgnoreCase("Percentage")){
            amount.setText(offerdetailModel.getReferralAmount()+" %");
        } else if(offerdetailModel.getReferralBonusType().equalsIgnoreCase("Amount")){
            amount.setText(offerdetailModel.getReferralAmount()+" Rs");
        }
        // set displaylocation
        displaylocation = (TextView)findViewById(R.id.displaylocation);
        displaylocation.setText(offerdetailModel.getLocationName());

        // set sales pitch
        descsalespitch = (TextView)findViewById(R.id.descsalespitch);
        descsalespitch.setText(offerdetailModel.getSalesPitch());

        final String salespitch = offerdetailModel.getSalesPitch();
        view_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(salespitch));
//                startActivity(i);
//                finish();
                new AlertDialog.Builder(Offer.this).setTitle("Sales Pitch")
                        .setMessage(salespitch)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        }).show();
            }
        });


        final String product_videos = offerdetailModel.getProductVideos();
        Log.d("ankit",product_videos);

        productvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProductDialog(product_videos);
            }
        });

        final String test_required = offerdetailModel.getTestsRequired();
        testrequired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Offer.this).setTitle("Test Required")
                        .setMessage(test_required)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        }).show();
            }
        });

        final String tnc_required = offerdetailModel.getTerms();
        tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Offer.this).setTitle("Terms and Conditions")
                        .setMessage(tnc_required)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        }).show();
            }
        });

        // set image view pager

        image_viewPager = (ViewPager)findViewById(R.id.image_viewPager);
        ImagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(),offerdetailModel.getOfferImagesList());
        image_viewPager.setAdapter(ImagePagerAdapter);
        startScroller();
        // set popular_offers_view_pager
        popular_offers_view_pager = (ViewPager)findViewById(R.id.popular_offers_view_pager);
        PopularPagerAdapter = new PopularPagerAdapter(getSupportFragmentManager(),offerList);
        popular_offers_view_pager.setAdapter(PopularPagerAdapter);

        bottomstartScroller();
    }

    private OfferDetailMainModel PARSE_JSON(String response) throws Exception{
        OfferDetailMainModel OfferDetailMainModel = new OfferDetailMainModel();
        JSONObject main_job = new JSONObject(response);

        // userInfo starts here
        ArrayList<userInfoModel> userInfoModelList = new ArrayList<>();
        JSONArray userInfo = main_job.getJSONArray("userInfo");
        for(int l=0;l<userInfo.length();l++){

            JSONObject i_job = userInfo.getJSONObject(l);

            String userstatus = i_job.getString("userstatus");

            // set user status
            userStatus = userstatus;

            String authority = i_job.getString("authority");
            String userid = i_job.getString("userid");
            String enabled = i_job.getString("enabled");

            userInfoModel model = new userInfoModel();
            model.setUserstatus(userstatus);
            model.setAuthority(authority);
            model.setUserid(userid);
            model.setEnabled(enabled);
            userInfoModelList.add(model);
        }


        OfferDetailMainModel.setUserInfoModel(userInfoModelList);
        // ends her

        // offerList starts here
        JSONArray offerList = main_job.getJSONArray("offerList");
        ArrayList<offerListModel> offerListMainModel = new ArrayList<>();
        for (int i = 0;i<offerList.length();i++){
            JSONObject i_job = offerList.getJSONObject(i);
            String referralAmount = i_job.getString("referralAmount");
            String offerName = i_job.getString("offerName");
            String locationName = i_job.getString("locationName");
            String detailedPageUrl = i_job.getString("detailedPageUrl");
            String offerStatus = i_job.getString("offerStatus");
            String corporateId = i_job.getString("corporateId");
            String offerShortDescription = i_job.getString("offerShortDescription");
            String fromdate = i_job.getString("fromdate");
            String referralBonusType = i_job.getString("referralBonusType");
            String createdDate = i_job.getString("createdDate");
            String todate = i_job.getString("todate");
            String createdby = i_job.getString("createdby");
            String offerDescription = i_job.getString("offerDescription");
            String offerImage = i_job.getString("offerImage");
            String offerId = i_job.getString("offerId");
            String categoryId = i_job.getString("categoryId");

            offerListModel offerListModel = new offerListModel();
            offerListModel.setReferralAmount(referralAmount);
            offerListModel.setOfferName(offerName);
            offerListModel.setLocationName(locationName);
            offerListModel.setDetailedPageUrl(detailedPageUrl);
            offerListModel.setOfferStatus(offerStatus);
            offerListModel.setCorporateId(corporateId);
            offerListModel.setOfferShortDescription(offerShortDescription);
            offerListModel.setFromdate(fromdate);
            offerListModel.setReferralBonusType(referralBonusType);
            offerListModel.setCreatedDate(createdDate);
            offerListModel.setTodate(todate);
            offerListModel.setCreatedby(createdby);
            offerListModel.setOfferDescription(offerDescription);
            offerListModel.setOfferImage(offerImage);
            offerListModel.setOfferId(offerId);
            offerListModel.setCategoryId(categoryId);

            offerListMainModel.add(offerListModel);
        }
        OfferDetailMainModel.setOfferList(offerListMainModel);
        // ends here

        // offerdetail starts here

        ArrayList<offerdetailModel> offerdetailModelList = new ArrayList<>();
        JSONArray offerdetailjarr = main_job.getJSONArray("offerdetail");
        for (int g=0;g<offerdetailjarr.length();g++){

            offerdetailModel offerdetailModel = new offerdetailModel();

            JSONObject offerdetail = offerdetailjarr.getJSONObject(g);

            String referralAmount = offerdetail.getString("referralAmount");
            offerdetailModel.setReferralAmount(referralAmount);

            String salesPitch = offerdetail.getString("salesPitch");
            offerdetailModel.setSalesPitch(salesPitch);

            String testsRequired = offerdetail.getString("testsRequired");
            offerdetailModel.setTestsRequired(testsRequired);

            String locationName = offerdetail.getString("locationName");
            offerdetailModel.setLocationName(locationName);

            String shortdesc = offerdetail.getString("shortdesc");
            offerdetailModel.setShortdesc(shortdesc);

            String productVideos = offerdetail.getString("productVideos");
            offerdetailModel.setProductVideos(productVideos);

            ArrayList<offerImagesListModel> offerImagesListModelList = new ArrayList<>();

            try {
                JSONArray offerImagesList = offerdetail.getJSONArray("offerImagesList");
                for (int j=0;j<offerImagesList.length();j++){
                    JSONObject i_job = offerImagesList.getJSONObject(j);
                    String imgname = i_job.getString("imgname");
                    String imgid = i_job.getString("imgid");
                    String imgtype = i_job.getString("imgtype");

                    offerImagesListModel offerImagesListModel = new offerImagesListModel();
                    offerImagesListModel.setImgname(imgname);
                    offerImagesListModel.setImgid(imgid);
                    offerImagesListModel.setImgtype(imgtype);
                    offerImagesListModelList.add(offerImagesListModel);
                }
                offerdetailModel.setOfferImagesList(offerImagesListModelList);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            String offerStatus = offerdetail.getString("offerStatus");
            offerdetailModel.setOfferStatus(offerStatus);

            String fromdate = offerdetail.getString("fromdate");
            offerdetailModel.setFromdate(fromdate);

            String longdesc = offerdetail.getString("longdesc");
            offerdetailModel.setLongdesc(longdesc);

            String referralBonusType = offerdetail.getString("referralBonusType");
            offerdetailModel.setReferralBonusType(referralBonusType);

            String offerimage = offerdetail.getString("offerimage");
            offerdetailModel.setOfferimage(offerimage);

            String offername = offerdetail.getString("offername");
            offerdetailModel.setOffername(offername);

            String todate = offerdetail.getString("todate");
            offerdetailModel.setTodate(todate);

            String terms = offerdetail.getString("terms");
            offerdetailModel.setTerms(terms);

            String offerid = offerdetail.getString("offerid");
            offerId = offerid;
            offerdetailModel.setOfferid(offerid);

            String categoryname = offerdetail.getString("categoryname");
            offerdetailModel.setCategoryname(categoryname);

            String corporatename = offerdetail.getString("corporatename");
            offerdetailModel.setCorporatename(corporatename);

            offerdetailModelList.add(offerdetailModel);
        }

        OfferDetailMainModel.setOfferdetail(offerdetailModelList);

        // ends here

        return OfferDetailMainModel;
    }


    class ImagePagerAdapter extends FragmentPagerAdapter {
        ArrayList<offerImagesListModel> offerImagesListModel = new ArrayList<>();

        public ImagePagerAdapter(FragmentManager manager, ArrayList<offerImagesListModel> offerImagesListModel) {
            super(manager);
            this.offerImagesListModel = offerImagesListModel;
        }

        @Override
        public Fragment getItem(int position) {

            return ImageFragment.newInstance(offerImagesListModel.get(position));

        }

        @Override
        public int getCount() {
            //return mFragmentList.size();
            return offerImagesListModel.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return offerImagesListModel.get(position).getImgid();
        }
    }

    // popular_offers_view_pager
    class PopularPagerAdapter extends FragmentPagerAdapter {
        ArrayList<offerListModel> offerListModel = new ArrayList<>();

        public PopularPagerAdapter(FragmentManager manager, ArrayList<offerListModel> offerListModel) {
            super(manager);
            this.offerListModel = offerListModel;
        }

        @Override
        public Fragment getItem(int position) {

            return PopularFragment.newInstance(offerListModel.get(position));

        }

        @Override
        public int getCount() {
            //return mFragmentList.size();
            return offerListModel.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return offerListModel.get(position).getOfferName();
        }
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

    private void showProductDialog(String product_videos){

        final Dialog dialog = new Dialog(Offer.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.product_videos);

        String arr[] = product_videos.split(",");
        LinearLayout mail_lin = (LinearLayout)dialog.findViewById(R.id.mail_lin);
        for (int i=0;i<arr.length;i++){
            TextView links = new TextView(Offer.this);
            links.setText(arr[i]);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(10, 10, 10, 10);
            links.setTextColor(getResources().getColor(R.color.terms_blue));
            links.setTag(arr[i]);
            links.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse((String)view.getTag()));
                    startActivity(i);
                }
            });

            mail_lin.addView(links,layoutParams);
        }





        dialog.show();


    }
    private void startScroller(){
        timer = new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(count<(ImagePagerAdapter.getCount())){
                    image_viewPager.setCurrentItem(count,true);
                    count++;

                }else {
                    image_viewPager.setCurrentItem(0,true);
                    count = 0;
                }
                startScroller();

            }
        }.start();
    }
    private void bottomstartScroller(){
        bottomtimer = new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                if(bottomcount<(PopularPagerAdapter.getCount())){
                    popular_offers_view_pager.setCurrentItem(bottomcount,true);
                    bottomcount++;

                }else {
                    image_viewPager.setCurrentItem(0,true);
                    bottomcount = 0;
                }
                bottomstartScroller();

            }
        }.start();
    }
}
