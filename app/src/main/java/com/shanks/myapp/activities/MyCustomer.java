package com.shanks.myapp.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.shanks.myapp.R;
import com.shanks.myapp.edit_flow.EditRegister;
import com.shanks.myapp.fragment.EditFragment;
import com.shanks.myapp.fragment.EditFragment2;
import com.shanks.myapp.fragment.FragmentAdd;
import com.shanks.myapp.models.ListModel;
import com.shanks.myapp.registration_flow.Login;
import com.shanks.myapp.utils.Session;
import com.shanks.myapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCustomer extends AppCompatActivity implements FragmentAdd.Communicator {

    TabLayout tabs;
    public ViewPager dashborad_pager;
    FragmentAdd contactFragment;
    EditFragment2 contactList;
    Session session;
    private TabLayout tabLayout;
    public ArrayList<ListModel> listModels = new ArrayList<>();

    @Override
    public void updateData(String data) {
        // TODO Auto-generated method stub
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try{
            getSupportActionBar().setTitle("SkillWorld");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // fab = (FloatingActionButton) findViewById(R.id.fab);
        init();
    }

    private void init(){
        session = Session.getSession(this);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        contactList = EditFragment2.newInstance();
        contactFragment = FragmentAdd.newInstance();
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("View/Edit"));
        tabLayout.addTab(tabLayout.newTab().setText("Add"));
        dashborad_pager = (ViewPager) findViewById(R.id.pager);
        dashborad_pager.setOffscreenPageLimit(2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        dashborad_pager.setAdapter(adapter);
        dashborad_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                dashborad_pager.setCurrentItem(position);

                /*if(position==0 && EditFragment.mHandler != null){
                    EditFragment.mHandler.sendEmptyMessage(2);
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        System.out.println("getCATEGORY>>>>" + Utils.CUSTOMER_ID);
//        System.out.println("getCATEGORY>>>>" + session.getCATEGORY());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){

                case 0:
                    return contactList;
                case 1:
                    return contactFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            //return mFragmentList.size();
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "View/Edit";
                case 1:
                    return "Add";

                default:
                    return null;


            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
