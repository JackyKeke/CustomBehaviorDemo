package com.jackykeke.custombehaviordemo.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.jackykeke.base.utils.DisplayUtil;
import com.jackykeke.custombehaviordemo.R;

import java.lang.reflect.Field;

public class ContentBehavior extends CoordinatorLayout.Behavior {

    private static final long ANIM_DURATION_FRACTION = 200L;

    private int topBarHeight;//topBar内容高度
    private float contentTransY;//滑动内容初始化TransY
    private float downEndY;//下滑时终点值
    private ValueAnimator restoreAnimator;//收起内容时执行的动画
    private View mLlContent;//Content部分
    private boolean flingFromCollaps = false;//fling是否从折叠状态发生的

    public ContentBehavior(Context context) {
        this(context, null);
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);


        int statusBarHeight = DisplayUtil.getStatusBarHeight();
        topBarHeight = (int) context.getResources().getDimension(R.dimen.top_bar_height) + statusBarHeight;
        contentTransY = (int) context.getResources().getDimension(R.dimen.content_trans_y);
        downEndY = (int) context.getResources().getDimension(R.dimen.content_trans_down_end_y);

        restoreAnimator = new ValueAnimator();
        restoreAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translation(mLlContent, (float) animation.getAnimatedValue());
            }
        });
    }


    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec,
                                  int widthUsed, int parentHeightMeasureSpec, int heightUsed) {

        final int childLpHeight = child.getLayoutParams().height;

        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT ||
                childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //先获取CoordinatorLayout的测量规格信息，若不指定具体高度则使用CoordinatorLayout的高度
            int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
            if (availableHeight == 0) {
                availableHeight = parent.getHeight();
            }
            //设置Content部分高度
            int height = availableHeight - topBarHeight;
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    height, childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                            ? View.MeasureSpec.EXACTLY
                            : View.MeasureSpec.AT_MOST
            );
            //执行指定高度的测量，并返回true表示使用Behavior来代理测量子View
            parent.onMeasureChild(child, parentWidthMeasureSpec,
                    widthUsed, heightMeasureSpec, heightUsed);

            return true;
        }
        return false;
    }

    private void translation(View view, float translationY) {
        view.setTranslationY(translationY);
    }



    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        boolean handleLayout = super.onLayoutChild(parent, child, layoutDirection);
        //绑定Content View
        mLlContent = child;

        return handleLayout;
    }


    //---NestedScrollingParent---//


    @Override
    @SuppressWarnings("deprecation")
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes) {
        return onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, ViewCompat.TYPE_TOUCH);
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes) {
//        ViewCompat.TYPE_TOUCH 指示手势的输入类型是来自用户触摸屏幕的。
        onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target) {
        onStopNestedScroll(coordinatorLayout, child, target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        flingFromCollaps = (child.getTranslationY() <= contentTransY);
        return false;
    }

    //---NestedScrollingParent2---//
    //当CoordinatorLayout的子view尝试启动嵌套滚动时调用。
// 与CoordinatorLayout的任何直接子级关联的任何行为都可以响应
//返回此事件并返回true以指示CoordinatorLayout应充当
//此滚动的嵌套滚动父级。 仅行为从以下返回true的行为
//此方法将接收后续的嵌套滚动事件。</ p>
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只接受内容View的垂直滑动
        return directTargetChild.getId() == R.id.ll_content
                && axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    //    当CoordinatorLayout接受了嵌套滚动时调用。
//<p>与CoordinatorLayout的任何直接子级关联的任何行为都可以选择
//接受嵌套滚动作为{@link #onStartNestedScroll}的一部分。 每种行为
//返回true的接收该嵌套滚动的后续嵌套滚动事件。
    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (restoreAnimator.isStarted()) {
            restoreAnimator.cancel();
        }
    }

//    当嵌套滚动结束时调用。
//与CoordinatorLayout的任何直接子级关联的任何行为都可以选择
//接受嵌套滚动作为{@link #onStartNestedScroll}的一部分。 每种行为
//返回true的接收该嵌套滚动的后续嵌套滚动事件。
// onStopNestedScroll标记单个嵌套滚动事件的结束
//序列。 这是清理与嵌套滚动相关的任何状态的好地方。
    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        //如果是从初始状态转换到展开状态过程触发收起动画
        if (child.getTranslationY() > contentTransY) {
            restore();
        }
    }


// 当目标正在消耗任何滚动距离之前，正在进行的嵌套滚动将要更新时调用。
//<p>与CoordinatorLayout的直接子级关联的任何行为都可以选择接受嵌套滚动作为{@link #onStartNestedScroll}的一部分。 每种行为
//返回true的接收该嵌套滚动的后续嵌套滚动事件。
//每当嵌套滚动子项消耗掉滚动距离之前，每次由嵌套滚动子项更新嵌套滚动条时，都会调用<p> <code> onNestedPreScroll </ code>。 <em>响应嵌套滚动的每个行为都会收到
//相同的值。</ em> CoordinatorLayout将报告在任一方向上消耗的最大像素数，这是任何行为响应嵌套滚动的行为所报告的
//    @param coordinatorLayout  此行为与之关联的视图的CoordinatorLayout父级
//@param child  此行为与之关联的CoordinatorLayout的子视图
//@param目标      是执行嵌套滚动的CoordinatorLayout的后代视图
//@param dx     用户尝试滚动的原始水平像素数
//@param dy         用户尝试滚动的原始垂直像素数
//@param消耗      out参数。 enabled [0]应设置为消耗的dx的距离，consumed [1]应设置为消耗的dy的距离
//@param类型  导致此滚动事件的输入类型

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                  @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        float transY = child.getTranslationY() - dy;

        //处理上滑
        if (dy > 0) {
            if (transY >= topBarHeight) {
                translationByConsume(child, transY, consumed, dy);
            } else {
                translationByConsume(child, topBarHeight, consumed, (child.getTranslationY() - topBarHeight));
            }
        }

        if (dy < 0 && !target.canScrollVertically(-1)) {
            //下滑时处理Fling,折叠时下滑Recycler(或NestedScrollView) Fling滚动到contentTransY停止Fling
            if (type == ViewCompat.TYPE_NON_TOUCH && transY >= contentTransY && flingFromCollaps) {
                flingFromCollaps = false;
                translationByConsume(child, contentTransY, consumed, dy);
                stopViewScroll(target);
                return;
            }

            //处理下滑
            if (transY >= topBarHeight && transY <= downEndY) {
                translationByConsume(child, transY, consumed, dy);
            } else {
                translationByConsume(child, downEndY, consumed, (downEndY - child.getTranslationY()));
                stopViewScroll(target);
            }
        }
    }

    private void stopViewScroll(View target) {
        if (target instanceof RecyclerView) {
            ((RecyclerView) target).stopScroll();
        }
        if (target instanceof NestedScrollView) {
            try {
                Class<? extends NestedScrollView> clazz = ((NestedScrollView) target).getClass();
                Field mScroller = clazz.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                OverScroller overScroller = (OverScroller) mScroller.get(target);
                overScroller.abortAnimation();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void translationByConsume(View view, float translationY, int[] consumed, float consumedDy) {
        consumed[1] = (int) consumedDy;
        view.setTranslationY(translationY);
    }


    private void restore() {
        if (restoreAnimator.isStarted()) {
            restoreAnimator.cancel();
            restoreAnimator.removeAllListeners();
        }
        restoreAnimator.setFloatValues(mLlContent.getTranslationY(), contentTransY);
        restoreAnimator.setDuration(ANIM_DURATION_FRACTION);
        restoreAnimator.start();
    }

    @Override
    public void onDetachedFromLayoutParams() {
        if (restoreAnimator.isStarted()) {
            restoreAnimator.cancel();
            restoreAnimator.removeAllUpdateListeners();
            restoreAnimator.removeAllListeners();
            restoreAnimator = null;
        }
        super.onDetachedFromLayoutParams();
    }

}
