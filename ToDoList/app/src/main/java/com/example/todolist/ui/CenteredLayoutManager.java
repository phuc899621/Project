package com.example.todolist.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

class CenteredLayoutManager extends LinearLayoutManager {
    public CenteredLayoutManager(Context context) {
        super(context);
    }

    public CenteredLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenteredLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if(getChildCount() == 0) return;

        int totalHeight = getHeight();
        int totalWidth = getWidth();
        int firstChildTop = getChildAt(0).getTop();

        int offset = (totalHeight / 2) - (firstChildTop + (getChildAt(0).getHeight() / 2));

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setTranslationY(child.getTranslationY() + offset);
        }
    }
}