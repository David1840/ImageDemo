package com.david.image.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by David on 17/3/14.
 */

public class ImageHelper {

    /**
     * @param bitmap     图像，默认不可修改
     * @param hue        色相
     * @param saturation 饱和度
     * @param lum        亮度
     * @return 返回一张修改后的图片
     */
    public static Bitmap handleImageEffect(Bitmap bitmap, float hue, float saturation, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);  //拥有一张与我们传进来的Bitmap一样大小的画布，之后的操作都讲在画布上完成
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  //抗锯齿

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue); //R
        hueMatrix.setRotate(1, hue); //G
        hueMatrix.setRotate(2, hue); //B


        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        // 4*5的色彩矩阵，第0、6、12、18位分别对应以下各个数值，分别控制红、绿、蓝和透明度
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bmp;
    }


    //底片效果
    public static Bitmap handleImageNegetive(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int R, G, B, A;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            R = Color.red(color);
            G = Color.green(color);
            B = Color.blue(color);
            A = Color.alpha(color);

            R = 255 - R;
            G = 255 - G;
            B = 255 - B;

            if (R > 255) {
                R = 255;
            } else if (R < 0) {
                R = 0;
            }
            if (G > 255) {
                G = 255;
            } else if (G < 0) {
                G = 0;
            }
            if (B > 255) {
                B = 255;
            } else if (B < 0) {
                B = 0;
            }

            //合成新的颜色
            newPx[i] = Color.argb(A, R, G, B);
        }

        bitmap.setPixels(newPx, 0, width, 0, 0, width, height);

        return bitmap;
    }


    //老照片效果
    public static Bitmap handleImageOldPhoto(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
        int r, g, b, a, r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }

            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    //浮雕效果
    public static Bitmap handleImageRelief(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0, colorBefore = 0;
        int r, g, b, a, r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPx[i - 1];
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);
            a = Color.alpha(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);
            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

}
