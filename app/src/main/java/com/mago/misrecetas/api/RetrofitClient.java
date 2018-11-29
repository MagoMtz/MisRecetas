package com.mago.misrecetas.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jorgemartinez on 26/11/18.
 */
public class RetrofitClient {
    private Retrofit retrofit;
    private final static String BASE_URL = "https://food2fork.com/api/";

    public RetrofitClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(loggerClient())
                .build();
    }

    public APIServiceRetrofit getRecipeService() {
        return this.retrofit.create(APIServiceRetrofit.class);
    }

    private OkHttpClient loggerClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        return httpClient.build();
    }

}
