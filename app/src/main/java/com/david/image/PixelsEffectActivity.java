package com.david.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by David on 17/3/14.
 */

public class PixelsEffectActivity extends Activity {

    private ImageView mImageView1, mImageView2, mImageView3, mImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixels);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        mImageView1 = (ImageView) findViewById(R.id.imageview1);
        mImageView2 = (ImageView) findViewById(R.id.imageview2);
        mImageView3 = (ImageView) findViewById(R.id.imageview3);
        mImageView4 = (ImageView) findViewById(R.id.imageview4);

        mImageView1.setImageBitmap(bitmap);
        mImageView2.setImageBitmap(ImageHelper.handleImageNegetive(bitmap));
        mImageView3.setImageBitmap(ImageHelper.handleImageOldPhoto(bitmap));
        mImageView4.setImageBitmap(ImageHelper.handleImageRelief(bitmap));

    }
}
