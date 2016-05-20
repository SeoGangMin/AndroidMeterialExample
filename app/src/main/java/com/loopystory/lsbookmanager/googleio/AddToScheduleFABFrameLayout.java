package com.loopystory.lsbookmanager.googleio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.loopystory.lsbookmanager.MyApplication;
import com.loopystory.lsbookmanager.R;


/**
 * Created by dhawal sodha parmar on 5/1/2015.
 */
public class AddToScheduleFABFrameLayout extends CheckableFrameLayout {
    private View mRevealView;
    private float mHotSpotX, mHotSpotY;
    private int mRevealViewOffColor;

    public AddToScheduleFABFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public AddToScheduleFABFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public AddToScheduleFABFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AddToScheduleFABFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        mRevealView = new View(context);
        mRevealView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mRevealView, 0);
        mRevealViewOffColor = getResources().getColor(R.color.theme_accent_1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mHotSpotX = event.getX();
            mHotSpotY = event.getY();
        }
        return super.onTouchEvent(event);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(MyApplication.isLollipop()) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            };
            setOutlineProvider(viewOutlineProvider);
            setClipToOutline(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setChecked(boolean checked, boolean allowAnimate) {
        super.setChecked(checked, allowAnimate);
        if (allowAnimate) {
            // TODO: switch to mHotSpotX/mHotSpotY/getWidth if/when nested reveals can be clipped
            // by parents. was possible in LPV79 but no longer as of this writing.
            Animator animator = ViewAnimationUtils.createCircularReveal(
                    mRevealView,
                    (int) getWidth() / 2, (int) getHeight() / 2, 0, getWidth() / 2);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setChecked(mChecked, false);
                }
            });
            animator.start();
            mRevealView.setVisibility(View.VISIBLE);
            mRevealView.setBackgroundColor(mChecked ? Color.WHITE : mRevealViewOffColor);
        } else {
            mRevealView.setVisibility(View.GONE);
            RippleDrawable newBackground = (RippleDrawable) getResources().getDrawable(mChecked
                    ? R.drawable.add_schedule_fab_ripple_background_on
                    : R.drawable.add_schedule_fab_ripple_background_off);
            if(MyApplication.isLollipop()) {
                setBackground(newBackground);
            }
        }
    }
}