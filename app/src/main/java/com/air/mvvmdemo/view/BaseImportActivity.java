package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.ActivityImportBinding;
import com.air.mvvmdemo.model.User;

/**
 * Created by Air on 15/9/11.
 */
public class BaseImportActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImportBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_import);
        User user1 = new User("first1", "last1", false);
        binding.setUser1(user1);
        User user2 = new User("first2", "last2", true);
        binding.setUser2(user2);
    }


}
