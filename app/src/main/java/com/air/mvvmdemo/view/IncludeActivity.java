package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.ActivityIncludeBinding;
import com.air.mvvmdemo.model.User;

/**
 * Created by Air on 15/9/15.
 */
public class IncludeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIncludeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_include);
        User user1 = new User("first1", "last1", false);
        binding.setUser1(user1);
        User user2 = new User("first2", "last2", true);
        binding.setUser2(user2);

    }
}
