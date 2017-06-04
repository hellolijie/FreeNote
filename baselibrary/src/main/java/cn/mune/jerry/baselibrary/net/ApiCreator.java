package cn.mune.jerry.baselibrary.net;

import java.util.concurrent.TimeUnit;

import cn.mune.jerry.baselibrary.net.stringConverter.StringConverterFactory;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lijie on 2017/3/28.
 */

public class ApiCreator {
    private static final int DEFAULT_TIMEOUT = 10;

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static<T> T create(Class<T> classOfApi, String baseURl){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseURl)
//                .addConverterFactory(gsonConverterFactory)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();


        return retrofit.create(classOfApi);
    }
}
