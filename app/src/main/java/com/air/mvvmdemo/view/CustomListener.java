package com.air.mvvmdemo.view;

import android.annotation.TargetApi;
import android.databinding.BindingAdapter;
import android.databinding.adapters.ListenerUtil;
import android.os.Build;
import android.view.View;

import com.air.mvvmdemo.R;

/**
 * Created by Air on 15/9/21.
 */
public class CustomListener {
    @BindingAdapter("android:onLayoutChange")
    public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener oldValue,
                                                 View.OnLayoutChangeListener newValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (oldValue != null) {
                view.removeOnLayoutChangeListener(oldValue);
            }
            if (newValue != null) {
                view.addOnLayoutChangeListener(newValue);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public interface OnViewDetachedFromWindow {
        void onViewDetachedFromWindow(View v);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public interface OnViewAttachedToWindow {
        void onViewAttachedToWindow(View v);
    }

    @BindingAdapter("android:onViewAttachedToWindow")
    public static void setListener(View view, OnViewAttachedToWindow attached) {
        setListener(view, null, attached);
    }

    @BindingAdapter("android:onViewDetachedFromWindow")
    public static void setListener(View view, OnViewDetachedFromWindow detached) {
        setListener(view, detached, null);
    }

    @BindingAdapter({"android:onViewDetachedFromWindow", "android:onViewAttachedToWindow"})
    public static void setListener(View view, final OnViewDetachedFromWindow detach,
                                   final OnViewAttachedToWindow attach) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            final View.OnAttachStateChangeListener newListener;
            if (detach == null && attach == null) {
                newListener = null;
            } else {
                newListener = new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        if (attach != null) {
                            attach.onViewAttachedToWindow(v);
                        }
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        if (detach != null) {
                            detach.onViewDetachedFromWindow(v);
                        }
                    }
                };
            }
            final View.OnAttachStateChangeListener oldListener = ListenerUtil.trackListener(view,
                    newListener, R.id.onAttachStateChangeListener);
            if (oldListener != null) {
                view.removeOnAttachStateChangeListener(oldListener);
            }
            if (newListener != null) {
                view.addOnAttachStateChangeListener(newListener);
            }
        }
    }
}
