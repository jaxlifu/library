package com.yimeng.library.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class BitmapUtils {

    public static Bitmap getScaleBitmap(String filename, int viewWidth, int viewHeight) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        options.inSampleSize = getSimpleSize(options, viewWidth, viewHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);

    }

    public static int getSimpleSize(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int simpleSize = 1;
        int imgHeight = options.outHeight;
        int imgWidth = options.outWidth;

        int widthRatio = (int) Math.ceil(imgWidth / viewWidth);
        int heightRatio = (int) Math.ceil(imgHeight / viewHeight);
        if (widthRatio > 1 && heightRatio > 1) {
            if (widthRatio > heightRatio) {
                simpleSize = widthRatio;
            } else {
                simpleSize = heightRatio;
            }
        }
        return simpleSize;
    }

    public static byte[] bitmap2Array(Bitmap bitmap) {
        byte[] byteArray = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
        return byteArray;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        try {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }


    /**
     * 图片转为灰色
     *
     * @param bmSrc
     * @return
     */
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        int width, height;
        height = bmSrc.getHeight();
        width = bmSrc.getWidth();
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);

        return bmpGray;
    }


    public static void saveBitmap(Bitmap mBitmap, String filePath) {
        //saveBitmap(mBitmap, new File(AppConstants.IMAGE_CACHE_PATH, filePath));
    }

    /**
     * 将bitmap 保存在文件中
     *
     * @param mBitmap 要保存的Bitmap
     * @param file    要保存的文件
     */
    public static void saveBitmap(Bitmap mBitmap, File file) {
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        if (file == null) {
            return;
        }
        try {
            baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bitmapData = baos.toByteArray();
            fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将Bitmap 对象转化为Base64字符串
     *
     * @param bitmap 图片
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将Base64字符串转化为图片
     */
    public static Bitmap base64ToBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.NO_WRAP);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String fileToBase64(String path) {
        try {
            FileInputStream inputFile = new FileInputStream(path);
            byte[] buffer = new byte[inputFile.available()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对图片进行压缩
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1024) {  //循环判断如果压缩后图片是否大于1M,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    //Bitmap压缩
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
    }

    /**
     * 对图片进行高斯模糊效果
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Bitmap fastBlur(Bitmap sentBitmap, int radius) {
        if (sentBitmap == null || sentBitmap.getConfig() == null) {
            return null;
        }
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rSum, gSum, bSum, x, y, i, p, yp, yi, yw;
        int vMin[] = new int[Math.max(w, h)];

        int divSum = (div + 1) >> 1;
        divSum *= divSum;
        int dv[] = new int[256 * divSum];
        for (i = 0; i < 256 * divSum; i++) {
            dv[i] = (i / divSum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackPointer;
        int stackStart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routSum, goutSum, boutSum;
        int rinSum, ginSum, binSum;

        for (y = 0; y < h; y++) {
            rinSum = ginSum = binSum = routSum = goutSum = boutSum = rSum = gSum = bSum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rSum += sir[0] * rbs;
                gSum += sir[1] * rbs;
                bSum += sir[2] * rbs;
                if (i > 0) {
                    rinSum += sir[0];
                    ginSum += sir[1];
                    binSum += sir[2];
                } else {
                    routSum += sir[0];
                    goutSum += sir[1];
                    boutSum += sir[2];
                }
            }
            stackPointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rSum];
                g[yi] = dv[gSum];
                b[yi] = dv[bSum];

                rSum -= routSum;
                gSum -= goutSum;
                bSum -= boutSum;

                stackStart = stackPointer - radius + div;
                sir = stack[stackStart % div];

                routSum -= sir[0];
                goutSum -= sir[1];
                boutSum -= sir[2];

                if (y == 0) {
                    vMin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vMin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinSum += sir[0];
                ginSum += sir[1];
                binSum += sir[2];

                rSum += rinSum;
                gSum += ginSum;
                bSum += binSum;

                stackPointer = (stackPointer + 1) % div;
                sir = stack[(stackPointer) % div];

                routSum += sir[0];
                goutSum += sir[1];
                boutSum += sir[2];

                rinSum -= sir[0];
                ginSum -= sir[1];
                binSum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinSum = ginSum = binSum = routSum = goutSum = boutSum = rSum = gSum = bSum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rSum += r[yi] * rbs;
                gSum += g[yi] * rbs;
                bSum += b[yi] * rbs;

                if (i > 0) {
                    rinSum += sir[0];
                    ginSum += sir[1];
                    binSum += sir[2];
                } else {
                    routSum += sir[0];
                    goutSum += sir[1];
                    boutSum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackPointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rSum] << 16) | (dv[gSum] << 8) | dv[bSum];

                rSum -= routSum;
                gSum -= goutSum;
                bSum -= boutSum;

                stackStart = stackPointer - radius + div;
                sir = stack[stackStart % div];

                routSum -= sir[0];
                goutSum -= sir[1];
                boutSum -= sir[2];

                if (x == 0) {
                    vMin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vMin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinSum += sir[0];
                ginSum += sir[1];
                binSum += sir[2];

                rSum += rinSum;
                gSum += ginSum;
                bSum += binSum;

                stackPointer = (stackPointer + 1) % div;
                sir = stack[stackPointer];

                routSum += sir[0];
                goutSum += sir[1];
                boutSum += sir[2];

                rinSum -= sir[0];
                ginSum -= sir[1];
                binSum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }
}
