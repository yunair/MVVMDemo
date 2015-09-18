package com.air.mvvmdemo.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.ActivityBaseBinding;
import com.air.mvvmdemo.databinding.ActivityImportBinding;

/**
 * Created by Air on 15/9/18.
 */
public class GeneratedBindingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActivityImportBinding binding1 = ActivityImportBinding.inflate(inflater);
        ActivityImportBinding binding2 = ActivityImportBinding.inflate(inflater, container, false);
        // 绑定和inflate分离
        View v = inflater.inflate(R.layout.activity_base, container, false);
        ActivityBaseBinding separatelyBinding = ActivityBaseBinding.bind(v);

        return v;
    }
}
