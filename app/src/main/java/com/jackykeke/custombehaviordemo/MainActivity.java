package com.jackykeke.custombehaviordemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.TypedValue;

import com.jackykeke.custombehaviordemo.fragment.SongFragment;
import com.jackykeke.custombehaviordemo.fragment.TabFragment;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


//  链接  https://mp.weixin.qq.com/s/K_PQkGihQXSzOjsme90wxA

    private final String[] mTitles = {
            "热门", "专辑", "视屏","资讯"
    };
//    private SlidingTabLayout mSl;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {
        mViewPager.setAdapter(mFragmentAdapter);
//        mSl.setViewPager(mViewPager, mTitles);

        //反射修改最少滑动距离
        try {
            Field mTouchSlop = ViewPager.class.getDeclaredField("mTouchSlop");
            mTouchSlop.setAccessible(true);
            mTouchSlop.setInt(mViewPager, dp2px(50));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }

    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    private void initData() {
        mFragments.add(new SongFragment());
        mFragments.add(TabFragment.newInstance("我是专辑页面"));
        mFragments.add(TabFragment.newInstance("我是视屏页面"));
        mFragments.add(TabFragment.newInstance("我是资讯页面"));
        mFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
    }

    private void initView() {
//        mSl = findViewById(R.id.stl);
        mViewPager = findViewById(R.id.vp);
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}