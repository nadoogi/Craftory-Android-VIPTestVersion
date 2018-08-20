package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by ssamkyu on 2018. 2. 22..
 */

public class SlaveScrollView extends ScrollView {

    private static final int SCROLL_THRESHOLD = 10;

    private boolean mScrolling;


    public SlaveScrollView(Context context) {
        super(context);
    }

    public SlaveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlaveScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getScrollY() > SCROLL_THRESHOLD) {
            mScrolling = true;
            onTouchEvent(ev);
            return false;
        } else if (mScrolling) {
            mScrolling = false;
            return false;
        }
        if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
            mScrolling = false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
