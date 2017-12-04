package com.project.levitg.teachmeclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Greg L on 04.12.2017.
 */


public interface BackendService {

    //i.e. http://teachmebackend.azurewebsites.net/tables/student
    @GET("student")
    Call<List<Student>> getStudent();

    //i.e. http://teachmebackend.azurewebsites.net/tables/student/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get student record base on ID
    @GET("student/{id}")
    Call<Student> getStudentById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/student
    //Add student record and post content in HTTP request BODY
    @POST("student")
    Call<Student> addStudent(@Body Student student);

    //i.e. http://teachmebackend.azurewebsites.net/tables/student/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete student record base on ID
    @DELETE("student/{id}")
    Call<Void> deleteStudentById(@Path("id") String id);
}

