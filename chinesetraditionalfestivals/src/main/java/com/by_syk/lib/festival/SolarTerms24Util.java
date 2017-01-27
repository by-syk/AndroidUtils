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
 * Created by By_syk on 2016-05-08.
 */

class SolarTerms24Util {
    /**
     * 获取二十四节气日期
     * 限21世纪、东八区
     *
     * 二十四节气的日期规定：
     * 太阳从黄经零度起，沿黄经每运行15度所经历的时日称为“一个节气”。
     * 每年运行360度，共经历24个节气，每月2个。
     * 其中，每月第一个节气为“节气”，即：立春、惊蛰、清明、立夏、芒种、小暑、立秋、白露、寒露、立冬、大雪和小寒等12个节气；
     * 每月的第二个节气为“中气”，即：雨水、春分、谷雨、小满、夏至、大暑、处暑、秋分、霜降、小雪、冬至和大寒等12个节气。
     * “节气”和“中气”交替出现，各历时15天，现在人们已经把“节气”和“中气”统称为“节气”。
     *
     * 日计算公式：
     * [Y * D + C] - L
     * Y：年数后两位，D：0.2422，L：闰年数
     * （例外：2084年春分+1）
     *
     * @param year 年份，有效范围（2000-2099）
     * @return 数组按时间排序（元素精确到日），返回 null 为无效
     */
    public static long[] getSolarTerms24Date(int year) {
        if (year < 1900 || year > 2099) {
            return null;
        }

        long[] dates = new long[24];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final float D = 0.2422f;

        // 小寒、大寒
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        int slightColdDay = (int) ((year % 100) * D + 5.4055f) - (year % 100 - 1) / 4;
        if (year == 1982) { // 例外
            ++slightColdDay;
        } else if (year == 2019) { // 例外
            --slightColdDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, slightColdDay);
        dates[0] = calendar.getTimeInMillis();
        int greatColdDay = (int) ((year % 100) * D + 20.12f) - (year % 100 - 1) / 4;
        if (year == 2082) { // 例外
            ++greatColdDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, greatColdDay);
        dates[1] = calendar.getTimeInMillis();

        // 立春、雨水
        calendar.set(Calendar.MONTH, Calendar.FEBRUARY); // 二十四节气从二月开始
        int springBeginsDay = (int) ((year % 100) * D + 3.87f) - (year % 100 - 1) / 4;
        calendar.set(Calendar.DAY_OF_MONTH, springBeginsDay);
        dates[2] = calendar.getTimeInMillis();
        int theRainsDay = (int) ((year % 100) * D + 18.73f) - (year % 100 - 1) / 4;
        if (year == 2026) { // 例外
            --theRainsDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, theRainsDay);
        dates[3] = calendar.getTimeInMillis();

        // 惊蛰、春分
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        int insectsAwakenDay = (int) ((year % 100) * D + 5.63f) - year % 100 / 4;
        calendar.set(Calendar.DAY_OF_MONTH, insectsAwakenDay);
        dates[4] = calendar.getTimeInMillis();
        int vernalEquinoxDay = (int) ((year % 100) * D + 20.646f) - year % 100 / 4;
        if (year == 2084) { // 例外
            ++vernalEquinoxDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, vernalEquinoxDay);
        dates[5] = calendar.getTimeInMillis();

        // 清明、谷雨
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        int clearAndBrightDay = (int) ((year % 100) * D + 4.81f) - year % 100 / 4;
        calendar.set(Calendar.DAY_OF_MONTH, clearAndBrightDay);
        dates[6] = calendar.getTimeInMillis();
        int grainRainDay = (int) ((year % 100) * D + 20.1f) - year % 100 / 4;
        calendar.set(Calendar.DAY_OF_MONTH, grainRainDay);
        dates[7] = calendar.getTimeInMillis();

        // 立夏、小满
        calendar.set(Calendar.MONTH, Calendar.MAY);
        int summerBeginsDay = (int) ((year % 100) * D + 5.52f) - year % 100 / 4;
        if (year == 1911) { // 例外
            ++summerBeginsDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, summerBeginsDay);
        dates[8] = calendar.getTimeInMillis();
        int grainBudsDay = (int) ((year % 100) * D + 21.04f) - year % 100 / 4;
        if (year == 2008) { // 例外
            ++grainBudsDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, grainBudsDay);
        dates[9] = calendar.getTimeInMillis();

        // 芒种、夏至
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        int grainInEarDay = (int) ((year % 100) * D + 5.678f) - year % 100 / 4;
        if (year == 1902) { // 例外
            ++grainInEarDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, grainInEarDay);
        dates[10] = calendar.getTimeInMillis();
        int summerSolsticeDay = (int) ((year % 100) * D + 21.37f) - year % 100 / 4;
        if (year == 1928) { // 例外
            ++summerSolsticeDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, summerSolsticeDay);
        dates[11] = calendar.getTimeInMillis();

        // 小暑、大暑
        calendar.set(Calendar.MONTH, Calendar.JULY);
        int slightHeatDay = (int) ((year % 100) * D + 7.108f) - year % 100 / 4;
        if (year == 1925 || year == 2016) { // 例外
            ++slightHeatDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, slightHeatDay);
        dates[12] = calendar.getTimeInMillis();
        int greatHeatDay = (int) ((year % 100) * D + 22.83f) - year % 100 / 4;
        if (year == 1922) { // 例外
            ++greatHeatDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, greatHeatDay);
        dates[13] = calendar.getTimeInMillis();

        // 立秋、处暑
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        int autumnBeginsDay = (int) ((year % 100) * D + 7.5f) - year % 100 / 4;
        if (year == 2002) { // 例外
            ++autumnBeginsDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, autumnBeginsDay);
        dates[14] = calendar.getTimeInMillis();
        int stoppingTheHeatDay = (int) ((year % 100) * D + 23.13f) - year % 100 / 4;
        calendar.set(Calendar.DAY_OF_MONTH, stoppingTheHeatDay);
        dates[15] = calendar.getTimeInMillis();

        // 白露、秋分
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        int whiteDewsDay = (int) ((year % 100) * D + 7.646f) - year % 100 / 4;
        if (year == 1927) { // 例外
            ++whiteDewsDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, whiteDewsDay);
        dates[16] = calendar.getTimeInMillis();
        int autumnEquinoxDay = (int) ((year % 100) * D + 23.042f) - year % 100 / 4;
        if (year == 1942) { // 例外
            ++autumnEquinoxDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, autumnEquinoxDay);
        dates[17] = calendar.getTimeInMillis();

        // 寒露、霜降
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        int coldDewsDay = (int) ((year % 100) * D + 8.318f) - year % 100 / 4;
        calendar.set(Calendar.DAY_OF_MONTH, coldDewsDay);
        dates[18] = calendar.getTimeInMillis();
        int hoarFrostFalls = (int) ((year % 100) * D + 23.438f) - year % 100 / 4;
        if (year == 2089) { // 例外
            ++hoarFrostFalls;
        }
        calendar.set(Calendar.DAY_OF_MONTH, hoarFrostFalls);
        dates[19] = calendar.getTimeInMillis();

        // 立冬、小雪
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        int winterBeginsDay = (int) ((year % 100) * D + 7.438f) - year % 100 / 4;
        if (year == 2089) { // 例外
            ++winterBeginsDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, winterBeginsDay);
        dates[20] = calendar.getTimeInMillis();
        int lightSnowDay = (int) ((year % 100) * D + 22.36f) - year % 100 / 4;
        if (year == 1978) { // 例外
            ++lightSnowDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, lightSnowDay);
        dates[21] = calendar.getTimeInMillis();

        // 大雪、冬至
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        int heavySnowDay = (int) ((year % 100) * D + 7.18f) - year % 100 / 4;
        if (year == 1954) { // 例外
            ++heavySnowDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, heavySnowDay);
        dates[22] = calendar.getTimeInMillis();
        int winterSolsticeDay = (int) ((year % 100) * D + 21.94f) - year % 100 / 4;
        if (year == 1918 || year == 2021) { // 例外
            --winterSolsticeDay;
        }
        calendar.set(Calendar.DAY_OF_MONTH, winterSolsticeDay);
        dates[23] = calendar.getTimeInMillis();

        return dates;
    }
}