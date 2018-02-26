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

    private BackendService backendService;
    private retrofit2.Retrofit restAdapter;

    public RestClient() {
        InitializeBackendService(null);
    }

    public RestClient(final String authToken) {
        InitializeBackendService(authToken);
    }

    private void InitializeBackendService(final String authToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor);


        if (authToken != null) {
            // add authorization token header to every Http request
            AuthenticationInterceptor authenticationInterceptor =
                    new AuthenticationInterceptor(authToken);

            if (!okHttpClient.interceptors().contains(authenticationInterceptor)) {
                okHttpClient.addInterceptor(authenticationInterceptor);
            }
        }

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        restAdapter = new retrofit2.Retrofit.Builder()
                .baseUrl(GlobalConstants.BACKEND_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        backendService = restAdapter.create(BackendService.class);
    }

    public BackendService getService() {
        return backendService;
    }


}

