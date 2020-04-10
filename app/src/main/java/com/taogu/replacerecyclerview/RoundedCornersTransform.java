package com.taogu.replacerecyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.Target;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.MessageDigest;

public class RoundedCornersTransform implements Transformation<Bitmap> {
//    private BitmapPool mBitmapPool;
    private int radius;
    //定义 常量
    public static final int TOP = 1;//上圆角
    public static final int BOTTOM = 2;//下圆角
    public static final int LEFT = 3;//下圆角
    public static final int RIGHT = 4;//下圆角
    public static final int TOP_LEFT = 5;//左上圆角
    public static final int BOTTOM_LEFT = 6;//左下圆角
    public static final int TOP_RIGHT = 7;//右上圆角
    public static final int BOTTOM_RIGHT = 8;//右下圆角
    public static final int ALL = 9;//圆角矩形

    private boolean isLeftTop=true, isRightTop=true, isLeftBottom=true, isRightBotoom=true;

    //定义枚举类型
    @CornerType int cornerType;
    // 声明构造器
    @IntDef({TOP, BOTTOM,LEFT,RIGHT,TOP_LEFT,BOTTOM_LEFT,TOP_RIGHT,BOTTOM_RIGHT,ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CornerType {

    }


    public RoundedCornersTransform(int radius) {
        this(radius,ALL);
    }

    public RoundedCornersTransform(int radius, @CornerType int cornerType) {
        this.radius = radius;
        this.cornerType = cornerType;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {

        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();//获取Glide位图池
        Bitmap toTransform = resource.get();//原图
        int targetWidth = outWidth == Target.SIZE_ORIGINAL ? toTransform.getWidth() : outWidth;//判断输出宽度是否与原尺寸相等
        int targetHeight = outHeight == Target.SIZE_ORIGINAL ? toTransform.getHeight() : outHeight;//判断输出高度是否与原尺寸相等

        Bitmap bitmap = bitmapPool.get(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);//返回一个原来大小ARGB_8888格式透明像素的Bitmap
        if(bitmap==null){//如果Glide位图池中不存在，创建新的Bitmap
            bitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        }
        bitmap.setHasAlpha(true);//透明的
        bitmap.setDensity(toTransform.getDensity());
        Canvas canvas = new Canvas(bitmap);//创建画布
        Paint paint = new Paint();//创建画笔
        paint.setAntiAlias(true);//抗锯齿
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));//将图片以平铺的方式按照原来的格式绘制到画布中
        switch (cornerType){
            case ALL://圆角矩形，
                canvas.drawRoundRect(new RectF(0,0,targetWidth,targetHeight), radius, radius, paint);//绘制圆角矩阵
                break;
            case TOP:
                canvas.drawRoundRect(new RectF(0,0,targetWidth,radius*2),radius,radius,paint);
                canvas.drawRect(new RectF(0,radius,targetWidth,targetHeight),paint);
                break;
            case BOTTOM:
                canvas.drawRoundRect(new RectF(0,0,targetWidth,radius*2),radius,radius,paint);
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case TOP_LEFT:
                break;
            case TOP_RIGHT:
                break;
            case BOTTOM_LEFT:
                break;
            case BOTTOM_RIGHT:
                break;
        }
        final Resource<Bitmap> resultBitmap;
        if (toTransform.equals(bitmap)) {//如Bitmap与绘制好的Bitmap相同，则直接返回原Bitmap
            resultBitmap = resource;
        } else {//如Bitmap与绘制好的Bitmap不相同，将绘制好的Bitmap添加到glide位图池
            resultBitmap = BitmapResource.obtain(bitmap, bitmapPool);
        }
        return resultBitmap;
    }



    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }

}
