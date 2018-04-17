package com.example.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.test.ClientAIDLCallback;
import com.example.test.TestAIDLManager;

/**
 * Created by lijie on 2017/8/11.
 */

public class AidlTestService extends Service {
    private RemoteCallbackList<ClientAIDLCallback> remoteCallbackList;

    @Override
    public void onCreate() {
        super.onCreate();
        remoteCallbackList = new RemoteCallbackList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return testAIDLManager;
    }

    private TestAIDLManager.Stub testAIDLManager = new TestAIDLManager.Stub() {
        @Override
        public void registerClientCallback(ClientAIDLCallback clientAIDLCallback) throws RemoteException {
            remoteCallbackList.register(clientAIDLCallback);

            clientAIDLCallback.notifyClient("remote-callback");
        }

        @Override
        public void trigBroadcastCallback() throws RemoteException {
            broadcastCallback();
        }
    };

    private void broadcastCallback(){
        final int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++){
            try {
                remoteCallbackList.getBroadcastItem(i).notifyClient("remote-bread-callback");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }
}
