package com.david.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.david.image.R;

/**
 * Created by David on 17/3/15.
 * 圆角矩形图片
 */

public class RoundRectXfermodView extends View {

    private Bitmap mBitmap;
    private Bitmap mOut;
    private Paint mPaint;

    public RoundRectXfermodView(Context context) {
        super(context);
        InitView();
    }

    public RoundRectXfermodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }

    public RoundRectXfermodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();
    }

    private void InitView() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);  //关闭硬件加速
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        mOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mOut);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Dst 遮罩层 也可以做一个圆形的遮罩层，形成圆形图片
        //矩形圆角
        //canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), 50, 50, mPaint);
        //圆形图片
        canvas.drawCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, mBitmap.getHeight() / 2, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //Src
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        mPaint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(mOut, 0, 0, null);
    }
}
