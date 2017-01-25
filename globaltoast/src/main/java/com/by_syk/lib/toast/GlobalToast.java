/**
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

package com.by_syk.lib.toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by By_syk on 2016-03-11.
 */
public class GlobalToast {
    private static Toast toast = null;

    private static int defGravity;
    private static int offsetX;
    private static int offsetY;

    public static void showToast(Context context, String msg, boolean isLong, int gravity) {
        if (context == null || msg == null) {
            return;
        }

        if (toast == null) { // Create Toast firstly.
            toast = Toast.makeText(context.getApplicationContext(), msg,
                    isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            defGravity = toast.getGravity();
            offsetX = toast.getXOffset();
            offsetY = toast.getYOffset();
        } else {
            toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setText(msg);
        }

        if (gravity == -1) {
            toast.setGravity(defGravity, offsetX, offsetY);
        } else {
            toast.setGravity(gravity, 0, 0);
        }

        toast.show();
    }

    public static void showToast(Context context, String msg, boolean isLong) {
        showToast(context, msg, isLong, -1);
    }

    public static void showToast(Context context, String msg, int gravity) {
        showToast(context, msg, false, gravity);
    }

    public static void showToast(Context context, String msg) {
        showToast(context, msg, false);
    }

    public static void showToast(Context context, int strId, boolean isLong, int gravity) {
        showToast(context, context.getString(strId), isLong, gravity);
    }

    public static void showToast(Context context, int strId, boolean isLong) {
        showToast(context, context.getString(strId), isLong);
    }

    public static void showToast(Context context, int strId, int gravity) {
        showToast(context, context.getString(strId), gravity);
    }

    public static void showToast(Context context, int strId) {
        showToast(context, context.getString(strId));
    }

    public static void copyAndShowToast(Context context, String msg, boolean isLong) {
        ExtraUtil.copy2Clipboard(context, msg);
        showToast(context, context.getString(R.string.toast_copied, msg), isLong);
    }

    public static void copyAndShowToast(Context context, String msg) {
        copyAndShowToast(context, msg, false);
    }
}