package com.david.image.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.david.image.R;
import com.david.image.helper.ImageHelper;

/**
 * Created by David on 17/3/14.
 *
 *
 */

public class PrimaryColorActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private ImageView mImageView;
    private SeekBar mSeekBarHue, mSeekBarSaturation, mSeekBarLum;
    private static int MAX_VALUE = 255;
    private static int MID_VALUE = 127;
    private float mHue, mStauration, mLum;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primarycolor);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mSeekBarHue = (SeekBar) findViewById(R.id.seekbarHue);
        mSeekBarSaturation = (SeekBar) findViewById(R.id.seekbarSaturation);
        mSeekBarLum = (SeekBar) findViewById(R.id.seekbarLum);
        mSeekBarHue.setOnSeekBarChangeListener(this);
        mSeekBarSaturation.setOnSeekBarChangeListener(this);
        mSeekBarLum.setOnSeekBarChangeListener(this);
        mSeekBarLum.setMax(MAX_VALUE);
        mSeekBarHue.setMax(MAX_VALUE);
        mSeekBarSaturation.setMax(MAX_VALUE);
        mSeekBarHue.setProgress(MID_VALUE);
        mSeekBarSaturation.setProgress(MID_VALUE);
        mSeekBarLum.setProgress(MID_VALUE);
        mImageView.setImageBitmap(bitmap);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {

            //以下公式并不是固定的，而是经验值
            case R.id.seekbarHue:
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
                break;
            case R.id.seekbarSaturation:
                mStauration = progress * 1.0F / MID_VALUE;
                break;
            case R.id.seekbarLum:
                mLum = progress * 1.0F / MID_VALUE;
                break;
        }

        mImageView.setImageBitmap(ImageHelper.handleImageEffect(bitmap, mHue, mStauration, mLum));
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
