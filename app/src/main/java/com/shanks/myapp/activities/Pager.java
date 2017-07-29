package com.shanks.myapp.activities;

/**
 * Created by fg on 5/19/2017.
 */


    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentStatePagerAdapter;

    import com.shanks.myapp.fragment.EditFragment;
    import com.shanks.myapp.fragment.FragmentAdd;

/**
     * Created by Belal on 2/3/2016.
     */
//Extending FragmentStatePagerAdapter
    public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount= tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    FragmentAdd tab1 = new FragmentAdd();
                    return tab1;
                case 1:
                    EditFragment tab2 = new EditFragment();
                    return tab2;
//                case 2:
//                    Tab3 tab3 = new Tab3();
//                    return tab3;
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return tabCount;
        }
    }

