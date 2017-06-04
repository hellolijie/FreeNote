package cn.mune.jerry.abc.db.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cn.mune.jerry.abc.db.model.BaseModel;

/**
 * Created by lijie on 17/4/8.
 */

@Table(name = "new_world_model")
public class NewWordModel extends BaseModel {
    @Column(name = "new_word_id")
    public String newWordId;

    @Column(name = "new_word")
    public String newWord;

    @Column(name = "translate")
    public String translate;

    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;
}
