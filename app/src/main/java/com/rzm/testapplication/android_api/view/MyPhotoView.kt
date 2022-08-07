package com.rzm.testapplication.android_api.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.rzm.testapplication.R
import kotlin.math.max
import kotlin.math.min

class MyPhotoView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    //最大放大比例的基础上再增加一定的比例，让view可以超出屏幕，为滑动做基础
    //不然总有一个方向贴着屏幕两边位置，无法拖动
    private var OVER_SCAL_FACTOR: Float = 1.5f
    private var mScaleLarge: Float = 1f
    private var mScaleSmall: Float = 1f
    private var scaleCurrent: Float = 1f
    private var mViewLeft: Float = 0f
    private var mViewTop: Float = 0f
    private var mOffSetX: Float = 0f
    private var mOffSetY: Float = 0f
    private var mLargeMode: Boolean = false
    private var mScale: Boolean = false
    private var animator: ObjectAnimator? = null

    private var mOverScroller = OverScroller(context)
    private var mGestureDetector = GestureDetector(context, PhotoViewGestureDetect())
    private var mScaleGestureDetector = ScaleGestureDetector(context, PhotoViewScaleGestureDetect())
    private var mPaint: Paint = Paint()
    private var mBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.girl)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(
            "MyPhotoView",
            "width = $width mBitmap.width = ${mBitmap.width} height = $height mBitmap.height = ${mBitmap.height}"
        )
        //初始化时view居中放置的left和top值
        mViewLeft = (width - mBitmap.width) / 2f
        mViewTop = (height - mBitmap.height) / 2f

        if (width / height < mBitmap.width / mBitmap.height) {
            //横屏图片
            mScaleSmall = width * 1.0f / mBitmap.width
            mScaleLarge = height * 1.0f / mBitmap.height * OVER_SCAL_FACTOR
        } else {
            //竖屏图片
            mScaleLarge = width * 1.0f / mBitmap.width * OVER_SCAL_FACTOR
            mScaleSmall = height * 1.0f / mBitmap.height
        }
        scaleCurrent = mScaleSmall
        Log.i(
            "MyPhotoView",
            "mScaleCurrent = $scaleCurrent mScaleLarge = $mScaleLarge mScaleSmall = $mScaleSmall"
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 处理放大后再缩小，图片偏移位置的问题(
        // 比如图片放大mScaleLarge比例后，将图片滑到左上角位置，然后点击左上角，你会发现图片偏离出
        // 屏幕了，而实际上我们是希望图片继续回到初始状态，即以屏幕中心为缩放点，完全显示在屏幕中
        // scaleCurrent的取值范围就是在mScaleLarge和mScaleSmall之间，所以平易的距离应该和缩放
        // 的比例关联起来
        // )
        var scaleFactor = (scaleCurrent - mScaleSmall) / (mScaleLarge - mScaleSmall)

        //实现拖动fling效果
        canvas?.translate(mOffSetX * scaleFactor, mOffSetY * scaleFactor)
        //后两个参数是缩放中心
        canvas?.scale(scaleCurrent, scaleCurrent, width / 2f, height / 2f)
        canvas?.drawBitmap(mBitmap, mViewLeft, mViewTop, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var onTouchEvent = mScaleGestureDetector.onTouchEvent(event)
        if (!mScaleGestureDetector.isInProgress) {
            onTouchEvent = mGestureDetector.onTouchEvent(event)
        }
        return onTouchEvent
    }

    private fun getScaleAnimator(): ObjectAnimator {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "scaleCurrent", 0f)
        }
        //当手动缩放之后，再双击要从当前缩放的比例缩放回来
        if (mScale) {
            mScale = false
            animator!!.setFloatValues(mScaleSmall, scaleCurrent)
        } else {
            animator!!.setFloatValues(mScaleSmall, mScaleLarge)
        }
        return animator!!
    }

    fun setScaleCurrent(scaleCurrent: Float) {
        this.scaleCurrent = scaleCurrent
        Log.i("MyPhotoView", "setMScaleCurrent = $scaleCurrent")
        invalidate()
    }

    /**
     * 边界限制，防止图片滑出屏幕外，始终把mOffSetX mOffSetY限制在一个合理的范围内
     * 整数取最小，负数取最大
     * 换一种写法更好理解：
     * 如果mOffSetX > 0, 取mOffSetX和(mBitmap.width * mScaleLarge - width) / 2中最小的值（(mBitmap.width * mScaleLarge - width) / 2一定是整数）
     * 如果mOffSetX < 0, 取mOffSetX和-(mBitmap.width * mScaleLarge - width) / 2中最大的值，如 -12和-100，取-12
     */
    private fun fixOffSets() {
        mOffSetX = min(mOffSetX, (mBitmap.width * mScaleLarge - width) / 2)
        Log.i(
            "MyPhotoView",
            "---min mOffSetX--- mOffSetX = $mOffSetX  totalOffSet = ${(mBitmap.width * mScaleLarge - width) / 2}"
        )
        mOffSetX = max(mOffSetX, -(mBitmap.width * mScaleLarge - width) / 2)
        Log.i(
            "MyPhotoView",
            "---max mOffSetX--- mOffSetX = $mOffSetX  totalOffSet = ${-(mBitmap.width * mScaleLarge - width) / 2}"
        )
        mOffSetY = min(mOffSetY, (mBitmap.height * mScaleLarge - height) / 2)
        Log.i(
            "MyPhotoView",
            "---min mOffSetY--- mOffSetY = $mOffSetY  totalOffSet = ${(mBitmap.height * mScaleLarge - height) / 2}"
        )
        mOffSetY = max(mOffSetY, -(mBitmap.height * mScaleLarge - height) / 2)
        Log.i(
            "MyPhotoView",
            "---max mOffSetY--- mOffSetY = $mOffSetY  totalOffSet = ${-(mBitmap.height * mScaleLarge - height) / 2}"
        )
    }

    inner class FlingRunnable : Runnable {
        override fun run() {
            if (mOverScroller.computeScrollOffset()) {
                mOffSetX = mOverScroller.currX.toFloat()
                mOffSetY = mOverScroller.currY.toFloat()
                Log.i(
                    "MyPhotoView",
                    "---FlingRunnable--- mOffSetX = $mOffSetX mOffSetY = $mOffSetY"
                )
                invalidate()
                postOnAnimation(this)
            }
        }
    }

    inner class PhotoViewScaleGestureDetect : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        var mInitialScale = 1f

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            mInitialScale = scaleCurrent
            return true
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            if ((scaleCurrent > mScaleSmall && !mLargeMode) || (scaleCurrent == mScaleSmall && mLargeMode)) {
                mLargeMode = !mLargeMode
            }
            scaleCurrent = mInitialScale * (detector?.scaleFactor ?: 1f)
            invalidate()
            mScale = true
            return false
        }
    }

    inner class PhotoViewGestureDetect : GestureDetector.SimpleOnGestureListener() {

        /**
         * 处理惯性滑动，借助OverScroller实现
         */
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (mLargeMode) {
                mOverScroller.fling(
                    mOffSetX.toInt(), mOffSetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(mBitmap.width * mScaleLarge - width) / 2).toInt(),
                    ((mBitmap.width * mScaleLarge - width) / 2).toInt(),
                    (-(mBitmap.height * mScaleLarge - height) / 2).toInt(),
                    ((mBitmap.height * mScaleLarge - height) / 2).toInt(),
                    200, 200
                )
                Log.i(
                    "MyPhotoView",
                    "---onFling--- mOffSetX = ${mOffSetX.toInt()} mOffSetY = ${mOffSetY.toInt()}" +
                            " minX = ${(-(mBitmap.width * mScaleLarge - width) / 2).toInt()}" +
                            " maxX = ${((mBitmap.width * mScaleLarge - width) / 2).toInt()}" +
                            " minY = ${(-(mBitmap.height * mScaleLarge - height) / 2).toInt()}" +
                            " maxY = ${((mBitmap.height * mScaleLarge - height) / 2).toInt()}"
                )
                postOnAnimation(FlingRunnable())
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        /**
         * 拖动事件
         * distanceX：x轴滑动的距离（单位时间）旧位置 - 新位置，所以是个负值（注意）
         */
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            //放大模式下才可以拖动，记录拖动的偏移量，注意distanceX是个负值，所以-=
            if (mLargeMode) {
                mOffSetX -= distanceX
                mOffSetY -= distanceY
                fixOffSets()
                invalidate()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            //一定要返回true，不然其他不会响应
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.i(
                "MyPhotoView",
                "onDoubleTap event = $e"
            )
            mLargeMode = !mLargeMode
            if (mLargeMode) {

                ////希望点击哪里就能以哪里为中心进行缩放///
                mOffSetX = (e?.x - width / 2) - (e.x - width / 2) * mScaleLarge / mScaleSmall
                mOffSetY = (e?.y - height / 2) - (e.y - height / 2) * mScaleLarge / mScaleSmall
                fixOffSets()
                ///////
                getScaleAnimator().start()
            } else {
                getScaleAnimator().reverse()
            }
            return super.onDoubleTap(e)
        }
    }
}