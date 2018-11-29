package com.mago.misrecetas.recipemain.ui.view;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private SwipeGestureListener listener;

    public SwipeGestureDetector(SwipeGestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();

        if (Math.abs(diffX) > Math.abs(diffY) &&
            Math.abs(diffX) > SWIPE_THRESHOLD &&
            Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (diffX > 0)
                listener.onLike();
            else
                listener.onDislike();
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
