package com.example.yzy.androidln;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.yzy.androidln.utils.ScreenUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private static final String CATEGORY_NAME = "intent.category.androidln.SAMPLE_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);

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

    protected List getData() {
        List<Map> myData = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CATEGORY_NAME);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;

        prefixPath = null;

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString()
                    : info.activityInfo.name;

            String[] labelPath = label.split("/");

            String nextLabel = prefixPath == null ? labelPath[0]
                    : labelPath[prefixPath.length];

            if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                addItem(myData,
                        nextLabel,
                        activityIntent(
                                info.activityInfo.applicationInfo.packageName,
                                info.activityInfo.name));
            } else {
                if (entries.get(nextLabel) == null) {
                    addItem(myData, nextLabel, browseIntent(nextLabel));
                    entries.put(nextLabel, true);
                }
            }

        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    protected void addItem(List<Map> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        return result;
    }

    private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map map1, Map map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map map = (Map) l.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }

    private ImageView img;

    private WindowManager windowManager;

}
