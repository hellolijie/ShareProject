package com.example.test.printerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gprinter.command.GpCom;

public class MainActivity extends AppCompatActivity {
    private PrinterHelper printerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printerHelper = new PrinterHelper(this);
        printerHelper.initGpPrinter();
    }

    private void display(String text){
        ((TextView)findViewById(R.id.tv_display)).append(text + "\n");
    }

    public void connectPrinter(View view){
        GpCom.ERROR_CODE result = printerHelper.connectGPrinter(0, "DC:0D:30:22:66:62");
        display("连接:" + result);
    }

    public void printTest(View view){
        GpCom.ERROR_CODE result = printerHelper.printTest();
        display("打印测试：" + result);
    }

    public void scanBluetooth(View view){
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivity(intent);
    }
}
