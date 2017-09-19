package com.dykj.breakcachelisbrary.cache;

/**
 * Created by lijie on 2017/9/12.
 */

public class CacheModel {
    public static final int STATE_WAITTING = 0;     //等待操作
    public static final int STATE_SUC = 1;          //操作成功
    public static final int STATE_ERROR = -1;       //操作失败

    public String cacheId;          //唯一标识
    public String type;             //操作类型
    public String cacheContent;     //操作数据
    public int result;              //操作结果
    public int priority;            //优先级

    public CacheModel(String type, String cacheContent, int priority){
        this.type = type;
        this.cacheContent = cacheContent;
        this.priority = priority;
    }
}
