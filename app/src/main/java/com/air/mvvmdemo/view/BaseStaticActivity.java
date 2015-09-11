package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.ActivityStaticImportBinding;

/**
 * Created by Air on 15/9/11.
 */
public class BaseStaticActivity extends Activity{
    ActivityStaticImportBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_static_import);
}
