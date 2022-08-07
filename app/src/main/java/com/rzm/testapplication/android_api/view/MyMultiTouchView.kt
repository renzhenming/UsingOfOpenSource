package com.rzm.testapplication.android_api.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.rzm.testapplication.R

class MyMultiTouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var bigmap = BitmapFactory.decodeResource(context.resources, R.mipmap.girl)
    private var paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bigmap, offsetX, offsetY, paint)
    }

    private var downX = 0f
    private var downY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var upX = 0f
    private var upY = 0f

    private var currentPointerId:Int = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //event.actionMasked多指操作事件
        when (event.actionMasked) {
//        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                //只会执行一次，是第一个手指按下的事件
                downX = event.x
                downY = event.y
                //每个手指都有一个id和index的属性，而第一根手指的id是0
                currentPointerId = 0
                Log.i("MyMultiTouchView","ACTION_DOWN currentPointerId = $currentPointerId")
            }

            MotionEvent.ACTION_MOVE -> {
                //多指操作的move事件也是在这里处理
                //根据id获取index. index不一定等于id
                var index = event.findPointerIndex(currentPointerId);
                //获取当前手指的偏移量来滑动view
                offsetX = upX + event.getX(index) - downX
                offsetY = upY + event.getY(index) - downY
                //move的时候不断刷新去移动view
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                //抬起手指是记录偏移值
                upX = offsetX
                upY = offsetY
                Log.i("MyMultiTouchView","ACTION_UP currentPointerId = $currentPointerId")
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                //第n个手指按下的事件（最后一个按下的手指处理滑动事件）
                var actionIndex = event.actionIndex
                currentPointerId = event.getPointerId(actionIndex)

                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                upX = offsetX
                upY = offsetY
                Log.i("MyMultiTouchView","ACTION_POINTER_DOWN currentPointerId = $currentPointerId")
            }

            MotionEvent.ACTION_POINTER_UP -> {
                //第n个手指抬起的事件,多指操作时，如果最后一个手指滑动后抬起，会crash（先抬其他手指没事）
                //这里判断如果抬起的手指刚好就是当前的触摸的手指，
                //IllegalArgumentException: pointerIndex out of range pointerIndex=-1 pointerCount=2
                var actionIndex = event.actionIndex
                var pointerId = event.getPointerId(actionIndex)
                //只有抬起的是当前处理事件的手指才需要处理，否则不处理
                if (pointerId == currentPointerId) {
                    //如果抬起的是最后一个手指，那么把事件交给倒数第二个手指
                    if (actionIndex == event.pointerCount - 1) {
                        actionIndex = event.pointerCount - 2
                    } else {
                        //如果抬起的是中间的手指，那么把事件交给他的下一个手指处理
                        actionIndex++
                    }
                    //当一个手指抬起后，需要把当前事件交给另一个手指去处理
                    currentPointerId = event.getPointerId(actionIndex)
                    downX = event.getX(actionIndex)
                    downY = event.getY(actionIndex)
                    upX = offsetX
                    upY = offsetY
                }
                Log.i("MyMultiTouchView","ACTION_POINTER_UP currentPointerId = $currentPointerId")
            }

            else -> {
            }

        }
        return true
    }
}