package com.rzm.testapplication.android_api.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.rzm.testapplication.R;

public class ColorTrackTextView extends AppCompatTextView {

    // 绘制不变色字体的画笔
    private Paint mOriginPaint;
    // 绘制变色字体的画笔
    private Paint mChangePaint;
    // 当前变色的进度
    private float mCurrentProgress = 0.5f;

    // 实现不同朝向
    private Direction mDirection;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView(@NonNull Context context) {
        this(context, null);
    }

    public ColorTrackTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        int originColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor,
                getTextColors().getDefaultColor());
        int changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor,
                getTextColors().getDefaultColor());

        // 回收
        typedArray.recycle();

        // 不变色的画笔
        mOriginPaint = getPaintByColor(originColor);
        // 变色的画笔
        mChangePaint = getPaintByColor(changeColor);
    }

    /**
     * 根据颜色获取画笔
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        // 设置颜色
        paint.setColor(color);
        // 设置抗锯齿
        paint.setAntiAlias(true);
        // 防抖动
        paint.setDither(true);
        // 设置字体的大小  就是TextView的字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int currentPoint = (int) (mCurrentProgress * getWidth());

        // 从左边到右边变色
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            // 绘制变色的部分 -- 开始 currentPoint = 0；结束 currentPoint = getWidth
            drawText(canvas, mChangePaint, 0, currentPoint);
            // 绘制不变色的部分
            drawText(canvas, mOriginPaint, currentPoint, getWidth());
        } else {
            // 绘制变色的部分 -- 开始 currentPoint = getWidth；结束 currentPoint = 0
            drawText(canvas, mChangePaint, getWidth() - currentPoint, getWidth());
            // 绘制不变色的部分
            drawText(canvas, mOriginPaint, 0, getWidth() - currentPoint);
        }
    }


    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
        // 判空
        if (TextUtils.isEmpty(text)) return;

        // 获取文字的区域
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        // 获取x坐标
        int dx = getWidth() / 2 - bounds.width() / 2;
        // 获取基线  baseLine
        Paint.FontMetricsInt fontMetricsInt = mChangePaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;

        // 绘制文字
        canvas.drawText(text, dx, baseLine, paint);
        canvas.restore();
    }

    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

}
