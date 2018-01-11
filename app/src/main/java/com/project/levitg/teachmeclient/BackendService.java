package com.project.levitg.teachmeclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Greg L on 04.12.2017.
 */


public interface BackendService {

    //i.e. http://teachmeserv.azurewebsites.net/tables/student
    @GET("tables/user")
    Call<List<User>> getUser();

    //i.e. http://teachmeserv.azurewebsites.net/tables/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get user record base on ID
    @GET("tables/user/{id}")
    Call<User> getUserById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/user
    //Add user record and post content in HTTP request BODY
    @POST("tables/user")
    Call<User> addUser(@Body User user);

    //i.e. http://teachmeserv.azurewebsites.net/tables/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete user record base on ID
    @DELETE("tables/user/{id}")
    Call<Void> deleteUserById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update user record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/user/{id}")
    Call<User> updateUserById(@Path("id") String id, @Body User user);

    //i.e. http://teachmeserv.azurewebsites.net/tables/pattern
    @GET("tables/pattern")
    Call<List<Pattern>> getPattern();

    //i.e. http://teachmeserv.azurewebsites.net/tables/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get pattern record base on ID
    @GET("tables/pattern/{id}")
    Call<Pattern> getPatternById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/pattern
    //Add pattern record and post content in HTTP request BODY
    @POST("tables/pattern")
    Call<Pattern> addPattern(@Body Pattern pattern);

    //i.e. http://teachmeserv.azurewebsites.net/tables/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete pattern record base on ID
    @DELETE("tables/pattern/{id}")
    Call<Void> deletePatternById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update pattern record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/pattern/{id}")
    Call<Pattern> updatePatternById(@Path("id") String id, @Body Pattern pattern);

    //i.e. http://teachmeserv.azurewebsites.net/tables/course
    @GET("tables/course")
    Call<List<Course>> getCourse();

    //i.e. http://teachmeserv.azurewebsites.net/tables/course/b04070c377c24b7295fda8ec8484dca5
    //Get course record base on ID
    @GET("tables/course/{id}")
    Call<Course> getCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/course
    //Add course record and post content in HTTP request BODY
    @POST("tables/course")
    Call<Course> addCourse(@Body Course course);

    //i.e. http://teachmeserv.azurewebsites.net/tables/course/b04070c377c24b7295fda8ec8484dca5
    //Delete course record base on ID
    @DELETE("tables/course/{id}")
    Call<Void> deleteCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/course/b04070c377c24b7295fda8ec8484dca5
    //Update course record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/course/{id}")
    Call<Course> updateCourseById(@Path("id") String id, @Body Course course);

    //i.e. http://teachmeserv.azurewebsites.net/tables/section
    @GET("tables/section")
    Call<List<Section>> getSection();

    //i.e. http://teachmeserv.azurewebsites.net/tables/section/b04070c377c24b7295fda8ec8484dca5
    //Get section record base on ID
    @GET("tables/section/{id}")
    Call<Section> getSectionById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/section
    //Add section record and post content in HTTP request BODY
    @POST("tables/section")
    Call<Section> addSection(@Body Section section);

    //i.e. http://teachmeserv.azurewebsites.net/tables/section/b04070c377c24b7295fda8ec8484dca5
    //Delete section record base on ID
    @DELETE("tables/section/{id}")
    Call<Void> deleteSectionById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/section/b04070c377c24b7295fda8ec8484dca5
    //Update section record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/section/{id}")
    Call<Section> updateSectionById(@Path("id") String id, @Body Section section);

    //i.e. http://teachmeserv.azurewebsites.net/tables/lesson
    @GET("tables/lesson")
    Call<List<Lesson>> getLesson();

    //i.e. http://teachmeserv.azurewebsites.net/tables/lesson/b04070c377c24b7295fda8ec8484dca5
    //Get lesson record base on ID
    @GET("tables/lesson/{id}")
    Call<Lesson> getLessonById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/lesson
    //Add lesson record and post content in HTTP request BODY
    @POST("tables/lesson")
    Call<Lesson> addLesson(@Body Lesson lesson);

    //i.e. http://teachmeserv.azurewebsites.net/tables/lesson/b04070c377c24b7295fda8ec8484dca5
    //Delete lesson record base on ID
    @DELETE("tables/lesson/{id}")
    Call<Void> deleteLessonById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/lesson/b04070c377c24b7295fda8ec8484dca5
    //Update lesson record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/lesson/{id}")
    Call<Lesson> updateLessonById(@Path("id") String id, @Body Lesson lesson);

    //i.e. http://teachmeserv.azurewebsites.net/tables/patternstudent
    @GET("tables/patternstudent")
    Call<List<PatternStudent>> getPatternStudent();

    //i.e. http://teachmeserv.azurewebsites.net/tables/patternstudent/b04070c377c24b7295fda8ec8484dca5
    //Get patternstudent record base on ID
    @GET("tables/patternstudent/{id}")
    Call<PatternStudent> getPatternStudentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/patternstudent
    //Add patternstudent record and post content in HTTP request BODY
    @POST("tables/patternstudent")
    Call<PatternStudent> addPatternStudent(@Body PatternStudent patternstudent);

    //i.e. http://teachmeserv.azurewebsites.net/tables/patternstudent/b04070c377c24b7295fda8ec8484dca5
    //Delete patternstudent record base on ID
    @DELETE("tables/patternstudent/{id}")
    Call<Void> deletePatternStudentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/patternstudent/b04070c377c24b7295fda8ec8484dca5
    //Update patternstudent record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/patternstudent/{id}")
    Call<PatternStudent> updatePatternStudentById(@Path("id") String id, @Body PatternStudent patternstudent);

    //i.e. http://teachmeserv.azurewebsites.net/tables/comment
    @GET("tables/comment")
    Call<List<Comment>> getComment();

    //i.e. http://teachmeserv.azurewebsites.net/tables/comment?$expand=user&$filter=patternId eq 'a3dce27b9f0b425a859f1bdb47b35af1'
    //Get comments records base on query
    @GET("tables/comment?$expand=user")
    Call<List<Comment>> getCommentByQuery(@Query("$filter") String filter);

    //i.e. http://teachmeserv.azurewebsites.net/tables/comment/b04070c377c24b7295fda8ec8484dca5
    //Get comment record base on ID
    @GET("tables/comment/{id}?$expand=user")
    Call<Comment> getCommentById(@Path("id") String id, @Query("$filter") String filter);

    //i.e. http://teachmeserv.azurewebsites.net/tables/comment
    //Add comment record and post content in HTTP request BODY
    @POST("tables/comment")
    Call<Comment> addComment(@Body Comment comment);

    //i.e. http://teachmeserv.azurewebsites.net/tables/comment/b04070c377c24b7295fda8ec8484dca5
    //Delete comment record base on ID
    @DELETE("tables/comment/{id}")
    Call<Void> deleteCommentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/tables/comment/b04070c377c24b7295fda8ec8484dca5
    //Update comment record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("tables/comment/{id}")
    Call<Comment> updateCommentById(@Path("id") String id, @Body Comment comment);

    //i.e. http://teachmeserv.azurewebsites.net/api/courses/b04070c377c24b7295fda8ec8484dca5
    //Delete section record including all its children( lessons)
    @DELETE("api/courses/{id}")
    Call<Void> deleteCourseAndChildrenById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/sections/b04070c377c24b7295fda8ec8484dca5
    //Delete section record including all its children( lessons)
    @DELETE("api/sections/{id}")
    Call<Void> deleteSectionAndChildrenById(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/users/95777a45afc241dd87f3cae3274fe0af/courses
    //Get courses records by parent User id
    @GET("api/users/{id}/courses")
    Call<List<Course>> getCoursesByUserId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/courses/95777a45afc241dd87f3cae3274fe0af/sections
    //Get sections records by parent Course id
    @GET("api/courses/{id}/sections")
    Call<List<Section>> getSectionsByCourseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/sections/95777a45afc241dd87f3cae3274fe0af/lessons
    //Get lessons records by parent Section id
    @GET("api/sections/{id}/lessons")
    Call<List<Lesson>> getLessonsBySectionId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/lessons/95777a45afc241dd87f3cae3274fe0af/patterns
    //Get patterns records by parent Lesson id
    @GET("api/lessons/{id}/patterns")
    Call<List<Pattern>> getPatternsByLessonId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/patterns/95777a45afc241dd87f3cae3274fe0af/patternstudents
    //Get patterns records by parent Pattern id
    @GET("api/patterns/{id}/patternstudents")
    Call<List<PatternStudent>> getPatternStudentsByPatternId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/patterns/95777a45afc241dd87f3cae3274fe0af/comments
    //Get patterns records by parent Pattern id
    @GET("api/patterns/{id}/comments")
    Call<List<Comment>> getCommentsByPatternId(@Path("id") String id);
}

