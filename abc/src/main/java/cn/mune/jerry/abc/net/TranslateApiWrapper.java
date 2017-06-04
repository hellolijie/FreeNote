package cn.mune.jerry.abc.net;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 翻译API
 * Created by lijie on 2017/4/6.
 */

public interface TranslateApiWrapper {

    @FormUrlEncoded
    @POST("api/trans/vip/translate")
    Observable<Response<String>> baiduTranslate(@Field("q") String q,
                                                @Field("from") String from,
                                                @Field("to") String to,
                                                @Field("appid") String appid,
                                                @Field("salt") String salt,
                                                @Field("sign") String sign);
}
