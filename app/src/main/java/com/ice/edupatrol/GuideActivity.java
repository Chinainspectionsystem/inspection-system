package com.ice.edupatrol;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GuideActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setActionBar setActionBar=new setActionBar();
        setActionBar.setActionBar2(getSupportActionBar(),getResources());
        init();
    }


    public void  init()
    {
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position==0) {
                    GuideFragment1 fragment1 = new GuideFragment1();
                    return fragment1;
                }
                if(position==1) {
                    GuideFragment2 fragment2 = new GuideFragment2();
                    return fragment2;
                }
                if(position==2) {
                    GuideFragment3 fragment3 = new GuideFragment3();
                    return fragment3;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }
}
