package com.item.demo.activity.wave;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gelitenight.waveview.library.WaveView;
import com.item.demo.R;

/**
 * 水波纹的效果
 */
public class WaveActivity extends AppCompatActivity {

    private WaveHelper mWaveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        WaveView waveView = (WaveView) findViewById(R.id.wave);
        mWaveHelper = new WaveHelper(waveView);
        waveView.setShapeType(WaveView.ShapeType.SQUARE); // 矩形
        waveView.setWaveColor(Color.parseColor("#045ee6"), Color.parseColor("#805196fe"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }
}
