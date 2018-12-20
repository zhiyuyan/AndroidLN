package com.example.yzy.androidln.viewpager.pagetransformer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by yzy on 2017/10/1.
 */

public class TransformPagerAdapter extends FragmentStatePagerAdapter {

    public TransformPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TransformFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 10;
    }
}
