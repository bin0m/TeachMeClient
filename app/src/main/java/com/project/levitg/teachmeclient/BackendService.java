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

    //i.e. http://teachmebackend.azurewebsites.net/tables/pattern
    @GET("pattern")
    Call<List<Pattern>> getPattern();

    //i.e. http://teachmebackend.azurewebsites.net/tables/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get pattern record base on ID
    @GET("pattern/{id}")
    Call<Pattern> getPatternById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/pattern
    //Add pattern record and post content in HTTP request BODY
    @POST("pattern")
    Call<Pattern> addPattern(@Body Pattern pattern);

    //i.e. http://teachmebackend.azurewebsites.net/tables/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete pattern record base on ID
    @DELETE("pattern/{id}")
    Call<Void> deletePatternById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update pattern record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("pattern/{id}")
    Call<Pattern> updatePatternById(@Path("id") String id, @Body Pattern pattern);

    //i.e. http://teachmebackend.azurewebsites.net/tables/course
    @GET("course")
    Call<List<Course>> getCourse();

    //i.e. http://teachmebackend.azurewebsites.net/tables/course/b04070c377c24b7295fda8ec8484dca5
    //Get course record base on ID
    @GET("course/{id}")
    Call<Course> getCourseById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/course
    //Add course record and post content in HTTP request BODY
    @POST("course")
    Call<Course> addCourse(@Body Course course);

    //i.e. http://teachmebackend.azurewebsites.net/tables/course/b04070c377c24b7295fda8ec8484dca5
    //Delete course record base on ID
    @DELETE("course/{id}")
    Call<Void> deleteCourseById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/course/b04070c377c24b7295fda8ec8484dca5
    //Update course record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("course/{id}")
    Call<Course> updateCourseById(@Path("id") String id, @Body Course course);

    //i.e. http://teachmebackend.azurewebsites.net/tables/section
    @GET("section")
    Call<List<Section>> getSection();

    //i.e. http://teachmebackend.azurewebsites.net/tables/section/b04070c377c24b7295fda8ec8484dca5
    //Get section record base on ID
    @GET("section/{id}")
    Call<Section> getSectionById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/section
    //Add section record and post content in HTTP request BODY
    @POST("section")
    Call<Section> addSection(@Body Section section);

    //i.e. http://teachmebackend.azurewebsites.net/tables/section/b04070c377c24b7295fda8ec8484dca5
    //Delete section record base on ID
    @DELETE("section/{id}")
    Call<Void> deleteSectionById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/section/b04070c377c24b7295fda8ec8484dca5
    //Update section record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("section/{id}")
    Call<Section> updateSectionById(@Path("id") String id, @Body Section section);

    //i.e. http://teachmebackend.azurewebsites.net/tables/lesson
    @GET("lesson")
    Call<List<Lesson>> getLesson();

    //i.e. http://teachmebackend.azurewebsites.net/tables/lesson/b04070c377c24b7295fda8ec8484dca5
    //Get lesson record base on ID
    @GET("lesson/{id}")
    Call<Lesson> getLessonById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/lesson
    //Add lesson record and post content in HTTP request BODY
    @POST("lesson")
    Call<Lesson> addLesson(@Body Lesson lesson);

    //i.e. http://teachmebackend.azurewebsites.net/tables/lesson/b04070c377c24b7295fda8ec8484dca5
    //Delete lesson record base on ID
    @DELETE("lesson/{id}")
    Call<Void> deleteLessonById(@Path("id") String id);

    //i.e. http://teachmebackend.azurewebsites.net/tables/lesson/b04070c377c24b7295fda8ec8484dca5
    //Update lesson record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("lesson/{id}")
    Call<Lesson> updateLessonById(@Path("id") String id, @Body Lesson lesson);
}

