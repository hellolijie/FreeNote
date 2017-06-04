package cn.mune.jerry.abc.db.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cn.mune.jerry.abc.db.model.BaseModel;

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

    @Column(name = "play_count")
    public int playCount;

    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;

}
