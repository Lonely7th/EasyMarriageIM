package com.em.im.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Time ： 2018/12/7 .
 * Author ： JN Zhang .
 * Description ： .
 */
public class ImgUtil {
    private Context mContext;
    private String ImgDirPath;

    public ImgUtil(Context mContext){
        this.mContext = mContext;
        ImgDirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/pic0/";
        //创建文件夹
        File dir = new File(ImgDirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }else {
            File[] files = dir.listFiles();
            for(File file : files){
                file.delete();
            }
        }
    }

    public Bitmap getBitmap(Uri uri){
        try {
            // 读取uri所在的图片
            return MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片到相册
     */
    public void saveImageToGallery(String path) {
        Glide.get(mContext).clearMemory();
        Glide.with(mContext).load(path).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                // 首先保存图片
                File appDir = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), "pic");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                            file.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
            }
        });
    }

    public void saveImageToGallery(Bitmap bitmap) {
        Glide.get(mContext).clearMemory();
        Glide.with(mContext).load(bitmap).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                // 首先保存图片
                File appDir = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), "pic");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                            file.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
            }
        });
    }

}
