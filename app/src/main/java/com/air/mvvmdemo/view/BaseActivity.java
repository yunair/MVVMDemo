package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.air.mvvmdemo.MyHandler;
import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.ActivityBaseBinding;
import com.air.mvvmdemo.model.User;

/**
 * Base Usage for DataBinding
 * Created by Air on 15/9/9.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user1 = new User("Test1", "User1", false);
        User user2 = new User("Test2", "User2", true);
        binding.setUser1(user1);
        binding.setUser2(user2);
        MyHandler myHandler = new MyHandler();
        binding.setHandlers(myHandler);

    }
}
