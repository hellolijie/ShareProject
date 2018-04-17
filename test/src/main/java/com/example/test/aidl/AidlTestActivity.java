package com.example.test.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.test.ClientAIDLCallback;
import com.example.test.R;
import com.example.test.TestAIDLManager;

/**
 * Created by lijie on 2017/8/11.
 */

public class AidlTestActivity extends AppCompatActivity {

    private TestAIDLManager testAIDLManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);

        bind();
    }

    private void bind(){
        Intent intent = new Intent(this, AidlTestService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                testAIDLManager = TestAIDLManager.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    public void onRegister(View view){
        try {
            testAIDLManager.registerClientCallback(new ClientAIDLCallback.Stub() {
                @Override
                public void notifyClient(String data) throws RemoteException {
                    ((TextView)findViewById(R.id.tv_display)).append(data + "\n");
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onTrigger(View view){
        try {
            testAIDLManager.trigBroadcastCallback();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
