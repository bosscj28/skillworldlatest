package com.shanks.myapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.shanks.myapp.R;
import com.shanks.myapp.fragment.FragmentOpenLead;

import com.shanks.myapp.fragment.FragmentCloseLead;
import com.shanks.myapp.fragment.FragmentRejectedLead;

public class MyLeads extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_leads);
        synchronized (this){
            initViews();
        }
    }

    private void initViews(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Open"));
        tabLayout.addTab(tabLayout.newTab().setText("Closed"));
        tabLayout.addTab(tabLayout.newTab().setText("Rejected"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return FragmentOpenLead.newInstance();
                case 1:
                    return FragmentCloseLead.newInstance();
                case 2:
                    return FragmentRejectedLead.newInstance();
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            //return mFragmentList.size();
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Open";

                case 1:
                    return "Close";

                case 2:
                    return "Rejected";

                default:
                    return null;


            }
        }
    }
}
