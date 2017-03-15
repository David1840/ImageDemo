package com.david.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.david.image.R;

/**
 * Created by David on 17/3/15.
 * <p>
 * 镜像倒影效果
 */

public class ReflectView extends View {

    private Bitmap mBitmap;
    private Bitmap mReflectBitmap;
    private Paint mPaint;

    public ReflectView(Context context) {
        super(context);
        initView();
    }

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ReflectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        Matrix matrix = new Matrix();
        //Scale是控制缩放，（1，-1）会将图片进行翻转，X轴对称
        matrix.setScale(1, -1);

        mReflectBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //线性渐变
        mPaint.setShader(new LinearGradient(0, mBitmap.getHeight(), 0, mBitmap.getHeight() * 1.4F, 0xDD000000, 0x10000000, Shader.TileMode.CLAMP));
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawBitmap(mReflectBitmap, 0, mBitmap.getHeight(), null);
        canvas.drawRect(0, mBitmap.getHeight(), mBitmap.getWidth(), mBitmap.getHeight() * 2, mPaint);
    }
}
