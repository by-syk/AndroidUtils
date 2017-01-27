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

package com.by_syk.lib.text;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;

import com.by_syk.lib.toast.GlobalToast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by By_syk on 2017-01-14.
 */

public class AboutMsgRender {
    /**
     * 渲染 APP 的文字关于信息，支持类似 Markdown 的标记：
     * [xxx](copy:xxx)
     * [xxx](email:xxx)
     * [xxx](xxx)
     *
     * @param CONTEXT
     * @param msg
     * @param underline 是否去掉链接的下划线
     * @return
     */
    public static SpannableString render(final Context CONTEXT, String msg, boolean underline) {
        String newMsg = msg;
        final List<String> tagsList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[(.*?)\\]\\((.*)\\)");
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            tagsList.add(matcher.group(1));
            tagsList.add(matcher.group(2));
            newMsg = newMsg.replaceFirst("\\[(.*?)\\]\\((.*)\\)", matcher.group(1));
        }

        SpannableString spannableString = new SpannableString(newMsg);
        int temp_pos = -1;
        for (int i = 0, len = tagsList.size(); i < len - 1; i += 2) {
            temp_pos = newMsg.indexOf(tagsList.get(i), temp_pos + 1);
            if (tagsList.get(i + 1).startsWith("copy:")) {
                final String TEXT = tagsList.get(i + 1).substring(5);
                spannableString.setSpan(new MyClickableSpan(underline) {
                    @Override
                    public void onClick(View widget) {
                        GlobalToast.copyAndShowToast(CONTEXT, TEXT);
                    }
                }, temp_pos, temp_pos + tagsList.get(i).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else if (tagsList.get(i + 1).startsWith("email:")) {
                final String TEXT = tagsList.get(i + 1).substring(6);
                spannableString.setSpan(new MyClickableSpan(underline) {
                    @Override
                    public void onClick(View widget) {
                        sendEmail(CONTEXT, TEXT);
                    }
                }, temp_pos, temp_pos + tagsList.get(i).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new MyURLSpan(tagsList.get(i + 1), underline),
                        temp_pos, temp_pos + tagsList.get(i).length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableString;
    }

    /**
     * @see #render(Context, String, boolean)
     */
    public static SpannableString render(final Context CONTEXT, String msg) {
        return render(CONTEXT, msg, true);
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param email
     */
    public static void sendEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", email, null));
        //intent.putExtra(Intent.EXTRA_SUBJECT, "");

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
