package com.example.yzy.androidln.window;

import android.Manifest;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yzy.androidln.R;
import com.example.yzy.androidln.utils.ScreenUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by yzy on 2019/1/15 0015.
 */

public class WindowDemoActivity extends Activity {

    //    private static final String TAG = "WindowDemoActivity";
//
//    private Button mFloatingButton;
//    private WindowManager.LayoutParams mLayoutParams;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        final RxPermissions rxPermissions = new RxPermissions(this);
//        super.onCreate(savedInstanceState);
//        mFloatingButton = new Button(this);
//        mFloatingButton.setText("button");
//        mLayoutParams = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                0,
//                0,
//                PixelFormat.TRANSPARENT
//        );
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
//        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        mLayoutParams.x = 100;
//        mLayoutParams.y = 300;
//        rxPermissions
//                .request(Manifest.permission.SYSTEM_ALERT_WINDOW)
//                .subscribe(new Consumer<Boolean>() {
//                               @Override
//                               public void accept(Boolean aBoolean) throws Exception {
//                                   if (aBoolean) {
//                                       getWindowManager().addView(mFloatingButton, mLayoutParams);
//                                   } else {
//                                       Log.d(TAG, "请求权限失败");
//                                   }
//                               }
//                           }
//                );
//
//    }
    private ImageView img;

    private WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo);

        windowManager = getWindowManager();

        // 动态初始化图层
        img = new ImageView(this);
        img.setLayoutParams(new WindowManager.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(R.drawable.guide);

        // 设置LayoutParams参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 设置显示格式
        params.format = PixelFormat.RGBA_8888;
        // 设置对齐方式
        params.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置宽高
        params.width = ScreenUtils.getScreenWidth(this);
        params.height = ScreenUtils.getScreenHeight(this);

        // 添加到当前的窗口上
        windowManager.addView(img, params);

        // 点击图层之后，将图层移除
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                windowManager.removeView(img);
            }
        });

    }
}
