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

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

import com.by_syk.lib.toast.GlobalToast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import moe.feng.alipay.zerosdk.AlipayZeroSdk;

/**
 * Created by By_syk on 2017-01-14.
 */

public class AboutMsgRender {
    /**
     * 渲染 APP 的文字关于信息，支持类似 Markdown 的标记。
     *
     * 实现点按复制：
     * [要显示文字](copy:要复制的文字)
     *
     * 实现点按发邮件：
     * [要显示文字](email:邮箱地址)
     *
     * 实现点按跳转支付宝转账页面（如果失败但有账号，则复制账号）：
     * [要显示文字](alipay:二维码ID|可选填的支付宝账号)
     * 如何获取支付宝二维码ID？
     * 由于此处使用了 fython 开源的 AlipayZeroSdk 项目，请参考：https://github.com/fython/AlipayZeroSdk
     *
     * 实现点按跳转链接（比如 http、mailto）：
     * [要显示文字](链接URL)
     *
     * @param ACTIVITY
     * @param msg
     * @param underline 是否去掉链接的下划线
     * @return
     */
    public static SpannableString render(final Activity ACTIVITY, String msg, boolean underline) {
        String newMsg = msg;
        final List<String> tagsList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[(.*?)\\]\\((.*?\\)*)\\)");
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            tagsList.add(matcher.group(1));
            tagsList.add(matcher.group(2));
            newMsg = newMsg.replaceFirst("\\[(.*?)\\]\\((.*?\\)*)\\)", matcher.group(1));
        }

        SpannableString spannableString = new SpannableString(newMsg);
        int tempPos = -1;
        for (int i = 0, len = tagsList.size(); i < len - 1; i += 2) {
            tempPos = newMsg.indexOf(tagsList.get(i), tempPos + 1);
            if (tagsList.get(i + 1).startsWith("copy:")) {
                final String TEXT = tagsList.get(i + 1).substring(5);
                spannableString.setSpan(new MyClickableSpan(underline) {
                    @Override
                    public void onClick(View widget) {
                        GlobalToast.copyAndShowToast(ACTIVITY, TEXT);
                    }
                }, tempPos, tempPos + tagsList.get(i).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else if (tagsList.get(i + 1).startsWith("email:")) {
                final String TEXT = tagsList.get(i + 1).substring(6);
                spannableString.setSpan(new MyClickableSpan(underline) {
                    @Override
                    public void onClick(View widget) {
                        sendEmail(ACTIVITY, TEXT);
                    }
                }, tempPos, tempPos + tagsList.get(i).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else if (tagsList.get(i + 1).startsWith("alipay:")) {
                String[] arr = tagsList.get(i + 1).substring(7).split("\\|", -1);
                final String QRCODE_ID = arr[0];
                final String ACCOUNT;
                if (arr.length > 1) {
                    ACCOUNT = arr[1];
                } else {
                    ACCOUNT = "";
                }
                spannableString.setSpan(new MyClickableSpan(underline) {
                    @Override
                    public void onClick(View widget) {
                        if (!TextUtils.isEmpty(QRCODE_ID) && AlipayZeroSdk.hasInstalledAlipayClient(ACTIVITY)
                                && AlipayZeroSdk.startAlipayClient(ACTIVITY, QRCODE_ID)) {
                        } else if (!TextUtils.isEmpty(ACCOUNT)) {
                            GlobalToast.copyAndShowToast(ACTIVITY, ACCOUNT);
                        }
                    }
                }, tempPos, tempPos + tagsList.get(i).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new MyURLSpan(tagsList.get(i + 1), underline),
                        tempPos, tempPos + tagsList.get(i).length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableString;
    }

    /**
     * @see #render(Activity, String, boolean)
     */
    public static SpannableString render(Activity activity, String msg) {
        return render(activity, msg, true);
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
