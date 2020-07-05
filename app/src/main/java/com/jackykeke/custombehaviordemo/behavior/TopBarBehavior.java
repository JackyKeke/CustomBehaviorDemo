package com.jackykeke.custombehaviordemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;

import com.jackykeke.base.utils.DisplayUtil;
import com.jackykeke.custombehaviordemo.R;

/**
 * @function: TopBar部分的Behavior
 */
public class TopBarBehavior extends CoordinatorLayout.Behavior {


    private float contentTransY;//滑动内容初始化TransY
    private int topBarHeight;//topBar内容高度

    public TopBarBehavior(Context context) {
        this(context, null);
    }

    public TopBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        //引入 尺寸值
        contentTransY = context.getResources().getDimension(R.dimen.content_trans_y);
        int statusBarHeight = DisplayUtil.getStatusBarHeight();
        topBarHeight = (int) context.getResources().getDimension(R.dimen.top_bar_height) + statusBarHeight;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getId() == R.id.ll_content;
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //计算Content上滑的百分比，设置子view的透明度
        float upPro = (contentTransY - MathUtils.clamp(dependency.getTranslationY(), topBarHeight, contentTransY)) / (contentTransY - topBarHeight);
        View tvName = child.findViewById(R.id.tv_top_bar_name);
        View tvColl = child.findViewById(R.id.tv_top_bar_coll);
        tvName.setAlpha(upPro);
        tvColl.setAlpha(upPro);
        return true;
    }

}
