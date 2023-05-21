package com.example.first

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator

class AnimatableView : View {

    private val TAG = "AnimatableView";

    private lateinit var mPaint: Paint;

    private val mXPos1: FloatArray = floatArrayOf(0.25f,0.75f,0.75f,0.25f);
    private val mYPos1: FloatArray = floatArrayOf(0.25f,0.25f,0.75f,0.75f);

    private val mXPos2: FloatArray = floatArrayOf(0.5f,0.7f,0.3f,0.3f);
    private val mYPos2: FloatArray = floatArrayOf(0.5f,0.7f,0.7f,0.7f);

    private var mXProcess: FloatArray = floatArrayOf(0.25f,0.75f,0.75f,0.25f);
    private var mYProcess: FloatArray = floatArrayOf(0.25f,0.25f,0.75f,0.75f);

    private fun init() {
        mPaint = Paint();
        mPaint.color = 0xFFFF00FF.toInt();
        mPaint.strokeWidth = 5.0f;
        mPaint.style = Paint.Style.STROKE;
        mPaint.strokeCap = Paint.Cap.ROUND;
    }

    fun startAnimation() {
        val animator = ValueAnimator();

        animator.setValues(PropertyValuesHolder.ofFloat("setValues", 0.0f,1.0f));
        animator.duration = 2000;
        animator.interpolator = AnticipateOvershootInterpolator(3.0f);

        animator.addUpdateListener {
            val n = animator.animatedValue as Float;
            for (i in mXProcess.indices) {
                mXProcess[i] = mXPos1[i] + (mXPos2[i] - mXPos1[i]) * n;
                mYProcess[i] = mYPos1[i] + (mYPos2[i] - mYPos1[i]) * n;
            }
            invalidate();
        };

        animator.startDelay = 2000;
        animator.start();
    }

    constructor(ctx: Context) : super(ctx) {
        Log.d(TAG, ": super Context");
        init();
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        Log.d(TAG, "super Context, AttributeSet: ");
        init();
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom);
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas);

        Log.d(TAG, "draw: $width $height");

        val path = Path();
        path.moveTo(mXProcess[0] * width, mYProcess[0] * height);
        for (i in 1 until mXProcess.size) {
            path.lineTo(mXProcess[i] * width, mYProcess[i] * height);
        }
        path.close();
        canvas?.drawPath(path, mPaint);
    }
}
