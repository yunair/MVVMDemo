package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.ActivityWithIdsBinding;

/**
 * test view with id will generate public final field
 * Created by Air on 15/9/18.
 */
public class ViewWithIDActivity extends Activity {
    public static final String TAG = "ViewWithIDActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWithIdsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_with_ids);
        TextView textViewFirstName = binding.firstName;
        TextView textViewLastName = binding.lastName;
        Log.d(TAG, "binding.firstName : " + textViewFirstName.getText());
        Log.d(TAG, "binding.lastName : " + textViewLastName.getText());
    }
}
