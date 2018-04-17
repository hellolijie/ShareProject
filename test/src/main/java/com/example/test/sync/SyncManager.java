package com.example.test.sync;

/**
 * Created by lijie on 2017/6/8.
 */

public class SyncManager {
    private static SyncManager instance;

    private SyncManager(){}

    public synchronized static SyncManager getInstance(){
        if (instance == null){
            instance = new SyncManager();
        }

        return instance;
    }

    public void startSync(){

    }
}
