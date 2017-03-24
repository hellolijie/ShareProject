package com.example.test.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class IntentUtils {

	/**
	 * 发邮件
	 * @param context
	 * @param title
	 * @param content
	 * @param address
	 * @return
	 */
	public static int sendMailByIntent(Context context,String title,String content,String address) {
		try{
			Intent data=new Intent(Intent.ACTION_SENDTO);
			data.setData(Uri.parse("mailto:" + address));
			data.putExtra(Intent.EXTRA_SUBJECT, title);
			data.putExtra(Intent.EXTRA_TEXT, content);
			context.startActivity(data);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
        return 1;

    }

	/**
	 * 发短信
	 * @param context
	 * @param content
	 * @param number
	 */
	public static void sendSms(Context context,String content,String number){
		Uri uri = Uri.parse("smsto:" + number);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);


//		SmsManager smsManager = SmsManager.getDefault();
//		List<String> divideContents = smsManager.divideMessage(content);
//		for (String text : divideContents) {
//			smsManager.sendTextMessage("150xxxxxxxx", null, text, sentPI, deliverPI);
//		}
	}


	/**
	 * 打电话
	 * @param context
	 * @param number
	 */
	public static void phone(Context context, String number){
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		context.startActivity(intent);
	}


	/**
	 * 拍照
	 * @param activity
	 * @param path
	 * @param fileName
	 * @param requestCode
	 * @return
	 */
	public static String startCamera(Activity activity,String path,String fileName,int requestCode){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file=new File(path);
		file.mkdirs();
        File imageFile = new File(file, fileName + ".jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		activity.startActivityForResult(intent, requestCode);
        return imageFile.getAbsolutePath();
	}


	/**
	 * 图库
	 * @param activity
	 * @param requestCode
	 * @param tile
	 */
	public static void startImageExplorer(Activity activity, int requestCode, String tile){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(Intent.createChooser(intent, tile),requestCode);
	}


	/**
	 *分享
	 * @param context
	 * @param activityTitle
	 * @param msgTitle
	 * @param msgText
	 * @param imgPath
	 */
	public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText,
						 String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}


	public static void openExplorer(Context context, String url){
		Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
	}

}
