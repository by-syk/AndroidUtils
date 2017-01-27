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

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by By_syk on 2016-06-09.
 */
class MyURLSpan extends URLSpan implements Parcelable {
    private boolean underline = true;

    public MyURLSpan(String url, boolean underline) {
        super(url);

        this.underline = underline;
    }

    private MyURLSpan(Parcel in) {
        super(in);
    }

    public static final Creator<MyURLSpan> CREATOR = new Creator<MyURLSpan>() {
        @Override
        public MyURLSpan createFromParcel(Parcel in) {
            return new MyURLSpan(in);
        }

        @Override
        public MyURLSpan[] newArray(int size) {
            return new MyURLSpan[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setUnderlineText(underline);
    }
}
