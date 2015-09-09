package com.air.mvvmdemo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.air.mvvmdemo.databinding.ActivityMainBinding;
import com.air.mvvmdemo.model.User;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user1 = new User("Test1", "User1", false);
        User user2 = new User("Test2", "User2", true);
        binding.setUser1(user1);
        binding.setUser2(user2);
        MyHandler myHandler = new MyHandler();
        binding.setHandlers(myHandler);
    }

}
