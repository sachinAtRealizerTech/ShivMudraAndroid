package com.realizertech.shivmudra.apicalls;

import com.google.gson.Gson;
import com.realizertech.shivmudra.utils.SaveData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by anh on 02/Feb/2017.
 */

public class ApiService {

    //public static final String API_END_POINT = "http://45.35.4.250/Shiv-MudraDevAPI/";
   public static final String API_END_POINT = "http://45.35.4.250/Shiv-MudraAPI/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient
                    .Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json; charset=utf-8")
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer " + SaveData.getToken())
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = httpClient
                    .readTimeout(62, TimeUnit.SECONDS)
                    .connectTimeout(62, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_END_POINT)
                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))

                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiServiceImpl getService() {
        return getRetrofit().create(ApiServiceImpl.class);
    }
}
