package com.example.callcategories.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    public static final String BASE_URL = "https://validation.appetizers.in/";
    private static Retrofit mRetrofit;
    Gson gson=new GsonBuilder().serializeNulls().create();


    public static Retrofit getApiClient() {

        if (mRetrofit==null){

            mRetrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }


        return mRetrofit;
    }

    static Interceptor interceptor = chain -> {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("Authorization", Credentials.basic("ck_d5a10c7f59d92b6defed8da69096c1bf95ccfbe9", "cs_a0840eb6bc47952051611c0fa5e42b7854d7d223"))
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    };

    public static OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();
}

