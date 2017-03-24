package com.example.test.commonlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ImageUtil {

    /**
     * 放大缩小图片
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
	public static Bitmap zoomBitmap(Bitmap bitmap,int w,int h){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float)w / width);
    	float scaleHeight = ((float)h / height);
    	matrix.postScale(scaleWidht, scaleHeight);
    	Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newbmp;
	}


    /**
     * 将Drawable转化为Bitmap
     * @param drawable
     * @return
     */
	 public static Bitmap drawableToBitmap(Drawable drawable){
	    	int width = drawable.getIntrinsicWidth();
	    	int height = drawable.getIntrinsicHeight();
	    	Bitmap bitmap = Bitmap.createBitmap(width, height,
					drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
							: Config.RGB_565);
	    	Canvas canvas = new Canvas(bitmap);
	    	drawable.setBounds(0,0,width,height);
	    	drawable.draw(canvas);
	    	return bitmap;
	    	
	}

    /**
     * 压缩图片
     * @param imgPath
     * @param maxSize
     */
	 public static void compressImageByPixel(String imgPath, float maxSize) {
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();
	    newOpts.inJustDecodeBounds = true;//只读�?,不读内容  
	    Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
	    newOpts.inJustDecodeBounds = false;  
	    int width = newOpts.outWidth;  
	    int height = newOpts.outHeight;  
	    int be = 1;  
	    if (width > height && width > maxSize) {//缩放�?,用高或�?�宽其中较大的一个数据进行计�?  
	        be = (int) (newOpts.outWidth / maxSize);  
	    } else if (width < height && height > maxSize) {  
	        be = (int) (newOpts.outHeight / maxSize);  
	    }  
	    be++;  
	    newOpts.inSampleSize = be;//设置采样�?  
	    newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认�?,可不�?
	    newOpts.inPurgeable = true;// 同时设置才会有效  
	    newOpts.inInputShareable = true;//。当系统内存不够时�?�图片自动被回收  
	    bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
	      
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    try {  
	        FileOutputStream fos = new FileOutputStream(new File(imgPath));
	        fos.write(baos.toByteArray());  
	        fos.flush();  
	        fos.close();  
	    } catch (Exception e) {
	        e.printStackTrace();  
	    }  
	}


    /**
     * 获得圆角图片
     * @param bitmap
     * @param roundPx
     * @return
     */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){
		
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
 
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
 
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
 
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
 
		return output;
	}

    /**
     * 获得带阴影影的图片
     * @param bitmap
     * @return
     */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap){
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap,
				0, height / 2, width, height / 2, matrix, false);
		
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
		
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap,
				deafalutPaint);
		
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
				+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
 
		return bitmapWithReflection;
	}


    /**
     * 将突破转换成base64字符串
     * @param filePath
     * @return
     */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);
		
	}


    /**
     * 将图片转换成base64字符串
     * @param bitmap
     * @return
     */
	public static String bitmapToString(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);
	}


    /**
     * 把base64字符串转换成图片
     * @param str
     * @return
     */
    public static Bitmap stringToBitmap(String str){
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }


    /**
     * 根据长、宽计算压缩比
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}


    /**
     * 获取小图
     * @param filePath
     * @return
     */
	public static Bitmap getSmallBitmap(String filePath) {
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(filePath, options);
	}


    /**
     * 获取指定压缩比例的bitmap
     * @param filePath
     * @param scale
     * @return
     */
	public static Bitmap getReduceBitmap(String filePath,int scale){
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(filePath, options);
	}


    /**
     * 添加图片到相册
     * @param context
     * @param path
     */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}


    /**
     * 获取图片大小
     * @param filePath
     * @return
     */
	public static int[] getImageSize(String filePath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int[] size = new int[2];
		size[0] = options.outWidth;
		size[1] = options.outHeight;
		return size;
	}
	
    /** android读取图片EXIF信息
    TAG_DATETIME 时间日期
    TAG_FLASH 闪光灯
    TAG_GPS_LATITUDE 纬度
    TAG_GPS_LATITUDE_REF 纬度参考 
    TAG_GPS_LONGITUDE 经度
    TAG_GPS_LONGITUDE_REF 经度参考 
    TAG_IMAGE_LENGTH 图片长
    TAG_IMAGE_WIDTH 图片宽
    TAG_MAKE 设备制造商
    TAG_MODEL 设备型号
    TAG_ORIENTATION 方向
    TAG_WHITE_BALANCE 白平衡
    */ 
    public static ArrayList<String> getImageEXIF(String path, ArrayList<String> tags){
	    try {
	        ExifInterface exifInterface=new ExifInterface(path);
	        ArrayList<String> result = new ArrayList<String>();
	        for(String tag : tags){
	        	result.add(exifInterface.getAttribute(tag));
	        }
//	        String smodel=exifInterface.getAttribute(ExifInterface.TAG_MODEL);
//	        String width=exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
//	        String height=exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
	        
	        return result;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
    }
}
