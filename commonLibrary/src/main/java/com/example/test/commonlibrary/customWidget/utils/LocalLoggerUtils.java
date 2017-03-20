package com.example.test.commonlibrary.customWidget.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lijie on 2016/10/26.
 */
public class LocalLoggerUtils {
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/MyCrash/log/";
    private static final String FILE_NAME = "log";//文件名
    private static final String FILE_NAME_SUFFIX = ".txt";//文件名后缀

    public static void writeLogToSdCard(String info){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        long now = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(now));
        File file = new File(PATH + FILE_NAME + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            //先记录当前时间
            pw.print(time + ":" + info);
            //记录当前机型相关信息
            pw.println();

            pw.close();

        } catch (Exception e) {

        }

    }
}
