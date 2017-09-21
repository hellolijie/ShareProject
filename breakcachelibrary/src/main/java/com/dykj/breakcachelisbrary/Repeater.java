package com.dykj.breakcachelisbrary;

import com.dykj.breakcachelisbrary.cache.CacheModel;
import com.dykj.breakcachelisbrary.cache.CacheStorage;

/**
 * Created by lijie on 2017/9/21.
 */

public class Repeater {

    private CacheStorage cacheStorage;
    private Puller puller;

    public Repeater(CacheStorage cacheStorage, Operator operator){
        this.cacheStorage = cacheStorage;
        puller = new Puller(cacheStorage, operator);
    }

    public Repeater(CacheStorage cacheStorage, Operator operator, int pullPeriod){
        this.cacheStorage = cacheStorage;
        puller = new Puller(cacheStorage, operator, pullPeriod);
    }

    /**
     * 开始
     */
    public void start(){
        puller.startPull();
    }

    /**
     * 结束
     */
    public void finish(){
        puller.stopPull();
    }

    /**
     * 推入缓存队列
     * @param cacheModel
     */
    public void push(CacheModel cacheModel){
        cacheStorage.pushData(cacheModel);
    }

}
