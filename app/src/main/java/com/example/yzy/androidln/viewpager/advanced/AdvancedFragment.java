package com.example.yzy.androidln.viewpager.advanced;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2017/8/3.
 */

public class AdvancedFragment extends Fragment {

    private static final String PHOTO_URL = "photo_url";

    private int mPhotoUrl;

    public static AdvancedFragment newInstance(Integer photoUrl) {
        AdvancedFragment fragment = new AdvancedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PHOTO_URL, photoUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_advanced, null);
        ImageView simpleDraweeView = root.findViewById(R.id.image);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPhotoUrl = bundle.getInt(PHOTO_URL);
        }
        simpleDraweeView.setImageResource(mPhotoUrl);
        return root;
    }
}
