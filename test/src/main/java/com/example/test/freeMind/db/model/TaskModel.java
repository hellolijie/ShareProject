package com.example.test.freeMind.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by lijie on 2017/6/7.
 */
@Table(name = "task_model")
public class TaskModel extends Model{

    @Column(name = "task_id")
    public String taskId;

    @Column(name = "mind_node_id")
    public String mindNodeId;

    @Column(name = "consumption")
    public long consumption;

    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;
}
