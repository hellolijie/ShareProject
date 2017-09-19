package com.dykj.breakcachelisbrary.cache;

/**
 * Created by lijie on 2017/9/12.
 */

public interface CacheStorage {
    /**
     * 放入缓存
     * @param cacheModel
     * @return  返回该条数据在缓存中的唯一标识
     */
    String pushData(CacheModel cacheModel);

    /**
     * 从缓存中获取一条数据
     * @return
     */
    CacheModel pullData();

    /**
     * 获取对应类型的数据
     * @param type
     * @return
     */
    CacheModel pullDataByType(String type);

    /**
     * 根据数据唯一标识从缓存中获取数据
     * @param cacheId  放入时生成的唯一标识
     * @return
     */
    CacheModel pullDataByCacheId(String cacheId);

    /**
     * 设置数据状态
     * @param cacheId
     * @param result
     */
    void setResult(String cacheId, int result);

    /**
     * 清除缓存
     * @param cacheId
     */
    void removeData(String cacheId);

    /**
     * 清除已完成缓存
     */
    void clearSucData();

    /**
     * 清除所有缓存
     */
    void clear();
}
