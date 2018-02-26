package com.project.levitg.teachmeclient;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by bin0m on 26.02.2018.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("X-ZUMO-AUTH", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
