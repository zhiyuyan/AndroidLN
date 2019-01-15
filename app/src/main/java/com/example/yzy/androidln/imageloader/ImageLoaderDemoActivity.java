package com.example.yzy.androidln.imageloader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.yzy.androidln.R;
import com.example.yzy.androidln.utils.MyUtils;
import com.example.yzy.androidln.view.widget.SquareImageView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yzy on 2019/1/11 0011.
 */

public class ImageLoaderDemoActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private static final String TAG = "ImageLoaderDemoActivity";

    private boolean mIsGridViewIdle = true;
    private boolean mIsWifiConnected = false;
    private boolean mCanGetBitmapFromNetwork = false;

    private ImageLoader mImageLoader;

    private GridView mGridView;
    private ImageAdapter mImageAdapter;

    private int mPreviousFirstVisibleItem=0;
    private long mPreviousEventTime=0;
    private double mScrollSpeed=0;

    private int mImageWidth;

    private int mSpeedCheckSwitch = 0;

    private static final int MAX_SCROLLING_SPEED = 30;

    private static final Executor CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader_demo);

        initData();
        initView();

        mImageLoader = ImageLoader.build(this);
    }

    private void initData() {
        int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        int space = (int) MyUtils.dp2px(this, 20f);

        mImageWidth = (screenWidth - space) / 3;

        mIsWifiConnected = MyUtils.isWifi(this);

        if (mIsWifiConnected) {
            mCanGetBitmapFromNetwork = true;
        }
    }

    private void initView() {
        mGridView = findViewById(R.id.gv_content);
        mImageAdapter = new ImageAdapter(this);
        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 当滑动停止时更新可见item
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsGridViewIdle = true;
            mImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onScroll(AbsListView view, final int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mSpeedCheckSwitch++;
        if (mSpeedCheckSwitch == 2) {
            CACHED_THREAD_POOL.execute(new Runnable() {
                @Override
                public void run() {
                    // 滚动速度较快时不更新item（防止快速滑动过程中产生大量的异步更新任务，导致卡顿）
                    if (mPreviousFirstVisibleItem != firstVisibleItem) {
                        long currTime = System.currentTimeMillis();
                        long timeToScrollOneElement = currTime - mPreviousEventTime;
                        mScrollSpeed = ((double)1/timeToScrollOneElement) * 1000;

                        mPreviousFirstVisibleItem = firstVisibleItem;
                        mPreviousEventTime = currTime;
                        Log.d(TAG, "Speed: " + mScrollSpeed + " elements/second");

                        if (mScrollSpeed > MAX_SCROLLING_SPEED) {
                            mIsGridViewIdle = false;
                        } else {
                            mIsGridViewIdle = true;
                        }
                    }
                }
            });
            mSpeedCheckSwitch = 0;
        }
    }

    public static String[] sImageLists = new String[]{
            "http://c.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec78e256df60d0f703908fc12e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec78e256df60d0f703908fc12e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec78e256df60d0f703908fc12e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec78e256df60d0f703908fc12e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec78e256df60d0f703908fc12e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ec78e256df60d0f703908fc12e.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68843f7b26ca6c27d1ed31b2404.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/0823dd54564e9258ccb036ef9e82d158ccbf4e2e.jpg"
    };

    class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;

        private static final int ONE_SCREEN_ITEMS = 18;

        public ImageAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return sImageLists == null ? 0 : sImageLists.length;
        }

        @Override
        public String getItem(int position) {
            return sImageLists[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_list_grid_view, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageView imageView = viewHolder.imageView;
            final String tag = (String) imageView.getTag();
            final String uri = getItem(position);
            if (!uri.equals(tag)) {
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
            }
            if (mIsGridViewIdle && mCanGetBitmapFromNetwork) {
                imageView.setTag(uri);
                mImageLoader.bindBitmap(uri, imageView, mImageWidth, mImageWidth);
            } else if (position < ONE_SCREEN_ITEMS || position > sImageLists.length - ONE_SCREEN_ITEMS) {
                imageView.setTag(uri);
                mImageLoader.bindBitmap(uri, imageView, mImageWidth, mImageWidth);
            }
            return convertView;
        }

        class ViewHolder {
            SquareImageView imageView;

            public ViewHolder(View root) {
                imageView = root.findViewById(R.id.siv_image);
            }
        }
    }

}
