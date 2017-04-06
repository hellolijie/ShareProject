package com.example.test.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by lijie on 2017/4/6.
 */

@Table(name = "note_model")
public class NoteModel extends BaseModel {
    @Column(name = "note_id")
    public String noteId;

    @Column(name = "sentence")
    public String sentence;

    @Column(name = "words_indexes")
    public String wordsIndexes;

    @Column(name = "tag_id")
    public String tagId;

    @Column(name = "weight")
    public int weight;

}
