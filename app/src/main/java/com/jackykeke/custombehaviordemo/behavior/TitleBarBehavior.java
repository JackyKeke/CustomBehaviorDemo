package com.jackykeke.custombehaviordemo.behavior;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;

import com.jackykeke.base.utils.DisplayUtil;
import com.jackykeke.custombehaviordemo.R;


import java.util.List;

/**
 * @function: TitleBar部分的Behavior
 */
public class TitleBarBehavior extends CoordinatorLayout.Behavior {

    private float contentTransY;//滑动内容初始化TransY
    private int topBarHeight;//topBar内容高度


    public TitleBarBehavior(Context context) {
        this(context, null);
    }

    public TitleBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);



        //引入 尺寸值
        contentTransY = context.getResources().getDimension(R.dimen.content_trans_y);
        int statusBarHeight =  DisplayUtil.getStatusBarHeight();
        topBarHeight = (int) context.getResources().getDimension(R.dimen.top_bar_height) + statusBarHeight;
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //依赖content
        return dependency.getId() == R.id.ll_content;
    }




    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //调整TitleBar位置要紧贴Content顶部上面
        adjustPosition(parent, child, dependency);
        //这里只计算Content上滑范围一半的百分比
        float start = (contentTransY + topBarHeight * 2) / 2;
        float upPro = (contentTransY - MathUtils.clamp(dependency.getTranslationY(), start, contentTransY)) / (contentTransY - start);
        child.setAlpha(1 - upPro);
        return true;
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        //找到Content的依赖引用
        List<View> dependencies = parent.getDependencies(child);
        View dependency = null;
        for (View view : dependencies) {
            if (view.getId() == R.id.ll_content) {
                dependency = view;
                break;
            }
        }
        if (dependency != null) {
            //调整TitleBar位置要紧贴Content顶部上面
            adjustPosition(parent, child, dependency);
            return true;
        } else {
            return false;
        }
    }

    private void adjustPosition(@NonNull CoordinatorLayout parent, @NonNull View child, View dependency) {
        final CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        int left = parent.getPaddingLeft() + lp.leftMargin;
        int top = (int) (dependency.getY() - child.getMeasuredHeight() + lp.topMargin);
        int right = child.getMeasuredWidth() + left - parent.getPaddingRight() - lp.rightMargin;
        int bottom = (int) (dependency.getY() - lp.bottomMargin);
        child.layout(left, top, right, bottom);
    }


}
