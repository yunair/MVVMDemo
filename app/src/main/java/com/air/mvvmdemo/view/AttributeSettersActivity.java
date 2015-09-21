package com.air.mvvmdemo.view;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.air.mvvmdemo.R;
import com.air.mvvmdemo.databinding.AttributeSetterBinding;
import com.air.mvvmdemo.model.Venue;
import com.squareup.picasso.Picasso;

/**
 * Attribute Setters
 * Created by Air on 15/9/21.
 */


/**
 * Renamed Setters
 */
@BindingMethods({
        @BindingMethod(type = android.widget.ImageView.class,
                attribute = "android:tint",
                method = "setImageTintList"),
})
public class AttributeSettersActivity extends Activity{
    public static final String TAG = "asd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AttributeSetterBinding binding = DataBindingUtil.setContentView(this, R.layout.attribute_setter);
        Venue venue = new Venue("mac", "http://www.zhuoku.com/zhuomianbizhi/computer-mac/20080923163206(37).htm");
        binding.setVenue(venue);


        // equals in attribute_setter.xml android:paddingLeft=1px"
        setPaddingLeft(binding.imageView, 1);
    }

    /**
     * Automatic Setters
     */
    private void automaticSetters(){
        DrawerLayout drawerLayout = new DrawerLayout(this);

        // equals in attribute_setter.xml app:scrimColor="@{@color/red}"
        drawerLayout.setScrimColor(Color.RED);

        // equals in attribute_setter.xml app:drawerListener="@{fragment.drawerListener}"
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * Custom Setters
     * @param view v
     * @param padding distance
     */
    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
        view.setPadding(padding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
        if (oldPadding != newPadding) {
            view.setPadding(newPadding,
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext()).load(url).error(error).into(view);
    }

}
