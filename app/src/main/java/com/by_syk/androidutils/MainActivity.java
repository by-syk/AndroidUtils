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

package com.by_syk.androidutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.by_syk.lib.toast.GlobalToast;

/**
 * Created by By_syk on 2016-07-16.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onMyClick(View view) {
        switch (view.getId()) {
            case R.id.bt_aboutmsgrender:

                break;
            case R.id.bt_chinesetraditionalfestivals:

                break;
            case R.id.bt_dialogrootview:

                break;
            case R.id.bt_globaltoast:
                GlobalToast.showToast(this, R.string.app_name);
                break;
            case R.id.bt_icongridview:
                startActivity(new Intent(this, IconGridActivity.class));
                break;
            case R.id.bt_sp:

                break;
            case R.id.bt_urianalysr:

                break;
        }
    }
}
