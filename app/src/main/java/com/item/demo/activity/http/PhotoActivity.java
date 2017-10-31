package com.item.demo.activity.http;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.item.demo.BuildConfig;
import com.item.demo.R;
import com.item.demo.utils.ToastUtils;
import com.item.demo.utils.dialog.PhotoDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 图片的拍照和从相册选择图片
 * 及图片的上传
 */
public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private PhotoDialog mDialog; // 弹窗
    private Button btnPhoto; // 上传头像
    private CircleImageView imgView; // 显示头像的控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        imgView = (CircleImageView) findViewById(R.id.img_photo);
        btnPhoto = (Button) findViewById(R.id.btn_photo);
        imgView.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_photo: //
                if (mDialog == null) mDialog = new PhotoDialog(this);
                mDialog.setPhotoInterface(new PhotoDialog.TakePhotoInterface() {
                    @Override
                    public void onTakeCamera() {
                        // 拍照
                        if (ToastUtils.hasPermission(PhotoActivity.this, Manifest.permission.CAMERA)) {
                            // 如果有权限就拍照了
                            gotoCamera();
                        } else {
                            // 没有权限就去申请
                            ToastUtils.requestPermission(PhotoActivity.this, 0x01, Manifest.permission.CAMERA);
                        }
                    }

                    @Override
                    public void onTakePhoto() {
                        // 从相册中选
                        if (ToastUtils.hasPermission(PhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            gotoPhoto();
                        } else {
                            ToastUtils.requestPermission(PhotoActivity.this, 0x02, Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }
                });
                mDialog.show();
                break;
            case R.id.btn_photo:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x01:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gotoCamera();
                }
                break;
            case 0x02:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gotoPhoto();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CAMERA: // 拍照
                Log.d("jiejie","--拍照--" +tempFile.getPath()+"  " + Uri.fromFile(tempFile));
                // --拍照--/storage/emulated/0/image/1509416401844.jpg  file:///storage/emulated/0/image/1509416401844.jpg
                Log.d("jiejie","--------" + getRealFilePathFromUri(PhotoActivity.this,Uri.fromFile(tempFile)));
                //--------/storage/emulated/0/image/1509416401844.jpg
                zoomPhoto(Uri.fromFile(tempFile));
                //gotoCutImage(Uri.fromFile(tempFile));
                break;
            case REQUEST_PHOTO: // 图库
                if (data != null) {
                    Uri sourceUri = data.getData();
                    Log.d("jiejie","---图库返回---" + sourceUri);
                    //---图库返回---content://media/external/images/media/9425
                    String s = getRealFilePathFromUri(PhotoActivity.this,sourceUri);
                    Log.d("jiejie","-------------" + s);
                    //-------------/storage/emulated/0/myHead/head.jpg
                    zoomPhoto(data.getData());

                }
                break;
            case REQUEST_CROP:// 剪切
                Log.d("jiejie","剪切返回--");
                if(data != null){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(jj));
                      // String j =  bitmaptoString(bitmap);
                        imgView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
                break;
        }
    }


    private File tempFile; // 调用照相机返回的图片文件
    private Uri jj;

    private static final int REQUEST_CAMERA = 100; // 照相返回
    private static final int REQUEST_PHOTO = 102; // 相册返回
    private static final int REQUEST_CROP = 104; //  切图返回

    /**
     * 去拍照 跳转到系统照相机
     */
    private void gotoCamera() {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) { // 判断SDK是否存在
            tempFile = new File(ToastUtils.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
            // 隐式的调用系统相册
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                // 如果是7.0及以上的系统使用FileProvider的方式创建一个Uri
                Uri contentUri = FileProvider.getUriForFile(PhotoActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            }
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    /**
     * 从图库获取相册
     */
    private void gotoPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, REQUEST_PHOTO);
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    /**
     * 调用系统剪切
     *
     * @param uri 路劲
     */
    private void gotoCutImage(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CROP);
    }
    private void zoomPhoto(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        // 这段注释掉就不会跳转到裁剪的activity
        intent.putExtra("crop","true");
        // 设置剪切图片的宽高比
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        // 是裁剪图片的宽高
        intent.putExtra("mOutputX",300);
        intent.putExtra("mOutputY",150);
        // 是否返回uri
        intent.putExtra("return-data",false); // true的话直接返回bitmap，可能很占内存 不建议
        jj=Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,jj);
        // 输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 面部识别，这里用不上
        // intent.putExtra("noFaceDetection",false);
        startActivityForResult(intent,REQUEST_CROP);
    }
    public String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return string;

    }
}
