package cn.mune.jerry.abc.db.dao;

import android.content.Context;

import com.activeandroid.query.Select;

import java.util.List;

import cn.mune.jerry.abc.db.DBUtil;
import cn.mune.jerry.abc.db.model.TagModel;

/**
 * Created by lijie on 17/4/16.
 */

public class TagDao {
    /**
     * 获取所有tag
     * @return
     */
    public static List<TagModel> getAllTag(){
        return new Select().from(TagModel.class).execute();
    }

    /**
     * 保存标签
     * @param context
     * @param tagName
     */
    public static TagModel saveTag(Context context, String tagName){
        TagModel tagModel = new TagModel();
        tagModel.tagName = tagName;
        tagModel.createTime = System.currentTimeMillis();
        tagModel.updateTime = tagModel.createTime;
        tagModel.tagId = DBUtil.createID(context, "tag");
        tagModel.save();

        return tagModel;
    }
}
