package com.item.demo.activity.http;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.item.demo.R;
import com.item.demo.entity.TestBean;
import com.item.demo.utils.databus.RxBus;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class PhotoTwoActivity extends AppCompatActivity {

    private CircleImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_two);
        imgView = (CircleImageView) findViewById(R.id.img_photo);
        findViewById(R.id.btn_photo_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });
        findViewById(R.id.btn_photo_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 向主界面发送一个消息
                TestBean bean = new TestBean(123, "你好啊撒打发斯蒂芬");
                RxBus.getInstance().send(bean);
            }
        });
        findViewById(R.id.btn_photo_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 测试
                test3();
            }
        });
    }

    private void setImage() {
        PictureSelector.create(PhotoTwoActivity.this)
                .openGallery(PictureMimeType.ofImage()) // 全部
                .theme(R.style.picture_white_style)
                .maxSelectNum(1) // 设置最大选择数量
                .imageSpanCount(4) // 每行显示个数
                .selectionMode(PictureConfig.SINGLE) // 单选
                .isCamera(true) // 是否显示拍照按钮
                .sizeMultiplier(0.5f)
                .enableCrop(true) // 是否裁剪
                .compress(true) // 是否压缩
                .hideBottomControls(true) // 是否显示uCrop工具栏
                .circleDimmedLayer(true) // 是否圆形剪切
                .showCropFrame(false) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isGif(true) // 是否显示gif图片
                .minimumCompressSize(100) // 小于100kb的图片不压缩
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true) // 裁剪是否可放大缩小图片
                .forResult(PictureConfig.CHOOSE_REQUEST); // 结果回调
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    LocalMedia media = PictureSelector.obtainMultipleResult(data).get(0);
                    String path = "";
                    if (media.isCut() && !media.isCompressed()) {
                        // 裁剪过
                        path = media.getCutPath();
                        Log.d("jiejie", "path------1--" + path);
                    } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        path = media.getCompressPath();
                        Log.d("jiejie", "path------2--" + path);
                    } else {
                        // 原图地址
                        path = media.getPath();
                        Log.d("jiejie", "path------3--" + path);
                    }
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.color.color_price)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(this).load(path).transition(new DrawableTransitionOptions().crossFade(500)).apply(options).into(imgView);
                    break;
            }
        }
    }

    private static final String TAG = "jiejie";

    private void test() {
        // 创建一个上游
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        // 创建一个下游Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext  " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };
        // 建立连接
        observable.subscribe(observer);
    }

    private void test1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit  1");
                emitter.onNext(1);
                Log.d(TAG, "emit  2");
                emitter.onNext(2);
                Log.d(TAG, "emit  3");
                emitter.onNext(3);
                Log.d(TAG, "emit  complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext  " + value);
                i++;
                if (i == 2) {
                    Log.d(TAG, "dispose");
                    mDisposable.dispose();
                    // Disposable（用完即可丢弃的）当调用它的dispose()方法时, 它就会将两根管道切断, 从而导致下游收不到事件
                    Log.d(TAG, "isDisposed  " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });
    }

    private void test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "Observable  thread is" + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                e.onNext(1);
                Log.d(TAG, "emit 2");
                e.onNext(2);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                        Log.d(TAG, "onNext :" + integer);
                    }
                });
    }
    private void test3(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,s);
            }
        });
    }
    private void test4(){
        Flowable.just("Hello,I am China!").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "___By Mars";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,"accept---" + s);
            }
        });
    }
}
