/*
 * Copyright 2016-2017 By_syk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.by_syk.lib.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * 用于在 Android 5.0+ 的可滚动对话框上显示上下边界指示线
 *
 * 何为上下边界指示线？
 * 在 Android 5.0+ 的 Material Design 设计中，只有默认的一些对话框有边界线，
 * 仅当对话框上部（下部）有超出的内容时会显示一条边界线。
 *
 * 基于 RelativeLayout 实现，嵌套在对话框自定义布局文件的最外层即可。
 * 可通过“lightTheme”属性设置当前主题（Light/Dark）。
 *
 * 注意：
 * 仅在 Android 5.0+ 上有效，低版本也可使用，但没有任何效果。
 *
 * Created by By_syk on 2016-03-05.
 */
public class RootView4ScrollableDialog extends RelativeLayout {
    // 唯一的子组件 ScrollView
    private ScrollView contentScrollView = null;
    // ScrollView 子组件中唯一的子组件
    private View contentChildView = null;

    // 滚动监听
    ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;

    private boolean draw_top_divider = false;
    private boolean draw_bottom_divider = false;

    private Paint dividerPaint = null;

    // 边界线高度：1dp
    private int divider_height;

    private final static int SDK = Build.VERSION.SDK_INT;

    public RootView4ScrollableDialog(Context context) {
        this(context, null);
    }

    public RootView4ScrollableDialog(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (SDK < 21) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RootView4ScrollableDialog);
        boolean is_light_theme = typedArray
                .getBoolean(R.styleable.RootView4ScrollableDialog_lightTheme, false);
        typedArray.recycle();

        init(context, is_light_theme);
    }

    /*
     * 当 View 中所有的子控件均被映射成 xml 后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 应该只包含一个 ScrollView 子组件
        if (getChildCount() == 1 && getChildAt(0) instanceof ScrollView) {
            contentScrollView = (ScrollView) getChildAt(0);
            contentChildView = contentScrollView.getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        // 使子元素能被绘制出来
        contentScrollView.layout(i, i1, i2, i1 + contentScrollView.getMeasuredHeight());

        if (SDK >= 21) {
            setUpDividersVisibility();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (draw_top_divider) {
            int y = getTop();
            canvas.drawRect(0, y, getMeasuredWidth(), y + divider_height, dividerPaint);
        }

        if (draw_bottom_divider) {
            int y = getBottom();
            canvas.drawRect(0, y - divider_height, getMeasuredWidth(), y, dividerPaint);
        }
    }

    @TargetApi(23)
    private void init(Context context, boolean is_light_theme) {
        Resources resources = context.getResources();

        dividerPaint = new Paint();
        int color_res_id = is_light_theme ? R.color.dialog_divider_color_light
                : R.color.dialog_divider_color;
        dividerPaint.setColor(SDK >= 23 ? context.getColor(color_res_id)
                : resources.getColor(color_res_id));

        divider_height = resources.getDimensionPixelSize(R.dimen.dialog_divider_height);

        /*
         * If this view doesn't do any drawing on its own,
         * set this flag to allow further optimizations.
         * By default, this flag is not set on View,
         * but could be set on some View subclasses such as ViewGroup.
         * Typically, if you override onDraw(Canvas) you should clear this flag.
         */
        setWillNotDraw(false);
    }

    private void setUpDividersVisibility() {
        // 组件加载出错
        // 或内容不多，不足以滚动
        if (contentScrollView == null || !canScroll()) {
            draw_top_divider = false;
            draw_bottom_divider = false;

            return;
        }

        if (onScrollChangedListener == null) {
            onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    draw_top_divider = contentScrollView.getScrollY() + contentScrollView.getPaddingTop()
                            > contentChildView.getTop();

                    draw_bottom_divider = contentScrollView.getScrollY() + contentScrollView.getHeight()
                            - contentScrollView.getPaddingBottom() < contentChildView.getBottom();

                    invalidate();
                }
            };

            contentScrollView.getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);

            onScrollChangedListener.onScrollChanged();
        }
    }

    /**
     * 判断ScrollView子组件的内容是否足够多且能滚动
     */
    private boolean canScroll() {
        /*
         * 需要减去上下边距，比如 ScrollView 中有这些属性：
         *     android:paddingBottom="@dimen/padding_content_dialog"
         *     android:paddingLeft="@dimen/padding_content_dialog"
         *     android:paddingRight="@dimen/padding_content_dialog"
         *     android:paddingTop="@dimen/padding_title_body_dialog"
         *     android:scrollbarStyle="outsideOverlay"
         *     android:clipToPadding="false"
         */
        return contentScrollView != null && contentChildView != null
                && (contentScrollView.getMeasuredHeight() - contentScrollView.getPaddingTop()
                - contentScrollView.getPaddingBottom() < contentChildView.getMeasuredHeight());
    }
}
