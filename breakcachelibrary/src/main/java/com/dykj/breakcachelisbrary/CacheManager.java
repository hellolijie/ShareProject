package com.dykj.breakcachelisbrary;

import com.dykj.breakcachelisbrary.cache.CacheModel;
import com.dykj.breakcachelisbrary.cache.CacheStorage;

/**
 * Created by lijie on 2017/9/12.
 */

public class CacheManager {
    private static CacheManager instance;

    private CacheStorage cacheStorage;
    private Puller puller;

    public static CacheManager getInstance(){
        if (instance != null){
            instance = new CacheManager();
        }
        return instance;
    }

    /**
     * 初始化
     * @param cacheStorage
     */
    public void init(CacheStorage cacheStorage, Operator operator){
        this.cacheStorage = cacheStorage;
        puller = new Puller(cacheStorage, operator);
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
