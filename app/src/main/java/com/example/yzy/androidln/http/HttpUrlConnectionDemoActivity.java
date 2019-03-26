package com.example.yzy.androidln.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yzy.androidln.R;
import com.example.yzy.androidln.utils.MyUtils;

/**
 * Created by yzy on 2019/3/18 0018.
 */

public class HttpUrlConnectionDemoActivity extends AppCompatActivity {

    private TextView txtMenu, txtshow;
    private ImageView imgPic;
    private WebView webView;
    private ScrollView scroll;
    private Bitmap bitmap;
    private String detail = "";
    private boolean flag = false;
    private final static String PIC_URL = "https://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";
    private final static String HTML_URL = "https://www.baidu.com";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_connection_demo);
        initViews();
    }

    private void initViews() {
        txtMenu = findViewById(R.id.txtMenu);
        txtshow = findViewById(R.id.txtshow);
        imgPic = findViewById(R.id.imgPic);
        webView = findViewById(R.id.webView);
        scroll = findViewById(R.id.scroll);
        registerForContextMenu(txtMenu);
    }

    // 定义一个隐藏所有控件的方法:
    private void hideAllWidget() {
        imgPic.setVisibility(View.GONE);
        scroll.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
    }

    @Override
    // 重写上下文菜单的创建方法
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.menu_http_url_connection, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // 上下文菜单被点击是触发该方法
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one:
                new Thread() {
                    public void run() {
                        try {
                            byte[] data = MyUtils.getImage(PIC_URL);
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x001);
                    }
                }.start();
                break;
            case R.id.two:
                new Thread() {
                    public void run() {
                        try {
                            detail = MyUtils.getHtml(HTML_URL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x002);
                    }
                }.start();
                break;
            case R.id.three:
                if (detail.equals("")) {
                    Toast.makeText(HttpUrlConnectionDemoActivity.this, "先请求HTML先嘛~", Toast.LENGTH_SHORT).show();
                } else {
                    handler.sendEmptyMessage(0x003);
                }
                break;
        }
        return true;
    }

    // 用于刷新界面
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    hideAllWidget();
                    imgPic.setVisibility(View.VISIBLE);
                    imgPic.setImageBitmap(bitmap);
                    Toast.makeText(HttpUrlConnectionDemoActivity.this, "图片加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    hideAllWidget();
                    scroll.setVisibility(View.VISIBLE);
                    txtshow.setText(detail);
                    Toast.makeText(HttpUrlConnectionDemoActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    hideAllWidget();
                    webView.setVisibility(View.VISIBLE);
                    webView.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
                    Toast.makeText(HttpUrlConnectionDemoActivity.this, "网页加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

}
