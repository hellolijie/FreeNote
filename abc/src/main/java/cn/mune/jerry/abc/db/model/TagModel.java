package cn.mune.jerry.abc.db.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cn.mune.jerry.abc.db.model.BaseModel;

/**
 * Created by lijie on 2017/4/6.
 */
@Table(name = "tag_model")
public class TagModel extends BaseModel {
    @Column(name = "tag_id")
    public String tagId;

    @Column(name = "tag_name")
    public String tagName;

    @Column(name = "create_time")
    public long createTime;

    @Column(name = "update_time")
    public long updateTime;
}
