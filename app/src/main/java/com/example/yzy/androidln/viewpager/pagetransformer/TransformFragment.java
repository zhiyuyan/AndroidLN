package com.example.yzy.androidln.viewpager.pagetransformer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yzy.androidln.R;

/**
 * Created by yzy on 2017/8/3.
 */

public class TransformFragment extends Fragment {

    private static final String POSITION = "position";

    private int mPosition;

    public static TransformFragment newInstance(int position) {
        TransformFragment fragment = new TransformFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transform, null);
        TextView textView = root.findViewById(R.id.tv_position);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(POSITION);
            textView.setText(String.valueOf(mPosition));
        }
        return root;
    }
}
