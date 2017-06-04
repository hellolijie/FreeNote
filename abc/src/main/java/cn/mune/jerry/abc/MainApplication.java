package cn.mune.jerry.abc;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.mune.jerry.abc.db.model.TagModel;
import io.paperdb.Paper;

/**
 * Created by lijie on 17/3/19.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Paper.init(this);

        initDBData();

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + Constans.XUNFEI_APPID);
    }

    /**
     * 初始化数据库原始数据
     */
    private void initDBData(){
        if (!Paper.book().exist("initDBData")){
            //初始化数据库
            TagModel defaultTag = new TagModel();
            defaultTag.tagId = Constans.DEFAULT_TAG_ID;
            defaultTag.tagName = "默认";
            defaultTag.createTime = System.currentTimeMillis();
            defaultTag.updateTime = System.currentTimeMillis();
            defaultTag.save();
            Paper.book().write("initDBData", true);
        }
    }
}
