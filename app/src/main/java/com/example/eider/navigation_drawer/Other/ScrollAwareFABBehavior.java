package com.example.eider.navigation_drawer.Other;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Eider on 04/11/2017.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    public ScrollAwareFABBehavior(Context context, AttributeSet attributeSet){
        super();
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed>0 && child.getVisibility() == View.VISIBLE){
            child.hide();
        }
        else if(dyConsumed<0 && child.getVisibility() != View.VISIBLE){
            child.show();
        }
    }
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,View directTargetChild,View Target,int nestedScrollAxes){

        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout,child,directTargetChild,Target,nestedScrollAxes);

    }

}
