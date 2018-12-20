package com.example.yzy.androidln.viewpager.pageradapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yzy on 2017/8/2.
 */

public class MyPagerAdapter extends PagerAdapter {
    private static final String TAG = MyPagerAdapter.class.getSimpleName();

    List<String> mData;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem: position " + position);
        TextView textView = new TextView(container.getContext());
        textView.setText(mData.get(position));
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem: position " + position);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setData(List<String> data) {
        mData = data;
    }
}
