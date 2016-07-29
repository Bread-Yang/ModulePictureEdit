package com.mdground.moduleedittest.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdground.moduleedittest.R;
import com.mdground.moduleedittest.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoghourt on 7/13/16.
 */

public class ProductionView extends FrameLayout {

    public FrameLayout bgColorLayer, shadingLayer, drawBoardLayer, textLayer;

    public ImageView backgroundLayer;

    private AnimatorListenerAdapter listener;

    public List<DrawingBoardView> drawingBoardViewList;

    private List<TextView> textViewList;

    private int margin;

    public ProductionView(Context context) {
        super(context);

        View view = View.inflate(context, R.layout.view_production, null);

        bgColorLayer = (FrameLayout) view.findViewById(R.id.bg_color);
        shadingLayer = (FrameLayout) view.findViewById(R.id.shading);
        drawBoardLayer = (FrameLayout) view.findViewById(R.id.drawing_board);
        backgroundLayer = (ImageView) view.findViewById(R.id.background);
        textLayer = (FrameLayout) view.findViewById(R.id.texts);
        addView(view);

        margin = ViewUtils.px2dp(8);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        this.textViewList = new ArrayList();
        this.drawingBoardViewList = new ArrayList();
    }

    private void init() {
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.leftMargin = margin;
        params.rightMargin = margin;
        setLayoutParams(params);
    }

    public void clear() {
        for (DrawingBoardView v : this.drawingBoardViewList) {
            v.clear();
        }
        this.drawingBoardViewList.clear();
        this.textViewList.clear();
        this.bgColorLayer.setBackground(null);
        this.shadingLayer.setBackground(null);
        this.drawBoardLayer.removeAllViews();
        this.backgroundLayer.setImageBitmap(null);
        this.textLayer.removeAllViews();
    }

    public void toRightAnim() {
        objectAnimator(0.0f, 1.0f, this.listener);
    }

    public void rightAnim() {
        objectAnimator(-1.0f, 0.0f, null);
    }

    public void leftAnim() {
        objectAnimator(1.0f, 0.0f, null);
    }

    public void toLeftAnim() {
        objectAnimator(0.0f, -1.0f, this.listener);
    }

    public void setAnimListener(final Animation.AnimationListener animListener) {
        this.listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ProductionView.this.init();
                animListener.onAnimationEnd(null);
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                animListener.onAnimationStart(null);
                super.onAnimationStart(animation);
            }
        };
    }

    private void objectAnimator(float from, float to, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "", new float[]{from, to}).setDuration(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams params = (LayoutParams) ProductionView.this.getLayoutParams();
                int move = (int) (((float) ViewUtils.screenWidth()) * ((Float) animation.getAnimatedValue()).floatValue());
                params.leftMargin += move;
                params.rightMargin -= move;
                ProductionView.this.setLayoutParams(params);
            }
        });

        if (listener != null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    public void moveUp(int height) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.topMargin -= height;
        setLayoutParams(params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
