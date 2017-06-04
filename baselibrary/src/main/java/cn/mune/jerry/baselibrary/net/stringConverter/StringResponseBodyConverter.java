package cn.mune.jerry.baselibrary.net.stringConverter;

/**
 * Created by lijie on 2017/4/10.
 */

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by 耿 on 2016/9/6.
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}
