package com.david.image.activity;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.david.image.R;
import com.david.image.view.ImageMatrixView;

/**
 * Created by David on 17/3/14.
 * <p>
 * 通过矩阵变换实现图像变形特效
 */
public class ImageMatrixActivity extends Activity {
    private GridLayout mGridLayout;
    private ImageMatrixView mImageMatrixView;
    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[9];
    private float[] mImageMatrix = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix);

        mImageMatrixView = (ImageMatrixView) findViewById(R.id.imageMatrixView);
        mGridLayout = (GridLayout) findViewById(R.id.group_image);


        mGridLayout.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = mGridLayout.getWidth() / 3;
                mEtHeight = mGridLayout.getHeight() / 3;
                addEdits();
                InitMatrix();
            }
        });

    }


    private void addEdits() {
        for (int i = 0; i < 9; i++) {
            EditText editText = new EditText(ImageMatrixActivity.this);
            editText.setGravity(Gravity.CENTER);
            mEts[i] = editText;
            mGridLayout.addView(editText, mEtWidth, mEtHeight);
        }
    }

    private void InitMatrix() {
        for (int i = 0; i < 9; i++) {
            if (i % 4 == 0) {
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    private void getImageMatrix() {
        for (int i = 0; i < 9; i++) {
            EditText ed = mEts[i];
            mImageMatrix[i] = Float.valueOf(ed.getText().toString());
        }
    }

    public void btnChange(View view) {
        getImageMatrix();
        Matrix matrix = new Matrix();
        //使用矩阵
//        matrix.setValues(mImageMatrix);
        //使用Android API
//        matrix.setTranslate(100, 100);
        matrix.setScale(2, 2);
        //使用POST方法才能顺序显示
        matrix.postTranslate(200, 200);
        mImageMatrixView.setImageMatrix(matrix);
        mImageMatrixView.invalidate();
    }

    public void btnReset(View view) {
        InitMatrix();
        getImageMatrix();
        Matrix matrix = new Matrix();
        matrix.setValues(mImageMatrix);
        mImageMatrixView.setImageMatrix(matrix);
        mImageMatrixView.invalidate();
    }
}
