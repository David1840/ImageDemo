# Android 图像处理

　　本文是我在看过eclipse_xu大神的课程之后做的一些笔记，希望可以很好的掌握Android图片处理的相关知识。

# 一、图像色彩变换

## １、RGBA模型和色相、饱和度、亮度
![](https://108.61.246.246/wp-content/uploads/2017/03/RGBA.png)

　　RGBA是在R(Red)G(Green)B(Blue)模式上增加了alpha通道,alpha通道是不透明度。
<br/>　　色相/色调：物体传递的颜色

```
ColorMatrix hueMatrix = new ColorMatrix();
hueMatrix.setRotate(0, hue); //R
hueMatrix.setRotate(1, hue); //G
hueMatrix.setRotate(2, hue); //B
```
<br/>　　饱和度：颜色的准度，从0(灰)到100%(饱和)来进行描述

```
ColorMatrix saturationMatrix = new ColorMatrix();
saturationMatrix.setSaturation(saturation);
```

<br/>　　亮度：颜色的相对明暗程度

```
ColorMatrix lumMatrix = new ColorMatrix();
// 4*5的色彩矩阵，第0、6、12、18位分别对应以下各个数值，分别控制红、绿、蓝和透明度
lumMatrix.setScale(lum, lum, lum, 1);
```

<br/>　　在图像处理中，我们可以通过上面几种Android API来调整图片对应的色相、饱和度和亮度。将其封装进一个方法中，方便以后调用。

```
/**
     * @param bitmap     图像，传进来的bitmap是默认不可修改的，所以要重新创建一个与原图相同大小的Bitmap
     * @param hue        色相
     * @param saturation 饱和度
     * @param lum        亮度
     * @return 返回一张修改后的图片
     */
    public static Bitmap handleImageEffect(Bitmap bitmap, float hue, float saturation, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);        
        Canvas canvas = new Canvas(bmp);  //拥有一张与我们传进来的Bitmap一样大小的画布，之后的操作都讲在画布上完成
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  //抗锯齿
        
        //这里必须要使用三个ColorMatrix，用一个只会有一个调整生效
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue); //R
        hueMatrix.setRotate(1, hue); //G
        hueMatrix.setRotate(2, hue); //B


        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bmp;
    }
```


![](https://108.61.246.246/wp-content/uploads/2017/03/test123.gif)

## 2、矩阵变化
　　ColorMatrix，Matrix是矩阵的意思。上面我们所做的操作其实也是对颜色矩阵进行处理，使图片展现出各种的变化。
<br/>　　下图中的初始化矩阵，当它与任何颜色矩阵分量相乘得到的仍然是之前的颜色矩阵分量(像素点)，所以它才被当做初始化矩阵。
　　
　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image003-1-e1490061684323.png)
　　
<br/>　　如下图所示，如果将左下角的矩阵带入，我们会得到R+100，G+100，B和透明度A不变的一个像素点

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image006-1-e1490061577891.png)
　　
<br/>　　将第二个矩阵带入，会得到一个R，B，A不变，G*2的像素点

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image005-1-e1490062022833.png)
　　

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image004-1-e1490062764156.png)
　　
<br/>　　做一个例子，展示矩阵对图片的影响

   ![](https://108.61.246.246/wp-content/uploads/2017/03/Matrixgif.gif)
　　
　　
## 3、像素点分析

![](https://108.61.246.246/wp-content/uploads/2017/03/Image007-1-e1490066131336.png)

<br/>　　通过上面两部分的分析，我们知道ColorMatrix实际上是通过一个特定的矩阵去改变每一个像素点，甚至我们可以为每一个像素点添加不同的修改方法。比如我们在各种图片处理App中经常见到的底片效果、黑白照片、浮雕效果。如下图

![](https://108.61.246.246/wp-content/uploads/2017/03/PixelsEffect.png)

##### 1、底片效果

```
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
            
            //主要算法
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
```

##### 2、黑白老照片效果

```
    //黑白老照片效果
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
            
            //主要算法
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
```

##### 3、浮雕效果

```
    //浮雕效果
    //B.r = C.r-B.r + 127
    //B.g = C.g-B.g + 127
    //B.b = C.b-B.b + 127
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
```

# 二、图像图形变换

## 1、图形变换原理

　　跟之前所学习到的颜色矩阵相同，在图形变化的时候可以使用变换矩阵对图片进行操作，基本原理和颜色矩阵相同。
　　
　　![](https://108.61.246.246/wp-content/uploads/2017/03/ImageMatrix-e1490077500687.png)

##### 　　平移变换 

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image015-e1490077948889.png)

##### 　　旋转变换

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image017-e1490078075469.png)

##### 　　缩放变换

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image016-e1490078161324.png)

##### 　　错切变换

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image018-e1490078223555.png)

　　根据以上各种变换，可以得出如下结论：

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image019-e1490078348396.png)
　　
　　根据图形变化的原理，做一个例子展示：
　　
　　![](https://108.61.246.246/wp-content/uploads/2017/03/MatrixImage.gif)
　　
## 2、画笔风格
### 1、Xfermode

Src 原图， Dst 遮罩层，如图所示，我们可以根据我们的需要，将两者堆叠起来，获取我们想要的图形。

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image020.png)
　　

```
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

```

### 2、Shader

#####  bitmapShader

```
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        //三种方式 CLAMP 拉伸 REPEAT 重复 MIRROR 镜像
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
        //圆角矩形
        canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), 50, 50, mPaint);
        //圆形
        //canvas.drawCircle(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, mBitmap.getHeight() / 2, mPaint);
    }
```


以上Xfermode和bitmapShader都可以实现一个常用的圆形图片或圆角矩形图片,代码中也有体现

![](https://108.61.246.246/wp-content/uploads/2017/03/Image22.png) ![](https://108.61.246.246/wp-content/uploads/2017/03/Image21.png)

### 3、渐变Shader和Xfermode配合做出倒影效果

```
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
```

　　![](https://108.61.246.246/wp-content/uploads/2017/03/Image023.png)

## drawBitmapMesh

Mesh就是就是网格的意思，drawBitmapMesh就是将Bitmap分割成为若干个网格，操作每个交点处的坐标值，而达到操作图片图形的效果

```
    //一个坐标的横坐标存到数组奇数，纵坐标存到数组偶数位  修改后坐标
    private float[] verts = new float[Count * 2];
    
    //横坐标不变化，纵坐标做一个Sin()曲线变化
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
```

　　![](https://108.61.246.246/wp-content/uploads/2017/03/MeshBitmap.gif)
　　
　　
　　
　　
代码地址[ImageDome](https://github.com/David1840/ImageDemo)
