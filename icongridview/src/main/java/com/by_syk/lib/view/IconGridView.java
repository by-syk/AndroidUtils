/*
 * Copyright 2017 By_syk
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

package com.by_syk.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * How do we create the IconGridView? What's the grids and keylines?
 * View it here: https://material.io/guidelines/style/icons.html
 *
 * Created by By_syk on 2017-01-26.
 */

public class IconGridView extends View {
    private int color = 0x80808080;
    private boolean isDash = false;
    private boolean withGrids = false;
    private int style = STYLE_UNION_JACK;

    private static final int STYLE_FRAME = 0;
    private static final int STYLE_CROSS = 1;
    private static final int STYLE_UNION_JACK = 2;
    private static final int STYLE_MATERIAL_LITE = 3;
    private static final int STYLE_MATERIAL_FULL = 4;

    public IconGridView(Context context) {
        this(context, null);
    }

    public IconGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.IconGridView);
        color = typedArray.getColor(R.styleable.IconGridView_igColor, 0x80808080);
        isDash = typedArray.getBoolean(R.styleable.IconGridView_igDash, false);
        withGrids = typedArray.getBoolean(R.styleable.IconGridView_igWithGrids, false);
        style = typedArray.getInteger(R.styleable.IconGridView_igStyle, STYLE_UNION_JACK);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawKeylines(canvas);
    }

    private void drawKeylines(Canvas canvas) {
        Path path = initPath();
        Paint paint = initPaint();
        canvas.drawPath(path, paint);

        if (withGrids) {
            path = initPathGrids();
            paint = initPaint();
            paint.setAlpha(paint.getAlpha() * 50 / 100);
            canvas.drawPath(path, paint);
        }
    }

    private Path initPath() {
        switch (style) {
            case STYLE_FRAME:
                return initPathFrame();
            case STYLE_CROSS:
                return initPathCross();
            case STYLE_UNION_JACK:
                return initPathUnionJack();
            case STYLE_MATERIAL_LITE:
                return initPathMaterialLite();
            case STYLE_MATERIAL_FULL:
                return initPathMaterialFull();
        }
        return initPathUnionJack();
    }

    private Path initPathFrame() {
        float w = getWidth();
        float h = getHeight();

        Path path = new Path();
        path.addRect(new RectF(0, 0, w, h), Path.Direction.CW);

        return path;
    }

    private Path initPathCross() {
        float w = getWidth();
        float h = getHeight();

        Path path = new Path();
        path.addRect(new RectF(0, 0, w, h), Path.Direction.CW);

        path.moveTo(w / 2, 0);
        path.lineTo(w / 2, h);
        path.moveTo(0, h / 2);
        path.lineTo(w, h / 2);

        return path;
    }

    private Path initPathUnionJack() {
        float w = getWidth();
        float h = getHeight();

        Path path = new Path();
        path.addRect(new RectF(0, 0, w, h), Path.Direction.CW);

        path.moveTo(w / 2, 0);
        path.lineTo(w / 2, h);
        path.moveTo(0, h / 2);
        path.lineTo(w, h / 2);

        path.moveTo(0, 0);
        path.lineTo(w, h);
        path.moveTo(w, 0);
        path.lineTo(0, h);

        return path;
    }

    private Path initPathMaterialLite() {
        float w = getWidth();
        float h = getHeight();
        float unitW = w / 24;
        float unitH = h / 24;

        Path path = new Path();
        path.addRect(new RectF(0, 0, w, h), Path.Direction.CW);

        path.moveTo(w / 2, 0);
        path.lineTo(w / 2, h);
        path.moveTo(0, h / 2);
        path.lineTo(w, h / 2);

        path.moveTo(0, 0);
        path.lineTo(w, h);
        path.moveTo(w, 0);
        path.lineTo(0, h);

        path.moveTo(0, unitH * 2);
        path.lineTo(w, unitH * 2);
        path.moveTo(0, unitH * 22);
        path.lineTo(w, unitH * 22);
        path.moveTo(unitH * 2, 0);
        path.lineTo(unitH * 2, h);
        path.moveTo(unitH * 22, 0);
        path.lineTo(unitH * 22, h);

        return path;
    }

    /**
     * Android expects product icons to be provided at 48dp, with edges at 1dp.
     * When you create the icon, maintain the 48-unit measure, but scale it to 400% at 192x192dp
     * (the edge becomes 4dp).
     *
     * Keyline Shapes:
     *   + Rectangles: 152x152dp (Square), 176x128dp (Landscape), 128x176dp (Portrait)
     *     + 12dp Radius rounded corners
     *   + Circle: 176x176dp
     * Maximum Area: 176x176dp
     */
    private Path initPathMaterialFull() {
        float w = getWidth();
        float h = getHeight();
        float unitW = w / 48;
        float unitH = h / 48;

        Path path = new Path();
        path.addRect(new RectF(0, 0, w, h), Path.Direction.CW);

        path.moveTo(w / 2, 0);
        path.lineTo(w / 2, h);
        path.moveTo(0, h / 2);
        path.lineTo(w, h / 2);

        path.moveTo(0, 0);
        path.lineTo(w, h);
        path.moveTo(w, 0);
        path.lineTo(0, h);

        path.moveTo(0, unitH * 17);
        path.lineTo(w, unitH * 17);
        path.moveTo(0, unitH * 31);
        path.lineTo(w, unitH * 31);
        path.moveTo(unitW * 17, 0);
        path.lineTo(unitW * 17, h);
        path.moveTo(unitW * 31, 0);
        path.lineTo(unitW * 31, h);

        path.addRoundRect(new RectF(unitW * 5, unitH * 5, unitW * 43, unitH * 43),
                unitW * 3, unitH * 3, Path.Direction.CW);
        path.addRoundRect(new RectF(unitW * 2, unitH * 8, unitW * 46, unitH * 40),
                unitW * 3, unitH * 3, Path.Direction.CW);
        path.addRoundRect(new RectF(unitW * 8, unitH * 2, unitW * 40, unitH * 46),
                unitW * 3, unitH * 3, Path.Direction.CW);
        path.addCircle(w / 2, h / 2, unitW * 22, Path.Direction.CW);
        path.addCircle(w / 2, h / 2, unitW * 10, Path.Direction.CW);

        return path;
    }

    private Path initPathGrids() {
        float w = getWidth();
        float h = getHeight();
        float unitW = w / 48;
        float unitH = h / 48;

        Path path = new Path();

        for (int i = 0; i < 48; ++i) {
            path.moveTo(0, unitH * i);
            path.lineTo(w, unitH * i);
            path.moveTo(unitW * i, 0);
            path.lineTo(unitW * i, h);
        }

        return path;
    }

    private Paint initPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        paint.setAntiAlias(true);

        if (isDash) {
            float span = dp2px(4);
            DashPathEffect dashPathEffect = new DashPathEffect(new float[]{span, span}, 1);
            paint.setPathEffect(dashPathEffect);
        }

        return paint;
    }

    private float dp2px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }
}
