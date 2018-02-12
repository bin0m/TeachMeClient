package com.project.levitg.teachmeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentCourseDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister;
    Button btnDelete;
    Button btnClose;
    EditText editTextStudentId;
    EditText editTextCourseId;
    private String _StudentCourse_Id;
    private String _User_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_studentcourse_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextStudentId = (EditText) findViewById(R.id.editTextStudentId);
        editTextCourseId = (EditText) findViewById(R.id.editTextCourseId);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _StudentCourse_Id = "";
        _User_Id = "";
        Intent intent = getIntent();
        _StudentCourse_Id = intent.getStringExtra("studentcourse_Id");
        _User_Id = intent.getStringExtra("user_Id");


        if (_StudentCourse_Id != null && !_StudentCourse_Id.isEmpty()) {
            Call<StudentCourse> call = restService.getService().getStudentCourseById(_StudentCourse_Id);
            call.enqueue(new Callback<StudentCourse>() {
                @Override
                public void onResponse(Call<StudentCourse> call, Response<StudentCourse> response) {
                    StudentCourse studentcourse = response.body();
                    editTextStudentId.setText(studentcourse.getUserId());
                    editTextCourseId.setText(studentcourse.getCourseId());
                    _User_Id = studentcourse.getUserId();
                }

                @Override
                public void onFailure(Call<StudentCourse> call, Throwable t) {
                    Toast.makeText(StudentCourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        } else {
            editTextStudentId.setText(_User_Id);
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteStudentCourseById(_StudentCourse_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(StudentCourseDetailActivity.this, "StudentCourse Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(StudentCourseDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(StudentCourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_StudentCourse_Id == null || _StudentCourse_Id.isEmpty()) {
                // No Id -> new studentcourse

                StudentCourse studentcourse = new StudentCourse();
                studentcourse.setUserId(editTextStudentId.getText().toString());
                studentcourse.setCourseId(editTextCourseId.getText().toString());

                restService.getService().addStudentCourse(studentcourse).enqueue(new Callback<StudentCourse>() {
                    @Override
                    public void onResponse(Call<StudentCourse> call, Response<StudentCourse> response) {
                        if (response.code() == 201) {
                            Toast.makeText(StudentCourseDetailActivity.this, "New StudentCourse Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(StudentCourseDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentCourse> call, Throwable t) {
                        Toast.makeText(StudentCourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing studentcourse

                Call<StudentCourse> call = restService.getService().getStudentCourseById(_StudentCourse_Id);
                call.enqueue(new Callback<StudentCourse>() {
                    @Override
                    public void onResponse(Call<StudentCourse> call, Response<StudentCourse> response) {
                        StudentCourse existingStudentCourse = response.body();
                        existingStudentCourse.setUserId(editTextStudentId.getText().toString());
                        existingStudentCourse.setCourseId(editTextCourseId.getText().toString());

                        // Use Backend API to update studentcourse
                        restService.getService().updateStudentCourseById(_StudentCourse_Id, existingStudentCourse).enqueue(new Callback<StudentCourse>() {
                            @Override
                            public void onResponse(Call<StudentCourse> call, Response<StudentCourse> response) {
                                Toast.makeText(StudentCourseDetailActivity.this, "StudentCourse updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<StudentCourse> call, Throwable t) {
                                Toast.makeText(StudentCourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<StudentCourse> call, Throwable t) {
                        Toast.makeText(StudentCourseDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}