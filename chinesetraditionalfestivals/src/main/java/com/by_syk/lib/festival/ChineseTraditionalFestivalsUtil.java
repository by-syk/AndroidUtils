/**
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

package com.by_syk.lib.festival;

import android.content.Context;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by By_syk on 2016-12-29.
 */

public class ChineseTraditionalFestivalsUtil {
    /**
     * 判断指定日子是否正值中国传统节日，是则返回名称，否则返回“”
     *
     * @param context
     * @param timeMillis
     * @return
     */
    public static String getFestival(Context context, long timeMillis) {
        if (context == null) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timeMillis = calendar.getTimeInMillis();
        int year = calendar.get(Calendar.YEAR);

        // 使用 LinkedHashMap，保证顺序，解决节日冲突问题
        LinkedHashMap<String, Long> map = new LinkedHashMap<>();

        map.put(context.getString(R.string.laba) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year - 1, 12, 8, false)); // 腊八节
        map.put(context.getString(R.string.minor_spring) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year - 1, 12, 23, false)); // 小年
        map.put(context.getString(R.string.chinese_new_years_eve) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 1, 1, false) - 24 * 60 * 60 * 1000); // 除夕
        map.put(context.getString(R.string.spring) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 1, 1, false)); // 春节
        map.put(context.getString(R.string.lantern) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 1, 15, false)); // 元宵节
        map.put(context.getString(R.string.double_second) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 2, 2, false)); // 龙抬头
        map.put(context.getString(R.string.food_cold) + "-" + Math.random(),
                TombSweepingUtil.getColdFoodDate(year)); // 寒食节
        map.put(context.getString(R.string.tomb_sweeping) + "-" + Math.random(),
                TombSweepingUtil.getTombSweepingDate(year)); // 清明节
        map.put(context.getString(R.string.dragon_boat) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 5, 5, false)); // 端午节
        map.put(context.getString(R.string.double_seventh) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 7, 7, false)); // 七夕节
        map.put(context.getString(R.string.hungry_ghost) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 7, 15, false)); // 中元节
        map.put(context.getString(R.string.mid_autumn) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 8, 15, false)); // 中秋节
        map.put(context.getString(R.string.double_ninth) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 9, 9, false)); // 重阳节
        map.put(context.getString(R.string.laba) + "-" + Math.random(),
                LunarCalendarUtil.lunarToSolar(year, 12, 8, false)); // 腊八节

        final String[] SOLAR_TERMS_24_NAMES = context.getResources().getStringArray(R.array.solar_terms_24);
        long[] solarTerms24Dates = SolarTerms24Util.getSolarTerms24Date(year);
        if (solarTerms24Dates != null) {
            for (int i = 0, len = solarTerms24Dates.length; i < len; ++i) {
                map.put(SOLAR_TERMS_24_NAMES[i] + "-" + Math.random(), solarTerms24Dates[i]);
            }
        }

        // 判断节日并取出名称
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (timeMillis == entry.getValue()) {
                return entry.getKey().split("-")[0];
            }
        }
        return "";
    }
}
