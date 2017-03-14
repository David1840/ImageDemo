package com.david.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /**
     * 图像处理其实就是研究不同颜色矩阵对图像的处理效果
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * 通过控制图片的色相、饱和度、亮度来改变图片
     *
     * @param view
     */
    public void btnPrimaryColor(View view) {
        startActivity(new Intent(this, PrimaryColorActivity.class));
    }

    /**
     * 通过图片矩阵来改变图片
     *
     * @param view
     */
    public void btnColorMatrix(View view) {
        startActivity(new Intent(this, ColorMatrixActivity.class));
    }

    public void btnPixelsEffect(View view) {
        startActivity(new Intent(this, PixelsEffectActivity.class));
    }

}
