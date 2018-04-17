package com.example.test.printerdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;

import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpUtils;
import com.gprinter.command.LabelCommand;
import com.gprinter.io.GpDevice;
import com.gprinter.io.PortParameters;
import com.gprinter.service.GpPrintService;

import java.util.Vector;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * Created by lijie on 2017/8/14.
 */

public class PrinterHelper {
    private Context context;
    private GpService gpService;

    private int printerId;

    /**
     * 打印机状态广播接收器
     */
    private BroadcastReceiver printerStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GpCom.ACTION_CONNECT_STATUS)) {//端口连接状态
                int type = intent.getIntExtra(GpPrintService.CONNECT_STATUS, 0);
                Log.d("print", type + "--");
                if (type == GpDevice.STATE_NONE){
                    //打印机连接断开 重新连接
                }
            }
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            gpService = GpService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public PrinterHelper(Context context){
        this.context = context;
    }

    /**
     * 初始化打印机
     */
    public void initGpPrinter(){
        bindGpService();
        registerConnBroadcast();
    }

    /**
     * 结束打印机
     */
    public void finishGpPrinter(){
        unRegisterConnBroadcast();
        disconnectGPrinter();
        unbindGpService();
    }

    public GpCom.ERROR_CODE printTest(){
        EscCommand esc = new EscCommand();

        esc.addPrintAndFeedLines((byte) 2);
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
        esc.addSelectPrintModes(EscCommand.FONT.FONTB, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF,
                EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);

        esc.addText("****************************\n");
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
        esc.addText("易爱米粉:这是测试页\n", "GB2312");//门店名称
        esc.addText("****************************\n");
        esc.addPrintAndFeedLines((byte) 2);

        return sendReceipt(esc);
    }

    /**
     * 发送打印命令
     * @param esc
     */
    public GpCom.ERROR_CODE sendReceipt(EscCommand esc){
        Vector<Byte> data = esc.getCommand();
        byte[] bytes = com.gprinter.command.GpUtils.ByteTo_byte(data);
        String escCmd = Base64.encodeToString(bytes, Base64.DEFAULT);

        try {
            int result = gpService.sendEscCommand(printerId, escCmd);
            GpCom.ERROR_CODE resultCode = GpCom.ERROR_CODE.values()[result];
            Log.d("print", String.valueOf(resultCode));
            return resultCode;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return GpCom.ERROR_CODE.FAILED;
    }

    /**
     * 开钱箱
     */
    private void openCashBox(int printerId) {
        EscCommand esc = new EscCommand();
        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 60, (byte) 40);
        Vector<Byte> data = esc.getCommand();

        byte[] bytes = GpUtils.ByteTo_byte(data);
        String commandStr = Base64.encodeToString(bytes, Base64.DEFAULT);

        try {
            int rel = gpService.sendEscCommand(printerId, commandStr);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                gpService.sendEscCommand(printerId, commandStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定打印机服务
     */
    private void bindGpService(){
        Intent intent = new Intent(context, GpPrintService.class);

        context.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    /**
     * 解除打印机服务绑定
     */
    private void unbindGpService(){
        context.unbindService(connection);
    }

    /**
     * 连接打印机
     */
    public GpCom.ERROR_CODE connectGPrinter(int printerId, String mac){

        try {
            this.printerId = printerId;
            int result = gpService.openPort(printerId,
                    PortParameters.BLUETOOTH,
                    mac,
                    0);
            GpCom.ERROR_CODE resultCode = GpCom.ERROR_CODE.values()[result];
            Log.d("print", String.valueOf(resultCode));
            return resultCode;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return GpCom.ERROR_CODE.FAILED;
    }

    /**
     * 注册打印机状态广播
     */
    private void registerConnBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GpCom.ACTION_CONNECT_STATUS);
        filter.addAction(GpCom.ACTION_DEVICE_REAL_STATUS);
        filter.addAction(GpCom.ACTION_RECEIPT_RESPONSE);
        context.registerReceiver(printerStatusReceiver, filter);
    }

    /**
     * 取消注册打印机状态广播
     */
    private void unRegisterConnBroadcast(){
        context.unregisterReceiver(printerStatusReceiver);
    }

    /**
     * 关闭打印机连接
     */
    private void disconnectGPrinter(){
        try {
            gpService.closePort(printerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
