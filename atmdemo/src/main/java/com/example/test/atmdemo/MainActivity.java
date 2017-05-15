package com.example.test.atmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.test.atmdemo.atmControl.serialPort.SerialPortController;

import com.example.test.atmdemo.atmControl.ATMController;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "SerialPort";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void find(View view){
        String [] devices = SerialPortController.getInstance().getSerialPortFinder().getAllDevicesPath();
        Log.d(TAG, String.valueOf(devices.length));
        for (String device : devices){
            Log.d(TAG, device);
        }

    }

    public void openSerialPort(View view){
        ATMController.getInstance().initSerialPort();

        ATMController.getInstance().setAtmListener(new ATMController.ATMListener() {
            @Override
            public void onReceived(final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.tv_received)).append(data + "\n");
                    }
                });

            }
        });
    }

    public void sendData(View view){
        String sendData = ((TextView)findViewById(R.id.et_send_data)).getText().toString();

        if (!TextUtils.isEmpty(sendData)){
            ATMController.getInstance().sendCommand(sendData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ATMController.getInstance().closeSerialPort();
    }
}
