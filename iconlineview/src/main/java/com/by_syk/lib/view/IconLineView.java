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
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by By_syk on 2017-01-26.
 */

public class IconLineView extends View {
    private int color = 0xffff0000;

    public IconLineView(Context context) {
        this(context, null);
    }

    public IconLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.IconLineView);
        color = typedArray.getColor(R.styleable.IconLineView_color, 0xffff0000);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawIconLine(canvas);
    }

    private void drawIconLine(Canvas canvas) {
        float w = getWidth();
        float h = getHeight();

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 0);
        path.lineTo(w, h);
        path.lineTo(0, h);
        path.lineTo(0, 0);
        path.lineTo(w, h);
        path.moveTo(w, 0);
        path.lineTo(0, h);
        path.moveTo(w / 2, 0);
        path.lineTo(w / 2, h);
        path.moveTo(0, h / 2);
        path.lineTo(w, h / 2);

        Paint paint = initPaint();
        canvas.drawPath(path, paint);
    }

    private Paint initPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        paint.setAntiAlias(true);

        float span = dp2px(4);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{span, span}, 1);
        paint.setPathEffect(dashPathEffect);

        return paint;
    }

    private float dp2px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }
}
