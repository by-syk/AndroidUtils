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
 * Created by By_syk on 2017-01-26.
 */

public class IconGridView extends View {
    private int color = 0x80808080;
    private boolean isDash = false;
    private int gridStyle = GRID_STYLE_UNION_JACK;

    private static final int GRID_STYLE_CROSS = 0;
    private static final int GRID_STYLE_UNION_JACK = 1;
    private static final int GRID_STYLE_MATERIAL_LITE = 2;
    private static final int GRID_STYLE_MATERIAL_FULL = 3;

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
        color = typedArray.getColor(R.styleable.IconGridView_color, 0xffff0000);
        isDash = typedArray.getBoolean(R.styleable.IconGridView_dash, false);
        gridStyle = typedArray.getInteger(R.styleable.IconGridView_gridStyle, GRID_STYLE_UNION_JACK);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawIconLine(canvas);
    }

    private void drawIconLine(Canvas canvas) {
        Path path = initPath();
        Paint paint = initPaint();
        canvas.drawPath(path, paint);
    }

    private Path initPath() {
        switch (gridStyle) {
            case GRID_STYLE_CROSS:
                return initPathCross();
            case GRID_STYLE_UNION_JACK:
                return initPathUnionJack();
            case GRID_STYLE_MATERIAL_LITE:
                return initPathMaterialLite();
            case GRID_STYLE_MATERIAL_FULL:
                return initPathMaterialFull();
        }
        return initPathUnionJack();
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

        path.moveTo(0, h * 2 / 24);
        path.lineTo(w, h * 2 / 24);
        path.moveTo(0, h * 22 / 24);
        path.lineTo(w, h * 22 / 24);
        path.moveTo(w * 2 / 24, 0);
        path.lineTo(w * 2 / 24, h);
        path.moveTo(w * 22 / 24, 0);
        path.lineTo(w * 22 / 24, h);

        return path;
    }

    private Path initPathMaterialFull() {
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

        path.moveTo(0, h * 17 / 48);
        path.lineTo(w, h * 17 / 48);
        path.moveTo(0, h * 31 / 48);
        path.lineTo(w, h * 31 / 48);
        path.moveTo(w * 17 / 48, 0);
        path.lineTo(w * 17 / 48, h);
        path.moveTo(w * 31 / 48, 0);
        path.lineTo(w * 31 / 48, h);

        path.addRoundRect(new RectF(w * 5 / 48, h * 5 / 48, w * 43 / 48, h * 43 / 48),
                w * 3 / 48, h * 3 / 48, Path.Direction.CW);
        path.addRoundRect(new RectF(w * 2 / 48, h * 8 / 48, w * 46 / 48, h * 40 / 48),
                w * 3 / 48, h * 3 / 48, Path.Direction.CW);
        path.addRoundRect(new RectF(w * 8 / 48, h * 2 / 48, w * 40 / 48, h * 46 / 48),
                w * 3 / 48, h * 3 / 48, Path.Direction.CW);
        path.addCircle(w / 2, h / 2, w * 22 / 48, Path.Direction.CW);
        path.addCircle(w / 2, h / 2, w * 10 / 48, Path.Direction.CW);

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
