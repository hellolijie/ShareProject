package com.dykj.breakcachelisbrary;

import com.dykj.breakcachelisbrary.cache.CacheStorage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijie on 2017/9/12.
 */

public class Puller {
    private static final int PULL_PERIOD = 300;

    private CacheStorage cacheStorage;
    private ScheduledExecutorService pullService;
    private Operator operator;

    public Puller(CacheStorage cacheStorage, Operator operator){
        this.cacheStorage = cacheStorage;
        this.operator = operator;
        pullService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * 开始轮询
     */
    public void startPull(){
        pullService.shutdownNow();

        pullService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                operator.onHandle(cacheStorage.pullData());
            }
        }, PULL_PERIOD, PULL_PERIOD, TimeUnit.MILLISECONDS);
    }

    /**
     * 结束
     */
    public void stopPull(){
        pullService.shutdownNow();
    }
}
