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

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/student
    @GET("api/v1.0/user")
    Call<List<User>> getUser();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get user record base on ID
    @GET("api/v1.0/user/{id}")
    Call<User> getUserById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user
    //Add user record and post content in HTTP request BODY
    @POST("api/v1.0/user")
    Call<User> addUser(@Body User user);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete user record base on ID
    @DELETE("api/v1.0/user/{id}")
    Call<Void> deleteUserById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update user record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/user/{id}")
    Call<User> updateUserById(@Path("id") String id, @Body User user);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pattern
    @GET("api/v1.0/pattern")
    Call<List<Pattern>> getPattern();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get pattern record base on ID
    @GET("api/v1.0/pattern/{id}")
    Call<Pattern> getPatternById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pattern
    //Add pattern record and post content in HTTP request BODY
    @POST("api/v1.0/pattern")
    Call<Pattern> addPattern(@Body Pattern pattern);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete pattern record base on ID
    @DELETE("api/v1.0/pattern/{id}")
    Call<Void> deletePatternById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pattern/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update pattern record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/pattern/{id}")
    Call<Pattern> updatePatternById(@Path("id") String id, @Body Pattern pattern);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course
    @GET("api/v1.0/course")
    Call<List<Course>> getCourse();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course/b04070c377c24b7295fda8ec8484dca5
    //Get course record base on ID
    @GET("api/v1.0/course/{id}")
    Call<Course> getCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course
    //Add course record and post content in HTTP request BODY
    @POST("api/v1.0/course")
    Call<Course> addCourse(@Body Course course);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course/b04070c377c24b7295fda8ec8484dca5
    //Delete course record base on ID
    @DELETE("api/v1.0/course/{id}")
    Call<Void> deleteCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course/b04070c377c24b7295fda8ec8484dca5
    //Update course record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/course/{id}")
    Call<Course> updateCourseById(@Path("id") String id, @Body Course course);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section
    @GET("api/v1.0/section")
    Call<List<Section>> getSection();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section/b04070c377c24b7295fda8ec8484dca5
    //Get section record base on ID
    @GET("api/v1.0/section/{id}")
    Call<Section> getSectionById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section
    //Add section record and post content in HTTP request BODY
    @POST("api/v1.0/section")
    Call<Section> addSection(@Body Section section);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section/b04070c377c24b7295fda8ec8484dca5
    //Delete section record base on ID
    @DELETE("api/v1.0/section/{id}")
    Call<Void> deleteSectionById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section/b04070c377c24b7295fda8ec8484dca5
    //Update section record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/section/{id}")
    Call<Section> updateSectionById(@Path("id") String id, @Body Section section);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson
    @GET("api/v1.0/lesson")
    Call<List<Lesson>> getLesson();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson/b04070c377c24b7295fda8ec8484dca5
    //Get lesson record base on ID
    @GET("api/v1.0/lesson/{id}")
    Call<Lesson> getLessonById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson
    //Add lesson record and post content in HTTP request BODY
    @POST("api/v1.0/lesson")
    Call<Lesson> addLesson(@Body Lesson lesson);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson/b04070c377c24b7295fda8ec8484dca5
    //Delete lesson record base on ID
    @DELETE("api/v1.0/lesson/{id}")
    Call<Void> deleteLessonById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson/b04070c377c24b7295fda8ec8484dca5
    //Update lesson record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/lesson/{id}")
    Call<Lesson> updateLessonById(@Path("id") String id, @Body Lesson lesson);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/patternstudent
    @GET("api/v1.0/patternstudent")
    Call<List<PatternStudent>> getPatternStudent();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/patternstudent/b04070c377c24b7295fda8ec8484dca5
    //Get patternstudent record base on ID
    @GET("api/v1.0/patternstudent/{id}")
    Call<PatternStudent> getPatternStudentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/patternstudent
    //Add patternstudent record and post content in HTTP request BODY
    @POST("api/v1.0/patternstudent")
    Call<PatternStudent> addPatternStudent(@Body PatternStudent patternstudent);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/patternstudent/b04070c377c24b7295fda8ec8484dca5
    //Delete patternstudent record base on ID
    @DELETE("api/v1.0/patternstudent/{id}")
    Call<Void> deletePatternStudentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/patternstudent/b04070c377c24b7295fda8ec8484dca5
    //Update patternstudent record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/patternstudent/{id}")
    Call<PatternStudent> updatePatternStudentById(@Path("id") String id, @Body PatternStudent patternstudent);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment
    @GET("api/v1.0/comment")
    Call<List<Comment>> getComment();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment?$expand=user,commentRatings&$filter=patternId eq 'a3dce27b9f0b425a859f1bdb47b35af1'
    //Get comments records base on query
    @GET("api/v1.0/comment?$expand=user,commentRatings")
    Call<List<Comment>> getCommentByQuery(@Query("$filter") String filter);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment/b04070c377c24b7295fda8ec8484dca5$expand=user,commentRatings
    //Get comment record base on ID
    @GET("api/v1.0/comment/{id}?$expand=user,commentRatings")
    Call<Comment> getCommentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment
    //Add comment record and post content in HTTP request BODY
    @POST("api/v1.0/comment")
    Call<Comment> addComment(@Body Comment comment);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment/b04070c377c24b7295fda8ec8484dca5
    //Delete comment record base on ID
    @DELETE("api/v1.0/comment/{id}")
    Call<Void> deleteCommentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment/b04070c377c24b7295fda8ec8484dca5
    //Update comment record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/comment/{id}")
    Call<Comment> updateCommentById(@Path("id") String id, @Body Comment comment);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating
    @GET("api/v1.0/commentrating")
    Call<List<CommentRating>> getCommentRating();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating?$expand=user&$filter=patternId eq 'a3dce27b9f0b425a859f1bdb47b35af1'
    //Get comments records base on query
    @GET("api/v1.0/commentrating?$expand=user")
    Call<List<CommentRating>> getCommentRatingByQuery(@Query("$filter") String filter);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating/b04070c377c24b7295fda8ec8484dca5
    //Get commentrating record base on ID
    @GET("api/v1.0/commentrating/{id}?$expand=user")
    Call<CommentRating> getCommentRatingById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating
    //Add commentrating record and post content in HTTP request BODY
    @POST("api/v1.0/commentrating")
    Call<CommentRating> addCommentRating(@Body CommentRating commentrating);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating/b04070c377c24b7295fda8ec8484dca5
    //Delete commentrating record base on ID
    @DELETE("api/v1.0/commentrating/{id}")
    Call<Void> deleteCommentRatingById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating/b04070c377c24b7295fda8ec8484dca5
    //Update commentrating record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/commentrating/{id}")
    Call<CommentRating> updateCommentRatingById(@Path("id") String id, @Body CommentRating commentrating);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/courses/b04070c377c24b7295fda8ec8484dca5
    //Delete section record including all its children( lessons)
    @DELETE("api/v1.0/courses/{id}")
    Call<Void> deleteCourseAndChildrenById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/sections/b04070c377c24b7295fda8ec8484dca5
    //Delete section record including all its children( lessons)
    @DELETE("api/v1.0/sections/{id}")
    Call<Void> deleteSectionAndChildrenById(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/users/95777a45afc241dd87f3cae3274fe0af/courses
    //Get courses records by parent User id
    @GET("api/v1.0/users/{id}/courses")
    Call<List<Course>> getCoursesByUserId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/courses/95777a45afc241dd87f3cae3274fe0af/sections
    //Get sections records by parent Course id
    @GET("api/v1.0/courses/{id}/sections")
    Call<List<Section>> getSectionsByCourseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/sections/95777a45afc241dd87f3cae3274fe0af/lessons
    //Get lessons records by parent Section id
    @GET("api/v1.0/sections/{id}/lessons")
    Call<List<Lesson>> getLessonsBySectionId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/lessons/95777a45afc241dd87f3cae3274fe0af/patterns
    //Get patterns records by parent Lesson id
    @GET("api/v1.0/lessons/{id}/patterns")
    Call<List<Pattern>> getPatternsByLessonId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/patterns/95777a45afc241dd87f3cae3274fe0af/patternstudents
    //Get patterns records by parent Pattern id
    @GET("api/v1.0/patterns/{id}/patternstudents")
    Call<List<PatternStudent>> getPatternStudentsByPatternId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/patterns/95777a45afc241dd87f3cae3274fe0af/comments
    //Get patterns records by parent Pattern id
    @GET("api/v1.0/patterns/{id}/comments")
    Call<List<Comment>> getCommentsByPatternId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/comments/95777a45afc241dd87f3cae3274fe0af/commentratings
    //Get commentratings records by parent Comment id
    @GET("api/v1.0/comments/{id}/commentratings")
    Call<List<CommentRating>> getCommentRatingsByCommentId(@Path("id") String id);
}

