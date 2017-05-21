package application.minor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class swipeadapter extends FragmentStatePagerAdapter {
    public swipeadapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {


        if(position==0)

        {
            Fragment fragment = new Fragment1();
            return fragment;
        }
        else if(position==1)
        {
            Fragment fragment = new Fragment2();
            return fragment;
        }
        else if(position==2)
        {
            Fragment fragment = new Fragment3();
            return fragment;
        }
        else if(position==3)
        {
            Fragment fragment = new Fragment4();
            return fragment;
        }
     return null ;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
