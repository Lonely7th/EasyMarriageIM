package com.em.im.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Time ： 2019/5/6 0006 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class TouchListView extends ListView {
    /**
     * 手指按下X的坐标
     */
    private float downY;
    /**
     * 手指按下Y的坐标
     */
    private float downX;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 认为是用户滑动的最小距离
     */
    private int mTouchSlop = 120;
    /**
     *  回调接口
     */
    private MyListTouchListener myListTouchListener;


    public TouchListView(Context context) {
        this(context, null);
    }

    public TouchListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        Log.d("TouchListView","screenWidth = " + screenWidth);
    }

    /**
     * 设置滑动删除的回调接口
     */
    public void setListTouchListener(MyListTouchListener myListTouchListener) {
        this.myListTouchListener = myListTouchListener;
    }

    /**
     * 处理我们拖动ListView item的逻辑
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float upX = ev.getX();
                float upY = ev.getY();
                Log.d("TouchListView","touch = " + Math.abs(upX - downX));
                if(Math.abs(upX - downX) > mTouchSlop && Math.abs(upY - downY) < 100){
                    if(upX - downX > 0){
                        myListTouchListener.touchRight();
                    }else{
                        myListTouchListener.touchLeft();
                    }
                    return true;
                }
        }

        return super.onTouchEvent(ev);
    }

    public interface MyListTouchListener {
        void touchLeft();
        void touchRight();
    }
}
