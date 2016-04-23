package com.mibarim.main.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Hamed on 3/8/2016.
 */
public class CustomViewPager extends ViewPager {


    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //it's map
        if (event.getX()>300 && this.getCurrentItem() == 3) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //it's map
        if (event.getX()>300 && this.getCurrentItem() == 3) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }
}