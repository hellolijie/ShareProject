package com.example.test.db;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by lijie on 2017/4/6.
 */
@Table(name = "tag_model")
public class TagModel extends BaseModel {
    @Column(name = "tag_id")
    public String tagId;

    @Column(name = "tag_name")
    public String tagName;
}
