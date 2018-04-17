package com.example.test.freeMind.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by lijie on 2017/6/7.
 */

@Table(name = "mind_node_model")
public class MindNodeModel extends Model{

    @Column(name = "mind_node_id")
    public String mindNodeId;

    @Column(name = "content")
    public String content;

    @Column(name = "parent_node_id")
    public String parentNodeId;

    @Column(name = "root_node_id")
    public String rootNodeId;

    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;

}
