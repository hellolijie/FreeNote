package cn.mune.jerry.abc.db.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cn.mune.jerry.abc.db.model.BaseModel;

/**
 * Created by lijie on 17/4/8.
 */

@Table(name = "note_new_word_model")
public class NoteNewWordModel extends BaseModel {
    @Column(name = "note_new_word_id")
    public String noteNewWordId;

    @Column(name = "note_id")
    public String noteId;

    @Column(name = "new_word_id")
    public String newWordId;

    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;
}
