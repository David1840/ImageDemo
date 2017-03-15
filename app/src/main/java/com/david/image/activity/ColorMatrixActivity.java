package com.david.image.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.david.image.R;

/**
 * Created by David on 17/3/14.
 * 颜色矩阵
 */

public class ColorMatrixActivity extends Activity {


    private ImageView mImageView;
    private GridLayout mGroup;
    private Bitmap bitmap;
    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colormatrix);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mImageView = (ImageView) findViewById(R.id.imageview);
        mGroup = (GridLayout) findViewById(R.id.group);
        mImageView.setImageBitmap(bitmap);

        //onCreate时控件还没有绘制完成，无法获取控件的宽和高，使用post方法，当控件绘制完成后执行run()方法
        mGroup.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = mGroup.getWidth() / 5;
                mEtHeight = mGroup.getHeight() / 4;
                addEdits();
                InitMatrix();
            }
        });
    }


    /**
     * 添加20个EditText
     */
    private void addEdits() {
        for (int i = 0; i < 20; i++) {
            EditText editText = new EditText(ColorMatrixActivity.this);
            mEts[i] = editText;
            mGroup.addView(editText, mEtWidth, mEtHeight);
        }
    }

    /**
     * 初始化矩阵
     */
    private void InitMatrix() {
        for (int i = 0; i < 20; i++) {
            if (i % 6 == 0) {   //  0\6\12\18位取1
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    private void getMatrix() {
        for (int i = 0; i < 20; i++) {
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    private void setImageMatrix() {
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        mImageView.setImageBitmap(bmp);
    }

    public void btnChange(View view) {
        getMatrix();
        setImageMatrix();
    }

    public void btnReset(View view) {
        InitMatrix();
        getMatrix();
        setImageMatrix();
    }
}
