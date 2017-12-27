package com.project.levitg.teachmeclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Greg L on 04.12.2017.
 */

public class RestClient {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    private static final String BackendURL = "http://teachmeserv.azurewebsites.net/";
    private BackendService backendService;
    private retrofit2.Retrofit restAdapter;

    public RestClient() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        restAdapter = new retrofit2.Retrofit.Builder()
                .baseUrl(BackendURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        backendService = restAdapter.create(BackendService.class);
    }

    public BackendService getService() {
        return backendService;
    }
}
