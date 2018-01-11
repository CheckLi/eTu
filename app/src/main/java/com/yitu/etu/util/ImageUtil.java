package com.yitu.etu.util;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lim
 * @ClassName: ImageUtil
 * @Description: 图片工具
 * @mail lgmshare@gmail.com
 * @date 2014年6月3日 下午4:02:29
 */
public class ImageUtil {

    private static float[] carray = new float[20];
    public static String cachePath = Environment.getExternalStorageDirectory() + "/etuImageCache/";//压缩图片的时候使用的缓存路径

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     *
     * @param imagePath  图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }

    /**
     * 编辑图片大小，保持图片不变形。
     *
     * @param sourceBitmap
     * @param resetWidth
     * @param resetHeight
     * @return
     */
    public static Bitmap resetImage(Bitmap sourceBitmap, int resetWidth,
                                    int resetHeight) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        int tmpWidth;
        int tmpHeight;
        float scaleWidth = (float) resetWidth / (float) width;
        float scaleHeight = (float) resetHeight / (float) height;
        float maxTmpScale = scaleWidth >= scaleHeight ? scaleWidth
                : scaleHeight;
        // 保持不变形
        tmpWidth = (int) (maxTmpScale * width);
        tmpHeight = (int) (maxTmpScale * height);
        Matrix m = new Matrix();
        m.setScale(maxTmpScale, maxTmpScale, tmpWidth, tmpHeight);
        sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, width, height,
                m, false);
        // 切图
        int x = (tmpWidth - resetWidth) / 2;
        int y = (tmpHeight - resetHeight) / 2;
        return Bitmap.createBitmap(sourceBitmap, x, y, resetWidth, resetHeight);
    }

    /**
     * 获取本地图片并指定高度和宽度
     *
     * @param imagePath
     * @return
     */
    public static Bitmap getNativeImage(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回myBitmap为空
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 200);
        int ys = options.outHeight % 200;// 求余数
        float fe = ys / (float) 200;
        if (fe >= 0.5)
            be = be + 1;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        myBitmap = BitmapFactory.decodeFile(imagePath, options);
        return myBitmap;
    }

    /**
     * 代码创建一个selector 代码生成会清除padding
     */
    public static Drawable CreateStateDrawable(Context context, int bulr,
                                               int focus) {
        Drawable bulrDrawable = context.getResources().getDrawable(bulr);
        Drawable focusDrawable = context.getResources().getDrawable(focus);
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},
                focusDrawable);
        drawable.addState(new int[]{}, bulrDrawable);
        return drawable;
    }

    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressForQuality(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方法：
     *
     * @param image
     * @return
     */
    public static Bitmap compressForQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        LogUtil.e("=====baos.toByteArray().length / 1024:" + baos.toByteArray().length / 1024);
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 质量压缩方法：
     *
     * @param image
     * @return
     */
    public static Bitmap compressForQualityByShare(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 30, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        LogUtil.e("=====baos.toByteArray().length / 1024:" + baos.toByteArray().length / 1024);
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressForScale(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        LogUtil.e("=====baos.toByteArray().length / 1024:" + baos.toByteArray().length / 1024);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressForQuality(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片按比例大小压缩方法 给分享使用
     *
     * @param image
     * @return
     */
    public static Bitmap compressForScaleByShare(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 30, baos);
        LogUtil.e("=====baos.toByteArray().length / 1024:" + baos.toByteArray().length / 1024);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressForQualityByShare(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {

        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        paint.setColorFilter(null);
        c.drawBitmap(bmpGrayscale, 0, 0, paint);
        ColorMatrix cm = new ColorMatrix();
        getValueBlackAndWhite();
        cm.set(carray);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static void getValueSaturation() {
        // 高饱和度
        carray[0] = 5;
        carray[1] = 0;
        carray[2] = 0;
        carray[3] = 0;
        carray[4] = -254;
        carray[5] = 0;
        carray[6] = 5;
        carray[7] = 0;
        carray[8] = 0;
        carray[9] = -254;
        carray[10] = 0;
        carray[11] = 0;
        carray[12] = 5;
        carray[13] = 0;
        carray[14] = -254;
        carray[15] = 0;
        carray[16] = 0;
        carray[17] = 0;
        carray[18] = 5;
        carray[19] = -254;

    }

    private static void getValueBlackAndWhite() {
        // 黑白
        carray[0] = (float) 0.308;
        carray[1] = (float) 0.609;
        carray[2] = (float) 0.082;
        carray[3] = 0;
        carray[4] = 0;
        carray[5] = (float) 0.308;
        carray[6] = (float) 0.609;
        carray[7] = (float) 0.082;
        carray[8] = 0;
        carray[9] = 0;
        carray[10] = (float) 0.308;
        carray[11] = (float) 0.609;
        carray[12] = (float) 0.082;
        carray[13] = 0;
        carray[14] = 0;
        carray[15] = 0;
        carray[16] = 0;
        carray[17] = 0;
        carray[18] = 1;
        carray[19] = 0;
    }

    /***/
    /**
     * 去色同时加圆角
     *
     * @param bmpOriginal 原图
     * @param pixels      圆角弧度
     * @return 修改后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
        return toRoundCorner(toGrayscale(bmpOriginal), pixels);
    }

    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 使圆角功能支持BitampDrawable
     *
     * @param bitmapDrawable
     * @param pixels
     * @return
     */
    @SuppressWarnings("deprecation")
    public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
                                               int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * 生成图片倒影
     *
     * @param originalImage
     * @return
     */
    public static Bitmap createReflectedImage(Bitmap originalImage) {
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * bitmap保存为本地图片
     */
    public static void saveBitmap(String url, Bitmap mBitmap)
            throws IOException {
        File f = new File(url);
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fOut!=null) {
            mBitmap.compress(CompressFormat.JPEG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getBitmap(Context context, Uri uri) {
        InputStream in = null;
        try {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = 2;// getPowerOfTwoForSampleRatio(ratio);
            bitmapOptions.inDither = true;// optional
            bitmapOptions.inPreferredConfig = Config.ARGB_8888;// optional
            in = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(in, null, bitmapOptions);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static void deleteImage(String path) {
        File file = new File(path);
        file.deleteOnExit();
    }

    /**
     * 图片资源回收
     *
     * @param bitmap
     */
    public static void distoryBitmap(Bitmap bitmap) {
        if (null != bitmap && bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * Resize the bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }


    /**
     * Check the SD card
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }


    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static File savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        File photoFile = null;

        if (checkSDCardAvailable()) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
//						fileOutputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (photoFile.isFile()) {
            System.out.println("aaaaa");
        }
        return photoFile;
    }


    /**
     * view转bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap createViewBitmap(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.RGB_565);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }

    public static String getBase64Encode(Bitmap bitmap) {
        String bitmapByEncode = null;
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            bitmapByEncode = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return bitmapByEncode;
    }

    public static Drawable createRoundRectBackground(int width, int height, int backgroundColor, int roundX, boolean isFill) {
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        if (isFill) {
            paint.setStyle(Style.FILL);
        } else {
            paint.setStyle(Style.STROKE);//paint.setStrokeWidth(DensityUtil.dip2px(HuiZhuangApplication.getInstance().getApplicationContext(), 1));
            paint.setStrokeWidth(1);
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), roundX, roundX, paint);
        return new BitmapDrawable(bitmap);
    }

    //rounds数组大小为4 分别对应leftTopCorner,rightTop,leftBotton,rightBotton
    public static Drawable createCustomCornerBackground(int width, int
            height, int backgroundColor, int[] rounds, boolean isFill, boolean isSemicircle) {
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        if (isFill) {
            paint.setStyle(Style.FILL);
        } else {
            paint.setStyle(Style.STROKE);//paint.setStrokeWidth(DensityUtil.dip2px(HuiZhuangApplication.getInstance().getApplicationContext(), 1));
            paint.setStrokeWidth(1);
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        if (rounds == null) {
            canvas.drawRect(0, 0, width, height, paint);
        } else if (isSemicircle) {
            Path path = new Path();
            int radis = rounds[0];
            path.arcTo(new RectF(0, 0, height, height), 90, 180);
            path.lineTo(width - height / 2f, 0);
            path.arcTo(new RectF(width - height, 0, width, height), 270, 180);
            path.close();
            canvas.drawPath(path, paint);
        } else {
            Path path = new Path();
            if (rounds[0] == 0) {
                path.moveTo(0, 0);
            } else {
                path.arcTo(new RectF(0, 0, 2 * rounds[0], 2 * rounds[0]), 180, 90);
                // path.moveTo(rounds[0], 0);
            }
            if (rounds[1] == 0) {
                path.lineTo(width, 0);
            } else {
                path.lineTo(width - rounds[1], 0);
                path.arcTo(new RectF(width - 2 * rounds[1], 0, width, 2 * rounds[1]), 270, 90);
            }
            if (rounds[2] == 0) {
                path.lineTo(width, height);
            } else {
                path.lineTo(width, height - rounds[2]);
                path.arcTo(new RectF(width - 2 * rounds[2], height - 2 * rounds[2], width, height), 0, 90);
            }
            if (rounds[3] == 0) {
                path.lineTo(0, height);
            } else {
                path.lineTo(width - rounds[3], height);
                path.arcTo(new RectF(0, height - 2 * rounds[3], 2 * rounds[3], height), 90, 90);
            }
            path.close();
            canvas.drawPath(path, paint);
        }
        //canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), roundX, roundX, paint);
        return new BitmapDrawable(bitmap);
    }

    public static Drawable setbg(Drawable normal, Drawable pressed, int[] states) {
        StateListDrawable bg = new StateListDrawable();
        if (states == null) {
            bg.addState(new int[]{android.R.attr.state_pressed},
                    pressed);
            bg.addState(new int[]{android.R.attr.selectable},
                    pressed);
        } else {
            bg.addState(states, pressed);
        }
        bg.addState(new int[]{}, normal);
        return bg;
    }


    public static Bitmap decodeSampledBitmap(String path, int sample) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Calculate inSampleSize
        options.inSampleSize = sample;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {

            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * 获得指定的位图图像路径
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * 图像位图存储到指定的路径
     *
     * @param bitmap
     * @param outPath
     * @throws FileNotFoundException
     */
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(CompressFormat.JPEG, 100, os);
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图像的像素,这将修改图像宽度/高度。
     * 使用缩略图
     *
     * @param imgPath image path
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap map;

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > pixelW) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / pixelW);
        } else if (w < h && h > pixelH) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / pixelH);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        map = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return map;
    }

    /**
     * 压缩图像的大小,这将修改图像宽度/高度。
     * 使用缩略图
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > pixelW) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / pixelW);
        } else if (w < h && h > pixelH) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / pixelH);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * 压缩的质量,并生成图像到指定的路径
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize && options > 50) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(CompressFormat.JPEG, options, os);
        }
        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * 压缩质量,并生成图像到指定的路径
     *
     * @param imgPath
     * @param outPath
     * @param needsDelete 压缩后是否删除原始文件
     * @throws IOException
     * @param最大容量目标将被压缩比这个尺寸小。(kb)
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 比和生成指定的路径的拇指
     *
     * @param outPath
     * @param pixelW  目标像素的宽度
     * @param pixelH  目标像素的高度
     * @throws FileNotFoundException
     * @param形象
     */
    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage(bitmap, outPath);
    }

    /**
     * 比和生成指定的路径的拇指
     *
     * @param outPath
     * @param pixelW      target pixel of width
     * @param pixelH      target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage(bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void transImage(String fromFile, String toFile, int sc, int max, int quality) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(fromFile, options);
            if (options.outWidth > 1080) {
                float sc1 = options.outWidth / 1080f;
                int width = (int) (options.outWidth / sc1);
                int height = (int) (options.outHeight / sc1);
                options.inSampleSize = (int) (sc1+0.5f);
                options.inDither=false;    /*不进行图片抖动处理*/
                options.inPreferredConfig=null;  /*设置让解码器以最佳方式解码*/
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(fromFile, options);
                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();
                // 缩放图片的尺寸
                float scaleWidth = (float) width / bitmapWidth;
                float scaleHeight = (float) height / bitmapHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // 产生缩放后的Bitmap对象
                Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();//记得释放资源，否则会内存溢出
                }
                // save file
                saveImage(toFile, resizeBitmap, max);
                if (!resizeBitmap.isRecycled()) {
                    resizeBitmap.recycle();
                }
            } else {
                bitmap = BitmapFactory.decodeFile(fromFile);
                saveImage(toFile, bitmap, max);
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到文字的高度
     *
     * @param mPaint
     * @return
     */
    public static float getTextHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(fm.bottom - fm.top);
    }


    /**
     * 获取指定宽高缩放尺寸图片
     *
     * @return
     */
    public static Bitmap calculateInSampleSize(Context mcontext, int res, float width) {

        Bitmap src = BitmapFactory.decodeResource(mcontext.getResources(), res);
        float sc = width / (float) src.getWidth();
        // 缩放的矩阵
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(sc, sc);
        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0,
                src.getWidth(), src.getHeight(), scaleMatrix, true);
        if (src.isRecycled()) {
            src.recycle();
        }
        return bitmap;
    }

    /**
     * 保存图片到指定路径
     * Save image with specified size
     *
     * @param filePath the image file save path 储存路径
     * @param bitmap   the image what be save   目标图片
     * @param size     the file size of image   期望大小
     */
    private static File saveImage(String filePath, Bitmap bitmap, long size) {

        File result = new File(filePath.substring(0, filePath.lastIndexOf("/")));

        if (!result.exists() && !result.mkdirs()) return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(CompressFormat.JPEG, options, stream);

        while (stream.toByteArray().length / 1024 > size && options > 4) {
            stream.reset();
            options -= 4;
            bitmap.compress(CompressFormat.JPEG, options, stream);
        }

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(filePath);
    }


    /***
     * 图片切圆角，方向自由.
     */
    public static final int ALL = 347120;
    public static final int TOP = 547120;
    public static final int LEFT = 647120;
    public static final int RIGHT = 747120;
    public static final int BOTTOM = 847120;


    public static Bitmap fillet(Resources resources, int type, @DrawableRes int res, int roundPx) {

        return fillet(type,BitmapFactory.decodeResource(resources,res),roundPx);
    }

    /**
     * 生成任意方向圆角图片
     * @param type
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap fillet(int type,Bitmap bitmap,int roundPx) {
        try {
            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
            // 然后在画板上画出一个想要的形状的区域。
            // 最后把源图片帖上。
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();

            Bitmap paintingBoard = Bitmap.createBitmap(width,height, Config.ARGB_8888);
            Canvas canvas = new Canvas(paintingBoard);
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);

            if( TOP == type ){
                clipTop(canvas,paint,roundPx,width,height);
            }else if( LEFT == type ){
                clipLeft(canvas,paint,roundPx,width,height);
            }else if( RIGHT == type ){
                clipRight(canvas,paint,roundPx,width,height);
            }else if( BOTTOM == type ){
                clipBottom(canvas,paint,roundPx,width,height);
            }else{
                clipAll(canvas,paint,roundPx,width,height);
            }

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            //帖子图
            final Rect src = new Rect(0, 0, width, height);
            canvas.drawBitmap(bitmap, src, src, paint);
            return paintingBoard;
        } catch (Exception exp) {
            return bitmap;
        }
    }

    private static void clipLeft(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(offset,0,width,height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, 0, offset * 2 , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipRight(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(0, 0, width-offset, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(width - offset * 2, 0, width , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipTop(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(0, offset, width, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, 0, width , offset * 2);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipBottom(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(0, 0, width, height - offset);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, height - offset * 2 , width , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }

    private static void clipAll(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final RectF rectF = new RectF(0, 0, width , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
}
