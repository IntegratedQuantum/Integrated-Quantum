package quantum.integratedquantum.implementation;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class Input implements View.OnTouchListener {
    public static class TouchEvent {
        static final int TOUCH_DOWN = 0;
        static final int TOUCH_UP = 1;
        static final int TOUCH_DRAGGED = 2;
        public int type;
        public int x, y;
        int pointer;
    }
    private static final int MAX_TOUCHINGS = 10;
    private List<TouchEvent> freeObjects;
    private List<TouchEvent> touchEvents = new ArrayList<>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>();
    private double scaleX;
    private double scaleY;

    Input(View view, double scaleX, double scaleY) {
        freeObjects = new ArrayList<>();
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            int pointerCount = event.getPointerCount();
            for (int i = 0; i < MAX_TOUCHINGS; i++) {
                if (i >= pointerCount || (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex)) {
                    continue;
                }
                TouchEvent touchEvent;
                if (freeObjects.size() == 0)
                    touchEvent = new TouchEvent();
                else
                    touchEvent = freeObjects.remove(freeObjects.size() - 1);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        break;
                }
                touchEvent.pointer = event.getPointerId(i);
                touchEvent.x = (int) (event.getX(i) * scaleX);
                touchEvent.y = (int) (event.getY(i) * scaleY);
                touchEventsBuffer.add(touchEvent);
            }
            return true;
        }
    }
    List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            freeObjects.addAll(touchEvents);
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}