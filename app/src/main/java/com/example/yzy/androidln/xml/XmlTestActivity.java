package com.example.yzy.androidln.xml;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yzy.androidln.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yzy on 2019/10/16 0016.
 */
public class XmlTestActivity extends Activity implements View.OnClickListener {

    private XmlAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_test);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn_dom).setOnClickListener(this);
        findViewById(R.id.btn_sax).setOnClickListener(this);
        findViewById(R.id.btn_pull).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new XmlAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        List<Person> list = null;
        InputStream is = null;
        AssetManager assetManager = getAssets();
        try {
            is = assetManager.open("persons.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (id) {
            case R.id.btn_dom:
                try {
                    list = new DomParseService().getPersonsByParseXml(is);
                    mAdapter.setData(list);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_sax:
                try {
                    list = new SAXParseService().getPersonsByParseXml(is);
                    mAdapter.setData(list);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_pull:
                try {
                    list = new PullParseService().getPersonsByParseXml(is);
                    mAdapter.setData(list);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class XmlAdapter extends RecyclerView.Adapter<XmlAdapter.ViewHolder> {

        private List<Person> mData;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xml_test, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Person person = mData.get(position);
            holder.tvId.setText(String.valueOf(person.getId()));
            holder.tvName.setText(person.getName());
            holder.tvAge.setText(String.valueOf(person.getAge()));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        public void setData(List<Person> data) {
            mData = data;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvId;
            TextView tvName;
            TextView tvAge;

            public ViewHolder(View itemView) {
                super(itemView);
                tvId = itemView.findViewById(R.id.tv_id);
                tvName = itemView.findViewById(R.id.tv_name);
                tvAge = itemView.findViewById(R.id.tv_age);
            }
        }

    }
}
