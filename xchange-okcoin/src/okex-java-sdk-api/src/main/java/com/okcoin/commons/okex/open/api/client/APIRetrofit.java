package com.okcoin.commons.okex.open.api.client;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * API Retrofit
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/8 15:40
 */
public class APIRetrofit {


    private APIConfiguration config;
    private OkHttpClient client;

    public APIRetrofit(APIConfiguration config, OkHttpClient client) {
        this.config = config;
        this.client = client;
    }

    /**
     * Get a retrofit 2 object.
     */
    public Retrofit retrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(this.client);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.baseUrl(this.config.getEndpoint());
        return builder.build();
    }
}
