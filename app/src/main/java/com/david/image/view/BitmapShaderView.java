package com.david.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.david.image.R;

/**
 * Created by David on 17/3/15.
 */

public class BitmapShaderView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    //Shader中比较奇特的一种BitmapShader
    private BitmapShader mBitmapShader;

    public BitmapShaderView(Context context) {
        super(context);
    }

    public BitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        //CLAMP 拉伸 REPEAT 重复 MIRROR 镜像
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
        //圆角矩形
        canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), 50, 50, mPaint);

        //圆形
//        canvas.drawCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, mBitmap.getHeight() / 2, mPaint);
    }
}
