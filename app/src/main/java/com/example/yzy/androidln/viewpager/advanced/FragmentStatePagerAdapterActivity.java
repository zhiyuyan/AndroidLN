package com.example.yzy.androidln.viewpager.advanced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yzy.androidln.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzy on 2017/8/3.
 */

public class FragmentStatePagerAdapterActivity extends AppCompatActivity {

    private static final String TAG = FragmentStatePagerAdapterActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        List<Integer> photos = getData();
        ViewPager viewPager = findViewById(R.id.view_pager);
        MyFragmentStatePagerAdapter advancedAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), photos);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(60);
        viewPager.setAdapter(advancedAdapter);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position <= 0) {
                    float alpha = 0.7f * (1 + position) + 0.3f;
                    page.setAlpha(alpha);
                } else {
                    float alpha = 0.7f * (1 - position) + 0.3f;
                    page.setAlpha(alpha);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "position:" + position + " ,positionOffset:" + positionOffset + " ,positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
            }
        });
    }

    private List<Integer> getData() {
        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.image1);
        data.add(R.drawable.image2);
        data.add(R.drawable.image3);
        data.add(R.drawable.image4);
        data.add(R.drawable.image5);
        data.add(R.drawable.image6);
        data.add(R.drawable.image7);
        data.add(R.drawable.image8);
        return data;
    }

    /**
     * 根据fraction获取颜色，实现渐变
     */
    public static int getColor(float fraction, Object startColor, Object endColor) {
        int startInt = (Integer) startColor;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endColor;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

}
