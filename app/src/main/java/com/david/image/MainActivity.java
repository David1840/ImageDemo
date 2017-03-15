package com.david.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.david.image.activity.BitmapShaderActivity;
import com.david.image.activity.ColorMatrixActivity;
import com.david.image.activity.ImageMatrixActivity;
import com.david.image.activity.MeshActivity;
import com.david.image.activity.PixelsEffectActivity;
import com.david.image.activity.PrimaryColorActivity;
import com.david.image.activity.ReflectActivity;
import com.david.image.activity.RoundRectXfermodActivity;

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

    /**
     * 通过对像素的修改完成底片、老照片、浮雕效果
     *
     * @param view
     */
    public void btnPixelsEffect(View view) {
        startActivity(new Intent(this, PixelsEffectActivity.class));
    }

    public void btnMatrix(View view) {
        startActivity(new Intent(this, ImageMatrixActivity.class));
    }

    public void btnRound(View view) {
        startActivity(new Intent(this, RoundRectXfermodActivity.class));
    }

    public void btnImageShader(View view) {
        startActivity(new Intent(this, BitmapShaderActivity.class));
    }

    public void btnReflect(View view) {
        startActivity(new Intent(this, ReflectActivity.class));
    }

    public void btnMesh(View view) {
        startActivity(new Intent(this, MeshActivity.class));
    }

}
