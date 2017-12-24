package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete, btnDeleteFull;
    Button btnClose, btnViewSections;
    EditText editTextName;
    EditText editTextDays;
    private String _Course_Id, _Teacher_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_course_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDeleteFull = (Button) findViewById(R.id.btnDeleteFull);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewSections = (Button) findViewById(R.id.btnViewSections);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDays = (EditText) findViewById(R.id.editTextDays);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDeleteFull.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewSections.setOnClickListener(this);


        _Course_Id = "";
        _Teacher_Id = "";
        Intent intent = getIntent();
        _Course_Id = intent.getStringExtra("course_Id");
        _Teacher_Id = intent.getStringExtra("teacher_Id");

        if (_Course_Id != null && !_Course_Id.isEmpty()) {
            Call<Course> call = restService.getService().getCourseById(_Course_Id);
            call.enqueue(new Callback<Course>() {
                @Override
                public void onResponse(Call<Course> call, Response<Course> response) {
                    Course course = response.body();
                    editTextName.setText(course.getName());
                    editTextDays.setText(String.valueOf(course.getDays()));
                }

                @Override
                public void onFailure(Call<Course> call, Throwable t) {
                    Toast.makeText(CourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteCourseById(_Course_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(CourseDetailActivity.this, "Course Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CourseDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (findViewById(R.id.btnDeleteFull) == v) {
            Call<Void> call = restService.getService().deleteCourseAndChildrenById(_Course_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Toast.makeText(CourseDetailActivity.this, "Course Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CourseDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewSections)) {
            Intent intent = new Intent(this, SectionsActivity.class);
            intent.putExtra("course_Id", _Course_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Course_Id == null || _Course_Id.isEmpty()) {
                // No Id -> new course

                Course course = new Course();
                course.setName(editTextName.getText().toString());
                course.setDays(Integer.parseInt(editTextDays.getText().toString()));
                course.setUserId(_Teacher_Id);

                restService.getService().addCourse(course).enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> call, Response<Course> response) {
                        if (response.code() == 201) {
                            Toast.makeText(CourseDetailActivity.this, "New Course Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CourseDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Course> call, Throwable t) {
                        Toast.makeText(CourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing course

                Call<Course> call = restService.getService().getCourseById(_Course_Id);
                call.enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> call, Response<Course> response) {
                        Course existingCourse = response.body();
                        existingCourse.setName(editTextName.getText().toString());
                        existingCourse.setDays(Integer.parseInt(editTextDays.getText().toString()));

                        // Use Backend API to update course
                        restService.getService().updateCourseById(_Course_Id, existingCourse).enqueue(new Callback<Course>() {
                            @Override
                            public void onResponse(Call<Course> call, Response<Course> response) {
                                Toast.makeText(CourseDetailActivity.this, response.body().getName() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Course> call, Throwable t) {
                                Toast.makeText(CourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Course> call, Throwable t) {
                        Toast.makeText(CourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}