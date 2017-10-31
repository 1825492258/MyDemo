package com.item.demo.activity.http;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.item.demo.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import de.hdodenhof.circleimageview.CircleImageView;


public class PhotoTwoActivity extends AppCompatActivity {

    private CircleImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_two);
        imgView = (CircleImageView)findViewById(R.id.img_photo);
        findViewById(R.id.btn_photo_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });
    }

    private void setImage(){
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
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    LocalMedia media  =  PictureSelector.obtainMultipleResult(data).get(0);
                    String path = "";
                    if (media.isCut() && !media.isCompressed()) {
                        // 裁剪过
                         path = media.getCutPath();
                        Log.d("jiejie","path------1--" + path );
                    } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        path = media.getCompressPath();
                        Log.d("jiejie","path------2--" + path );
                    } else {
                        // 原图地址
                         path = media.getPath();
                        Log.d("jiejie","path------3--" + path );
                    }
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.color.color_price)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(this).load(path).apply(options).into(imgView);
                    break;
            }
        }
    }
}
