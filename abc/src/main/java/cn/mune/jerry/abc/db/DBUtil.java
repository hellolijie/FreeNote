package cn.mune.jerry.abc.db;

import android.content.Context;

import cn.mune.jerry.baselibrary.utils.CommonUtil;

/**
 * Created by lijie on 17/4/15.
 */

public class DBUtil {
    /**
     * 生成数据库id
     * @param context
     * @return
     */
    public static String createID(Context context, String tableName){
        return CommonUtil.md5(tableName + CommonUtil.getDeviceId(context) + System.currentTimeMillis() + Math.random());
    }
}
