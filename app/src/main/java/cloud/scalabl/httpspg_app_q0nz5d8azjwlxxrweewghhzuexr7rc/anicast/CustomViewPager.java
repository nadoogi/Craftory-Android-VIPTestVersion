package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by ssamkyu on 2017. 4. 17..
 */

public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {

            Log.d("msg", "CustomViewPager hello1");
            return super.onTouchEvent(ev);

        } catch (IllegalArgumentException ex) {

            Log.d("msg", "error");
            ex.printStackTrace();

        }

        return false;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        try {

            Log.d("msg", "CustomViewPager hello2");
            return super.onInterceptTouchEvent(ev);

        } catch (IllegalArgumentException ex) {

            Log.d("msg", "error");
            ex.printStackTrace();

        }

        return false;

    }

}
