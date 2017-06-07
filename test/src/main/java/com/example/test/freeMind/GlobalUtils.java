package com.example.test.freeMind;

import com.example.test.commonlibrary.utils.CommonUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lijie on 2017/6/7.
 */

public class GlobalUtils {
    public static String createDBId(String tableName, String deviceId){
        return CommonUtil.md5("freeMind" + tableName + System.currentTimeMillis() + Math.random() + deviceId);
    }
}
