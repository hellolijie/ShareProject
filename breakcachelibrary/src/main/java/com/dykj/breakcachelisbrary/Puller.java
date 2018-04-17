package com.dykj.breakcachelisbrary;

import com.dykj.breakcachelisbrary.cache.CacheStorage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijie on 2017/9/12.
 */

public class Puller {
    private static final int PULL_PERIOD = 300;

    private CacheStorage cacheStorage;
    private ScheduledExecutorService pullService;
    private ScheduledFuture scheduledFuture;
    private Operator operator;

    private int pullPeriod = PULL_PERIOD;

    public Puller(CacheStorage cacheStorage, Operator operator){
        this(cacheStorage, operator, PULL_PERIOD);
    }

    public Puller(CacheStorage cacheStorage, Operator operator, int pullPeriod){
        this.cacheStorage = cacheStorage;
        this.operator = operator;
        pullService = Executors.newSingleThreadScheduledExecutor();
        this.pullPeriod = pullPeriod;
    }

    /**
     * 开始轮询
     */
    public void startPull(){
        try {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }

            scheduledFuture = pullService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        operator.onHandle(cacheStorage.pullData());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }, pullPeriod, pullPeriod, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 结束
     */
    public void stopPull(){
        try {
            scheduledFuture.cancel(true);
            pullService.shutdownNow();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
