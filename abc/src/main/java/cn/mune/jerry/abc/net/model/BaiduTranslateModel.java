package cn.mune.jerry.abc.net.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lijie on 17/4/15.
 */

public class BaiduTranslateModel {
    @SerializedName("frome")
    public String from;
    @SerializedName("to")
    public String to;
    @SerializedName("trans_result")
    public List<TranslateResult> transResult;

    public static class TranslateResult{
        public String src;
        public String dst;
    }
}
