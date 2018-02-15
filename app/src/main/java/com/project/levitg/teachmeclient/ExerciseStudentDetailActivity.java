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


public class ExerciseStudentDetailActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose;
    EditText editTextStudentId;
    EditText editTextStudentAnswer;
    CheckBox checkBoxIsDone;
    private String _ExerciseStudent_Id, _Exercise_Id;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_exercisestudent_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextStudentId = (EditText) findViewById(R.id.editTextStudentId);
        editTextStudentAnswer = (EditText) findViewById(R.id.editTextStudentAnswer);
        checkBoxIsDone = (CheckBox) findViewById(R.id.checkBoxIsDone);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        checkBoxIsDone.setOnClickListener(this);


        _ExerciseStudent_Id = "";
        _Exercise_Id = "";
        Intent intent = getIntent();
        _ExerciseStudent_Id = intent.getStringExtra("exercisestudent_Id");
        _Exercise_Id = intent.getStringExtra("exercise_Id");

        if (_ExerciseStudent_Id != null && !_ExerciseStudent_Id.isEmpty()) {
            Call<ExerciseStudent> call = restService.getService().getExerciseStudentById(_ExerciseStudent_Id);
            call.enqueue(new Callback<ExerciseStudent>() {
                @Override
                public void onResponse(Call<ExerciseStudent> call, Response<ExerciseStudent> response) {
                    ExerciseStudent exercisestudent = response.body();
                    editTextStudentId.setText(exercisestudent.getUserId());
                    editTextStudentAnswer.setText(exercisestudent.getStudentAnswer());
                    checkBoxIsDone.setChecked(exercisestudent.getIsDone());
                    _Exercise_Id = exercisestudent.getExerciseId();
                }

                @Override
                public void onFailure(Call<ExerciseStudent> call, Throwable t) {
                    Toast.makeText(ExerciseStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restService.getService().deleteExerciseStudentById(_ExerciseStudent_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(ExerciseStudentDetailActivity.this, "ExerciseStudent Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ExerciseStudentDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ExerciseStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_ExerciseStudent_Id == null || _ExerciseStudent_Id.isEmpty()) {
                // No Id -> new exercisestudent

                ExerciseStudent exercisestudent = new ExerciseStudent();
                exercisestudent.setUserId(editTextStudentId.getText().toString());
                exercisestudent.setStudentAnswer(editTextStudentAnswer.getText().toString());
                exercisestudent.setIsDone(checkBoxIsDone.isChecked());
                exercisestudent.setExerciseId(_Exercise_Id);

                restService.getService().addExerciseStudent(exercisestudent).enqueue(new Callback<ExerciseStudent>() {
                    @Override
                    public void onResponse(Call<ExerciseStudent> call, Response<ExerciseStudent> response) {
                        if (response.code() == 201) {
                            Toast.makeText(ExerciseStudentDetailActivity.this, "New ExerciseStudent Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExerciseStudentDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ExerciseStudent> call, Throwable t) {
                        Toast.makeText(ExerciseStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing exercisestudent

                Call<ExerciseStudent> call = restService.getService().getExerciseStudentById(_ExerciseStudent_Id);
                call.enqueue(new Callback<ExerciseStudent>() {
                    @Override
                    public void onResponse(Call<ExerciseStudent> call, Response<ExerciseStudent> response) {
                        ExerciseStudent existingExerciseStudent = response.body();
                        existingExerciseStudent.setUserId(editTextStudentId.getText().toString());
                        existingExerciseStudent.setStudentAnswer(editTextStudentAnswer.getText().toString());
                        existingExerciseStudent.setIsDone(checkBoxIsDone.isChecked());
                        existingExerciseStudent.setExerciseId(_Exercise_Id);

                        // Use Backend API to update exercisestudent
                        restService.getService().updateExerciseStudentById(_ExerciseStudent_Id, existingExerciseStudent).enqueue(new Callback<ExerciseStudent>() {
                            @Override
                            public void onResponse(Call<ExerciseStudent> call, Response<ExerciseStudent> response) {
                                Toast.makeText(ExerciseStudentDetailActivity.this, response.body().getUserId() + " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<ExerciseStudent> call, Throwable t) {
                                Toast.makeText(ExerciseStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ExerciseStudent> call, Throwable t) {
                        Toast.makeText(ExerciseStudentDetailActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}