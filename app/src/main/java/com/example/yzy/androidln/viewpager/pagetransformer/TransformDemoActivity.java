package com.example.yzy.androidln.viewpager.pagetransformer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yzy.androidln.R;

public class TransformDemoActivity extends AppCompatActivity {

    private static final float MIN_SCALE = 0.9f;
    private static final int PAGE_OFFSET = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform_demo);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TransformPagerAdapter advancedAdapter = new TransformPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(60);
        viewPager.setAdapter(advancedAdapter);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();
                float scaleFactor;
                if (position < -1) { // [-Infinity,-1)
                    page.setAlpha(0);
                } else if (position <= 0) {
                    page.setAlpha(1 + position);
                    scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                            * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else {
                    page.setAlpha(1 + position);
                    if (position <= 3) {
                        if (position <= 2) {
                            page.setAlpha(1);
                        } else {
                            page.setAlpha(1 - (position - 2));
                        }
                        page.setTranslationX(pageWidth * -position);
                    } else {
                        page.setAlpha(0);
                    }
                    scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                            * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                    page.setTranslationY(-position * PAGE_OFFSET - (pageHeight - pageHeight * scaleFactor) / 2);
                }
            }
        });
    }
}
