package com.david.image.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.david.image.R;

/**
 * Created by David on 17/3/15.
 */

public class MeshView extends View {
    private int Width = 200;
    private int Height = 200;
    private int Count = (Width + 1) * (Height + 1);
    //一个坐标的横坐标存到数组奇数，纵坐标存到数组偶数位  修改后坐标
    private float[] verts = new float[Count * 2];
    //修改前原始坐标
    private float[] origs = new float[Count * 2];
    private Bitmap mBitmap;
    private float k = 1;


    public MeshView(Context context) {
        super(context);
        InitView();
    }

    public MeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView();

    }

    public MeshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();

    }

    private void InitView() {
        int index = 0;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test2);
        float bmWidth = mBitmap.getWidth();
        float bmHeight = mBitmap.getHeight();

        for (int i = 0; i < Height + 1; i++) {
            float fy = bmHeight * i / Height;
            for (int j = 0; j < Width + 1; j++) {
                float fx = bmWidth * j / Width;
                origs[index * 2 + 0] = verts[index * 2 + 0] = fx;
                origs[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index += 1;
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < Height + 1; i++) {
            for (int j = 0; j < Width + 1; j++) {
                //横坐标不变化
                verts[(i * (Width + 1) + j) * 2 + 0] += 0;

                float offsetY = (float) Math.sin((float) j / Width * 2 * Math.PI + k * 2 * Math.PI);
                verts[(i * (Width + 1) + j) * 2 + 1] = origs[(i * (Width + 1) + j) * 2 + 1] + offsetY * 50;
            }
        }
        k += 0.01F;
        canvas.drawBitmapMesh(mBitmap, Width, Height, verts, 0, null, 0, null);
        invalidate();
    }
}
