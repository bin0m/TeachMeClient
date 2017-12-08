package com.project.levitg.teachmeclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Greg L on 04.12.2017.
 */


public interface BackendService {

    //i.e. http://teachmebackend.azurewebsites.net/tables/student
    @GET("user")
    Call<List<User>> getUser();

    //i.e. http://teachmebackend.azurewebsites.net/tables/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get user record base on ID
    @GET("user/{id}")
    Call<User> getUserById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/user
    //Add user record and post content in HTTP request BODY
    @POST("user")
    Call<User> addUser(@Body User user);

    //i.e. http://teachmebackend.azurewebsites.net/tables/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete user record base on ID
    @DELETE("user/{id}")
    Call<Void> deleteUserById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update user record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("user/{id}")
    Call<User> updateUserById(@Path("id") String id, @Body User user);
}

