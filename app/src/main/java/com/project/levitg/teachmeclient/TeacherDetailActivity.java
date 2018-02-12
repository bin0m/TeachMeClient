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


public class TeacherDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose, btnViewCourses;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextLogin;
    private String _Teacher_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_teacher_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewCourses = (Button) findViewById(R.id.btnViewCourses);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewCourses.setOnClickListener(this);


        _Teacher_Id = "";
        Intent intent = getIntent();
        _Teacher_Id = intent.getStringExtra("teacher_Id");
        if (_Teacher_Id != null && !_Teacher_Id.isEmpty()) {
            Call<User> call = restService.getService().getUserById(_Teacher_Id);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    editTextName.setText(user.getFullName());
                    editTextEmail.setText(user.getEmail());
                    editTextLogin.setText(user.getLogin());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(TeacherDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteUserById(_Teacher_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(TeacherDetailActivity.this, "Teacher Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(TeacherDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewCourses)) {
            Intent intent = new Intent(this, CoursesActivity.class);
            intent.putExtra("teacher_Id", _Teacher_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Teacher_Id == null || _Teacher_Id.isEmpty()) {
                // No Id -> new user

                User user = new User();
                user.setEmail(editTextEmail.getText().toString());
                user.setFullName(editTextName.getText().toString());
                user.setLogin(editTextLogin.getText().toString());

                //TODO: Add UI fields
                user.setPassword("hardcoded Password");
                user.setUserRole(GlobalConstants.UserRole.STUDENT);

                user.setCompletedCoursesCount(0);
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                user.setRegisterDate(DATE_FORMAT.format(Calendar.getInstance().getTime()));


                restService.getService().addUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 201) {
                            Toast.makeText(TeacherDetailActivity.this, "New Teacher Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TeacherDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(TeacherDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing user

                Call<User> call = restService.getService().getUserById(_Teacher_Id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User existingUser = response.body();
                        existingUser.setEmail(editTextEmail.getText().toString());
                        existingUser.setFullName(editTextName.getText().toString());
                        existingUser.setLogin(editTextLogin.getText().toString());

                        // Use Backend API to update user
                        restService.getService().updateUserById(_Teacher_Id, existingUser).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(TeacherDetailActivity.this, response.body().getFullName() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(TeacherDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(TeacherDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}