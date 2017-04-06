package com.example.test.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by lijie on 2017/4/6.
 */

public class BaseModel extends Model {
    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;
}
