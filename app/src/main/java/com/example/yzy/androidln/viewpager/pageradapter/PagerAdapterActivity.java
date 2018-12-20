package com.example.yzy.androidln.viewpager.pageradapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.yzy.androidln.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzy on 2018/12/20 0020.
 */

public class PagerAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_adapter);

        ViewPager viewPager = findViewById(R.id.view_pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("我是" + i);
        }
        myPagerAdapter.setData(list);
        viewPager.setAdapter(myPagerAdapter);
    }
}
