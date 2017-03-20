/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.test.commonlibrary.customWidget.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lulei on 2016/4/7.
 */
public class CommonUtil {

    /**
     * 获取设备id
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {

        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("dykj");
        try {

            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!TextUtils.isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                Loger.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                Loger.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

            //序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!TextUtils.isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                Loger.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Loger.e("getDeviceId : ", deviceId.toString());

        return deviceId.toString();
    }

    /**
     * 获取手机mac地址
     *
     * @param context
     * @return
     */
    public static String getMACAddress(Context context) {
        String macAddress = "000000000000";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();

        if (info != null) {
            if (!TextUtils.isEmpty(info.getMacAddress())) {
                macAddress = info.getMacAddress().replace(":", "");
            }
        }
        return macAddress;
    }

    /**
     * 获取当前设备宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取版本名
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前设备高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 格式化日期数据
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String formatTime(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        return format.format(date);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date strToDate(String str, String formatStr) {

        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
//        String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9]))\\d{8}$";

//        return Pattern.matches(REGEX_MOBILE, mobile);
        return true;
    }


    /**
     * 将数据库复制到SD卡
     *
     * @param context
     * @param databaseName
     */
    public static void copyDatabase2SD(Context context, String databaseName) {
        String fileName = "/data/data/" + context.getPackageName() + "/databases/" + databaseName;

//        File file = new File("/data/data/" + context.getPackageName());
//        for (String path : file.list()) {
//            Log.e("Database2SD", path);
//        }
        File databaseFile = new File(fileName);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(databaseFile);
            FileChannel inChannel = fis.getChannel();

            fos = new FileOutputStream(new File("/sdcard/" + databaseName));
            FileChannel outChannel = fos.getChannel();
            outChannel.transferFrom(inChannel, 0, inChannel.size());

            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                fis.close();
                fos.close();
            }catch (Exception e){

            }

        }


    }


    /**
     * 校验IP
     * @param addr
     * @return
     */
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

    /**
     * 获取sd卡路径
     * @return
     */
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 设置包含图片的是文字
     * @param contentList
     * @param textView
     */
    public static void appendMixtureText(Context context, List<Object> contentList, TextView textView){
        for (Object obj : contentList){
            if (obj instanceof String){
                textView.append((CharSequence) obj);
            }
            else if (obj instanceof Bitmap){
                ImageSpan imgSpan = new ImageSpan(context, (Bitmap) obj);
                SpannableString spanString = new SpannableString("icon");
                spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.append(spanString);
            }
        }
    }

    /**
     * 获取当月第一天
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }

    /**
     * 获取当月最后一天
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        return s;

    }

    /**
     * MD5加密
     * @param string
     * @return
     */
    public static String md5(String string) {

        byte[] hash;

        try {

            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("Huh, MD5 should be supported?", e);

        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("Huh, UTF-8 should be supported?", e);

        }



        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {

            if ((b & 0xFF) < 0x10) hex.append("0");

            hex.append(Integer.toHexString(b & 0xFF));

        }

        return hex.toString();

    }

    /**
     * sign 加密算法
     * @param params 包括timestamp，groupId,deviceId,resId,token，
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createSign(Map<String, Object> params, boolean encode) {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            if (encode) {
                try {
                    temp.append(URLEncoder.encode(valueString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                temp.append(valueString);
            }
        }
        return md5(temp.toString()).toUpperCase();
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        try {
            return Base64.encodeToString(input.getBytes("utf-8"), Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 生成圆
     * @param strokeWidth
     * @param strokeColor
     * @param fillColor
     * @return
     */
    public static GradientDrawable createCircleShape(int strokeWidth,
                                              int strokeColor,
                                              int fillColor){
//        int strokeWidth = 5; // 3dp 边框宽度
//        int roundRadius = 15; // 8dp 圆角半径
//        int strokeColor = Color.parseColor("#2E3135");//边框颜色
//        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(fillColor);
        gd.setStroke(strokeWidth, strokeColor);

        return gd;
    }
}
