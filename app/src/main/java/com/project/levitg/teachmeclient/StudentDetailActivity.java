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


public class StudentDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextLogin;
    private String _Student_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_student_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Student_Id = "";
        Intent intent = getIntent();
        _Student_Id = intent.getStringExtra("student_Id");
        if (_Student_Id != null && !_Student_Id.isEmpty()) {
            Call<Student> call = restService.getService().getStudentById(_Student_Id);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    Student student = response.body();
                    editTextName.setText(student.getFullName());
                    editTextEmail.setText(student.getEmail());
                    editTextLogin.setText(student.getLogin());
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(StudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteStudentById(_Student_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(StudentDetailActivity.this, "Student Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(StudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Student_Id == null || _Student_Id.isEmpty()) {
                // No Id -> new student

                Student student = new Student();
                student.setEmail(editTextEmail.getText().toString());
                student.setFullName(editTextName.getText().toString());
                student.setLogin(editTextLogin.getText().toString());

                //TODO: Add UI fields
                student.setPassword("hardcoded Password");

                student.setCompletedOursesCount(0);
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                student.setRegisterDate(DATE_FORMAT.format(Calendar.getInstance().getTime()));


                restService.getService().addStudent(student).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if (response.code() == 201) {
                            Toast.makeText(StudentDetailActivity.this, "New Student Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(StudentDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(StudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing student

                Call<Student> call = restService.getService().getStudentById(_Student_Id);
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Student existingStudent = response.body();
                        existingStudent.setEmail(editTextEmail.getText().toString());
                        existingStudent.setFullName(editTextName.getText().toString());
                        existingStudent.setLogin(editTextLogin.getText().toString());

                        // Use Backend API to update student
                        restService.getService().updateStudentById(_Student_Id, existingStudent).enqueue(new Callback<Student>() {
                            @Override
                            public void onResponse(Call<Student> call, Response<Student> response) {
                                Toast.makeText(StudentDetailActivity.this, response.body().getFullName() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Student> call, Throwable t) {
                                Toast.makeText(StudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(StudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}