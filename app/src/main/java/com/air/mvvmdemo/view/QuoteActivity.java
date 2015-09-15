package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.TestQuote;

import java.util.HashMap;
import java.util.Map;

/**
 * About How to use quote (`, ", ', &quot;)
 * Created by Air on 15/9/15.
 */
public class QuoteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestQuote binding = DataBindingUtil.setContentView(this, R.layout.activity_test_quote);
        Map<String, String> map = new HashMap<>();
        map.put("firstName", "first1");
        binding.setMap(map);
    }
}
