package com.dykj.breakcachelisbrary;

import com.dykj.breakcachelisbrary.cache.CacheModel;

/**
 * Created by lijie on 2017/9/12.
 */

public interface Operator {
    void onHandle(CacheModel cacheModel);
}
