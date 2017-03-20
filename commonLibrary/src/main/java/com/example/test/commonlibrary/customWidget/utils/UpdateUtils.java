package com.example.test.commonlibrary.customWidget.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dykj.baselibrary.R;
import com.dykj.baselibrary.customWidget.CustomDialog;
import com.dykj.baselibrary.wrapper.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by lijie on 2016/9/10.
 */
public class UpdateUtils {

    private UpdateModel updateModel;
    private File apkFile;

    private UpdateStepListener updateStepListener;

    public void setUpdateStepListener(UpdateStepListener updateStepListener){
        this.updateStepListener = updateStepListener;
    }

    /**
     * 检查更新
     * @param url
     * @param appType
     * @param appCategory
     * @param appVersion
     * @param groupId
     */
    public void checkUpdate(String url, int appType, int appCategory, String appVersion, int groupId){
        Map<String, String> params = new HashMap<>();
        params.put("appType", String.valueOf(appType));
        params.put("appCategory", String.valueOf(appCategory));
        params.put("appCategory", String.valueOf(appCategory));
        params.put("appVersion", appVersion);
        params.put("groupId", String.valueOf(groupId));

        HttpUtils.post(url,
                params,
                new HashMap<String, String>(),
                new HttpUtils.HttpCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Logger.e("error-" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.e("check--" + response);
                        try {
                            UpdateModel updateModel = new Gson().fromJson(response, UpdateModel.class);
                            UpdateUtils.this.updateModel = updateModel;
                            if (updateModel.status == 1 && updateModel.response.updateFlag != 2){
//                            downloadAPK(updateModel);
                                if (updateStepListener != null){
                                    updateStepListener.checkSuc(updateModel);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 显示更新弹窗
     * @param context
     */
    public void showUpdateDialog(Context context){

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);

        if (updateModel.response.updateFlag == 0){
            dialogView.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
        }

        CustomDialog dialog = new CustomDialog.Builder(context).create(dialogView
                , new int[]{R.id.btn_confirm, R.id.btn_cancel}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == R.id.btn_cancel){
                            dialog.dismiss();
                            if (updateStepListener != null){
                                updateStepListener.cancelInstall();
                            }
                        }
                        else if (which == R.id.btn_confirm){
                            if (updateStepListener != null){
                                updateStepListener.confirmInstall();
                            }
                        }
                    }
                });

        dialog.setCancelable(false);

        dialog.show();
    }

    /**
     * 显示更新弹窗
     * @param context
     */
    public void showUpdateDialog(Context context, int dialogLayout){

        View dialogView = LayoutInflater.from(context).inflate(dialogLayout, null);

        if (updateModel.response.updateFlag == 0){
            dialogView.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
        }

        CustomDialog dialog = new CustomDialog.Builder(context).create(dialogView
                , new int[]{R.id.btn_confirm, R.id.btn_cancel}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == R.id.btn_cancel){
                            dialog.dismiss();
                            if (updateStepListener != null){
                                updateStepListener.cancelInstall();
                            }
                        }
                        else if (which == R.id.btn_confirm){
                            if (updateStepListener != null){
                                updateStepListener.confirmInstall();
                            }
                        }
                    }
                });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_NUMPAD_DIVIDE:
                            dialog.dismiss();
                            break;
                        case KeyEvent.KEYCODE_NUMPAD_ENTER:
                            if (updateStepListener != null){
                                updateStepListener.confirmInstall();
                            }
                            break;
                    }
                }
                return false;
            }
        });

        dialog.setCancelable(false);

        dialog.show();
    }

    /**
     * 下载apk
     */
    public void downloadAPK(){
        String sdCardPath = CommonUtil.getSDPath();
        String destFileDir = sdCardPath + "/" + "DYKJ";
        String destFileName = "dykj.apk";

        if (TextUtils.isEmpty(updateModel.response.appPath)){
            return;
        }

        HttpUtils.download(updateModel.response.appPath, new HttpUtils.HttpFileCallback(destFileDir, destFileName){

            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.e("downloadError" + e.getMessage());
            }

            @Override
            public void onResponse(File response, int id) {
                Logger.e("downloadSuc");
                apkFile = response;
                if (updateStepListener != null){
                    updateStepListener.downloadSuc();
                }
            }
        });
    }


    /**
     * 安装
     * @param context
     */
    public void install(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        context.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本名
     * @return 当前应用的版本号
     */
    public String getVersionName(Context context) {
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
     * 检查更新返回
     */
    public static class UpdateModel{
        @SerializedName("status")
        public int status;
        @SerializedName("info")
        public String info;
        @SerializedName("extInfo")
        public String extInfo;
        public Response response;

        public static class Response{
            public String appPath;
            public int updateFlag;
        }
    }

    public interface UpdateStepListener{
        void checkSuc(UpdateModel updateModel);
        void downloadSuc();
        void confirmInstall();
        void cancelInstall();
    }
}
