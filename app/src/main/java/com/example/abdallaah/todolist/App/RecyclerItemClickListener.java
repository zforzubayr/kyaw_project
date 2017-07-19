package com.example.abdallaah.todolist.App;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

//this class and interface is used for gestures inside the recycler view
public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    public interface OnRecyclerClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    //for override all intercept touch events,
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerview, final OnRecyclerClickListener mListener) {

        //initialize the private variables
        this.mListener = mListener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View childView = recyclerview.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, recyclerview.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: started");
                View childView = recyclerview.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    Log.d(TAG, "onSingleTapUp: calling Listener.onLongPress");
                    mListener.onItemLongClick(childView, recyclerview.getChildAdapterPosition(childView));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector != null) {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }
    }
}
