package com.air.mvvmdemo;

import android.util.Log;
import android.view.View;

public class MyHandler {
    public static final String TAG = "MyHandler";
    public void onClickFriend(View view) {
        Log.d(TAG, "click friend");
    }

    public void onClickEnemy(View view) {
        Log.d(TAG, "click enemy");
    }
}
