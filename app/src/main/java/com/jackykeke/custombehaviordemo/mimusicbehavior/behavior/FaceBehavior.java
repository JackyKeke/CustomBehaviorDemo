package com.jackykeke.custombehaviordemo.mimusicbehavior.behavior;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.palette.graphics.Palette;

import com.jackykeke.custombehaviordemo.R;
import com.jackykeke.custombehaviordemo.utils.ColorUtil;
import com.jackykeke.custombehaviordemo.utils.DisplayUtil;


/**
 * @function: face部分的Behavior
 */
public class FaceBehavior extends CoordinatorLayout.Behavior {


//   链接 https://mp.weixin.qq.com/s/K_PQkGihQXSzOjsme90wxA

    private int topBarHeight;// topBar内容高度
    private int contentTranY;  //滑动内容初始化 TransY
    private int downEndY;    //下滑时终点值
    private float faceTransY; ////图片往上位移值
    private GradientDrawable drawable;//蒙层的背景


    public FaceBehavior(Context context) {
        this(context, null);
    }

    public FaceBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        //引入尺寸值
        int statusBarHeight =  DisplayUtil.getStatusBarHeight();

        topBarHeight = (int) context.getResources().getDimension(R.dimen.top_bar_height) + statusBarHeight;
        contentTranY = (int) context.getResources().getDimension(R.dimen.content_trans_y);
        downEndY = (int) context.getResources().getDimension(R.dimen.content_trans_down_end_y);
        faceTransY = context.getResources().getDimension(R.dimen.face_trans_y);

//        蒙层
        palette(context);
    }

    private void palette(Context context) {
        Palette palette = Palette.from(BitmapFactory.decodeResource(context.getResources(), R.mipmap.cloud)).generate();
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        Palette.Swatch mutedSwatch = palette.getMutedSwatch();

        int colors[] = new int[2];
        if (mutedSwatch != null) {
            colors[0] = mutedSwatch.getRgb();
            colors[1] = ColorUtil.getTranslucentColor(0.4f, mutedSwatch.getRgb());
        } else if (vibrantSwatch != null) {
            colors[0] = vibrantSwatch.getRgb();
            colors[1] = ColorUtil.getTranslucentColor(0.4f, vibrantSwatch.getRgb());
        } else {
            colors[0] = Color.parseColor("#4D000000");
            colors[1] = ColorUtil.getTranslucentColor(0.4f, Color.parseColor("#4D000000"));
        }

        drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
    }

    //  你要依赖 哪个Content View？
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getId() == R.id.ll_content;
    }

    //所在ViewGroup下的Child展示 从onLayout事件分发
    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {

//    设置蒙层背景
        child.findViewById(R.id.v_mask).setBackground(drawable);
        return false;
    }


    //这里的 child 就是带有这个behavior的ViewGroup或View   dependency 就是所依赖的视图
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //计算Content的上滑百分比、下滑百分比
        float upPercent = (contentTranY - MathUtils.clamp(dependency.getTranslationY(), topBarHeight, contentTranY)) / (contentTranY - topBarHeight);
        float downPercent = (downEndY - MathUtils.clamp(dependency.getTranslationY(), contentTranY, downEndY)) / (downEndY - contentTranY);

        ImageView imageView = child.findViewById(R.id.iv_face);
        View maskView = child.findViewById(R.id.v_mask);

        if (dependency.getTranslationY() >= contentTranY) {
            imageView.setTranslationY(downPercent * faceTransY);
        } else {
            imageView.setTranslationY(faceTransY + 4 * upPercent * faceTransY);
        }
        //根据Content上滑百分比设置图片和蒙层的透明度
        imageView.setAlpha(1 - upPercent);
        maskView.setAlpha(upPercent);
        //因为改变了child的位置，所以返回true
        return true;
    }



}
