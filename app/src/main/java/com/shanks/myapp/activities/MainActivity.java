package com.shanks.myapp.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanks.myapp.fragment.MainFragment;
import com.shanks.myapp.models.OfferModel;
import com.shanks.myapp.models.User_Get_Set_class;
import com.shanks.myapp.models.offerListModel;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shanks.myapp.R;
import com.shanks.myapp.edit_flow.EditRegister;
import com.shanks.myapp.fragment.CategoryFragment;
import com.shanks.myapp.models.CategoryModel;
import com.shanks.myapp.models.OfferDetailMainModel;
import com.shanks.myapp.models.offerImagesListModel;
import com.shanks.myapp.registration_flow.Login;
import com.shanks.myapp.utils.CallService;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;
import com.squareup.picasso.Picasso;
import com.shanks.myapp.models.offerdetailModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.shanks.myapp.utils.Utils.aadharnumber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<CategoryModel> categoryList = new ArrayList<>();
    private ArrayList<offerListModel> offerList = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int count;
    OfferModel model;
    Session session;
    MainActivity.PopularPagerAdapter PopularPagerAdapter1;
    CountDownTimer bottomtimer;
    CountDownTimer timer;
    ViewPager image_viewPager1;
    ImageView image;
    String pincode,firstname,address,gender,qualificationid,catid,mobileNumber,
            stateid,cityid,emailId,statename,pannumber,lastname,dob,userimage,Aaadharnumber,username;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        synchronized (this) {
            initViews();
        }

        synchronized (this) {
            getCategories();
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        image_viewPager1 = (ViewPager) findViewById(R.id.image_viewPager1);
        //image_viewPager1.putExtra("name", offerListModel.);

        //fetchOffers();
    }

    void fetchOffers()
    {
        String url =  Utils.BASE_URL+ Utils.OFFER_DETAIL1
                + "?userid=" + session.getUserId()
//                + "&offerid=" + model.getOfferId()
                + "&categoryid=" + Utils.CATEGORY_ID;
        Log.wtf("ankit", url);
        CallService service = new CallService(this, url, Utils.GET, true, new CallService.OnServicecall()
        {
            @Override
            public void onServicecall(String response) {

                try{
                    JSONObject jobj = new JSONObject(response);
                    String responseCode = jobj.getString("responseCode");

                    Log.d("OfferList","ResponseCode : "+responseCode);
                    if(responseCode.equalsIgnoreCase("2001")){
                        Log.d("OfferList","Response : "+response.toString());
                        PARSE_JSON(response);
                        Log.d("OfferList","Size : "+offerList.size());
                        PopularPagerAdapter1 = new MainActivity.PopularPagerAdapter(getSupportFragmentManager(), offerList);
                        image_viewPager1.setAdapter(PopularPagerAdapter1);
                        startScroller();
                    }

                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        service.execute();
    }


    private OfferDetailMainModel PARSE_JSON(String result) throws Exception
    {
        try{
            offerList.clear();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        OfferDetailMainModel OfferDetailMainModel = new OfferDetailMainModel();
        JSONObject main_job = new JSONObject(result);

        ArrayList<offerdetailModel> offerdetailModelList = new ArrayList<>();
        JSONArray offerdetailjarr = main_job.getJSONArray("offerList");
        for (int g = 0; g < offerdetailjarr.length(); g++) {

            offerdetailModel offerdetailModel = new offerdetailModel();

            JSONObject offerdetail = offerdetailjarr.getJSONObject(g);
            ArrayList<offerImagesListModel> offerImagesListModelList = new ArrayList<>();

            try {
                JSONArray offerImagesList = offerdetail.getJSONArray("bannerImagesList");
                for (int j = 0; j < offerImagesList.length(); j++) {
                    JSONObject i_job = offerImagesList.getJSONObject(j);
                    offerListModel offerImg = new offerListModel();
                    //offerListModel offerUrl = new offerListModel();
                    offerImg.setOfferImage(i_job.getString("bannerImage"));
//                    offerList.add(offerImg);
                    offerImg.setUrl(i_job.getString("Url"));
                    offerList.add(offerImg);
                }
                offerdetailModel.setOfferImagesList(offerImagesListModelList);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


//        OfferDetailMainModel.setOfferdetail(offerdetailModelList);

        // ends here

        return OfferDetailMainModel;
    }

    class PopularPagerAdapter extends FragmentPagerAdapter {
        ArrayList<offerListModel> offerListModel = new ArrayList<>();

        public PopularPagerAdapter(FragmentManager manager, ArrayList<offerListModel> offerListModel) {
            super(manager);
            this.offerListModel = offerListModel;
        }

        @Override
        public Fragment getItem(int position) {

            return MainFragment.newInstance(offerListModel.get(position));

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

    private void startScroller() {

        if(timer!=null)
            timer.cancel();

        timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (count < (PopularPagerAdapter1.getCount())) {
                    image_viewPager1.setCurrentItem(count, true);
                    count++;

                } else {
                    image_viewPager1.setCurrentItem(0, true);
                    count = 0;
                }
                startScroller();

            }
        }.start();
    }

    private void initViews() {

        session = Session.getSession(MainActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // navigational header
        View header = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        ImageView profile_image = (ImageView) header.findViewById(R.id.profile_image);
        Picasso.with(MainActivity.this).load(Utils.BASE_IMAGE_URL + session.getUserimage()).error(R.drawable.profile).into(profile_image);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);
        user_name.setText(session.getUsername());

        TextView mobile_number = (TextView) header.findViewById(R.id.mobile_number);
        mobile_number.setText(session.getMobilenumber());

        ImageView iv_editprofile = (ImageView) header.findViewById(R.id.iv_editprofile);
        iv_editprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditRegister.class);
                startActivity(intent);
            }
        });

        navigationView.addHeaderView(header);
        // ends here

        viewPager = (ViewPager) findViewById(R.id.viewpager);

//        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void getCategories() {
        String url = Utils.BASE_URL + Utils.CATEGORIES;
        CallService service = new CallService(MainActivity.this, url, Utils.GET, true, new CallService.OnServicecall() {
            @Override
            public void onServicecall(String response) {
                try {
                    synchronized (this) {
                        categoryList.addAll(parseJSON(response));
                    }


                    if (categoryList != null && categoryList.size() > 0) {

                        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), categoryList);
                        //   adapter.notifyDataSetChanged();
                        viewPager.setAdapter(adapter);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        // Intent in = getIntent();
                        //String int_Str = getIntent().getStringExtra("Closed_Lead");
                        Utils.CATEGORY_ID = categoryList.get(0).getId();

                        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
                        {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
//                                adapter.notifyDataSetChanged();

                                viewPager.setCurrentItem(tab.getPosition());
                                Utils.CATEGORY_ID = categoryList.get(tab.getPosition()).getId();
                                fetchOffers();
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {
                                Utils.CATEGORY_ID = categoryList.get(0).getId();
                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {
                                Utils.CATEGORY_ID = categoryList.get(tab.getPosition()).getId();
                            }
                        });
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        service.execute();
    }

    private ArrayList<CategoryModel> parseJSON(String response) throws Exception {
        ArrayList<CategoryModel> categoryList = new ArrayList<>();
        JSONObject jobj = new JSONObject(response);
        JSONArray jsonArray = jobj.getJSONArray("categoryList");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject innerJob = jsonArray.getJSONObject(i);
            CategoryModel model = new CategoryModel();
            model.setName("" + innerJob.getString("name"));
            model.setSelectedCatImage("" + innerJob.getString("selectedCatImage"));
            model.setId("" + innerJob.getString("id"));
            model.setNonSelectedCatImage("" + innerJob.getString("nonSelectedCatImage"));
            tabLayout.addTab(tabLayout.newTab().setText(model.getName()));
            categoryList.add(model);
        }
        return categoryList;
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.isChecked()) {
            item.setChecked(false);
        }

        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_offer) {
//
//            Intent in = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(in);
//
//            // Handle the camera action
//        } else

        if (id == R.id.nav_mylead) {

            Intent in = new Intent(MainActivity.this, MyLeads.class);
            startActivity(in);

        } else if (id == R.id.nav_payment) {

            Intent in = new Intent(MainActivity.this, MyPayment.class);
            startActivity(in);

        } else if (id == R.id.nav_customer) {

            Intent in = new Intent(MainActivity.this, MyCustomer.class);
            startActivity(in);

        } else if (id == R.id.nav_pref) {

            Intent in = new Intent(MainActivity.this, Preferences.class);
            startActivity(in);
        } else if (id == R.id.nav_pritest) {

//            String url = "http://skillworld.wiziq.com/";
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//            Intent in = new Intent(MainActivity.this, LearningActivity.class);
//            startActivity(in);
            try{
                if(isPackageExisted("learning.skillworld.in")){
                    callingDeeplinkIntent();
                } else {
                    String url = "https://play.google.com/store/apps/details?id=learning.skillworld.in";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }catch (Exception ex){
                ex.printStackTrace();
                String url = "https://play.google.com/store/apps/details?id=learning.skillworld.in";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }


        } else if (id == R.id.nav_tnc) {

//            Intent in = new Intent(MainActivity.this, TermsandCondition.class);
//            startActivity(in);
            String url = "http://skillworld.in/user-agreement.php";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
//        else if (id == R.id.nav_legal) {
//            //   getSupportActionBar().setTitle(item.getTitle());
//
//        }
        else if (id == R.id.nav_rate) {
            String url = "https://play.google.com/store/apps/details?id=com.shanks.myapp#details-reviews";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

            // getSupportActionBar().setTitle(item.getTitle());

        } else if (id == R.id.nav_logout) {
            // getSupportActionBar().setTitle(item.getTitle());

            Toast.makeText(getBaseContext(), "You are Logged Out", Toast.LENGTH_LONG).show();
            Intent in = new Intent(MainActivity.this, Login.class);
            session.setUserId("");
            startActivity(in);

        } else if (id == R.id.nav_help) {
            // getSupportActionBar().setTitle(item.getTitle());
//
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Need Help ?")
                    .setMessage("Pl write to Query@skillworld.in from your registered mail id.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete

                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto", "Query@skillworld.in", null));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Skill world");
                            startActivity(Intent.createChooser(emailIntent, "Send email..."));

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<CategoryModel> cateModel = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager, ArrayList<CategoryModel> cateModel) {
            super(manager);
            this.cateModel = cateModel;
        }

        @Override
        public Fragment getItem(int position) {

            return CategoryFragment.newInstance(cateModel.get(position));

        }

        @Override
        public int getCount() {
            //return mFragmentList.size();
            return cateModel.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return cateModel.get(position).getName();
        }

    }

    private void callingDeeplinkIntent() throws Exception{
        Uri uri = Uri.parse("learning.skillworld.in://data?email=" + session.getEmail() +
                "&name=" + session.getFullname() +
                "&phone=" + session.getMobilenumber() +
                "&username=" + session.getUsername() +
                "&password=" + session.getPassowrd());
        System.out.println("URL>>>"+uri);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(uri);
        startActivity(i);
    }

    public boolean isPackageExisted(String targetPackage){
        PackageManager pm=getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}