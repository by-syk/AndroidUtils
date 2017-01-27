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

package com.by_syk.lib.festival;

import java.util.Calendar;

/**
 * 清明节计算方法：
 * [Y * D + C] - L
 * Y：年数后两位，D：0.2422，L：闰年数
 * C：二十世纪 5.59，二十一世纪 4.81
 *
 * Created by By_syk on 2016-12-29.
 */

class TombSweepingUtil {
    /**
     * 获取清明节日期
     *
     * @param year 年份，有效范围（1900-2099）
     * @return 返回 -1 为无效
     */
    public static long getTombSweepingDate(int year) {
        if (year < 1900 || year > 2099) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final float D = 0.2422f;
        final float C;
        if (year >= 2000) {
            C = 4.81f;
        } else {
            C = 5.59f;
        }
        calendar.set(Calendar.DAY_OF_MONTH, (int) ((year % 100) * D + C) - year % 100 / 4);

        return calendar.getTimeInMillis();
    }

    /**
     * 获取寒食节日期
     *
     * @param year 年份，有效范围（1900-2099）
     * @return 精确到日，返回 -1 为无效
     */
    public static long getColdFoodDate(int year) {
        long tombSweepingDate = getTombSweepingDate(year);
        if (tombSweepingDate == -1) {
            return -1;
        }
        return tombSweepingDate - 24 * 60 * 60 * 1000;
    }
}
