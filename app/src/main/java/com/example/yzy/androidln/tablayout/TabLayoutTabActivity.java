package com.example.yzy.androidln.tablayout;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yzy.androidln.R;

public class TabLayoutTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_manual);

        TabLayout tlTabs = findViewById(R.id.tl_tabs);
        //设置icon和text
        tlTabs.addTab(tlTabs.newTab().setText("Tab 1"));
        tlTabs.addTab(tlTabs.newTab().setText("Tab 2").setIcon(R.mipmap.ic_launcher));
        //选择Tab
        TabLayout.Tab tab = tlTabs.newTab().setText("Tab 3");
        tlTabs.addTab(tab);
        tab.select();
        //设置自定义view
        TabLayout.Tab tab1 = tlTabs.newTab().setCustomView(R.layout.view_tab_custom);
        tab1.setText("Tab 4");
        tab1.setIcon(R.mipmap.ic_launcher);
        tlTabs.addTab(tab1);
    }
}
