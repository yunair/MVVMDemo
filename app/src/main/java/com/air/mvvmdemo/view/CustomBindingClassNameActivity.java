package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.air.ContanctItem3;
import com.air.mvvmdemo.ContactItem2;
import com.air.mvvmdemo.databinding.ContactItem1;
import com.air.mvvmdemo.R;

/**
 * Created by Air on 15/9/13.
 */
public class CustomBindingClassNameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pay attention to import
        ContactItem1 binding1 = DataBindingUtil.setContentView(this, R.layout.activity_custom_class_name1);
        ContactItem2 binding2 = DataBindingUtil.setContentView(this, R.layout.activity_custom_class_name2);
        ContanctItem3 binding3 = DataBindingUtil.setContentView(this, R.layout.activity_custom_class_name3);
    }
}
