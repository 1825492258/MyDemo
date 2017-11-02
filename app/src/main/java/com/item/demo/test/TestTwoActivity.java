package com.item.demo.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.item.demo.R;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class TestTwoActivity extends AppCompatActivity  {
    private IjkMediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private LinearLayout linearLayout;
    private boolean f = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        linearLayout = (LinearLayout)findViewById(R.id.line);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 连接ijkPlayer
                mediaPlayer.setDisplay(holder);
                // 开启异步准备
                mediaPlayer.prepareAsync();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
        mediaPlayer = new IjkMediaPlayer();
        try {
            mediaPlayer.setDataSource("https://media.w3.org/2010/05/sintel/trailer.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                // 准备工作
                Log.d("jiejie","---- onPrepared");
                iMediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                // 完成
                Log.d("jiejie","-----   onCompletion");
                iMediaPlayer.seekTo(0);
                iMediaPlayer.start();
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                // 当前加载进度的
            }
        });
        // 当触摸时显示或消失底部按钮
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(f){
                        f =false;
                        linearLayout.setVisibility(View.VISIBLE);
                    }else {
                        f=true;
                        linearLayout.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
    }
}
