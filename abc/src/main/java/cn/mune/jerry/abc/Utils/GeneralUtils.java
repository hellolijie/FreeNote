package cn.mune.jerry.abc.Utils;

import cn.mune.jerry.abc.Constans;
import cn.mune.jerry.baselibrary.utils.CommonUtil;

/**
 * Created by lijie on 17/4/15.
 */

public class GeneralUtils {
    /**
     * 生成百度翻译签名
     * @param salt
     * @param q
     * @return
     */
    public static String createBaiduTranslateSign(int salt, String q){
        String sign = Constans.BAIDU_TRANSLATE_APPID + q + salt + Constans.BAIDU_TRANSLATE_KEY;
        return CommonUtil.md5(sign);
    }
}
